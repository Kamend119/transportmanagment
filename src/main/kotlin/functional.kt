import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

fun authorization(login: String, password: String): List<String>? {
    var result: List<String>
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM authorization('$login', '$password')")
            if (resultSet.next()) {
                val employeeId = resultSet.getInt("id").toString()
                val jobName = resultSet.getString("job_name")?: ""
                result = listOf(employeeId, jobName)
                return result
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return null
}
fun getActiveTrip(driverId: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_active_trip($driverId)")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contracts_number").toString(),
                    resultSet.getString("driver_fullname")?: "",
                    resultSet.getString("client_fullname")?: "",
                    resultSet.getDate("conclusion_date").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun generateCargoDeclaration(contractId: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM generate_cargo_declaration($contractId)")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("cargo_name")?: "",
                    resultSet.getFloat("cargo_volume").toString(),
                    resultSet.getFloat("cargo_weight").toString(),
                    resultSet.getString("cargo_description")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun getContractInfo(contractId: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_contracts_info($contractId)")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contracts_id").toString(),
                    resultSet.getDate("conclusion_date").toString(),
                    resultSet.getFloat("cost").toString(),
                    resultSet.getString("customer_fullname")?: "",
                    resultSet.getString("manager_fullname")?: "",
                    resultSet.getString("driver_fullname")?: "",
                    resultSet.getString("car_licenseplate")?: "",
                    resultSet.getString("car_model")?: "",
                    resultSet.getString("car_brand")?: "",
                    resultSet.getString("destinationpoints_info")?: "",
                    resultSet.getString("additional_services")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewCargosInContract(contractId: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM view_cargos_in_contract($contractId)")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("cargos_id").toString(),
                    resultSet.getString("cargos_name")?: "",
                    resultSet.getFloat("weight").toString(),
                    resultSet.getFloat("volume").toString(),
                    resultSet.getString("cargo_class")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun getContractDestinationPoints(contractId: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_contract_destination_points(${contractId.toInt()})")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("destination_point_id").toString(),
                    resultSet.getString("type")?: "",
                    resultSet.getString("city")?: "",
                    resultSet.getString("address")?: "",
                    resultSet.getDate("arrivaldate").toString(),
                    resultSet.getString("status")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateStatusDestinationPoints(contractId: String, status: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("SELECT update_destination_status(${contractId.toInt()}, '$status')")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun viewActiveContractInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM view_active_contract_info()")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contract_number").toString(),
                    resultSet.getString("manager_fullname")?: "",
                    resultSet.getString("customer_fullname")?: "",
                    resultSet.getDate("conclusion_date").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun calculatePreliminaryCost(cityFrom: String, cityTo: String, weight: Double, volume: Double): Double {
    var preliminaryCost = 0.0
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM calculate_preliminary_cost('$cityFrom', '$cityTo', $weight, $volume)")
            if (resultSet.next()) {
                preliminaryCost = resultSet.getDouble(1)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }

    return preliminaryCost
}
fun getAdditionalServices(contractId: Int): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_additional_services($contractId)")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("name"),
                    resultSet.getFloat("cost").toString(),
                    resultSet.getString("description"),
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun contractsSummaryForPeriod(startDate: String, endDate: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM contracts_summary_for_period('$startDate', '$endDate')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("manager_fullname")?: "",
                    resultSet.getString("contracts_count")?: "",
                    resultSet.getFloat("total_amount").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun driverPerformanceForPeriod(startDate: String, endDate: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM driver_performance_for_period('$startDate', '$endDate')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("driver_fullname")?: "",
                    resultSet.getInt("contracts_count").toString(),
                    resultSet.getInt("hours_on_routes").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
