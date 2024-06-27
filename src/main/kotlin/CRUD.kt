import java.math.BigInteger
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate

fun createContact(lastname: String, firstname: String, patronymic: String, phone: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_contact(?, ?, ?, ?)")
            statement.setString(1, lastname)
            statement.setString(2, firstname)
            statement.setString(3, patronymic)
            statement.setString(4, phone)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getContact(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_contacts($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("lastnames"),
                    resultSet.getString("firstnames"),
                    resultSet.getString("patronymics"),
                    resultSet.getString("phones")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateContact(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL update_contact(${data[0].toInt()}, " +
                    "${data[1]}, ${data[2]}, ${data[3]}, ${data[4]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContact(contactId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contact(?)")
            statement.setLong(1, contactId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createAutopark(name: String, address: String, contactId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_autopark(?, ?, ?)")
            statement.setString(1, name)
            statement.setString(2, address)
            statement.setLong(3, contactId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getAutopark(inId: Int): List<String>  {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_autopark($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getString("addresss"),
                    resultSet.getLong("contact_ids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateAutopark(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
            connection.prepareStatement("CALL update_autopark(${data[0].toInt()}, ${data[1]}, ${data[2]}, ${data[3].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteAutopark(autoparkId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_autopark(?)")
            statement.setLong(1, autoparkId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCar(licenseplate: String, model: String, brand: String, autoparkId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_car(?, ?, ?, ?)")
            statement.setString(1, licenseplate)
            statement.setString(2, model)
            statement.setString(3, brand)
            statement.setLong(4, autoparkId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getCar(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_car($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("licenseplates"),
                    resultSet.getString("models"),
                    resultSet.getString("brands"),
                    resultSet.getLong("autopark_ids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateCar(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_car(${data[0].toInt()}, " +
                        "${data[1]}, ${data[2]}, ${data[3]}, ${data[4].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCar(carId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_car(?)")
            statement.setLong(1, carId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createAdditionalService(name: String, cost: Float, description: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_additional_service(?, ?, ?)")
            statement.setString(1, name)
            statement.setFloat(2, cost)
            statement.setString(3, description)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getAdditionalService(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_autopark($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getBigDecimal("costs").toString(),
                    resultSet.getString("descriptions")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateAdditionalService(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_additional_service(${data[0].toInt()}, ${data[1]}, ${data[2].toFloat()}, ${data[3]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteAdditionalService(serviceId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_additional_service(?)")
            statement.setLong(1, serviceId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCustomer(lastname: String, firstname: String, patronymic: String, phone: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_customer(?, ?, ?, ?)")
            statement.setString(1, lastname)
            statement.setString(2, firstname)
            statement.setString(3, patronymic)
            statement.setString(4, phone)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getCustomer(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_customer($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("lastnames"),
                    resultSet.getString("firstnames"),
                    resultSet.getString("patronymics"),
                    resultSet.getString("phones")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateCustomer(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_customer(${data[0].toInt()}, ${data[1]}, ${data[2]}, ${data[3]}, ${data[4]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCustomer(customerId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_customer(?)")
            statement.setLong(1, customerId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCargoClass(name: String, description: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_cargo_class(?, ?)")
            statement.setString(1, name)
            statement.setString(2, description)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getCargoClass(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_cargo_class($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getString("descriptions")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateCargoClass(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL update_cargo_class(${data[0].toInt()}, ${data[1]}, ${data[2]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCargoClass(cargoClassId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_cargo_class(?)")
            statement.setLong(1, cargoClassId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCargo(name: String, weight: Float, volume: Float, contractId: Long, classCargosId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_cargo(?, ?, ?, ?, ?)")
            statement.setString(1, name)
            statement.setFloat(2, weight)
            statement.setFloat(3, volume)
            statement.setLong(4, contractId)
            statement.setLong(5, classCargosId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getCargo(inId: Int): List<String> {
    var result = listOf<String>()
    println("SELECT * FROM get_cargo($inId)")
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_cargo($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getBigDecimal("weights").toString(),
                    resultSet.getBigDecimal("volumes").toString(),
                    resultSet.getLong("contract_ids").toString(),
                    resultSet.getLong("class_cargos_ids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateCargo(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_cargo(${data[0].toInt()}, ${data[1]}, ${data[2].toFloat()}, " +
                        "${data[3].toFloat()}, ${data[4].toInt()}, ${data[5].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCargo(cargoId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_cargo(?)")
            statement.setLong(1, cargoId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createJob(name: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_job(?)")
            statement.setString(1, name)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getJob(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_job($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateJob(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL update_job(${data[0].toInt()}, ${data[1]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteJob(jobId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_job(?)")
            statement.setLong(1, jobId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createEmployee(lastname: String, firstname: String, patronymic: String, dateOfBirth: LocalDate, phone: String, passportData: String, workdays: List<String>, login: String, password: String, jobId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
            statement.setString(1, lastname)
            statement.setString(2, firstname)
            statement.setString(3, patronymic)
            statement.setObject(4, dateOfBirth)
            statement.setString(5, phone)
            statement.setString(6, passportData)
            statement.setArray(7, connection.createArrayOf("text", workdays.toTypedArray()))
            statement.setString(8, login)
            statement.setString(9, password)
            statement.setLong(10, jobId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getEmployee(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_employee($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                val workdaysArray = resultSet.getArray("workdayss").array as Array<*>
                val workdaysList = workdaysArray.map { it.toString() }
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("lastnames"),
                    resultSet.getString("firstnames"),
                    resultSet.getString("patronymics"),
                    resultSet.getObject("date_of_births", LocalDate::class.java).toString(),
                    resultSet.getString("phones"),
                    resultSet.getString("passport_datas"),
                    workdaysList.joinToString(", "),
                    resultSet.getString("logins"),
                    resultSet.getString("passwords"),
                    resultSet.getLong("job_ids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateEmployee(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_employee(${data[0].toInt()}, ${data[1]}, " +
                        "${data[2]}, ${data[3]}, ${data[4]}, ${data[5]}, ${data[6]}," +
                        " ${connection.createArrayOf("text", arrayOf(data[7]))}, " +
                        "${data[8]}, ${data[9]}, ${data[10].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteEmployee(employeeId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_employee(?)")
            statement.setLong(1, employeeId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createDestinationPoint(name: String, address: String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_destination_point(?, ?)")
            statement.setString(1, name)
            statement.setString(2, address)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getDestinationPoint(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_destinationpoint($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getString("addresss")
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateDestinationPoint(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_destination_point(${data[0].toInt()}, ${data[1]}, ${data[2]})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteDestinationPoint(pointId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_destination_point(?)")
            statement.setLong(1, pointId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createContract(name: String, dateStart: LocalDate, dateEnd: LocalDate, price: Float, additionalServices: List<Long>, cargos: List<Long>, customerId: Long, autoparkId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_contract(?, ?, ?, ?, ?, ?, ?, ?)")
            statement.setString(1, name)
            statement.setObject(2, dateStart)
            statement.setObject(3, dateEnd)
            statement.setFloat(4, price)
            statement.setArray(5, connection.createArrayOf("bigint", additionalServices.toTypedArray()))
            statement.setArray(6, connection.createArrayOf("bigint", cargos.toTypedArray()))
            statement.setLong(7, customerId)
            statement.setLong(8, autoparkId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getContract(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_contract($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                val additionalServicesArray = resultSet.getArray("additional_servicess").array as Array<*>
                val additionalServicesList = additionalServicesArray.map { it.toString() }
                val cargosArray = resultSet.getArray("cargoss").array as Array<*>
                val cargosList = cargosArray.map { it.toString() }
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getObject("date_starts", LocalDate::class.java).toString(),
                    resultSet.getObject("date_ends", LocalDate::class.java).toString(),
                    resultSet.getBigDecimal("prices").toString(),
                    additionalServicesList.joinToString(", "),
                    cargosList.joinToString(", "),
                    resultSet.getLong("customer_ids").toString(),
                    resultSet.getLong("autopark_ids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateContract(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_contract(${data[0].toInt()}, ${data[1]}, " +
                        "${data[2]}, ${data[3]}, ${data[4].toFloat()}, " +
                        "${connection.createArrayOf("bigint", arrayOf(data[5]))}, " +
                        "${connection.createArrayOf("bigint", arrayOf(data[6]))}, " +
                        "${data[7].toInt()}, ${data[8].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContract(contractId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contract(?)")
            statement.setLong(1, contractId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createContractAdditionalService(contractId: Long, additionalServiceId: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_contract_additional_service(?, ?)")
            statement.setLong(1, contractId)
            statement.setLong(2, additionalServiceId)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getContractAdditionalService(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_contract_additional_service($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getLong("contractids").toString(),
                    resultSet.getLong("additionalserviceids").toString()
                )
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}
fun updateContractAdditionalService(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_contract_additional_service(${data[0].toInt()}, " +
                        "${data[1].toInt()}, ${data[2].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContractAdditionalService(id: Long) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contract_additional_service(?)")
            statement.setLong(1, id)
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}