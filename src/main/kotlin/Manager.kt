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
fun ManagerDrawerContent(onLogout: (Pages) -> Unit){
    Column {
        Text("Доставка", fontSize=18.sp,
            modifier = Modifier
                .padding(10.dp)
                .padding(15.dp))
        Divider()
        Text("Расчитать предварительную стоимость", fontSize=18.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable { onLogout(Pages.PreliminaryCost) }
                .padding(15.dp))
    }
}

@Composable
fun ManagerMainPage(onLogout: (Pages) -> Unit) {
    var data by remember { mutableStateOf(listOf(listOf(""))) }

    LaunchedEffect(Unit) {
        data = viewActiveContractInfo()
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

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Не верные данные") },
            text = { Text("Данные не должны быть пустыми!") },
            confirmButton = {
                Button(onClick = { dialogWindow = false }) {
                    Text("OK", fontSize = 22.sp)
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
                if (cityTo.isNotEmpty() && cityFrom.isNotEmpty() && weight != 0.0 && volume != 0.0)
                    data = calculatePreliminaryCost(cityFrom,cityTo,weight,volume)
                else dialogWindow = true
            }){
                Text("Расчитать")
            }
        }
    }
}