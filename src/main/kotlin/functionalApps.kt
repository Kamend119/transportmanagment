import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.io.File
import java.io.InputStream
import java.io.PrintWriter

fun saveToCsv(data: List<List<String>>, filePath: String, titles: List<String>) {
    val outputFile = File(filePath)
    PrintWriter(outputFile).use { out ->
        out.println(titles)
        data.forEach { row ->
            out.println(row.joinToString(","))
        }
    }
}

fun loadImageResource(path: String): ImageBitmap {
    val inputStream: InputStream = File(path).inputStream()
    return loadImageBitmap(inputStream)
}

fun fetchData(tables: String): List<List<String>> {
    return when (tables) {
        "Контактные лица" -> viewContactsInfo()
        "Автопарки" -> viewAutoparkInfo()
        "Автомобили" -> viewCarInfo()
        "Должности" -> viewJobsInfo()
        "Сотрудники" -> viewEmployeeInfo()
        "Точки назначения" -> viewDestinationPointsInfo()
        "Клиенты" -> viewCustomersInfo()
        "Классификация грузов" -> viewClassCargosInfo()
        "Дополнительные услуги" -> viewAdditionalServicesInfo()
        "Договоры" -> viewContractInfo()
        "Договор Дополнительные услуги" -> viewContractAdditionalService()
        else -> if (tables == "Грузы") viewCargoInfo() else viewAuditLogInfo()
    }
}

fun deleteEntry(tables: String, currentId: String) {
    when (tables) {
        "Контактные лица" -> deleteContact(currentId.toInt())
        "Автопарки" -> deleteAutopark(currentId.toInt())
        "Автомобили" -> deleteCar(currentId.toInt())
        "Должности" -> deleteJob(currentId.toInt())
        "Сотрудники" -> deleteEmployee(currentId.toInt())
        "Точки назначения" -> deleteDestinationPoint(currentId.toInt())
        "Клиенты" -> deleteCustomer(currentId.toInt())
        "Классификация грузов" -> deleteCargoClass(currentId.toInt())
        "Дополнительные услуги" -> deleteAdditionalService(currentId.toInt())
        "Договоры" -> deleteContract(currentId.toInt())
        "Договор Дополнительные услуги" -> deleteContractAdditionalService(currentId.toInt())
        else -> if (tables == "Грузы") deleteCargo(currentId.toInt())
    }
}
