
  import org.apache.spark.{SparkConf, SparkContext}

  // spark-submit --class WordLengthCount BD-301-spark-basics.jar
  object WordLengthCount extends App {

    override def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setAppName("WordLengthCount Spark 1.6")
      val sc = new SparkContext(conf)

      // Create an RDD from the files in the given folder
      val rddCapra = sc.textFile("hdfs:/bigdata/datasets/capra/capra.txt")

      val rddWordCount = rddCapra.flatMap( line => line.split(" ")).map( word => (word.length, 1)).reduceByKey((v1,v2)=>v1+v2)

      //Sort, coalesce and cache the result(because it is used twice)
      val rddResult = rddWordCount.sortByKey().coalesce(1)

      //Save the RDD on HDFS; the directory should NOT exist
      rddResult.saveAsTextFile("hdfs:/user/cloudera/spark/wordLenghtCount")
    }


}
