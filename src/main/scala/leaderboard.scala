package org.leaderboard.jobs

import dataInit.{mysqlDbData, loadRedis, apiData}
import processor.{scoreLogic, mergeTables}

object leaderboard extends core {

  def main(args: Array[String]): Unit = {

    if (args.length == 3) {
      val teamTableName = args(0)
      val contestTableName = args(1)
      val json_path = args(2)

      try {
        val teamDF = mysqlDbData(spark, teamTableName, teamsql, mysql_url, database, username, password, rootLogger)
        val contestDF = mysqlDbData(spark, contestTableName, contestsql, mysql_url, database, username, password, rootLogger)
        val mergedDF = mergeTables(contestDF, teamDF, rootLogger)
        val apiDF = apiData(spark, json_path, rootLogger)
        val masterDF = scoreLogic(apiDF, mergedDF, rootLogger)
        loadRedis(spark, masterDF, redis_keyname, rootLogger)
      } catch {
        case a: Throwable =>
          val message = "Error While Running the LeaderBoard Job"
          println(message)
          rootLogger.error(message + ": ", a)
          throw new Exception(message)
      } finally {
        spark.stop()
      }
    } else {
      val message = "Pass two table name and json path in this order Team Table, Contest Table, Json Path"
      println(message)
      rootLogger.info(message)
    }
  }
}