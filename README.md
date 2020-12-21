
**Commands to Install Sbt Ubuntu**
```bash
wget http://apt.typesafe.com/repo-deb-build-0002.deb
sudo dpkg -i repo-deb-build-0002.deb
sudo apt-get update
sudo apt-get install sbt
```

**Commands to Install Sbt CentOS**
```bash
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
sudo yum install sbt
```

**Install local redis to Test**
```bash
wget http://download.redis.io/redis-stable.tar.gz
tar xvzf redis-stable.tar.gz
cd redis-stable
make
sudo cp src/redis-server /usr/local/bin/
sudo cp src/redis-cli /usr/local/bin/
vi redis.conf
#Comment out bind 127.0.0.1 line and protected-mode to -> no (to write in db)
redis-server ./redis.conf
redis-cli ping
```

**Clone the project and build the Jar**
```bash
git clone https://git-codecommit.us-east-2.amazonaws.com/v1/repos/LeaderBoard
cd LeaderBoard/
sbt assembly
```

**AWS Code Commit Credentials**
```bash
AWSUser: leaderboard
Access_Key: AKIA5GKK34MFEWGNESHO
Secret_KEY: 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

CodeCommitCredential: leaderboard-at-906931987210
pwd: xOVA82VH2294JiEzvLda4kNY/zXLI25UGMkuakUTiP4=

```

**Command to Run Spark Application in AWS**
```bash
# to run on different cluster mode try this link
# https://spark.apache.org/docs/latest/submitting-applications.html
spark-submit --class org.leaderboard.jobs.leaderboard --master local[*] --packages redis.clients:jedis:3.1.0,org.apache.commons:commons-pool2:2.9.0,com.redislabs:spark-redis:2.4.0,mysql:mysql-connector-java:8.0.22,com.amazonaws:aws-java-sdk:1.7.4,org.apache.hadoop:hadoop-aws:2.7.3  --conf "spark.redis.host=0.0.0.0" --conf "spark.redis.port=6379" --conf "spark.redis.db=0" target/scala-2.11/LeaderBoard-assembly-0.1.jar TEAM CONTEST s3://leaderboardcode/api.json
```

**Command to Run Spark Application in local machine**
```bash
spark-submit --class org.leaderboard.jobs.leaderboard --master local[*] --packages org.xerial.snappy:snappy-java:1.0.4.1,redis.clients:jedis:3.1.0,org.apache.commons:commons-pool2:2.9.0,com.redislabs:spark-redis:2.4.0,mysql:mysql-connector-java:8.0.22,com.amazonaws:aws-java-sdk:1.7.4,org.apache.hadoop:hadoop-aws:2.7.3 --driver-class-path ./jars/mysql_mysql-connector-java-8.0.22.jar --conf spark.executor.extraClassPath=./jars/mysql_mysql-connector-java-8.0.22.jar  --conf "spark.redis.host=0.0.0.0" --conf "spark.redis.port=6379" --conf "spark.redis.db=0" target/scala-2.11/LeaderBoard-assembly-0.1.jar TEAM CONTEST s3://leaderboardcode/api.json
```

**Command to Run Spark Application in cluster mode**
```bash
spark-submit --class org.leaderboard.jobs.leaderboard --master yarn --deploy-mode cluster --packages org.xerial.snappy:snappy-java:1.0.4.1,redis.clients:jedis:3.1.0,org.apache.commons:commons-pool2:2.9.0,com.redislabs:spark-redis:2.4.0,mysql:mysql-connector-java:8.0.22,com.amazonaws:aws-java-sdk:1.7.4,org.apache.hadoop:hadoop-aws:2.7.3 --driver-class-path ./jars/mysql_mysql-connector-java-8.0.22.jar --conf spark.executor.extraClassPath=./jars/mysql_mysql-connector-java-8.0.22.jar  --conf "spark.redis.host=0.0.0.0" --conf "spark.redis.port=6379" --conf "spark.redis.db=0" target/scala-2.11/LeaderBoard-assembly-0.1.jar TEAM CONTEST s3://leaderboardcode/api.json
```



**Known Errors and resolution in AWS EMR**
```bash

java.lang.ClassNotFoundException: Class com.amazon.ws.emr.hadoop.fs.EmrFileSystem not found
-----
For more reference check this link
https://aws.amazon.com/premiumsupport/knowledge-center/emr-spark-classnotfoundexception/
sudo vi /etc/spark/conf/spark-defaults.conf

spark.driver.extraClassPath :/home/hadoop/LeaderBoard/jars/*
spark.executor.extraClassPath :/home/hadoop/LeaderBoard/jars/*
```