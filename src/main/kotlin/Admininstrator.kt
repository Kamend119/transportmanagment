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
        Divider()
        Text("Документы", fontSize=18.sp,
            modifier = Modifier
                .padding(10.dp)
                .padding(15.dp))
        Divider()
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
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
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
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
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
