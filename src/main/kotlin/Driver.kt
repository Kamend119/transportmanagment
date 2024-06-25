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
fun DriverMainPage(onLogout: (Pages) -> Unit, driverID: String) {
    var activeTrips by remember { mutableStateOf(listOf<List<String>>()) }
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
            confirmButton = {
                Button(onClick = { dialogWindow = false }) {
                    Text("Просмотреть полную информацию о рейсе", fontSize = 22.sp)
                }
                Button(onClick = { dialogWindow = false }) {
                    Text("Просмотреть грузы в рейсе", fontSize = 22.sp)
                }
                Button(onClick = { dialogWindow = false }) {
                    Text("Просмотреть точки назначения", fontSize = 22.sp)
                }
                Button(onClick = { dialogWindow = false }) {
                    Text("Создать декларацию на грузы", fontSize = 22.sp)
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
                                .clickable {
                                dialogWindow = true
                                currentId = index.toString()
                            }
                        ) {
                            TableContent(trip)
                        }
                    }
                }
            }
        }
    }
}
