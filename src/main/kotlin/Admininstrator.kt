import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
                        onLoginSuccess(currentId, Pages.FillInfoTripAdministrator)
                        dialogWindow = false
                    }) {
                        Text("Просмотреть полную информацию", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        onLoginSuccess(currentId, Pages.DeclarationAdministrator)
                        dialogWindow = false
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
    val download = remember { loadImageResource("src/main/resources/images/download.png") }

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
            val titles = listOf("ФИО менеджера", "Количество договоров", "Общая сумма")
            if (savePath.isNotEmpty()) {
                saveToCsv(data, savePath, titles)
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
            Text("Статистика менеджеров", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
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

                    Box(
                        modifier = Modifier
                            .size(37.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(98, 0, 238))
                            .clickable { showDialog = true }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = download,
                            contentDescription = "Сохранить CSV",
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
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
    val download = remember { loadImageResource("src/main/resources/images/download.png") }

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
            val titles = listOf("ФИО водителя", "Количество договоров", "Общее количество часов")
            if (savePath.isNotEmpty()) {
                saveToCsv(data, savePath, titles)
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
            Text("Статистика водителей", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
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

                    Box(
                        modifier = Modifier
                            .size(37.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(98, 0, 238))
                            .clickable { showDialog = true }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = download,
                            contentDescription = "Сохранить CSV",
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
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
fun DataAdministrator(
    onLogout: (Pages) -> Unit,
    onLoginSuccess: (title: String, head: List<String>, table: String, currentPagess: Pages, page: Pages) -> Unit
) {
    val dataItems = listOf(
        DataItem("Контактные лица", listOf("ID", "Фамилия", "Имя", "Отчество", "Номер телефона"), "Контактные лица", 0),
        DataItem("Автопарки", listOf("ID", "Наименование", "Адрес", "ID контактное лицо"), "Автопарки", 1),
        DataItem("Автомобили", listOf("ID", "Гос. номер", "Модель", "Производитель", "ID автопарка"), "Автомобили", 2),
        DataItem("Должности", listOf("ID", "Название"), "Должности", 3),
        DataItem("Сотрудники", listOf("ID", "Фамилия", "Имя", "Отчество", "Дата рождения", "Номер телефона", "Логин", "ID Должности", "Паспортные данные", "Рабочие дни"), "Сотрудники", 4),
        DataItem("Точки назначения", listOf("ID", "Тип", "Город", "Адрес", "Дата прибытия", "Статус", "ID договора"), "Точки назначения", 5),
        DataItem("Клиенты", listOf("ID", "Фамилия", "Имя", "Отчество", "Номер телефона"), "Клиенты", 6),
        DataItem("Дополнительные услуги", listOf("ID", "Название", "Стоимость", "Описание"), "Дополнительные услуги", 7),
        DataItem("Договоры", listOf("ID","Дата заключения","Стоимость","ID клиента", "ID менеджера","ID водителя","ID автомобиля", "Статус"), "Договоры", 8),
        DataItem("Классификация грузов", listOf("ID", "Название", "Описание"), "Классификация грузов", 9),
        DataItem("Грузы", listOf("ID", "Наименование", "Вес", "Объем", "ID договора", "ID класса груза"), "Грузы", 10),
        DataItem("Договор Дополнительные услуги", listOf("ID", "ID договора", "ID дополнительной услуги"), "Договор Дополнительные услуги", 11),
        DataItem("Аудит", listOf("ID", "Таблица", "Операция", "Кем", "Дата", "Старые данные", "Новые данные"), "Аудит", 12)
    )

    val titles = listOf(
        "Контактные\nлица", "Автопарки", "Автомобили", "Должности",
        "Сотрудники", "Точки\nназначения", "Клиенты", "Дополнительные\nуслуги",
        "Договоры", "Классификация\nгрузов", "Грузы", "Договор\nДополнительные\nуслуги", "Аудит"
    )

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

            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dataItems) { item ->
                    Text(
                        text = titles[item.number],
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color(239, 228, 255))
                            .height(100.dp)
                            .clickable {
                                onLoginSuccess(
                                    item.title,
                                    item.headers,
                                    item.table,
                                    Pages.DataAdministrator,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp),
                        textAlign = TextAlign.Center
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
    var savePath by remember { mutableStateOf("") }
    val download = remember { loadImageResource("src/main/resources/images/download.png") }

    LaunchedEffect(Unit) {
        declaration = generateCargoDeclaration(contractID)
    }

    if (showDialog){
        SelectFileDialog(onDialogDismiss = { selectedPath ->
            showDialog = false
            savePath = selectedPath
            val titles = listOf("Наименование", "Объем", "Вес", "Описание")
            if (savePath.isNotEmpty()) {
                saveToCsv(declaration, savePath, titles)
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
                    item{
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(37.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(98, 0, 238))
                                    .clickable { showDialog = true }
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    bitmap = download,
                                    contentDescription = "Сохранить CSV",
                                    modifier = Modifier.size(30.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
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
                "Модель автомобиля", "Производитель автомобиля",
                "Точки назначения", "Дополнительные услуги"
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
                        TableCell(text = header)
                        Spacer(modifier = Modifier.width(8.dp))
                        TableCell(text = value)
                    }
                }
            }
        }
    }
}
