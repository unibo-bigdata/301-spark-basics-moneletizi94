import org.apache.spark.{SparkConf, SparkContext}
// spark-submit --class AverageInitialLetterLenght BD-301-spark-basics.jar
object InvertedIndex extends App {

    override def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setAppName("InvertedIndex Spark 1.6")
      val sc = new SparkContext(conf)

      // Create an RDD from the files in the given folder
      val rddCapra = sc.textFile("hdfs:/bigdata/datasets/capra/capra.txt")

      val rddWordWithIndex = rddCapra.
        flatMap( line => line.split(" ")).
        zipWithIndex()


      //Save the RDD on HDFS; the directory should NOT exist
      rddWordWithIndex.saveAsTextFile("hdfs:/user/cloudera/spark/invertedIndex")
    }
}
