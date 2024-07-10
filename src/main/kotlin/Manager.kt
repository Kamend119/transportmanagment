import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManagerMainPage(
    onLoginSuccess: (userData: String, page: Pages) -> Unit,
    onLogout: (page: Pages) -> Unit
) {
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
                        onLoginSuccess(currentId, Pages.FillInfoTripManager)
                        println("gthdsq $currentId")
                        dialogWindow = false
                    }) {
                        Text("Просмотреть полную информацию", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        onLoginSuccess(currentId, Pages.AdditionalServicesContract)
                        dialogWindow = false
                    }) {
                        Text("Просмотреть дополнительные услуги в договоре", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    MainScaffold(
        title = "Менеджер",
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
                                    currentId = trip[0]
                                    dialogWindow = true
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
fun PreliminaryCostManager(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(0.0) }
    var cityFrom by remember { mutableStateOf("") }
    var cityTo by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf(0.0) }
    var volume by remember { mutableStateOf(0.0) }
    var dialogWindow by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    if (dialogWindow) {
        if (error){
            AlertDialog(
                onDismissRequest = { dialogWindow = false
                    error = false},
                title = { Text(text = "Не верные данные") },
                text = { Text("Данные не должны быть пустыми!") },
                confirmButton = {
                    Button(onClick = { dialogWindow = false
                    error = false}) {
                        Text("OK", fontSize = 22.sp)
                    }
                }
            )
        }
        else {
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
        title = "Менеджер",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Предварительная стоимость грузоперевозки", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)


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
                onValueChange = { weight = it.toDouble() },
                label = { Text("Вес") }
            )

            OutlinedTextField(
                volume.toString(),
                onValueChange = { volume = it.toDouble() },
                label = { Text("Объем") }
            )

            Button(onClick = {
                if (cityTo.isNotEmpty() && cityFrom.isNotEmpty() && weight != 0.0 && volume != 0.0) {
                    data = calculatePreliminaryCost(cityFrom, cityTo, weight, volume)
                    dialogWindow = true
                }
                else {
                    dialogWindow = true
                    error = true
                }
            }){
                Text("Расчитать")
            }
        }
    }
}

@Composable
fun FillInfoTripManager(onLogout: (Pages) -> Unit, contractID: String) {
    var declaration by remember { mutableStateOf(listOf(listOf(""))) }
println(contractID)
    LaunchedEffect(Unit) {
        declaration = getContractInfo(contractID)
    }

    MainScaffold(
        title = "Менеджер",
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

@Composable
fun AdditionalServicesContract(onLogout: (Pages) -> Unit, contractID: String){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = getAdditionalServices(contractID.toInt())
    }

    MainScaffold(
        title = "Менеджер",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Грузы в договоре №$contractID", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("Наименование", "Стоимость", "Описание"))
                LazyColumn {
                    items(data.size) { index ->
                        val trip = data[index]
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
    }
}

@Composable
fun DataManager(
    onLogout: (Pages) -> Unit,
    onLoginSuccess: (title: String, head: List<String>, table: String, currentPagess: Pages, page: Pages) -> Unit
) {
    val dataItems = listOf(
        DataItem("Менеджер", listOf("ID", "Наименование", "Вес", "Объем", "ID договора", "ID класса груза"), "Грузы", 0),
        DataItem("Менеджер", listOf("ID", "Название", "Описание"), "Классификация грузов", 1),
        DataItem("Менеджер", listOf("ID", "Название", "Стоимость", "Описание"), "Дополнительные услуги", 2),
        DataItem("Менеджер", listOf("ID", "Тип", "Город", "Адрес", "Дата прибытия", "Статус", "ID договора"), "Точки назначения", 3),
        DataItem("Менеджер", listOf("ID", "Фамилия", "Имя", "Отчество", "Телефон"), "Клиенты", 4),
        DataItem("Менеджер", listOf("ID", "Дата заключения", "Стоимость", "ID клиента", "ID менеджера", "ID водителя", "ID автомобиля", "Статус"), "Договоры", 5),
        DataItem("Менеджер", listOf("ID", "ID договора", "ID дополнительной услуги"), "Договор Дополнительные услуги", 6)
    )

    val titles = listOf("Грузы", "Классификация\nгрузов", "Дополнительные\nуслуги", "Точки\nназначения",
        "Клиенты", "Договоры", "Договор\nДополнительные\nуслуги")

    MainScaffold(
        title = "Менеджер",
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
                columns = GridCells.Adaptive(150.dp),
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
                            .background(Color(153, 153, 255))
                            .height(100.dp)
                            .clickable {
                                onLoginSuccess(
                                    item.title,
                                    item.headers,
                                    item.table,
                                    Pages.DataManager,
                                    Pages.TablePage
                                )
                            }
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
