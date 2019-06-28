package sparkSQLDemo

import org.apache.spark.sql.SparkSession

object SparSQLFunDemo {

  val spark = SparkSession.builder().master("local[2]").appName("SpakSQLFun")
    .getOrCreate()
  spark.conf.set("spark.sql.shuffle.partitions", 6)
  spark.conf.set("spark.executor.memory", "6g")

  spark.sparkContext.setLogLevel("warn")



  def main(args: Array[String]): Unit = {
    val filePath =""
    val df_1 = spark.read.option("headler", "false").option("inferschema", "true").csv(filePath)
      .toDF("mac", "phone_brand", "enter_time", "first_time", "last_time", "region", "screen", "stay_long")

  }
}
