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
java -Djava.rmi.server.hostname=h5129.pi.uni-bamberg.de -cp build/classes/java/main dsg.rmi.socialmedia.DSGSocialMediaServer
```
### Client side: h5140 VM
Start the abstract social media client with following command and test in this terminal:
```
java -cp build/classes/java/main dsg.DSGSocialMediaClient
```
When the client asks for the client type, please type in "rmi".

## 4. Run gRPC version
### Server side: h5129 VM
Start the gRPC server with following command and keep this terminal running.
```
java -cp "$(gradle -q printRuntimeClasspath)" dsg.grpc.SocialNetworkServer
```
### Client side: h5140 VM
Start the shared abstract client with following command and test in this terminal.
```
java -cp "$(gradle -q printRuntimeClasspath)" dsg.DSGSocialMediaClient
```
When the client asks for the client type, please enter "grpc".

## Available commands for both RMI and gRPC version
```
createprofile <userId> <firstname> <lastname> <bio>
getprofile <userId>
updateprofile <userId> <firstname> <lastname> <bio>
publishpost <userId> <content>
deletepost <userId> <postId>
followstudent <followerId> <targetId>
unfollowstudent <followerId> <targetId>
getfeed <userId>
exit
```


