package org.leaderboard.jobs

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.redislabs.provider.redis._
import org.apache.log4j.Logger

object dataInit {

  def mysqlDbData(spark: SparkSession, tableName: String, sql: String,
                  mysql_url: String, database: String, username: String, password: String, rootLogger: Logger): DataFrame = {

    val query: String = sql.format(tableName)
    try {
      val df: DataFrame = spark.read.format("jdbc")
        .option("url", "jdbc:mysql://" + mysql_url + "/" + database)
        .option("dbtable", s"( $query ) t")
        .option("user", username)
        .option("password", password).load()

      df
    } catch {
      case a: Throwable =>
        val message = "Error While Reading from Mysql Database from table " + tableName
        println(message)
        rootLogger.error(message + ": ", a)
        throw new Exception(message)
    }
  }

  def apiData(spark: SparkSession, json_path: String, rootLogger: Logger): DataFrame = {
    try {
      //    import spark.implicits._
      //    val jsonstr = """[{"name":"Team_1","scores":17},{"name":"Team_2","scores":24},{"name":"Team_3","scores":39},{"name":"Team_4","scores":50},{"name":"Team_5","scores":52},{"name":"Team_6","scores":65},{"name":"Team_7","scores":72},{"name":"Team_8","scores":90},{"name":"Team_9","scores":93},{"name":"Team_10","scores":107},{"name":"Team_11","scores":19},{"name":"Team_12","scores":30},{"name":"Team_13","scores":37},{"name":"Team_14","scores":43},{"name":"Team_15","scores":-56},{"name":"Team_16","scores":69},{"name":"Team_17","scores":72},{"name":"Team_18","scores":83},{"name":"Team_19","scores":97},{"name":"Team_20","scores":-103},{"name":"Team_21","scores":13},{"name":"Team_22","scores":-23},{"name":"Team_23","scores":39},{"name":"Team_24","scores":-44},{"name":"Team_25","scores":53},{"name":"Team_26","scores":67},{"name":"Team_27","scores":80},{"name":"Team_28","scores":84},{"name":"Team_29","scores":93},{"name":"Team_30","scores":-106}]"""
      //    val api_df = spark.read.option("mode", "PERMISSIVE").json(Seq(jsonstr).toDS())
      val api_df = spark.read.option("multiLine", true).option("mode", "PERMISSIVE").json(path = json_path)
      api_df
    } catch {
      case a: Throwable =>
        val message = "Error While Reading JSON Data from S3 " + json_path
        println(message)
        rootLogger.error(message + ": ", a)
        throw new Exception(message)
    }
  }

  def loadRedis(spark: SparkSession, df: DataFrame, keyName: String, rootLogger: Logger): Unit = {
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    try {
      val final_data = df.map(r => (r(0).toString, r(1).toString)).rdd
      spark.sparkContext.toRedisZSET(kvs = final_data, zsetName = keyName)
      println("LeaderBoard Job Successfully Completed!!!")
      rootLogger.info("LeaderBoard Job Successfully Completed!!!")
    } catch {
      case a: Throwable =>
        val message = "Error While Converting and Pushing Data to Redis " + keyName
        println(message)
        rootLogger.error(message + ": ", a)
        throw new Exception(message)
    }
  }
}
