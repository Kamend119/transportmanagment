import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

fun viewCargoInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM cargos_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")?: "",
                    resultSet.getFloat("weight").toString(),
                    resultSet.getFloat("volume").toString(),
                    resultSet.getInt("contract_id").toString(),
                    resultSet.getInt("class_cargos_id").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewClassCargosInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM class_cargos_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")?: "",
                    resultSet.getString("description")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewAdditionalServicesInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM additionalservices_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")?: "",
                    resultSet.getFloat("cost").toString(),
                    resultSet.getString("description")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewDestinationPointsInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM destinationpoints_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("type")?: "",
                    resultSet.getString("city")?: "",
                    resultSet.getString("address")?: "",
                    resultSet.getDate("arrivaldate").toString(),
                    resultSet.getString("status")?: "",
                    resultSet.getInt("contract_id").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewCustomersInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM customers_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("lastname")?: "",
                    resultSet.getString("firstname")?: "",
                    resultSet.getString("patronymic") ?: "",
                    resultSet.getString("phone")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewContractInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM contracts_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getDate("conclusiondate").toString(),
                    resultSet.getFloat("cost").toString(),
                    resultSet.getInt("customer_id").toString(),
                    resultSet.getInt("manager_id").toString(),
                    resultSet.getInt("driver_id").toString(),
                    resultSet.getInt("car_id").toString(),
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
fun viewContactsInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM contacts_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("lastname")?: "",
                    resultSet.getString("firstname")?: "",
                    resultSet.getString("patronymic") ?: "",
                    resultSet.getString("phone")?: "",
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewAutoparkInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM autoparks_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")?: "",
                    resultSet.getString("address")?: "",
                    resultSet.getString("contact_id")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewCarInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM cars_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("licenseplate")?: "",
                    resultSet.getString("model")?: "",
                    resultSet.getString("brand")?: "",
                    resultSet.getInt("autopark_id").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewJobsInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM jobs_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("name")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewEmployeeInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM employees_view ORDER BY ID ASC")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("lastname")?: "",
                    resultSet.getString("firstname")?: "",
                    resultSet.getString("patronymic") ?: "",
                    resultSet.getDate("dateofbirth").toString(),
                    resultSet.getString("phone")?: "",
                    resultSet.getString("login")?: "",
                    resultSet.getLong("job_id").toString(),
                    resultSet.getString("passport_data")?: "",
                    resultSet.getString("workdays")?: "",
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewAuditLogInfo(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM audit_log_view")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("audit_id").toString(),
                    resultSet.getString("table_name")?: "",
                    resultSet.getString("operation")?: "",
                    resultSet.getString("changed_by")?: "",
                    resultSet.getString("change_timestamp")?: "",
                    resultSet.getString("old_data")?: "",
                    resultSet.getString("new_data")?: ""
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun viewContractAdditionalService(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM contract_additionalservices_view ORDER BY ID ASC")
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
