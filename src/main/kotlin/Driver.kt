import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DriverMainPage(onLoginSuccess: (userData: String, page: Pages) -> Unit, onLogout: (Pages) -> Unit, driverID: String) {
    var activeTrips by remember { mutableStateOf(listOf(listOf(""))) }
    var dialogWindow by remember { mutableStateOf(false) }
    var currentId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        activeTrips = getActiveTrip(driverID)
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
                        onLoginSuccess(currentId, Pages.FillInfoTripDriver)
                    }) {
                        Text("Просмотреть полную информацию", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.CargosWithTripDriver)
                    }) {
                        Text("Просмотреть грузы", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.DepartPointsDriver)
                    }) {
                        Text("Просмотреть точки назначения", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(currentId, Pages.Declaration)
                    }) {
                        Text("Создать декларацию на грузы", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    MainScaffold(
        title = "Водитель",
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Активные рейсы", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableHeader(headers = listOf("ID", "ФИО водителя", "ФИО клиента", "Дата оформления"))
                LazyColumn {
                    items(activeTrips.size) { index ->
                        val trip = activeTrips[index]
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    dialogWindow = true
                                    currentId = trip[0]
                                }
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
fun FillInfoTripDriver(onLogout: (Pages) -> Unit = {}) {
    MainScaffold(
        title = "Водитель",
        onLogout = onLogout
    ){

    }
}

@Composable
fun CargosWithTripDriver(onLogout: (Pages) -> Unit = {}) {
    MainScaffold(
        title = "Водитель",
        onLogout = onLogout
    ){

    }
}

@Composable
fun DepartPointsDriver(onLogout: (Pages) -> Unit = {}) {
    MainScaffold(
        title = "Водитель",
        onLogout = onLogout
    ){

    }
}

@Composable
fun Declaration(onLogout: (Pages) -> Unit, contractID: String) {
    var declaration by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        declaration = generateCargoDeclaration(contractID)
    }

    MainScaffold(
        title = "Водитель",
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
                            Modifier
                                .fillMaxWidth()
                        ) {
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
                onClick = { saveToCsv(declaration, contractID) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Сохранить в CSV")
            }
        }
    }
}
