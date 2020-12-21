package org.leaderboard.jobs

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.{Level, Logger}

trait core extends constant {

  @transient lazy val log = org.apache.log4j.LogManager.getLogger(getClass)

  var rootLogger: Logger = null

  rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.ERROR)

  val spark: SparkSession = try {

    val conf: SparkConf = new SparkConf().setAppName(name = app_name)
    val spark: SparkSession = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    spark
  } catch {
    case a: Throwable =>
      val message = "Something Went wrong while Initializing Spark Session"
      println(message)
      rootLogger.error(message + ": ", a)
      throw new Exception(message)
  }

}
