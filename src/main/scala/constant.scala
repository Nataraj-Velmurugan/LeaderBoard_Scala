package org.leaderboard.jobs

trait constant {

  val app_name = "LeaderBoard Dashboard"

  val s3_path = "s3://leaderboardcode/api.json"
  val mysql_url = "ultra.couejfcsmiwa.us-east-2.rds.amazonaws.com"
  val username = "alpha"
  val password = "Se3ret123"
  val database = "ULTRA_GAME"

  val teamsql =
    """
       SELECT
                ACCOUNT_UUID,
                TEAM_TYPE,
                TEAM_GAMES_UUID,
                TEAM_COMBINATION->>"$.one" as one,
                TEAM_COMBINATION->>"$.two" as two,
                TEAM_COMBINATION->>"$.three" as three,
                TEAM_COMBINATION->>"$.four" as four,
                TEAM_COMBINATION->>"$.five" as five,
                TEAM_COMBINATION->>"$.six" as six,
                TEAM_COMBINATION->>"$.seven" as seven,
                TEAM_COMBINATION->>"$.eight" as eight,
                TEAM_COMBINATION->>"$.nine" as nine,
                TEAM_COMBINATION->>"$.ten" as ten
              FROM
                """ + database + """.%s t
                """

  val contestsql = """SELECT
                      	CONTEST_UUID,
                        ACCOUNT_UUID,
                        ACCOUNT_CONTEST_TEAM
                       FROM
                      	""" + database + """.%s c """

  val col_api1 = "name"
  val col_api2 = "scores"

  val col_db1 = "one"
  val col_db2 = "two"
  val col_db3 = "three"
  val col_db4 = "four"
  val col_db5 = "five"
  val col_db6 = "six"
  val col_db7 = "seven"
  val col_db8 = "eight"
  val col_db9 = "nine"
  val col_db10 = "ten"

  val sumCol = col_db1 + "scores + " + col_db2 + "scores + " + col_db3 + "scores + " + col_db4 + "scores + " + col_db5 + "scores + " + col_db6 + "scores + " + col_db7 + "scores + " + col_db8 + "scores + " + col_db9 + "scores + " + col_db10 + "scores"

  val cols = Seq(col_db1 + "scores", col_db2 + "scores", col_db3 + "scores", col_db4 + "scores",
    col_db5 + "scores", col_db6 + "scores", col_db7 + "scores", col_db8 + "scores",
    col_db9 + "scores", col_db10 + "scores")

  val redis_keyname = "leaderboard"

}
