


## Simple test using Lagom 1.3.10 / 1.4.0-M3 and Akka Cluster 


### Start first node using 1.3.10

```
cd hello-akka-cluster-1.3.10
sbt runAll
```

On another shell, run script that bring entities into memory (port 9000). 
It will create 100 entities

```
  ./say-hello.sh 9000
```

output console:

```
on node running 1.3.10 - [world-1]
on node running 1.3.10 - [world-2]
on node running 1.3.10 - [world-3]
...
```

output http request:  
(many of this)
```
greeting       = Hello, world-{n}!
additional ser = off
remote port    = 2552
version        = 1.3.10
```

### Start second node using 1.4.0-M3

```
cd hello-akka-cluster-1.4.0
sbt runAll
```

Wait for nodes to form a cluster. First node had about 100 entities in memory, but now the entities will be distributed over the two nodes. 

Run script on node 1.4.0-M3 (port 9001)

```
  ./say-hello.sh 9001
```

Console output must be distributed. Some entities will stay in 1.3.10, others will have moved to 1.4.0-M3

 Http output will be:
 ```
 greeting       = Hello, world-{n}!
 additional ser = on
 remote port    = 2553
 version        = 1.4.0-M3
 ```
