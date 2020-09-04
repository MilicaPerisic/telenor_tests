package avro

import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions.{col}
import org.apache.spark.sql.avro.from_avro

object Consumer extends App{
  val spark = SparkSession.builder()
    .appName("Consumer")
    .config("spark.master", "local")
    .getOrCreate()

  val schema =
    """
      |{
      |"type": "record",
      |"name" : "bike",
      |"fields" : [ {"name" : "_c0", "type" : [ "int", "null" ] }, {"name" : "tripduration", "type" : [ "int", "null" ]} ]
      |}
    """.stripMargin

  val df = spark
    .read
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "avro")
    .option("startingOffsets", "earliest")
    .load()
    .select(from_avro(col("value"), schema).as("data")).select("data.*")
//    .repartition(10)
//    .write
//    .mode(SaveMode.Overwrite)
//    .parquet("hdfs://10.0.0.4/user/hdfs/test_mm/result_kafka")

}