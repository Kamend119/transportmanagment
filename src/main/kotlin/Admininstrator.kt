import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun AdministratorMainPage(onLogout: (Pages) -> Unit, onLoginSuccess: (userData: String, page: Pages) -> Unit) {
    var data by remember { mutableStateOf(listOf(listOf(""))) }
    var dialogWindow by remember { mutableStateOf(false) }
    var currentId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        data = viewActiveContractInfo()
    }

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Рейс №$currentId.") },
            text = { Text("Выберите действие") },
            buttons = {
                Column(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.FillInfoTripAdministrator)
                    }) {
                        Text("Просмотреть полную информацию", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.DeclarationAdministrator)
                    }) {
                        Text("Создать декларацию на грузы", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Активные договоры", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("ID", "ФИО менеджера", "ФИО клиента", "Дата заключения"))
                LazyColumn {
                    items(data.size) { index ->
                        val trip = data[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    dialogWindow = true
                                    currentId = trip[0]
                                }
                                .background(Color.Transparent)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            trip.forEach { item ->
                                TableCell(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PreliminaryCostAdmin(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(0.0) }
    var cityFrom by remember { mutableStateOf("") }
    var cityTo by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf(0.0) }
    var volume by remember { mutableStateOf(0.0) }
    var dialogWindow by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    if (dialogWindow) {
        if (error) {
            AlertDialog(
                onDismissRequest = {
                    dialogWindow = false
                    error = false
                },
                title = { Text(text = "Не верные данные") },
                text = { Text("Данные не должны быть пустыми!") },
                confirmButton = {
                    Button(onClick = {
                        dialogWindow = false
                        error = false
                    }) {
                        Text("OK", fontSize = 22.sp)
                    }
                }
            )
        } else {
            AlertDialog(
                onDismissRequest = { dialogWindow = false },
                title = { Text(text = "Стоимость грузоперевозки") },
                text = { Text("Предварительная стоимость = $data") },
                confirmButton = {
                    Button(onClick = { dialogWindow = false }) {
                        Text("OK", fontSize = 22.sp)
                    }
                }
            )
        }
    }

    MainScaffold(
        title = "Администратор",  // Ensure that the title is set to "Администратор"
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                "Предварительная стоимость грузоперевозки",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                cityFrom,
                onValueChange = { cityFrom = it },
                label = { Text("Город отправления") }
            )

            OutlinedTextField(
                cityTo,
                onValueChange = { cityTo = it },
                label = { Text("Город доставки") }
            )

            OutlinedTextField(
                weight.toString(),
                onValueChange = { weight = it.toDoubleOrNull() ?: 0.0 },
                label = { Text("Вес") }
            )

            OutlinedTextField(
                volume.toString(),
                onValueChange = { volume = it.toDoubleOrNull() ?: 0.0 },
                label = { Text("Объем") }
            )

            Button(onClick = {
                if (cityTo.isNotEmpty() && cityFrom.isNotEmpty() && weight != 0.0 && volume != 0.0) {
                    data = calculatePreliminaryCost(cityFrom, cityTo, weight, volume)
                    dialogWindow = true
                } else {
                    dialogWindow = true
                    error = true
                }
            }) {
                Text("Рассчитать")
            }
        }
    }
}

@Composable
fun ReportsAdministrator(onLogout: (Pages) -> Unit){
    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Отчетность", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                item {
                    Text("Статистика менеджеров", fontSize=18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { onLogout(Pages.ContractsSummaryForManagers) }
                            .padding(15.dp))
                    Text("Статистика водителей", fontSize=18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { onLogout(Pages.DriverPerformance) }
                            .padding(15.dp))
                }
            }
        }
    }
}

@Composable
fun ContractsSummaryForManagers(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(listOf(listOf(""))) }
    var startDate by remember { mutableStateOf(TextFieldValue("")) }
    var endDate by remember { mutableStateOf(TextFieldValue("")) }
    var dialogWindow by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var savePath by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Ошибка ввода данных") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = { dialogWindow = false }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }

    if (showDialog) {
        SelectFileDialog(onDialogDismiss = { selectedPath ->
            showDialog = false
            savePath = selectedPath
            if (savePath.isNotEmpty()) {
                saveToCsv(data, "contracts_summary", savePath)
            }
        })
    }

    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Начало периода") }
                )

                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Конец периода") }
                )

                Button(onClick = {
                    val startDateString = startDate.text
                    val endDateString = endDate.text
                    try {
                        val start = LocalDate.parse(startDateString, dateFormatter)
                        val end = LocalDate.parse(endDateString, dateFormatter)
                        if (start.isAfter(end)) {
                            dialogMessage = "Дата начала не может быть позже даты окончания!"
                            dialogWindow = true
                        } else {
                            data = contractsSummaryForPeriod(startDateString, endDateString)
                        }
                    } catch (e: DateTimeParseException) {
                        dialogMessage = "Введите даты в формате день-месяц-год"
                        dialogWindow = true
                    }
                }) {
                    Text("Расчитать")
                }

                Button(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Text("Сохранить в CSV")
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("ФИО менеджера", "Количество договоров", "Общая сумма"))
                LazyColumn {
                    items(data.size) { index ->
                        val trip = data[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            trip.forEach { item ->
                                TableCell(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DriverPerformance(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(listOf(listOf(""))) }
    var startDate by remember { mutableStateOf(TextFieldValue("")) }
    var endDate by remember { mutableStateOf(TextFieldValue("")) }
    var dialogWindow by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var savePath by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text("Ошибка ввода данных") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = { dialogWindow = false }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }

    if (showDialog) {
        SelectFileDialog(onDialogDismiss = { selectedPath ->
            showDialog = false
            savePath = selectedPath
            if (savePath.isNotEmpty()) {
                saveToCsv(data, "driver_performance", savePath)
            }
        })
    }

    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Начало периода") }
                )

                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Конец периода") }
                )

                Button(onClick = {
                    val startDateString = startDate.text
                    val endDateString = endDate.text
                    try {
                        val start = LocalDate.parse(startDateString, dateFormatter)
                        val end = LocalDate.parse(endDateString, dateFormatter)
                        if (start.isAfter(end)) {
                            dialogMessage = "Дата начала не может быть позже даты окончания!"
                            dialogWindow = true
                        } else {
                            data = driverPerformanceForPeriod(startDateString, endDateString)
                        }
                    } catch (e: DateTimeParseException) {
                        dialogMessage = "Введите даты в формате день-месяц-год"
                        dialogWindow = true
                    }
                }) {
                    Text("Расчитать")
                }

                Button(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Text("Сохранить в CSV")
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("ФИО водителя", "Количество договоров", "Общее количество часов"))
                LazyColumn {
                    items(data.size) { index ->
                        val trip = data[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            trip.forEach { item ->
                                TableCell(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataAdministrator(onLogout: (Pages) -> Unit,
                onLoginSuccess: (title: String, head: List<String>, table: String, currentPagess: Pages, page: Pages) -> Unit
){
    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Данные", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                item {
                    Text("Контактные лица", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Фамилия", "Имя", "Отчество", "Номер телефона"),
                                    "Контактные лица",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Автопарки", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Наименование", "Адрес", "ID контактное лицо"),
                                    "Автопарки",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Автомобили", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Гос. номер", "Модель", "Производитель", "ID автопарка"),
                                    "Автомобили",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Должности", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Название"),
                                    "Должности",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Сотрудники", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Фамилия", "Имя", "Отчество", "Дата рождения",
                                        "Номер телефона", "Паспортные данные", "Рабочие дни",
                                        "Логин", "ID Должности"),
                                    "Сотрудники",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Точки назначения", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Тип", "Город", "Адрес", "Дата прибытия", "Статус"),
                                    "Точки назначения",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Клиенты", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Фамилия", "Имя", "Отчество", "Телефон"),
                                    "Клиенты",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Дополнительные услуги", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Название", "Стоимость", "Описание"),
                                    "Дополнительные услуги",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Договоры", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID","Дата заключения","Стоимость","ID клиента",
                                        "ID менеджера","ID водителя","ID автомобиля","ID точки назначения", "Статус"),
                                    "Договоры",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Классификация грузов", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Название", "Описание"),
                                    "Классификация грузов",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Грузы", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Наименование", "Вес", "Объем", "ID договора", "ID класса груза"),
                                    "Грузы",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Договор Дополнительные услуги", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "ID договора", "ID дополнительной услуги"),
                                    "Договор Дополнительные услуги",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                    Text("Аудит", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Администратор",
                                    listOf("ID", "Таблица", "Операция", "Кем", "Дата", "Старые данные", "Новые данные"),
                                    "Аудит",
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DeclarationAdministrator(onLogout: (Pages) -> Unit, contractID: String) {
    var declaration by remember { mutableStateOf(listOf(listOf(""))) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        declaration = generateCargoDeclaration(contractID)
    }

    if (showDialog) {
        val filePath = SelectFileDialog {
            showDialog = false
        }
        println(filePath)
    }

    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Декларация №$contractID", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("Наименование", "Объем", "Вес", "Описание"))
                LazyColumn {
                    items(declaration.size) { index ->
                        val trip = declaration[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        )  {
                            trip.forEach { item ->
                                TableCell(text = item)
                            }
                        }
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    showDialog = true
//                    saveToCsv(declaration, contractID)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Сохранить в CSV")
            }
        }
    }
}

@Composable
fun FillInfoTripAdministrator(onLogout: (Pages) -> Unit, contractID: String) {
    var declaration by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        declaration = getContractInfo(contractID)
    }

    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Договор №$contractID", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)

            val headers = listOf(
                "ID", "Дата заключения договора", "Стоимость", "ФИО клиента",
                "ФИО менеджера", "ФИО водителя", "Номер автомобиля",
                "Модель автомобиля", "Производитель автомобиля", "Тип ", "Город",
                "Адрес", "Дата", "Дополнительные услуги"
            )

            val contractInfo = declaration.getOrNull(0) ?: listOf()

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(headers.size) { index ->
                    val header = headers[index]
                    val value = contractInfo.getOrElse(index) { "N/A" }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TableCell(text = header, isHeader = true)
                        Spacer(modifier = Modifier.width(8.dp))
                        TableCell(text = value)
                    }
                }
            }
        }
    }
}
