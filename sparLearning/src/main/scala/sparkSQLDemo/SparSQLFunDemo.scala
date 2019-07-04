package sparkSQLDemo

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SparSQLFunDemo {

  val spark = SparkSession.builder().master("local[2]").appName("SpakSQLFun")
    .getOrCreate()
  spark.conf.set("spark.sql.shuffle.partitions", 6)
  spark.conf.set("spark.executor.memory", "6g")

  spark.sparkContext.setLogLevel("warn")



  def main(args: Array[String]): Unit = {
    val filePath ="D:\\working\\sparLearning\\src\\main\\resources\\visit.txt"
    val df_1 = spark.read.option("headler", "false").option("inferschema", "true").csv(filePath)
      .toDF("mac", "phone_brand", "enter_time", "first_time", "last_time", "region", "screen", "stay_long")

    df_1.select("*").show(5)

    //abs 绝对值
    df_1.selectExpr("abs(stay_long) as res_abs").show(5)
    // 手动将第一条记录的第一个字段置为空，则显示第二个字段值　
    df_1.selectExpr("coalesce(mac,screen,stay_long) as res_colesce").show(5)

    val df_numFie = spark.read.json("D:\\people.json")
    df_numFie.select("*").show(5)

    val df_numFie1 = spark.read.json("D:\\peoples.json")
    df_numFie1.select("*").show(5)

    val df_score = df_numFie1.select(df_numFie1("name"),df_numFie1("age"),explode(df_numFie1("myScore"))).toDF("name","age","myScore")
    val dfMyScore = df_score.select("name","age","myScore.score1","myScore.score2")
    dfMyScore.select("*").show()
    df_numFie1.createOrReplaceTempView("d1")
    spark.sql(
      """
        |select explode(Array("a","b","c","d"))
        |from d1
      """
      .stripMargin).show(4, false)

    spark.sql(
      """
        |select explode(Map("a","b"))
        |from d1
      """.stripMargin).show(4,false)

    df_1.select(greatest("enter_time","first_time","last_time") as("greatest")).show(3)

  }
}
