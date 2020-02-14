#!/bin/bash

JAR_DIR="s3://deephub/user/yijiang/doglover/"

echo "Making jar file"
sbt package

echo
echo "Uploading to S3"
s5cmd cp target/scala-2.12/doglover_2.12-0.1.0-SNAPSHOT.jar $JAR_DIR

echo "Done"
