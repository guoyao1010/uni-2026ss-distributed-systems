package dsg;

import dsg.grpc.SocialNetworkClient;
import dsg.rmi.socialmedia.DSGSocialMediaRMIClient;

import java.util.List;

public class DSGSocialMediaBenchmarkClient {

    private static final int WARMUP = 1000;
    private static final int RUNS = 10000;

    public static void main(String[] args) throws Exception{
        if (args.length < 2) {
            System.out.println("Please run the benchmark again using commands with two parameters: <rmi|grpc> <hostname>.");
            return;
        }
        String type = args[0];
        String host = args[1];
        DSGSocialMediaClient client;

        if (type.equals("rmi")) {
            client = new DSGSocialMediaRMIClient();
            client.init(host,1099);
            System.out.println("Benchmark is using the RMI.");
        } else if (type.equals("grpc")) {
            client = new SocialNetworkClient();
            client.init(host, 8080);
            System.out.println("Benchmark is using the gRPC.");
        } else {
            System.out.println("Please choose the client type in command: rmi or grpc.");
            return;
        }

        measureCreateProfile(client, type);

        String userID = "001" + System.currentTimeMillis();
        client.createProfile(userID, "Ada", "Musterfrau", "test");

        String targetID = "002" + System.currentTimeMillis();
        client.createProfile(targetID, "Bob", "Mustermann", "test");

        measureGetProfile(client, userID, type);
        measureUpdateProfile(client, userID, type);
        measureDeletePost(client, userID, type);
        measureFollowStudent(client, userID, targetID, type);
        measureUnfollowStudent(client, userID, targetID, type);

        client.followStudent(userID, targetID);
        client.publishPost(targetID, "For getfeed");
        measureGetFeed(client, userID, type);

        // Evaluate functions with increasing parameter and return value sizes
        measurePublishPostWithPayload (client, type, 10);
        measurePublishPostWithPayload (client, type, 100);
        measurePublishPostWithPayload (client, type, 1000);

        measureGetPostsWithPayload(client,type, 1);
        measureGetPostsWithPayload(client,type, 10);
        measureGetPostsWithPayload(client,type, 100);

        client.shutdown();
    }

    // CreateProfile
    private static void measureCreateProfile(DSGSocialMediaClient client, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        // Make sure each benchmark call uses a new userID
        long baseID = System.currentTimeMillis();

        for (int i = 0; i < WARMUP; i++){
            String newUserID = "warmup" + baseID + "_"+i;
            client.createProfile(newUserID, "NewAda", "NewMusterfrau", "warmup");
        }

        for (int i= 0; i < RUNS; i++) {
            String newUserID = "run" +baseID + "_" +i;
            long start = System.nanoTime();
            client.createProfile(newUserID, "NewAda", "NewMusterfrau", "run");
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        // Head of output table, in csv format
        System.out.println("framework,method,avg_latency_ms,min_latency_ms,max_latency_ms");
        System.out.println(framework +",createProfile," + avgMs + "," + minMs +"," + maxMs);
    }

    // GetProfile
    private static void measureGetProfile(DSGSocialMediaClient client, String userID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        for (int i = 0; i < WARMUP; i++){
            client.getProfile(userID);
        }

        for (int i= 0; i < RUNS; i++) {
            long start = System.nanoTime();

            // end to end latency
            client.getProfile(userID);

            long end = System.nanoTime();
            long time = end - start;
            total = total + time;

            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        // Average, minimal and maximal time in milliseconds. 1 millisecond = 1000000 nanoseconds.
        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",getProfile," + avgMs + "," + minMs +"," + maxMs);
    }

    // UpdateProfile
    private static void measureUpdateProfile(DSGSocialMediaClient client, String userID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        for (int i = 0; i < WARMUP; i++){
            client.updateProfile(userID, "NewAda", "NewMusterfrau", "warmup");
        }

        for (int i= 0; i < RUNS; i++) {
            long start = System.nanoTime();
            client.updateProfile(userID, "NewAda", "NewMusterfrau", "run");
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",updateProfile," + avgMs + "," + minMs +"," + maxMs);
    }

    private static void measureDeletePost (DSGSocialMediaClient client, String userID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        for (int i = 0; i < WARMUP; i++){
            client.publishPost(userID, "warmup_content");
            List<String> userposts = client.getPosts(userID);
            String postID = userposts.get(0).split(" ", 2)[0];
            client.deletePost(userID, postID);
        }

        for (int i= 0; i < RUNS; i++) {
            client.publishPost(userID, "run_content");
            List<String> userposts = client.getPosts(userID);
            String postID = userposts.get(0).split(" ", 2)[0];
            long start = System.nanoTime();
            client.deletePost(userID, postID);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",deletePost," + avgMs + "," + minMs +"," + maxMs);
    }

    private static void measureFollowStudent(DSGSocialMediaClient client, String userID, String targetID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        client.followStudent(userID, targetID);

        for (int i = 0; i < WARMUP; i++){
            client.unfollowStudent(userID, targetID);
            client.followStudent(userID, targetID);
        }

        for (int i= 0; i < RUNS; i++) {
            client.unfollowStudent(userID, targetID);
            long start = System.nanoTime();
            client.followStudent(userID, targetID);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",followStudent," + avgMs + "," + minMs +"," + maxMs);
    }

    private static void measureUnfollowStudent(DSGSocialMediaClient client, String userID, String targetID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        for (int i = 0; i < WARMUP; i++){
            client.followStudent(userID,targetID);
            client.unfollowStudent(userID, targetID);
        }

        for (int i= 0; i < RUNS; i++) {
            client.followStudent(userID, targetID);
            long start = System.nanoTime();
            client.unfollowStudent(userID, targetID);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",unfollowStudent," + avgMs + "," + minMs +"," + maxMs);
    }

    // GetFeed: fixed payload, currently evaluated with 1 post
    private static void measureGetFeed(DSGSocialMediaClient client, String userID, String framework) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        for (int i = 0; i < WARMUP; i++){
            client.getFeed(userID);
        }

        for (int i= 0; i < RUNS; i++) {
            long start = System.nanoTime();
            client.getFeed(userID);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",getFeed," + avgMs + "," + minMs +"," + maxMs);
    }

    // Evaluate two methods with different payload: publishPost, getPosts
    // For creating different length of post content (for publishpost)
    private static String createContent (int contentlength) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i <contentlength; i++) {
            content.append("c");
        }
        return content.toString();
    }

    // PublishPost
    private static void measurePublishPostWithPayload(DSGSocialMediaClient client, String framework, int contentlength) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        String publishpostuserID = "publishpost_user_payload" + contentlength + System.currentTimeMillis();
        client.createProfile(publishpostuserID, "firstname", "lastname", "publishpotwithpayload");
        String content = createContent(contentlength);

        for (int i = 0; i < WARMUP; i++){
            client.publishPost(publishpostuserID, content);
        }

        for (int i= 0; i < RUNS; i++) {
            long start = System.nanoTime();
            client.publishPost(publishpostuserID, content);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",publishPost_" + contentlength + "_contentlength," + avgMs + "," + minMs +"," + maxMs);
    }

    // GetPosts
    private static void measureGetPostsWithPayload(DSGSocialMediaClient client, String framework, int postCounter) throws Exception {
        long min = Long.MAX_VALUE;
        long max = 0;
        long total = 0;

        String getpostsuserID = "getposts_user_payload_" + postCounter + System.currentTimeMillis();
        client.createProfile(getpostsuserID, "firstname", "lastname", "getpostswithpayload");

        for (int i = 0; i < postCounter; i++) {
            client.publishPost(getpostsuserID, "getpostscontent"+i);
        }

        for (int i = 0; i < WARMUP; i++){
            client.getPosts(getpostsuserID);
        }

        for (int i= 0; i < RUNS; i++) {
            long start = System.nanoTime();
            client.getPosts(getpostsuserID);
            long end = System.nanoTime();
            long time = end - start;
            total = total + time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avgMs = total/(double) RUNS / 1000000.0;
        double minMs = min/ 1000000.0;
        double maxMs = max/1000000.0;

        System.out.println(framework +",getPosts_" + postCounter + "_posts," + avgMs + "," + minMs +"," + maxMs);
    }

}
