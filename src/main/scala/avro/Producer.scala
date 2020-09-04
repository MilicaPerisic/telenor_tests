package avro

import org.apache.spark.sql.avro.to_avro
import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions._

object Producer extends App {

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Producer")
    .getOrCreate()

  import spark.implicits._
  spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .format("csv")
    .csv("src/main/resources/result.csv")
    .select(to_avro(struct($"_c0", $"tripduration")) as "value")
    .write
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("topic", "avro")
    .option("partition", 100)
    .save()
}
