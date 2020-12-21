package org.leaderboard.jobs

import org.apache.log4j.Logger
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, concat_ws, expr}

object processor extends constant {

  def mergeTables(teamDF: DataFrame, contestDF: DataFrame, rootLogger: Logger): DataFrame = {
    try {
      val secondDF: DataFrame = contestDF.withColumnRenamed("ACCOUNT_UUID", "ACCOUNT_UUID_b")

      val finalDF = teamDF.join(secondDF, teamDF("ACCOUNT_UUID") === secondDF("ACCOUNT_UUID_b") &&
        teamDF("ACCOUNT_CONTEST_TEAM") === secondDF("TEAM_TYPE")).drop("ACCOUNT_UUID_b")

      finalDF
    } catch {
      case a: Throwable =>
        val message = "Error While Joining Two Tables Contest and Team"
        println(message)
        rootLogger.error(message + ": ", a)
        throw new Exception(message)
    }
  }

  def scoreLogic(api_df: DataFrame, db_df: DataFrame, rootLogger: Logger): DataFrame = {
    try {
      var master_df =
        db_df.join(api_df, db_df.col(colName = col_db1) === api_df.col(colName = col_api1))
          .drop(colName = col_db1).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db1 + col_api2)

          .join(api_df, db_df.col(colName = col_db2) === api_df.col(colName = col_api1))
          .drop(colName = col_db2).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db2 + col_api2 + "2")

          .join(api_df, db_df.col(colName = col_db3) === api_df.col(colName = col_api1))
          .drop(colName = col_db3).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db3 + col_api2)

          .join(api_df, db_df.col(colName = col_db4) === api_df.col(colName = col_api1))
          .drop(colName = col_db4).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db4 + col_api2)

          .join(api_df, db_df.col(colName = col_db5) === api_df.col(colName = col_api1))
          .drop(colName = col_db5).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db5 + col_api2 + "5")

          .join(api_df, db_df.col(colName = col_db6) === api_df.col(colName = col_api1))
          .drop(colName = col_db6).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db6 + col_api2)

          .join(api_df, db_df.col(colName = col_db7) === api_df.col(colName = col_api1))
          .drop(colName = col_db7).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db7 + col_api2)

          .join(api_df, db_df.col(colName = col_db8) === api_df.col(colName = col_api1))
          .drop(colName = col_db8).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db8 + col_api2)

          .join(api_df, db_df.col(colName = col_db9) === api_df.col(colName = col_api1))
          .drop(colName = col_db9).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db9 + col_api2)

          .join(api_df, db_df.col(colName = col_db10) === api_df.col(colName = col_api1))
          .drop(colName = col_db10).drop(colName = col_api1)
          .withColumnRenamed(existingName = col_api2, newName = col_db10 + col_api2)

          .withColumn(col_db2 + col_api2, col(col_db2 + col_api2 + "2") * 2).drop(col_db2 + col_api2 + "2")
          .withColumn(col_db5 + col_api2, col(col_db5 + col_api2 + "5") * 1.5).drop(col_db5 + col_api2 + "5")

      master_df = master_df.withColumn("total", expr(sumCol)).drop(cols: _*)

      master_df = master_df.select(concat_ws(sep = ".", master_df.col("ACCOUNT_UUID"),
        master_df.col("TEAM_TYPE"),
        master_df.col("TEAM_GAMES_UUID"),
        master_df.col("CONTEST_UUID")) as "id", master_df.col("total"))

      master_df
    } catch {
      case a: Throwable =>
        val message = "Error While MultiJoining the DB df and API df"
        println(message)
        rootLogger.error(message + ": ", a)
        throw new Exception(message)
    }
  }

}
