import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import java.io.File
import java.io.PrintWriter
import javax.swing.JFileChooser
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.toSize
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
@Preview
fun App() {
    var currentPage by remember { mutableStateOf(Pages.LoginPage) }
    var currentId by remember { mutableStateOf("") }

    var userID by remember { mutableStateOf("") }

    var currentTitle by remember { mutableStateOf("") }
    var currentTable by remember { mutableStateOf("") }
    var currentHead by remember { mutableStateOf(listOf("")) }
    var currentPages by remember { mutableStateOf(Pages.LoginPage) }

    MaterialTheme {
        when (currentPage) {
            Pages.LoginPage -> LoginPage { userId, page ->
                currentPage = page
                userID = userId
            }

            Pages.AdministratorMainPage -> AdministratorMainPage ({ currentPage = it },
                { userId, page ->
                    currentId = userId
                    currentPage = page
                },
            )
            Pages.DriverMainPage -> DriverMainPage({ userId, page, driver ->
                currentPage = page
                currentId = userId
                userID = driver
            }, { currentPage = it }, userID)
            Pages.ManagerMainPage -> ManagerMainPage ({ userId, page ->
                currentPage = page
                userID = userId }, { currentPage = it }
            )

            // водитэл
            Pages.FillInfoTripDriver -> FillInfoTripDriver({ currentPage = it }, currentId)
            Pages.CargosWithTripDriver -> CargosWithTripDriver({ currentPage = it }, currentId)
            Pages.DepartPointsDriver -> DepartPointsDriver({ currentPage = it }, currentId,
                {page, id ->
                    currentPage = page
                    currentId = id
                })
            Pages.DeclarationDriver -> DeclarationDriver({ currentPage = it }, currentId)
            Pages.UpdateStatus -> UpdateStatus({ currentPage = it }, currentId)


            //менеджэр
            Pages.PreliminaryCostManager -> PreliminaryCostManager{ currentPage = it }
            Pages.AdditionalServicesContract -> AdditionalServicesContract({ currentPage = it }, currentId)
            Pages.FillInfoTripManager -> FillInfoTripManager({ currentPage = it }, currentId)
            Pages.DataManager -> DataManager ({currentPage = it}, { title, head, table, currentPagess, page ->
                currentTitle = title
                currentHead = head
                currentTable = table
                currentPages = currentPagess
                currentPage = page
            })

            //адмэн
            Pages.ContractsSummaryForManagers -> ContractsSummaryForManagers{ currentPage = it }
            Pages.DriverPerformance -> DriverPerformance{ currentPage = it }
            Pages.FillInfoTripAdministrator -> FillInfoTripAdministrator({ currentPage = it }, currentId)
            Pages.DataAdministrator -> DataAdministrator ({currentPage = it}, { title, head, table, currentPagess, page ->
                currentTitle = title
                currentHead = head
                currentTable = table
                currentPages = currentPagess
                currentPage = page
            })
            Pages.ReportsAdministrator -> ReportsAdministrator{ currentPage = it }
            Pages.PreliminaryCostAdmin -> PreliminaryCostAdmin{ currentPage = it }
            Pages.DeclarationAdministrator -> DeclarationAdministrator({ currentPage = it }, currentId)

            Pages.UpdatePage -> UpdatePage(
                { page -> currentPage = page },
                currentTitle,
                currentHead,
                currentTable,
                currentId,
                currentPages
            )
            Pages.TablePage -> TablePage(
                onLogout = { page -> currentPage = page },
                titles = currentTitle,
                heads = currentHead,
                tables = currentTable,
                onLoginSuccess = { title, head, table, currentIds, currentPagess, page ->
                    currentTitle = title
                    currentHead = head
                    currentTable = table
                    currentId = currentIds
                    currentPages = currentPagess
                    currentPage = page
                }
            )
            Pages.AddPage -> AddPage(
                { page -> currentPage = page },
                currentTitle,
                currentHead,
                currentTable
            )
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Грузоперевозки"
    ) {
        App()
    }
}

@Composable
fun TableHeader(headers: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        headers.forEach { header ->
            Text(
                text = header,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun TableCell(text: String, isHeader: Boolean = false) {
    Text(
        text = text,
        Modifier
            .width(150.dp)
            .padding(8.dp),
        style = if (isHeader) MaterialTheme.typography.subtitle1 else MaterialTheme.typography.body1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MainScaffold(title: String, onLogout: (Pages) -> Unit, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val calculator = remember { loadImageResource("src/main/resources/images/calculator.png") }
    val databases = remember { loadImageResource("src/main/resources/images/database.png") }
    val edit = remember { loadImageResource("src/main/resources/images/edit.png") }
    val home = remember { loadImageResource("src/main/resources/images/home.png") }
    val users = remember { loadImageResource("src/main/resources/images/user.png") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    Box {
                        Image(bitmap = users, contentDescription = "Показать меню", Modifier.size(60.dp).clickable { expanded = true }.padding(15.dp))

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Text("Выйти", fontSize=15.sp, modifier = Modifier.padding(10.dp) .clickable { onLogout(Pages.LoginPage) } .padding(15.dp))
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar{
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    when (title) {
                        "Водитель" -> {
                            Column(
                                Modifier.clickable { onLogout(Pages.DriverMainPage) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = home, contentDescription = "Главная страница", Modifier.size(30.dp))
                            }
                        }
                        "Менеджер" -> {
                            Column(
                                Modifier.clickable { onLogout(Pages.ManagerMainPage) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = home, contentDescription = "Главная страница", Modifier.size(30.dp))
                            }
                            Column(
                                Modifier.clickable { onLogout(Pages.DataManager) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = databases, contentDescription = "База данных", Modifier.size(30.dp))
                            }
                            Column(
                                Modifier.clickable { onLogout(Pages.PreliminaryCostManager) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = calculator, contentDescription = "Расчитать предварительную стоимость", Modifier.size(30.dp))
                            }
                        }
                        else -> {
                            Column(
                                Modifier.clickable { onLogout(Pages.AdministratorMainPage) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = home, contentDescription = "Главная страница", Modifier.size(30.dp))
                            }
                            Column(
                                Modifier.clickable { onLogout(Pages.DataAdministrator) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = databases, contentDescription = "Данные", Modifier.size(30.dp))
                            }
                            Column(
                                Modifier.clickable { onLogout(Pages.PreliminaryCostAdmin) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = calculator, contentDescription = "Расчитать предварительную стоимость", Modifier.size(30.dp))
                            }
                            Column(
                                Modifier.clickable { onLogout(Pages.ReportsAdministrator) }.padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(bitmap = edit, contentDescription = "Главная страница", Modifier.size(30.dp))
                            }
                        }
                    }
                }
            }
        }
    ) {
        innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
    }
}

@Composable
fun LoginPage(onLoginSuccess: (userData: String, page: Pages) -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var selectUser by remember { mutableStateOf(false) }
    var userData by remember { mutableStateOf(listOf<String>()) }
    var userNotFound by remember { mutableStateOf(false) }

    if (selectUser) {
        LaunchedEffect(Unit) {
            val result = authorization(login, password)

            if (result != null) {
                userData = result
                val page = when (result[1]) {
                    "Администратор" -> Pages.AdministratorMainPage
                    "Водитель" -> Pages.DriverMainPage
                    "Менеджер" -> Pages.ManagerMainPage
                    else -> null
                }
                if (page != null) {
                    onLoginSuccess(result[0], page)
                }
            }
            else{
                userNotFound = true
            }
            selectUser = false
        }
    }

    if (userNotFound) {
        AlertDialog(
            onDismissRequest = { userNotFound = false },
            title = { Text(text = "Пользователь не найден") },
            text = { Text("Неправильный логин или пароль!") },
            confirmButton = {
                Button(onClick = { userNotFound = false }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            login,
            onValueChange = { login = it },
            label = { Text("Логин") }
        )

        Spacer(Modifier.padding(10.dp))

        OutlinedTextField(
            password,
            onValueChange = { password = it },
            label = { Text("Пароль") }
        )

        Spacer(Modifier.padding(10.dp))

        Button(
            onClick = {
                selectUser = true
            }
        ) {
            Text("Войти")
        }
    }
}

fun saveToCsv(data: List<List<String>>, filePath: String, titles: List<String>) {
    val outputFile = File(filePath)
    PrintWriter(outputFile).use { out ->
        out.println(titles)
        data.forEach { row ->
            out.println(row.joinToString(","))
        }
    }
}

fun loadImageResource(path: String): ImageBitmap {
    val inputStream: InputStream = File(path).inputStream()
    return loadImageBitmap(inputStream)
}

@Composable
fun SelectFileDialog(
    onDialogDismiss: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val fileChooser = JFileChooser().apply {
                fileSelectionMode = JFileChooser.FILES_ONLY
            }
            val result = fileChooser.showSaveDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                onDialogDismiss(fileChooser.selectedFile.absolutePath)
            } else {
                onDialogDismiss("")
            }
        }
    }
}

@Composable
fun TablePage(
    onLogout: (Pages) -> Unit, titles: String, heads: List<String>, tables: String,
    onLoginSuccess: (title: String, head: List<String>, table: String, currentId: String, currentPagess: Pages, page: Pages) -> Unit
){
    var data by remember { mutableStateOf(listOf(listOf(""))) }
    var dialogWindow by remember { mutableStateOf(false) }
    var confirmation by remember { mutableStateOf(false) }
    var currentId by remember { mutableStateOf("") }
    val add = remember { loadImageResource("src/main/resources/images/add.png") }

    LaunchedEffect(Unit) {
        data = when (tables){
            "Контактные лица" -> viewContactsInfo()
            "Автопарки" -> viewAutoparkInfo()
            "Автомобили" -> viewCarInfo()
            "Должности" -> viewJobsInfo()
            "Сотрудники" -> viewEmployeeInfo()
            "Точки назначения" -> viewDestinationPointsInfo()
            "Клиенты" -> viewCustomersInfo()
            "Классификация грузов" -> viewClassCargosInfo()
            "Дополнительные услуги" -> viewAdditionalServicesInfo()
            "Договоры" -> viewContractInfo()
            "Договор Дополнительные услуги" -> viewContractAdditionalService()
            else -> if (tables == "Грузы") viewCargoInfo() else viewAuditLogInfo()
        }
    }

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "$tables $currentId") },
            text = { Text("Выберите действие") },
            buttons = {
                Column(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        dialogWindow = false
                        onLoginSuccess(titles, heads, tables, currentId, Pages.TablePage, Pages.UpdatePage)
                    }) {
                        Text("Изменить", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        confirmation = true
                        dialogWindow = false
                    }) {
                        Text("Удалить", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    if (confirmation) {
        AlertDialog(
            onDismissRequest = { confirmation = false },
            title = { Text(text = "Подтвердите действие") },
            buttons = {
                Column(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        when (tables){
                            "Контактные лица" -> deleteContact(currentId.toInt())
                            "Автопарки" -> deleteAutopark(currentId.toInt())
                            "Автомобили" -> deleteCar(currentId.toInt())
                            "Должности" -> deleteJob(currentId.toInt())
                            "Сотрудники" -> deleteEmployee(currentId.toInt())
                            "Точки назначения" -> deleteDestinationPoint(currentId.toInt())
                            "Клиенты" -> deleteCustomer(currentId.toInt())
                            "Классификация грузов" -> deleteCargoClass(currentId.toInt())
                            "Дополнительные услуги" -> deleteAdditionalService(currentId.toInt())
                            "Договоры" -> deleteContract(currentId.toInt())
                            "Договор Дополнительные услуги" -> deleteContractAdditionalService(currentId.toInt())
                            else -> if (tables == "Грузы") deleteCargo(currentId.toInt())
                        }
                        data = when (tables){
                            "Контактные лица" -> viewContactsInfo()
                            "Автопарки" -> viewAutoparkInfo()
                            "Автомобили" -> viewCarInfo()
                            "Должности" -> viewJobsInfo()
                            "Сотрудники" -> viewEmployeeInfo()
                            "Точки назначения" -> viewDestinationPointsInfo()
                            "Клиенты" -> viewCustomersInfo()
                            "Классификация грузов" -> viewClassCargosInfo()
                            "Дополнительные услуги" -> viewAdditionalServicesInfo()
                            "Договоры" -> viewContractInfo()
                            "Договор Дополнительные услуги" -> viewContractAdditionalService()
                            else -> if (tables == "Грузы") viewCargoInfo() else viewAuditLogInfo()
                        }
                        confirmation = false
                    }) {
                        Text("Ок", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        confirmation = false
                    }) {
                        Text("Отмена", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    MainScaffold(
        title = titles,
        onLogout = onLogout
    ) {
        Scaffold(
            floatingActionButton = {
                if (tables != "Аудит"){
                    FloatingActionButton(onClick = {
                        onLoginSuccess(titles, heads, tables, "", Pages.TablePage, Pages.AddPage)
                    },
                    backgroundColor = Color(98, 0, 238)) {
                        Image(bitmap = add, contentDescription = "Добавить", Modifier.size(30.dp))
                    }
                }
        }){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(tables, style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
                TableHeader(headers = heads)
                LazyColumn {
                    items(data.size) { index ->
                        val row = data[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    if (tables != "Аудит"){
                                        currentId = row[0]
                                        dialogWindow = true
                                    }
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            row.forEach { item ->
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
fun UpdatePage(onLogout: (Pages) -> Unit, title: String, heads: List<String>, table: String, currentId: String, currentPagess: Pages){
    var data by remember { mutableStateOf(listOf("")) }
    var inDay by remember { mutableStateOf(listOf("")) }
    var pasport by remember { mutableStateOf(listOf("")) }
    var head by remember { mutableStateOf(listOf("")) }
    var errorWindow by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var dialogWindow by remember { mutableStateOf(false) }
    val typePoints = listOf("Отправление", "Прибытие")
    var typeExpanded by remember { mutableStateOf(false) }
    var typeSelectedText by remember { mutableStateOf("") }
    var typeTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val typeIcon = if (typeExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    var statusExpanded by remember { mutableStateOf(false) }
    val statusPoints = listOf("Доставлен", "В работе", "Отменен")
    var statusSelectedText by remember { mutableStateOf("") }
    var statusTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val statusIcon = if (statusExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    LaunchedEffect(Unit) {
        if (table == "Договоры"){
            val drop = listOf("Стоимость","Статус")
            head = heads.filterNot { it in drop }
            data = List(head.size) { "" }
        } else head = heads

        if (table != "Сотрудники"){
            data = when (table){
                "Контактные лица" -> getContact(currentId.toInt())
                "Автопарки" -> getAutopark(currentId.toInt())
                "Автомобили" -> getCar(currentId.toInt())
                "Должности" -> getJob(currentId.toInt())
                "Точки назначения" -> getDestinationPoint(currentId.toInt())
                "Клиенты" -> getCustomer(currentId.toInt())
                "Классификация грузов" -> getCargoClass(currentId.toInt())
                "Дополнительные услуги" -> getAdditionalService(currentId.toInt())
                "Договоры" -> getContract(currentId.toInt())
                "Договор Дополнительные услуги" -> getContractAdditionalService(currentId.toInt())
                else -> if (table == "Грузы") getCargo(currentId.toInt()) else TODO()
            }
        }
        else {
            val (genData, passData, workData) = getEmployee(currentId.toInt())
            data = genData
            pasport = passData
            inDay = workData
        }
    }

    if (dialogWindow) {
        val typeIndex = head.indexOf("Тип")
        if (typeIndex != -1) {
            data = data.toMutableList().apply {
                this[typeIndex] = typeSelectedText
            }
        }
        val statusIndex = head.indexOf("Статус")
        if (statusIndex != -1) {
            data = data.toMutableList().apply {
                this[statusIndex] = statusSelectedText
            }
        }

        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Подтвердите действие") },
            text = { Text("Сохранить данные") },
            buttons = {
                Row(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        dialogWindow = false
                        when (table){
                            "Контактные лица" -> updateContact(data)
                            "Автопарки" -> updateAutopark(data)
                            "Автомобили" -> updateCar(data)
                            "Должности" -> updateJob(data)
                            "Сотрудники" -> updateEmployee(data, inDay, pasport)
                            "Точки назначения" -> updateDestinationPoint(data)
                            "Клиенты" -> updateCustomer(data)
                            "Классификация грузов" -> updateCargoClass(data)
                            "Дополнительные услуги" -> updateAdditionalService(data)
                            "Договоры" -> updateContract(data)
                            "Договор Дополнительные услуги" -> updateContractAdditionalService(data)
                            else -> if (table == "Грузы") updateCargo(data)
                        }
                        onLogout(currentPagess)
                    }) {
                        Text("Сохранить", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                    }) {
                        Text("Отменить", fontSize = 15.sp)
                    }
                }
            }
        )
    }

    MainScaffold(
        title = title,
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Изменить $table", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                for (i in head.indices) {
                    if (head[i] != "ID") {
                        if (table == "Сотрудники" && head[i] == "Паспортные данные") {
                            Text("Паспортные данные")
                            val labels = arrayOf("Серия", "Номер", "Кем выдан", "Когда выдан")
                            for (j in labels.indices) {
                                val value = pasport.getOrNull(j) ?: ""
                                OutlinedTextField(
                                    value = value,
                                    onValueChange = { newData ->
                                        pasport = pasport.toMutableList().also {
                                            if (j < it.size) {
                                                it[j] = newData
                                            } else {
                                                it.add(newData)
                                            }
                                        }
                                    },
                                    label = { Text(labels[j]) }
                                )
                            }
                        } else if (table == "Сотрудники" && head[i] == "Рабочие дни") {
                            val labels = arrayOf(
                                "Понедельник",
                                "Вторник",
                                "Среда",
                                "Четверг",
                                "Пятница",
                                "Суббота",
                                "Воскресенье"
                            )

                            val checkedState = remember {
                                mutableStateOf(labels.map { inDay.contains(it) }.toBooleanArray())
                            }

                            Text("Рабочие дни")

                            labels.forEachIndexed { index, label ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = checkedState.value[index],
                                        onCheckedChange = { isChecked ->
                                            val newCheckedState = checkedState.value.copyOf()
                                            newCheckedState[index] = isChecked
                                            checkedState.value = newCheckedState
                                            inDay = labels.filterIndexed { i, _ -> newCheckedState[i] }
                                        }
                                    )
                                    Text(label)
                                }
                            }
                        } else if (table == "Точки назначения" && head[i] == "Тип") {
                            OutlinedTextField(
                                value = typeSelectedText,
                                onValueChange = { typeSelectedText = it },
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        typeTextFieldSize = coordinates.size.toSize()
                                    },
                                label = {Text("Тип точки назначения")},
                                trailingIcon = {
                                    Icon(typeIcon,"",
                                        Modifier.clickable { typeExpanded = !typeExpanded })
                                }
                            )

                            DropdownMenu(
                                expanded = typeExpanded,
                                onDismissRequest = { typeExpanded = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current){typeTextFieldSize.width.toDp()})
                            ) {
                                typePoints.forEach { label ->
                                    DropdownMenuItem(onClick = {
                                        typeSelectedText = label
                                        typeExpanded = false
                                    }) {
                                        Text(text = label)
                                    }
                                }
                            }
                        }  else if (table == "Точки назначения" && head[i] == "Статус") {
                            OutlinedTextField(
                                value = statusSelectedText,
                                onValueChange = { statusSelectedText = it },
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        statusTextFieldSize = coordinates.size.toSize()
                                    },
                                label = {Text("Статус точки назначения")},
                                trailingIcon = {
                                    Icon(statusIcon,"",
                                        Modifier.clickable { statusExpanded = !statusExpanded })
                                }
                            )

                            DropdownMenu(
                                expanded = statusExpanded,
                                onDismissRequest = { statusExpanded = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current){statusTextFieldSize.width.toDp()})
                            ) {
                                statusPoints.forEach { label ->
                                    DropdownMenuItem(onClick = {
                                        statusSelectedText = label
                                        statusExpanded = false
                                    }) {
                                        Text(text = label)
                                    }
                                }
                            }
                        } else {
                            val value = data.getOrNull(i) ?: ""
                            OutlinedTextField(
                                value = value,
                                onValueChange = { newData ->
                                    data = data.toMutableList().also {
                                        if (i < it.size) {
                                            it[i] = newData
                                        } else {
                                            it.add(newData)
                                        }
                                    }
                                },
                                label = { Text(head[i]) }
                            )
                        }
                    }
                }

                if (table == "Сотрудники") {
                    OutlinedTextField(
                        value = data.getOrNull(head.size) ?: "",
                        onValueChange = { newData ->
                            data = data.toMutableList().also {
                                if (head.size < it.size) {
                                    it[head.size] = newData
                                } else {
                                    it.add(newData)
                                }
                            }
                        },
                        label = { Text("Пароль") }
                    )
                }

                Button(onClick = {
                    var err = false
                    head.forEachIndexed { index, item ->
                        val fieldValue = data[index]

                        if (item.contains("дата", ignoreCase = true)) {
                            try {
                                LocalDate.parse(data[index], dateFormatter)
                            } catch (e: DateTimeParseException) {
                                errorText = "Неправильный формат даты в поле: ${head[index]} - ${data[index]}\nВведите дату в формате год-месяц-день"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("телефон", ignoreCase = true)) {
                            val phoneNumber = data[index]
                            if (!phoneNumber.matches(Regex("^89\\d{9}\$"))) {
                                errorText = "Неправильный формат номера телефона в поле: ${head[index]} - $phoneNumber\nНомер должен содержать только цифры, быть длиной в 11 символов и начинаться с 89"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("Гос. номер", ignoreCase = true)) {
                            val stateNumber = data[index]
                            if (!stateNumber.matches(Regex("^[A-ZА-Я]\\d{3}[A-ZА-Я]{2}\$"))) {
                                errorText = "Неправильный формат гос. номера в поле: ${head[index]} - $stateNumber\nНомер должен содержать одну букву, три цифры и две буквы (например, A123BC)"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("ID", ignoreCase = true)) {
                            if (!fieldValue.matches(Regex("^\\d+\$"))) {
                                errorText = "Неправильный формат ID в поле: ${head[index]} - $fieldValue\nID должен содержать только цифры"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("Вес", ignoreCase = true) || item.contains("Объем", ignoreCase = true) || item.contains("Стоимость", ignoreCase = true)) {
                            if (!fieldValue.matches(Regex("^\\d+\\.?\\d*\$"))) {
                                errorText = "Неправильный формат в поле: ${head[index]} - $fieldValue\nЗначение должно быть целым числом или числом с плавающей точкой (например, 123 или 123.45)"
                                errorWindow = true
                                err = true
                            }
                        }
                    }

                    if(!err) dialogWindow = true
                }) {
                    Text("Сохранить")
                }
            }
        }
    }
}

@Composable
fun AddPage(onLogout: (Pages) -> Unit,title: String,heads: List<String>,table: String) {
    var data by remember { mutableStateOf(listOf<String>()) }
    var inDay by remember { mutableStateOf(listOf<String>()) }
    var passport by remember { mutableStateOf(List(4) { "" }) }
    var errorWindow by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var dialogWindow by remember { mutableStateOf(false) }
    var head by remember { mutableStateOf(heads) }
    var dropRow by remember { mutableStateOf(listOf<String>()) }
    val typePoints = listOf("Отправление", "Прибытие")
    var typeExpanded by remember { mutableStateOf(false) }
    var typeSelectedText by remember { mutableStateOf("") }
    var typeTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val typeIcon = if (typeExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var password by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    LaunchedEffect(Unit) {
        dropRow = when (table) {
            "Договоры" -> listOf("ID", "Стоимость", "Дата заключения", "Статус")
            "Точки назначения" -> listOf("ID", "Статус", "Тип")
            else -> listOf("ID")
        }

        head = heads.filterNot { it in dropRow }
        data = List(head.size) { "" }
    }

    if (dialogWindow) {
        AlertDialog(
            onDismissRequest = { dialogWindow = false },
            title = { Text(text = "Подтвердите действие") },
            text = { Text("Добавить данные") },
            buttons = {
                Row(
                    Modifier.padding(25.dp)
                ) {
                    Button(onClick = {
                        data += password
                        println(data)
                        when (table) {
                            "Контактные лица" -> createContact(data)
                            "Автопарки" -> createAutopark(data)
                            "Автомобили" -> createCar(data)
                            "Должности" -> createJob(data)
                            "Сотрудники" -> createEmployee(data, inDay, passport)
                            "Точки назначения" -> createDestinationPoint(data, typeSelectedText)
                            "Клиенты" -> createCustomer(data)
                            "Классификация грузов" -> createCargoClass(data)
                            "Дополнительные услуги" -> createAdditionalService(data)
                            "Договоры" -> createContract(data)
                            "Договор Дополнительные услуги" -> createContractAdditionalService(data)
                            else -> if (table == "Грузы") createCargo(data)
                        }
                        onLogout(Pages.TablePage)
                        dialogWindow = false
                    }) {
                        Text("Добавить", fontSize = 15.sp)
                    }
                    Button(onClick = {
                        dialogWindow = false
                    }) {
                        Text("Отменить", fontSize = 15.sp)
                    }
                }
            }
        )
    }
    if (errorWindow) {
        AlertDialog(
            onDismissRequest = { errorWindow = false },
            title = { Text(text = "Не коррекктные данные") },
            text = { Text(errorText) },
            buttons = {
                Button(onClick = {
                    errorWindow = false
                }) {
                    Text("Ок", fontSize = 15.sp)
                }
            }
        )
    }

    MainScaffold(
        title = title,
        onLogout = onLogout
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Добавить $table", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                if (table != "Сотрудники") {
                    head.forEachIndexed { i, label ->
                        if (label != "ID") {
                            if (table == "Договоры" && label != "Стоимость" && label != "Дата заключения" && label != "Статус") {
                                OutlinedTextField(
                                    value = data.getOrElse(i) { "" },
                                    onValueChange = { newData ->
                                        data = data.toMutableList().apply {
                                            if (size > i) set(i, newData) else add(newData)
                                        }
                                    },
                                    label = { Text(label) }
                                )
                            } else if (table == "Точки назначения" && label != "Тип" && label != "Статус") {
                                OutlinedTextField(
                                    value = data.getOrElse(i) { "" },
                                    onValueChange = { newData ->
                                        data = data.toMutableList().apply {
                                            if (size > i) set(i, newData) else add(newData)
                                        }
                                    },
                                    label = { Text(label) }
                                )
                            }  else {
                                OutlinedTextField(
                                    value = data.getOrElse(i) { "" },
                                    onValueChange = { newData ->
                                        data = data.toMutableList().apply {
                                            if (size > i) set(i, newData) else add(newData)
                                        }
                                    },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                } else {
                    head.forEachIndexed { i, label ->
                        if (label != "ID") {
                            when (label) {
                                "Паспортные данные" -> {
                                    Text("Паспортные данные")
                                    val passportLabels = listOf("Серия", "Номер", "Кем выдан", "Когда выдан")
                                    passportLabels.forEachIndexed { j, passportLabel ->
                                        OutlinedTextField(
                                            value = passport.getOrElse(j) { "" },
                                            onValueChange = { newData ->
                                                passport = passport.toMutableList().apply {
                                                    if (size > j) set(j, newData) else add(newData)
                                                }
                                            },
                                            label = { Text(passportLabel) }
                                        )
                                    }
                                }
                                "Рабочие дни" -> {
                                    Text("Рабочие дни")
                                    val daysOfWeek = listOf(
                                        "Понедельник",
                                        "Вторник",
                                        "Среда",
                                        "Четверг",
                                        "Пятница",
                                        "Суббота",
                                        "Воскресенье"
                                    )
                                    daysOfWeek.forEach { day ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = inDay.contains(day),
                                                onCheckedChange = { checked ->
                                                    inDay = if (checked) {
                                                        inDay + day
                                                    } else {
                                                        inDay - day
                                                    }
                                                }
                                            )
                                            Text(text = day)
                                        }
                                    }
                                }
                                else -> {
                                    OutlinedTextField(
                                        value = data.getOrElse(i) { "" },
                                        onValueChange = { newData ->
                                            data = data.toMutableList().apply {
                                                if (size > i) set(i, newData) else add(newData)
                                            }
                                        },
                                        label = { Text(label) }
                                    )
                                }
                            }
                        }
                    }
                }
                if (table == "Точки назначения") {
                    OutlinedTextField(
                        value = typeSelectedText,
                        onValueChange = { typeSelectedText = it },
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                typeTextFieldSize = coordinates.size.toSize()
                            },
                        label = { Text("Тип точки назначения") },
                        trailingIcon = {
                            Icon(typeIcon,"",
                                Modifier.clickable { typeExpanded = !typeExpanded })
                        }
                    )

                    DropdownMenu(
                        expanded = typeExpanded,
                        onDismissRequest = { typeExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){typeTextFieldSize.width.toDp()})
                    ) {
                        typePoints.forEach { label ->
                            DropdownMenuItem(onClick = {
                                typeSelectedText = label
                                typeExpanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
                if (table == "Сотрудники") {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Пароль") }
                    )
                }

                Button(onClick = {
                    var err = false
                    head.forEachIndexed { index, item ->
                        val fieldValue = data[index]

                        if (item.contains("дата", ignoreCase = true)) {
                            try {
                                LocalDate.parse(data[index], dateFormatter)
                            } catch (e: DateTimeParseException) {
                                errorText = "Неправильный формат даты в поле: ${head[index]} - ${data[index]}\nВведите дату в формате год-месяц-день"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("телефон", ignoreCase = true)) {
                            val phoneNumber = data[index]
                            if (!phoneNumber.matches(Regex("^89\\d{9}\$"))) {
                                errorText = "Неправильный формат номера телефона в поле: ${head[index]} - $phoneNumber\nНомер должен содержать только цифры, быть длиной в 11 символов и начинаться с 89"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("Гос. номер", ignoreCase = true)) {
                            val stateNumber = data[index]
                            if (!stateNumber.matches(Regex("^[A-ZА-Я]\\d{3}[A-ZА-Я]{2}\$"))) {
                                errorText = "Неправильный формат гос. номера в поле: ${head[index]} - $stateNumber\nНомер должен содержать одну букву, три цифры и две буквы (например, A123BC)"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("ID", ignoreCase = true)) {
                            if (!fieldValue.matches(Regex("^\\d+\$"))) {
                                errorText = "Неправильный формат ID в поле: ${head[index]} - $fieldValue\nID должен содержать только цифры"
                                errorWindow = true
                                err = true
                            }
                        }

                        if (item.contains("Вес", ignoreCase = true) || item.contains("Объем", ignoreCase = true) || item.contains("Стоимость", ignoreCase = true)) {
                            if (!fieldValue.matches(Regex("^\\d+\\.?\\d*\$"))) {
                                errorText = "Неправильный формат в поле: ${head[index]} - $fieldValue\nЗначение должно быть целым числом или числом с плавающей точкой (например, 123 или 123.45)"
                                errorWindow = true
                                err = true
                            }
                        }
                    }
                    if(!err) dialogWindow = true
                }) {
                    Text("Добавить")
                }
            }
        }
    }
}
