package sparkSQLDemo

import org.apache.spark.sql.SparkSession

object TestReadDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("readDemo")
      .master("local").getOrCreate()
    //方式一
    val df1 = spark.read.json("E:\\people.json")
    val df2 = spark.read.parquet("E:\\users.parquet")
//    //方式二
    val df3 = spark.read.format("json").load("E:\\people.json")
    val df4 = spark.read.format("parquet").load("E:\\users.parquet")


    //方式一
    df1.write.json("E:\\111")
    df1.write.parquet("E:\\222")
    //方式二
    df1.write.format("json").save("E:\\333")
    df1.write.format("parquet").save("E:\\444")
    //方式三
    df1.write.save("E:\\555")
  }
}
