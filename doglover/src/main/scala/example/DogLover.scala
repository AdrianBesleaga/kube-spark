package example

import org.apache.spark.sql.SparkSession

object DogLover {

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("Dog Lover").getOrCreate()

	// Read JSON data into Spark DataFrame using Spark.
	val tweetsDF = spark.read.json("s3a://deephub/tweets/")

	// Count total number of rows in all JSON files.
	var numTweets = tweetsDF.count()

    println(s"Number of tweets: $numTweets")

    spark.stop()
  }

}
