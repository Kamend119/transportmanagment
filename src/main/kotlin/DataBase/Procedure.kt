package DataBase

import java.math.BigInteger
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

fun getTripInfo(trip_id_in : BigInteger, user: String, pass: String) : List<List<String>>{
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("CALL get_trip_info('$trip_id_in')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("trip_id").toString(),
                    resultSet.getTimestamp("departure_datetime").toString(),
                    resultSet.getInt("destination_points_id").toString(),
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

fun getAdditionalServices(contract_id: Int, user: String, pass: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet = statement.executeQuery("CALL get_additional_services('$contract_id')")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getString("name"),
                    resultSet.getInt("cost").toString(),
                    resultSet.getString("validityperiod"),
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

fun getActiveCustomersWithContracts(user: String, pass: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet = statement.executeQuery("CALL get_active_customers_with_contracts()")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("customer_id").toString(),
                    resultSet.getString("lastname"),
                    resultSet.getString("firstname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("phone"),
                    resultSet.getInt("contract_id").toString(),
                    resultSet.getTimestamp("contract_date").toString(),
                    resultSet.getString("departure_address"),
                    resultSet.getBigDecimal("cost").toString(),
                    resultSet.getString("additional_service_name")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}