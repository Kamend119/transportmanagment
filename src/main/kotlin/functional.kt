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
                val jobName = resultSet.getString("job_name")
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
            println()
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_active_trip($driverId)")
            println(resultSet)
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contracts_number").toString(),
                    resultSet.getString("driver_fullname"),
                    resultSet.getString("client_fullname"),
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
            println()
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM generate_cargo_declaration($contractId)")
            println(resultSet)
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("cargo_name"),
                    resultSet.getFloat("cargo_volume").toString(),
                    resultSet.getFloat("cargo_weight").toString(),
                    resultSet.getString("cargo_description")
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
            println()
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_contract_info($contractId)")
            println(resultSet)
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contracts_id").toString(),
                    resultSet.getDate("conclusion_date").toString(),
                    resultSet.getFloat("cost").toString(),
                    resultSet.getString("customer_fullname"),
                    resultSet.getString("manager_fullname"),
                    resultSet.getString("driver_fullname"),
                    resultSet.getString("car_licenseplate"),
                    resultSet.getString("car_model"),
                    resultSet.getString("car_brand"),
                    resultSet.getString("destinationpoint_type"),
                    resultSet.getString("destinationpoint_city"),
                    resultSet.getString("destinationpoint_address"),
                    resultSet.getDate("destinationpoint_arrival_date").toString(),
                    resultSet.getString("additional_services")
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
            println()
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM view_cargos_in_contract($contractId)")
            println(resultSet)
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("cargo_id").toString(),
                    resultSet.getString("cargo_name"),
                    resultSet.getFloat("cargo_volume").toString(),
                    resultSet.getFloat("cargo_weight").toString(),
                    resultSet.getString("cargo_class")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}