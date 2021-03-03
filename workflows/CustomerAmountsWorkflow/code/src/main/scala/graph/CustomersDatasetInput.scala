package graph

import org.apache.spark.sql.types._
import io.prophecy.libs._
import io.prophecy.libs.UDFUtils._
import io.prophecy.libs.Component._
import io.prophecy.libs.DataHelpers._
import io.prophecy.libs.SparkFunctions._
import io.prophecy.libs.FixedFileFormatImplicits._
import org.apache.spark.sql.ProphecyDataFrame._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import config.ConfigStore._
import udfs.UDFs._
import graph._

@Visual(id = "CustomersDatasetInput", label = "CustomersDatasetInput", x = 7, y = 154, phase = 0)
object CustomersDatasetInput {

  @UsesDataset(id = "950", version = 3)
  def apply(spark: SparkSession): Source = {
    import spark.implicits._

    val fabric = Config.fabricName

    val out = fabric match {
      case "dev" =>
        val schemaArg = StructType(
          Array(
            StructField("customer_id",       IntegerType, false),
            StructField("first_name",        StringType,  false),
            StructField("last_name",         StringType,  false),
            StructField("phone",             StringType,  false),
            StructField("email",             StringType,  false),
            StructField("country_code",      StringType,  false),
            StructField("account_open_date", StringType,  false),
            StructField("account_flags",     StringType,  false)
          )
        )
        spark.read
          .format("csv")
          .option("sep", ",")
          .schema(schemaArg)
          .load("dbfs:/Prophecy/demo2@simpledatalabs.com/CustomersDatasetInput.csv")
          .cache()
      case _ => throw new Exception(s"The fabric '$fabric' is not handled")
    }

    out

  }

}
