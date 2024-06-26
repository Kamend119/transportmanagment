import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManagerDrawerContent(onLogout: (Pages) -> Unit){
    LazyColumn {
        item {
            Text(
                "Доставка", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(15.dp)
            )
            Divider()
            Text("Расчитать предварительную стоимость", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.PreliminaryCost) }
                    .padding(15.dp))
            Divider()
            Text(
                "Данные", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(15.dp)
            )
            Divider()

            Text("Грузы", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.CargosManager) }
                    .padding(15.dp))
            Text("Классификация грузов", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.ClassificationManager) }
                    .padding(15.dp))
            Text("Дополнительные услуги", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.AdditionalServicesManager) }
                    .padding(15.dp))
            Text("Точки назначения", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.DestinationPointsManager) }
                    .padding(15.dp))
            Text("Клиенты", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.CustomerManager) }
                    .padding(15.dp))
            Text("Договоры", fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onLogout(Pages.ContractManager) }
                    .padding(15.dp))
        }
    }
}

@Composable
fun ManagerMainPage( onLoginSuccess: (userData: String, page: Pages) -> Unit,
                     onLogout: (Pages) -> Unit) {
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
fun CargosManager(onLogout: (Pages) -> Unit){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewCargoInfo()
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
            Text("Грузы", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            TableHeader(headers = listOf(
                "ID", "Наименование", "Классификация", "Вес", "Объем"
            ))
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

@Composable
fun ClassificationManager(onLogout: (Pages) -> Unit){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewClassCargosInfo()
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
            Text("Классификация грузов", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            TableHeader(headers = listOf(
                "ID", "Наименование", "Описание"
            ))
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

@Composable
fun AdditionalServicesManager(onLogout: (Pages) -> Unit){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewAdditionalServicesInfo()
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
            Text("Дополнительные услуги", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            TableHeader(headers = listOf(
                "ID", "Наименование", "Стоимость",  "Описание"
            ))
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

@Composable
fun DestinationPointsManager(onLogout: (Pages) -> Unit){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewDestinationPointsInfo()
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
            Text("Точки назначения", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            TableHeader(headers = listOf(
                "ID", "Тип", "Город",  "Адрес", "Дата", "Статус"
            ))
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

@Composable
fun CustomerManager(onLogout: (Pages) -> Unit){
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewCustomersInfo()
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
            Text("Клиенты", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            TableHeader(headers = listOf(
                "ID", "Фамилия", "Имя",  "Отчество", "Номер телефона"
            ))
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

@Composable
fun ContractManager(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(listOf(listOf(""))) }
    var dialogWindow by remember { mutableStateOf(false) }
    var currentId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        data = viewContractInfo()
    }

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Договор №$currentId.") },
            text = { Text("Выберите действие") },
            buttons = {
                Column(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        dialogWindow = false
                        //onLoginSuccess(currentId, Pages.FillInfoTripManager)
                    }) {
                        Text("Изменить данные", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        //
                    }) {
                        Text("Удалить данные", fontSize = 15.sp)
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
            Text("Договор", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)

            val headers = listOf(
                "ID", "Дата заключения договора", "Стоимость",
                "ФИО менеджера", "ФИО водителя", "Производитель автомобиля",
                "Модель автомобиля", "Номер автомобиля", "Грузы",
                "Точки назначения", "Дополнительные услуги"
            )

            Column(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    headers.forEach { header ->
                        TableCell(text = header, isHeader = true)
                    }
                }

                LazyColumn {
                    items(data) { rowData ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    dialogWindow = true
                                    currentId = rowData[0]
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            rowData.forEach { cellData ->
                                TableCell(text = cellData)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContractUpdateManager(){

}