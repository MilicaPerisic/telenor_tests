name := "test_avro"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % "compile",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion % "compile",
  "org.apache.spark" %% "spark-avro" % sparkVersion % "compile"
)