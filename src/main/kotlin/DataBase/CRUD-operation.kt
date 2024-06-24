package DataBase

import java.math.BigDecimal
import java.sql.*
import java.util.Date

fun insertContact(lastname: String, firstname: String, patronymic: String, phone: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_contact('$lastname', '$firstname', '$patronymic', '$phone')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertAutopark(name: String, address: String, contactpersonId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_autopark('$name', '$address', $contactpersonId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertCar(licenseplate: String, cartype: String, model: String, brand: String, autoparkId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_car('$licenseplate', '$cartype', '$model', '$brand', $autoparkId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertAdditionalService(name: String, cost: BigDecimal, validityPeriod: String, description: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_additional_service('$name', $cost, '$validityPeriod', '$description')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertCustomer(lastname: String, firstname: String, patronymic: String, phone: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_customer('$lastname', '$firstname', '$patronymic', '$phone')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertCargo(name: String, weight: BigDecimal, volume: BigDecimal, dimensions: String, hazardClassification: String, description: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_cargo('$name', $weight, $volume, '$dimensions', '$hazardClassification', '$description')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertJob(name: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_job('$name')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertEmployee(lastname: String, firstname: String, patronymic: String, dateOfBirth: Date, phone: String, passportData: String, workdays: Array<String>, login: String, password: String, jobId: Long, carsId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_employee('$lastname', '$firstname', '$patronymic', '$dateOfBirth', '$phone', '$passportData', ARRAY[${workdays.joinToString(",")}]::text[], '$login', '$password', $jobId, $carsId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertDestinationPoint(city: String, address: String, arrivalDate: Date, arrivalTime: Time, status: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_destinationpoint('$city', '$address', '$arrivalDate', '$arrivalTime', '$status')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertTrip(departureDatetime: Timestamp, destinationPointId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_trip('$departureDatetime', $destinationPointId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertTripDriver(tripId: Long, driverId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_tripdriver($tripId, $driverId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertContract(phone: String, conclusionDate: Date, customerId: Long, managerId: Long, cargoId: Long, tripId: Long, departureAddress: String, cost: BigDecimal, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_contract('$phone', '$conclusionDate', $customerId, $managerId, $cargoId, $tripId, '$departureAddress', $cost)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun insertContractAdditionalService(contractId: Long, additionalServiceId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL insert_contractadditionalservice($contractId, $additionalServiceId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateContact(contactId: Long, newFirstname: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_contact($contactId, '$newFirstname')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateAutopark(autoparkId: Long, newAddress: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_autopark($autoparkId, '$newAddress')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateCar(carId: Long, newBrand: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_car($carId, '$newBrand')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateAdditionalService(serviceId: Long, newCost: BigDecimal, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_additional_service($serviceId, $newCost)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateCustomer(customerId: Long, newFirstname: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_customer($customerId, '$newFirstname')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateCargo(cargoId: Long, newWeight: BigDecimal, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_cargo($cargoId, $newWeight)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateJob(jobId: Long, newName: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_job($jobId, '$newName')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateEmployee(employeeId: Long, newLastname: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_employee($employeeId, '$newLastname')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateDestinationPoint(destinationPointId: Long, newCity: String, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_destinationpoint($destinationPointId, '$newCity')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateTrip(tripId: Long, newDepartureDatetime: Timestamp, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_trip($tripId, '$newDepartureDatetime')")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateContract(contractId: Long, newCost: BigDecimal, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_contract($contractId, $newCost)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteContact(contactId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_contact($contactId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteAutopark(autoparkId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_autopark($autoparkId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteCar(carId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_car($carId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteAdditionalService(serviceId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_additional_service($serviceId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteCustomer(customerId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_customer($customerId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteCargo(cargoId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_cargo($cargoId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteJob(jobId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_job($jobId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteEmployee(employeeId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_employee($employeeId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteDestinationPoint(destinationPointId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_destinationpoint($destinationPointId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteTrip(tripId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_trip($tripId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteContract(contractId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_contract($contractId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateTripDriver(tripDriverId: Long, newDriverId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_tripdriver($tripDriverId, $newDriverId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateContractAdditionalService(contractServiceId: Long, newServiceId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL update_contractadditionalservice($contractServiceId, $newServiceId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteTripDriver(tripDriverId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_tripdriver($tripDriverId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun deleteContractAdditionalService(contractServiceId: Long, user: String, pass: String) {
    try {
        DataBasePostgres.getConnection(user, pass).use { connection ->
            val statement = connection.createStatement()
            statement.execute("CALL delete_contractadditionalservice($contractServiceId)")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}
