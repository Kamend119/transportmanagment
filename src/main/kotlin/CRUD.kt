import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun createContact(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_contact('${data[0]}'::varchar, '${data[1]}'::varchar, '${data[2]}'::varchar, '${data[3]}'::text)")
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
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_contact($inId)")
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
            val statement: PreparedStatement = connection.prepareStatement("CALL update_contact(${data[0].toInt()},::int " +
                    "'${data[1]}'::varchar, '${data[2]}'::varchar, '${data[3]}'::varchar, '${data[4]}'::text)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContact(contactId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contact($contactId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createAutopark(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_autopark('${data[0]}'::varchar, '${data[1]}'::varchar, ${data[2].toInt()}::int)")
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
            connection.prepareStatement("CALL update_autopark(${data[0].toInt()}::int, '${data[1]}'::varchar, '${data[2]}'::varchar, ${data[3].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteAutopark(autoparkId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_autopark($autoparkId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCar(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_car('${data[0]}'::varchar, '${data[1]}'::varchar, '${data[2]}'::varchar, ${data[3].toInt()}::int)")
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
                connection.prepareStatement("CALL update_car(${data[0].toInt()}::int, " +
                        "'${data[1]}'::varchar, '${data[2]}'::varchar, '${data[3]}'::varchar, ${data[4].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCar(carId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_car($carId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createAdditionalService(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_additional_service('${data[0]}'::varchar, ${data[1].toFloat()}, '${data[2]}'::text)")
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
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_additional_service($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getFloat("costs").toString(),
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
                connection.prepareStatement("CALL update_additional_service(${data[0].toInt()}::int, '${data[1]}'::varchar, ${data[2].toFloat()}, '${data[3]}'::text)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteAdditionalService(serviceId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_additional_service($serviceId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCustomer(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_customer('${data[0]}'::varchar, '${data[1]}'::varchar, '${data[2]}'::varchar, '${data[3]}'::text)")
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
                connection.prepareStatement("CALL update_customer(${data[0].toInt()}::int, '${data[1]}'::varchar, '${data[2]}'::varchar, '${data[3]}'::varchar, '${data[4]}'::text)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCustomer(customerId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_customer($customerId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCargoClass(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_cargo_class('${data[0]}'::varchar, '${data[1]}'::text)")
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
            val statement: PreparedStatement = connection.prepareStatement("CALL update_cargo_class(${data[0].toInt()}::int, '${data[1]}'::varchar, '${data[2]}'::text)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCargoClass(cargoClassId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_cargo_class($cargoClassId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createCargo(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_cargo('${data[0]}', ${data[1].toFloat()}, " +
                        "${data[2].toFloat()}, ${data[3].toInt()}::int, ${data[4].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getCargo(inId: Int): List<String> {
    var result = listOf<String>()
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_cargo($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("names"),
                    resultSet.getFloat("weights").toString(),
                    resultSet.getFloat("volumes").toString(),
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
            val statement: PreparedStatement = connection.prepareStatement(
                "CALL update_cargo(${data[0].toInt()}::int, '${data[1]}'::varchar, ${data[2].toFloat()}, ${data[3].toFloat()}, ${data[4].toInt()}::int, ${data[5].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteCargo(cargoId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_cargo($cargoId::int)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createJob(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("CALL create_job('${data[0]}'::varchar)")
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
            val statement: PreparedStatement = connection.prepareStatement("CALL update_job(${data[0].toInt()}::int, '${data[1]}'::varchar)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteJob(jobId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_job($jobId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createEmployee(data: List<String>, inDay: List<String>, pasport: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val pasportData = "'{\"series\": \"${pasport[0]}\", \"number\": \"${pasport[1]}\", \"issued_by\": \"${pasport[3]}\", \"issued_date\": \"${pasport[4]}\"}'"
            var day = "'{"
            for (i in inDay){
                day += " '$i',"
            }
            day.dropLast(1)
            day += "}'"

            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_employee('${data[0]}', '${data[1]}', '${data[2]}', " +
                        "'${data[3]}', '${data[4]}', $pasportData, " +
                        "$day, '${data[5]}', '${data[6]}', ${data[7].toInt()})")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun getEmployee(inId: Int): Triple<List<String>, List<String>, List<String>> {
    var generalData = listOf<String>()
    var passportData = listOf<String>()
    var workDays = listOf<String>()

    val mapper = jacksonObjectMapper()

    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM get_employee($inId)")
            val resultSet: ResultSet = statement.executeQuery()
            if (resultSet.next()) {
                generalData = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("lastnames"),
                    resultSet.getString("firstnames"),
                    resultSet.getString("patronymics"),
                    resultSet.getString("dateofbirths"),
                    resultSet.getString("phones"),
                    resultSet.getString("logins"),
                    resultSet.getInt("job_ids").toString()
                )

                // Разбор JSON строки для passportData
                val passportJson = resultSet.getString("passport_datas")
                val passportMap: Map<String, String> = mapper.readValue(passportJson)
                passportData = listOf(
                    passportMap["series"].orEmpty(),
                    passportMap["number"].orEmpty(),
                    passportMap["issued_by"].orEmpty(),
                    passportMap["issued_date"].orEmpty()
                )

                workDays = resultSet.getString("workdayss").split(",")
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }

    return Triple(generalData, passportData, workDays)
}
fun updateEmployee(generalData: List<String>, workDays: List<String>, passportData: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val passportJson = "'{\"series\": \"${passportData[0]}\", \"number\": \"${passportData[1]}\", \"issued_by\": \"${passportData[2]}\", \"issued_date\": \"${passportData[3]}\"}'"
            val workDaysArray = workDays.joinToString(prefix = "'{", postfix = "}'", separator = ", ")
            val statement: PreparedStatement =
                connection.prepareStatement("CALL update_employee(${generalData[0].toInt()}::int, '${generalData[1]}'::varchar, '${generalData[2]}'::varchar, '${generalData[3]}'::varchar, '${generalData[4]}'::date, '${generalData[5]}'::text, $passportJson::json, $workDaysArray::text[], '${generalData[6]}'::varchar, '${generalData[8]}'::varchar, ${generalData[7].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteEmployee(employeeId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_employee($employeeId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createDestinationPoint(data: List<String>, type : String) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_destinationpoint('$type', '${data[0]}'::varchar, '${data[1]}'::varchar, " +
                        "'${data[2]}'::date, 'В работе', ${data[3].toInt()}::int)")
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
                    resultSet.getString("types"),
                    resultSet.getString("citys"),
                    resultSet.getString("addresss"),
                    resultSet.getString("arrivaldates"),
                    resultSet.getString("statuss"),
                    resultSet.getInt("contract_ids").toString()
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
                connection.prepareStatement("CALL update_destinationpoint(${data[0].toInt()}::int, '${data[1]}', '${data[2]}'::varchar, '${data[3]}'::varchar, " +
                        "                        '${data[4]}'::date, '${data[5]}', ${data[6].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteDestinationPoint(pointId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_destinationpoint($pointId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createContract(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_contract(${data[0].toInt()}::int, ${data[1].toInt()}::int, " +
                        "${data[2].toInt()}::int, ${data[3].toInt()}::int)")
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
                result = listOf(
                    resultSet.getInt("ids").toString(),
                    resultSet.getString("conclusiondates"),
                    resultSet.getFloat("costs").toString(),
                    resultSet.getInt("customer_ids").toString(),
                    resultSet.getInt("manager_ids").toString(),
                    resultSet.getInt("driver_ids").toString(),
                    resultSet.getInt("car_ids").toString()
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
                connection.prepareStatement("CALL update_contract(${data[0].toInt()}::int, '${data[1]}'::date, " +
                        "${data[2].toInt()}::int, ${data[3].toInt()}::int, ${data[4].toInt()}::int, ${data[5].toInt()}::int")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContract(contractId: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contract($contractId)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun createContractAdditionalService(data: List<String>) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement =
                connection.prepareStatement("CALL create_contract_additional_service(${data[0].toInt()}::int, ${data[1].toInt()}::int)")
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
                connection.prepareStatement("CALL update_contract_additional_service(${data[0].toInt()}::int, " +
                        "${data[1].toInt()}::int, ${data[2].toInt()}::int)")
            statement.executeUpdate()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
fun deleteContractAdditionalService(id: Int) {
    try {
        DataBasePostgres.getConnection().use { connection ->
            val statement: PreparedStatement = connection.prepareStatement("SELECT delete_contract_additional_service($id)")
            statement.executeQuery()
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}