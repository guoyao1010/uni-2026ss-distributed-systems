# Welcome to your group's exercise repository

You can find all information regarding the exercises in our
[VC course](https://vc.uni-bamberg.de/course/view.php?id=81307).
This repository already contains Java class definitions for some classes that
you have to implement over the course of this semester.

Additionally, it has also been initialized as a [Gradle](https://gradle.org/)
project, allowing you to build the project by using the shell command:

```sh
gradle build
```

Resulting class files are located in the directory called `build/classes`,
meaning you can run a JAVA class that implements the main method by adding the
sub-directory `build/classes/java/main` to your classpath.

# Run and test Social Media RPC frameworks on VMs
This section describes how to run and test the Mini Social Network project on the assigned VMs.
The project supports two RPC types: Java RMI and gRPC. Both versions use the shared abstract client class 'DSGSocialMediaClient'.

We run server on 5129 VM and run client on 5140 VM.

**The server must be started before the client.**

## 1. Connect to the VMs
Open two terminal windows. 

One for server and the other for client. Connect to the two VMs.

## 2. Go to project folder, update and build the project **on both VMs**
Run following commands one by one **on both VMs**:
```
ls
cd group-7
git pull
gradle clean build
```

## 3. Run the RMI version
### Server side: h5129 VM
Start the RMI server with following command and keep this terminal running:
```
gradle -q runRmiServer --console=plain
```
### Client side: h5140 VM
Start the abstract social media client with following command and test rmi version in this terminal:
```
gradle -q runSocialMediaClient --console=plain
```
When the client asks for the client type, please type in `rmi`.

## 4. Run gRPC version
### Server side: h5129 VM
Start the gRPC server with following command and keep this terminal running.
```
gradle -q runGrpcServer --console=plain
```
### Client side: h5140 VM
Start the shared abstract client with following command and test in this terminal.
```
gradle -q runSocialMediaClient --console=plain
```
When the client asks for the client type, please enter `grpc`.

## 5. Available commands for both RMI and gRPC version
```
createprofile <userID> <firstname> <lastname> <bio>
getprofile <userID>
updateprofile<userID> <firstname> <lastname> <bio>
publishpost <userID> <content>
deletepost <userID> <postId>
followstudent <followerID> <targetID>
unfollowstudent <followerID> <targetID>
getfeed <userID>
getposts<userID>
exit
```

## 6. Run the Benchmark client for evaluation
### Run the RMI benchmark
Start the RMI server on the server VM:
```
gradle -q runRmiServer --console=plain
```
Start the benchmark client in rmi version on the client VM:
```
gradle -q runBenchmark --args="rmi" --console=plain
```
### Run the gRPC benchmark
Start the gRPC server on the server VM:
```
gradle -q runGrpcServer --console=plain
```
Start the benchmark client in gRPC version on client VM:
```
gradle -q runBenchmark --args="grpc" --console=plain
```




