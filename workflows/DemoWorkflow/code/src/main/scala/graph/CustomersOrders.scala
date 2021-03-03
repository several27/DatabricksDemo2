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

@Visual(id = "CustomersOrders", label = "CustomersOrders", x = 694, y = 156, phase = 0)
object CustomersOrders {

  @UsesDataset(id = "966", version = 0)
  def apply(spark: SparkSession, in: DataFrame): Target = {
    import spark.implicits._

    val fabric = Config.fabricName
    fabric match {
      case "dev" =>
        val schemaArg = StructType(
          Array(
            StructField("customer_id", IntegerType, false),
            StructField("phone",       StringType,  false),
            StructField("email",       StringType,  false),
            StructField("order_id",    IntegerType, false),
            StructField("amount",      DoubleType,  false)
          )
        )
        in.write
          .format("delta")
          .mode("overwrite")
          .saveAsTable("default.customers_orders_output_demo")
      case _ => throw new Exception("Unknown Fabric")
    }

  }

}
