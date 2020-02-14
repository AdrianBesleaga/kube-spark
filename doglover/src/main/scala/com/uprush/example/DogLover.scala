package com.uprush.example

import org.apache.spark.sql.SparkSession

object DogLover {

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("Dog Lover").getOrCreate()
    import spark.implicits._

	// Read JSON data into Spark DataFrame using Spark.
	val tweetsDF = spark.read.json("s3a://deephub/tweets/")

	// Count total number of rows in all JSON files.
	var numTweets = tweetsDF.count()

    println(s"Number of tweets: $numTweets")

    // Take a look at its JSON schema.
	tweetsDF.printSchema()

	// Who are the most influential users and what did they tweet?
	tweetsDF.select($"user.id", $"user.screen_name", $"user.followers_count", $"text").orderBy($"user.followers_count".desc).show(10)

	// We are interested in the user information. Let's create a dataframe just for the user data.
	val users = tweetsDF.select($"user.*", $"text")
	users.printSchema()
	users.createOrReplaceTempView("users")

	// Top 10 influential English language users.
	val top10 = spark.sql("select screen_name, followers_count from users where lang = 'en' order by followers_count desc limit 10")
	println("Top 10 influential English language users:")
	println(top10)

	// Persist the user dataframe in query optimized format. Parquet is a optimized file format for Apache Hive query.
	// S3 is a eventual consistency model, which may cause some issue on the Spark save job. 
	// On Amazon S3 and some S3 compitable storages, it is not reliable to persisit dataframe directly.
	// On FlashBlade, Spark can directly persist dataframe via S3A without consistency concern. 
	// This is possible because FlashBlade supports strong consistency S3 implementation.
	val users_dir = "s3a://deephub/user/yijiang/doglover/twitter-users/"
	println(s"Writing dataframe to: $users_dir")
	users.write.format("parquet").mode("overwrite").save(users_dir)

	// Validate data is successfully written.
	println(s"Loading parquet data from: $users_dir")
	val users_pq = spark.read.format("parquet").load(users_dir)

	println("Running validating SQL")
	users_pq.createOrReplaceTempView("users_pq")
	spark.sql("select count(*) from users_pq").show()
	spark.sql("SELECT screen_name, text from users_pq limit 10").show()

    spark.stop()
  }

}
