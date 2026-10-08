# Distributed Social Media System with Java RMI and gRPC

This project was developed by **Yao Guo** as part of a two-person team during the Summer Semester 2026 at the University of Bamberg. It was completed within the university project **DSG-Proj-B/M: Remote Procedure Calls** at the Chair of Distributed Systems (Lehrstuhl für Verteilte Systeme).

The project implements a small distributed social media system using two RPC technologies, **Java RMI** and **gRPC with Protocol Buffers**. Both implementations provide the same core social media functionality. The client and server were deployed on separate university virtual machines for functional testing and performance benchmarking.

## Project Information

- **University:** University of Bamberg
- **Chair:** Lehrstuhl für Verteilte Systeme
- **Project:** DSG-Proj-B/M: Remote Procedure Calls
- **Period:** Summer Semester 2026 (15 April – 15 July 2026)
- **Supervision:** Prof. Dr.-Ing. habil. Tobias Distler and Harald Böhm
- **Team (Group 7):** Yao Guo and Osama Abood Alsaho
- **Technologies:** Java, Java RMI, gRPC, Protocol Buffers, Gradle

## Project Scope

The project focuses on implementing and evaluating remote procedure calls in practice. For our project scope, the same social media application was implemented using **Java RMI** and **gRPC**, deployed in a distributed environment, and evaluated through performance benchmarking.

## Build

The project uses Java 25 and the Gradle Wrapper:

```sh
./gradlew clean build
```

## Run and test Social Media RPC frameworks on VMs

This section documents how to run and test the Mini Social Network project on the assigned VMs.
The project supports two RPC types: Java RMI and gRPC. Both versions use the shared abstract client class 'DSGSocialMediaClient'.

We run **server on 5129 VM** and run **client on 5140 VM**.

(In the original university deployment, the server ran on VM h5129 and the client ran on VM h5140. These university-provided VMs may no longer be accessible. For local testing, use the localhost commands provided below.)

**The server must be started before the client.**

### 1. Connect to the VMs

Open two terminal windows. One for server and the other for client. Connect to the two VMs.

### 2. Go to project folder, update and build the project **on both VMs**

After cloning and updating the repository, navigate to the project directory and build the project on **both virtuel machines**:

```
./gradlew clean build
```

### 3. Run the RMI version

#### Server side: h5129 VM

Start the RMI server with following command and keep this terminal running:

```
./gradlew -q runRmiServer --console=plain
```

For local running: `./gradlew -q runRmiServer -PrmiHost=localhost --console=plain`

#### Client side: h5140 VM

Start the abstract social media client with following command and test rmi version in this terminal:

```
./gradlew -q runSocialMediaClient --args="rmi h5129.pi.uni-bamberg.de" --console=plain
```

For local running: `./gradlew -q runSocialMediaClient --args="rmi localhost" --console=plain`

### 4. Run gRPC version

#### Server side: h5129 VM

Start the gRPC server with following command and keep this terminal running.

```
./gradlew -q runGrpcServer --console=plain
```

For local running: use same command as in VM.

#### Client side: h5140 VM

Start the shared abstract client with following command and test in this terminal.

```
./gradlew -q runSocialMediaClient --args="grpc h5129.pi.uni-bamberg.de" --console=plain
```

For local running: `./gradlew -q runSocialMediaClient --args="grpc localhost" --console=plain`

### 5. Run the Benchmark client for evaluation

#### Run the RMI benchmark

Start the RMI server on the server VM:

```
./gradlew -q runRmiServer --console=plain
```

For local running: `./gradlew -q runRmiServer -PrmiHost=localhost --console=plain`

Start the benchmark client in rmi version on the client VM:

```
./gradlew -q runBenchmark --args="rmi h5129.pi.uni-bamberg.de" --console=plain
```

For local running: `./gradlew -q runBenchmark --args="rmi localhost" --console=plain`

#### Run the gRPC benchmark

Start the gRPC server on the server VM:

```
./gradlew -q runGrpcServer --console=plain
```

For local running: `./gradlew -q runGrpcServer --console=plain`

Start the benchmark client in gRPC version on client VM:

```
./gradlew -q runBenchmark --args="grpc h5129.pi.uni-bamberg.de" --console=plain
```

For local running: `./gradlew -q runBenchmark --args="grpc localhost" --console=plain`

### 6. Available commands for both RMI and gRPC version

```
createprofile <userID> <firstname> <lastname> <bio>
getprofile <userID>
updateprofile <userID> <firstname> <lastname> <bio>
publishpost <userID> <content>
deletepost <userID> <postID>
followstudent <followerID> <targetID>
unfollowstudent <followerID> <targetID>
getfeed <userID>
getposts <userID>
exit
```
