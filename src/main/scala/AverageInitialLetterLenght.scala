import org.apache.spark.{SparkConf, SparkContext}

// spark-submit --class AverageInitialLetterLenght BD-301-spark-basics.jar
object AverageInitialLetterLenght extends App {

  override def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("AverageInitialLetterLenght Spark 1.6")
    val sc = new SparkContext(conf)

    // Create an RDD from the files in the given folder
    val rddCapra = sc.textFile("hdfs:/bigdata/datasets/capra/capra.txt")

    val rddAvgLetterLenght = rddCapra.
                        flatMap( line => line.split(" ")).
                        map( word => (word.substring(0,1), word.length)).
                        aggregateByKey((0.0,0.0))((a, v) => (a._1 + v, a._2 +1), (a1, a2) => (a1._1 + a2._2, a1._2 + a2._2)).
                        mapValues(accumulators=> accumulators._1/accumulators._2)

    //Sort, coalesce and cache the result(because it is used twice)
    val rddResult = rddAvgLetterLenght.sortByKey().coalesce(1)

    //Save the RDD on HDFS; the directory should NOT exist
    rddResult.saveAsTextFile("hdfs:/user/cloudera/spark/letterAvgLenght")
  }

}
