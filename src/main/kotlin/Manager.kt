import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.FillInfoTripManager)
                    }) {
                        Text("Просмотреть полную информацию", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.AdditionalServicesContract)
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
fun PreliminaryCost(onLogout: (Pages) -> Unit) {
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
fun DataManager(onLogout: (Pages) -> Unit,
    onLoginSuccess: (title: String, head: List<String>, table: String, currentPagess: Pages, page: Pages) -> Unit
){
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
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                item {
                    Text("Грузы", fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onLoginSuccess(
                                    "Менеджер",
                                    listOf("ID", "Наименование", "Вес", "Объем", "ID договора", "ID класса груза"),
                                    "Грузы",
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
                                    "Менеджер",
                                    listOf("ID", "Название", "Описание"),
                                    "Классификация грузов",
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
                                    "Менеджер",
                                    listOf("ID", "Название", "Стоимость", "Описание"),
                                    "Дополнительные услуги",
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
                                    "Менеджер",
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
                                    "Менеджер",
                                    listOf("ID", "Фамилия", "Имя", "Отчество", "Телефон"),
                                    "Клиенты",
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
                                    "Менеджер",
                                    listOf("ID","Дата заключения","Стоимость","ID клиента",
                                        "ID менеджера","ID водителя","ID автомобиля","ID точки назначения", "Статус"),
                                    "Договоры",
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

