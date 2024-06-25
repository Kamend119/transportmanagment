import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdministratorDrawerContent(onLogout: (Pages) -> Unit){
    Column {
        Text("Отчетность", fontSize=18.sp,
            modifier = Modifier
                .padding(10.dp)
                .padding(15.dp))
        Divider()
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

@Composable
fun AdministratorMainPage(onLogout: (Pages) -> Unit) {
    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {

    }
}

@Composable
fun ContractsSummaryForManagers(onLogout: (Pages) -> Unit) {
    var data by remember {mutableStateOf(listOf(listOf("")))}
    var start_date by remember { mutableStateOf("") }
    var end_date by remember { mutableStateOf("") }
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
            Row (
                Modifier.fillMaxWidth()
                    .height(100.dp)
            ){
                OutlinedTextField(
                    start_date,
                    onValueChange = { start_date = it },
                    label = { Text("Начало периода") }
                )

                OutlinedTextField(
                    end_date,
                    onValueChange = { end_date = it },
                    label = { Text("Конец периода") }
                )

                Button(onClick = {
                    if (start_date.isNotEmpty() && end_date.isNotEmpty())
                        data = contractsSummaryForPeriod(start_date, end_date)
                    else dialogWindow = true
                }){
                    Text("Расчитать")
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
                        Row {
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
    var data by remember {mutableStateOf(listOf(listOf("")))}
    var start_date by remember { mutableStateOf("") }
    var end_date by remember { mutableStateOf("") }
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
            Row (
                Modifier.fillMaxWidth()
                    .height(100.dp)
            ){
                OutlinedTextField(
                    start_date,
                    onValueChange = { start_date = it },
                    label = { Text("Начало периода") }
                )

                OutlinedTextField(
                    end_date,
                    onValueChange = { end_date = it },
                    label = { Text("Конец периода") }
                )

                Button(onClick = {
                    if (start_date.isNotEmpty() && end_date.isNotEmpty())
                        data = driverPerformanceForPeriod(start_date, end_date)
                    else dialogWindow = true
                }){
                    Text("Расчитать")
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
                        Row {
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