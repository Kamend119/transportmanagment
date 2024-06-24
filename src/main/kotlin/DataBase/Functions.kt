package DataBase

import java.math.BigDecimal
import java.math.BigInteger
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*

fun authorization(login: String, password: String, user : String, pass : String): List<String>? {
    var result: List<String>
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM authorization('$login', '$password')")
            if (resultSet.next()) {
                val employeeId = resultSet.getInt("employee_id").toString()
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


fun getActiveTripsByDriver(driver_id : BigInteger, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_active_trips_by_driver('$driver_id')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getTimestamp("departuredatetime").toString(),
                    resultSet.getString("address")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun getCargosForTrip(trip_id  : BigInteger, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_cargos_for_trip('$trip_id')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("weight").toString(),
                    resultSet.getBigDecimal("volume").toString(),
                    resultSet.getString("dimensions"),
                    resultSet.getString("hazardclassification"),
                    resultSet.getString("description")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun getDestinationPointsForContract(contract_id  : BigInteger, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_destination_points_for_contract('$contract_id')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("sity"),
                    resultSet.getString("address"),
                    resultSet.getDate("arrivaldate").toString(),
                    resultSet.getTime("arrivaltime").toString(),
                    resultSet.getString("status")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun getFullContractInfo(contract_id_in : BigInteger, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_full_contract_info('$contract_id_in')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("contract_id").toString(),
                    resultSet.getString("customer_lastname"),
                    resultSet.getString("customer_firstname"),
                    resultSet.getString("customer_patronymic"),
                    resultSet.getString("customer_phone"),
                    resultSet.getString("manager_lastname"),
                    resultSet.getString("manager_firstname"),
                    resultSet.getString("manager_patronymic"),
                    resultSet.getString("manager_phone"),
                    resultSet.getString("cargo_name"),
                    resultSet.getBigDecimal("cargo_weight").toString(),
                    resultSet.getBigDecimal("cargo_volume").toString(),
                    resultSet.getString("cargo_dimensions"),
                    resultSet.getString("cargo_hazard_classification"),
                    resultSet.getString("cargo_description"),
                    resultSet.getString("departure_address"),
                    resultSet.getString("destination_city"),
                    resultSet.getString("destination_address"),
                    resultSet.getDate("arrival_date").toString(),
                    resultSet.getTime("arrival_time").toString(),
                    resultSet.getString("destination_status"),
                    resultSet.getBigDecimal("contract_cost").toString(),
                    resultSet.getTimestamp("conclusion_date").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun updateDestinationStatus(contract_id : BigInteger, destination_name : String, user : String, pass : String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            statement.executeQuery(
                "SELECT * FROM update_destination_status('$contract_id', '$destination_name')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun generateCargoDeclaration(contract_id : BigInteger, user : String, pass : String): String {
    var result = String()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM generate_cargo_declaration('$contract_id')")
            if (resultSet.next()) {
                val text = resultSet.getString("cargo_declaration")
                result = text
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun calculateShippingCost(departure_city : String, destination_city : String,
                            weight : BigDecimal, volume : BigDecimal, hazardclass : String,
                            user : String, pass : String): String {
    var result = String()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery(
                "SELECT * FROM calculate_shipping_cost('$departure_city', '$destination_city', '$weight', '$volume', '$hazardclass')")
            if (resultSet.next()) {
                val text = resultSet.getString("shipping_cost")
                result = text
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun getEmployeeContractSummary(start_date : Date, end_date : Date, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_employee_contract_summary('$start_date', '$end_date')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("employee_name"),
                    resultSet.getInt("contracts_count").toString(),
                    resultSet.getBigDecimal("total_contract_amount").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun getDriversWithHighDrivingHours(start_date : Date, end_date : Date, user : String, pass : String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM get_drivers_with_high_driving_hours('$start_date', '$end_date')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("driver_name"),
                    resultSet.getInt("total_driving_hours").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun createBackup(user : String, pass : String){
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            statement.executeQuery(
                "SELECT * FROM create_backup()")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun restoreBackup(backup_file : String, user : String, pass : String){
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            statement.executeQuery(
                "SELECT * FROM restore_backup('$backup_file')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

