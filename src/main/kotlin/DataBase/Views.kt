package DataBase

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

fun fullInfoAdditionalservices(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM full_info_additionalservices")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("additional_service_id").toString(),
                    resultSet.getString("additional_service_name"),
                    resultSet.getBigDecimal("additional_service_cost").toString(),
                    resultSet.getString("additional_service_validity_period"),
                    resultSet.getString("additional_service_description")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun fullInfoCustomers(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM full_info_customers")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("customer_id").toString(),
                    resultSet.getString("lastname"),
                    resultSet.getString("firstname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("phone")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun fullInfoEmployees(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM full_info_employees")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("employee_id").toString(),
                    resultSet.getString("full_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("passport_details"),
                    resultSet.getDate("birth_date").toString(),
                    resultSet.getString("position_name")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewContacts(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Contacts")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("lastname"),
                    resultSet.getString("firstname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("phone")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewAutoparks(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Autoparks")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getInt("contactpersonid").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewCars(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Cars")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("licenseplate"),
                    resultSet.getString("cartype"),
                    resultSet.getString("model"),
                    resultSet.getString("brand"),
                    resultSet.getInt("autoparkid").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewCargos(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Cargos")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
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

fun viewJobs(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Jobs")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewDestinationPoints(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_DestinationPoints")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
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

fun viewTrips(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Trips")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getTimestamp("departuredatetime").toString(),
                    resultSet.getInt("destinationpointsid").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewTripDrivers(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_TripDrivers")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getInt("tripid").toString(),
                    resultSet.getInt("driverid").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewContracts(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_Contracts")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("phone"),
                    resultSet.getTimestamp("conclusiondate").toString(),
                    resultSet.getInt("customerid").toString(),
                    resultSet.getInt("managerid").toString(),
                    resultSet.getInt("cargoid").toString(),
                    resultSet.getInt("tripid").toString(),
                    resultSet.getString("departureaddress"),
                    resultSet.getBigDecimal("cost").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun viewContractAdditionalServices(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_ContractAdditionalServices")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getInt("contractid").toString(),
                    resultSet.getInt("additionalserviceid").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun View_AuditLog(user : String, pass : String): MutableList<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM View_AuditLog")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("audit_id").toString(),
                    resultSet.getString("table_name"),
                    resultSet.getString("operation"),
                    resultSet.getString("changed_by"),
                    resultSet.getTimestamp("change_timestamp").toString(),
                    resultSet.getString("old_data"),
                    resultSet.getString("new_data")
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

