import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    var currentPage by remember { mutableStateOf(Pages.LoginPage) }
    var currentEmployeeId by remember { mutableStateOf("") }

    MaterialTheme {
        when (currentPage) {
            Pages.LoginPage -> LoginPage { userId, page ->
                currentPage = page
                currentEmployeeId = userId
            }
            Pages.AdministratorMainPage -> AdministratorMainPage { currentPage = it }
            Pages.DriverMainPage -> DriverMainPage({ currentPage = it }, currentEmployeeId)
            Pages.ManagerMainPage -> ManagerMainPage { currentPage = it }
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
    Row {
        headers.forEach { header ->
            TableCell(text = header, isHeader = true)
        }
    }
}

@Composable
fun TableCell(text: String, isHeader: Boolean = false) {
    val backgroundColor = if (isHeader) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val textColor = if (isHeader) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface

    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colors.onSurface)
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}

@Composable
fun TableRow(rowData: String) {
    Row {
        TableCell(text = rowData)
    }
}

@Composable
fun TableContent(data: List<String>) {
    Column {
        data.forEach { row ->
            TableRow(rowData = row)
        }
    }
}

@Composable
fun MainScaffold(title: String, onLogout: (Pages) -> Unit, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier.width(100.dp)
            ){
                when (title) {
                    "Администратор" -> AdministratorDrawerContent(onLogout)
                    "Менеджер" -> ManagerDrawerContent(onLogout)
                }
                Text("sbfhdnviufdn")
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(title) },
                        navigationIcon = {
                            if (title != "Водитель") {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { onLogout(Pages.LoginPage) }) {
                                Icon(Icons.Filled.Home, contentDescription = "Выйти")
                            }
                        }
                    )
                }
            ) {
                content()
            }
        }
    )
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
