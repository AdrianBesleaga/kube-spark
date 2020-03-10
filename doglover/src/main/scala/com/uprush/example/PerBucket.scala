package com.uprush.example

import org.apache.spark.sql.SparkSession

object PerBucket {

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("Per-bucket Example").getOrCreate()
    import spark.implicits._

	// Read JSON data into Spark DataFrame using Spark.
	val tweetsDF = spark.read.json("s3a://deephub/tweets/")

	// Count total number of rows in all JSON files.
	var numTweets = tweetsDF.count()

    println(s"\nNumber of tweets: $numTweets")

    // Read data from another bucket
    val helloDF = spark.read.text("s3a://bigdatabucket/hello.txt")
	var numHello = helloDF.count()
    println(s"\nNumber of hello: $numHello")

    spark.stop()
  }

}
