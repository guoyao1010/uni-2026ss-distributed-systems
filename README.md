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

# Run and test RMI framework on VMs
This section describes the current proposed way to run the RMI part of the project on the assigned VMs.
We could run server on 5129 VM and run client on 5140 VM.

## 1. Connect to the VMs
Open two terminal windows. 

One for server and the other for client. Connect to the two VMs.

## 2. Go to project folder and update the repository **on both VMs**
Run following commands one by one **on both VMs**:
```
ls
cd group-7
git pull
gradle build
```

## 3. Start the RMI server and RMI client
**The server must be started before the client.**

On 5129 VM (Server):
```
java -Djava.rmi.server.hostname=h5129.pi.uni-bamberg.de -cp build/classes/java/main dsg.rmi.socialmedia.DSGSocialMediaServer
```
On 5140 VM (Client):
```
java -cp build/classes/java/main dsg.rmi.socialmedia.DSGSocialMediaRMIClient
```



