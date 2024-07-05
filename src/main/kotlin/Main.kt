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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable

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
                currentPage = page
                userID = userId },
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


            //
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
                currentTable,
                currentId,
                currentPages
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Показать меню")
                        }
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
                            IconButton(onClick = {
                                onLogout(Pages.DriverMainPage)
                            }) {
                                Icon(Icons.Default.Home, "Главная страница")
                            }
                        }
                        "Менеджер" -> {
                            IconButton(onClick = {
                                onLogout(Pages.ManagerMainPage)
                            }) {
                                Icon(Icons.Default.Home, "Главная страница")
                            }

                            IconButton(onClick = {
                                onLogout(Pages.DataManager)
                            }) {
                                Icon(Icons.Filled.Settings, "Данные")
                            }

                            IconButton(onClick = {
                                onLogout(Pages.PreliminaryCostManager)
                            }){
                                Icon(Icons.Default.ShoppingCart, "Расчитать предварительную стоимость")
                            }
                        }
                        else -> {
                            IconButton(onClick = {
                                onLogout(Pages.AdministratorMainPage)
                            }) {
                                Icon(Icons.Default.Home,  "Главная страница")
                            }

                            IconButton(onClick = {
                                onLogout(Pages.DataAdministrator)
                            }) {
                                Icon(Icons.Filled.Settings, "Данные")
                            }

                            IconButton(onClick = {
                                onLogout(Pages.PreliminaryCostAdmin)
                            }){
                                Icon(Icons.Default.ShoppingCart, "Расчитать предварительную стоимость")
                            }

                            IconButton(onClick = {
                                onLogout(Pages.ReportsAdministrator)
                            }){
                                Icon(Icons.Default.Check, "Отчетность")
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
    println(data)

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
                        confirmation = false
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
                            else -> if (titles == "Грузы") deleteCargo(currentId.toInt())
                        }
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
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
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
fun UpdatePage(onLogout: (Pages) -> Unit, title: String, head: List<String>, table: String, currentId: String, currentPagess: Pages){
    var data by remember { mutableStateOf(listOf("")) }
    var inDay by remember { mutableStateOf(listOf("")) }
    var pasport by remember { mutableStateOf(listOf("")) }
    var dialogWindow by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
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

                Button(onClick = { dialogWindow = true }) {
                    Text("Сохранить")
                }
            }
        }
    }
}

@Composable
fun AddPage(onLogout: (Pages) -> Unit, title: String, head: List<String>, table: String, currentId: String, currentPagess: Pages) {
    var data by remember { mutableStateOf(List(head.size) { "" }) }
    var inDay by remember { mutableStateOf(listOf<String>()) }
    var passport by remember { mutableStateOf(List(4) { "" }) }
    var dialogWindow by remember { mutableStateOf(false) }

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
                        dialogWindow = false
                        when (table) {
                            "Контактные лица" -> createContact(data)
                            "Автопарки" -> createAutopark(data)
                            "Автомобили" -> createCar(data)
                            "Должности" -> createJob(data)
                            "Сотрудники" -> createEmployee(data, inDay, passport)
                            "Точки назначения" -> createDestinationPoint(data)
                            "Клиенты" -> createCustomer(data)
                            "Классификация грузов" -> createCargoClass(data)
                            "Дополнительные услуги" -> createAdditionalService(data)
                            "Договоры" -> createContract(data)
                            "Договор Дополнительные услуги" -> createContractAdditionalService(data)
                            else -> if (table == "Грузы") createCargo(data)
                        }
                        onLogout(Pages.TablePage)
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
                        if (head[i] != "ID") {
                            OutlinedTextField(
                                value = data[i],
                                onValueChange = { newData ->
                                    data = data.toMutableList().also { it[i] = newData }
                                },
                                label = { Text(label) }
                            )
                        }
                    }
                } else {
                    head.forEachIndexed { i, label ->
                        if (head[i] != "ID") {
                            when (label) {
                                "Паспортные данные" -> {
                                    Text("Паспортные данные")
                                    val passportLabels = listOf("Серия", "Номер", "Кем выдан", "Когда выдан")
                                    passportLabels.forEachIndexed { j, passportLabel ->
                                        OutlinedTextField(
                                            value = passport[j],
                                            onValueChange = { newData ->
                                                passport = passport.toMutableList().also { it[j] = newData }
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
                                        value = data[i],
                                        onValueChange = { newData ->
                                            data = data.toMutableList().also { it[i] = newData }
                                        },
                                        label = { Text(label) }
                                    )
                                }
                            }
                        }
                    }
                }

                Button(onClick = {
                    dialogWindow = true
                }) {
                    Text("Добавить")
                }
            }
        }
    }
}