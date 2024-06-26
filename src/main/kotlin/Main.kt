import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import java.awt.FileDialog
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
@Preview
fun App() {
    var currentPage by remember { mutableStateOf(Pages.LoginPage) }
    var currentId by remember { mutableStateOf("") }

    MaterialTheme {
        when (currentPage) {
            Pages.LoginPage -> LoginPage { userId, page ->
                currentPage = page
                currentId = userId
            }

            Pages.AdministratorMainPage -> AdministratorMainPage { currentPage = it }
            Pages.DriverMainPage -> DriverMainPage({ userId, page ->
                currentPage = page
                currentId = userId }, { currentPage = it }, currentId)
            Pages.ManagerMainPage -> ManagerMainPage { currentPage = it }

            // водитэл
            Pages.FillInfoTripDriver -> FillInfoTripDriver({ currentPage = it }, currentId)
            Pages.CargosWithTripDriver -> CargosWithTripDriver({ currentPage = it }, currentId)
            Pages.DepartPointsDriver -> DepartPointsDriver({ currentPage = it }, currentId)
            Pages.Declaration -> Declaration({ currentPage = it }, currentId)

            //менеджэр
            Pages.PreliminaryCost -> PreliminaryCost{ currentPage = it }

            //адмэн
            Pages.ContractsSummaryForManagers -> ContractsSummaryForManagers{ currentPage = it }
            Pages.DriverPerformance -> DriverPerformance{ currentPage = it }

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
                color = Color.White,
                modifier = Modifier.weight(1f)
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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            when (title) {
                    "Администратор" -> AdministratorDrawerContent(onLogout)
                    "Менеджер" -> ManagerDrawerContent(onLogout)
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
                            IconButton(onClick = {
                                when (title) {
                                    "Водитель" -> onLogout(Pages.DriverMainPage)
                                    "Администратор" -> onLogout(Pages.AdministratorMainPage)
                                    "Менеджер" -> onLogout(Pages.ManagerMainPage)
                                }
                            }) {
                                Icon(Icons.Default.Home, contentDescription = "Главная страница")
                            }

                            Box {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Default.AccountCircle, contentDescription = "Показать меню")
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    //Divider()
                                    Text("Выйти", fontSize=15.sp, modifier = Modifier.padding(10.dp) .clickable { onLogout(Pages.LoginPage) } .padding(15.dp))
                                }
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

fun saveToCsv(data: List<List<String>>, contractID: String) {
    val outputFile = File("declaration_$contractID.csv")

    outputFile.printWriter().use { out ->
        // Write header
        out.println("Наименование,Объем,Вес,Описание")

        // Write data
        data.forEach { row ->
            out.println(row.joinToString(","))
        }
    }

    println("CSV file saved successfully.")
}

@Composable
fun SelectFileDialog(
    onDialogDismiss: () -> Unit
): String {
    val dialogState = rememberDialogState()
    val coroutineScope = rememberCoroutineScope()
    var filePath by remember { mutableStateOf("") }

    Dialog(
        state = dialogState,
        title = "Select File or Folder",
        onCloseRequest = onDialogDismiss
    ) {
        val fileDialog = remember { FileDialog(null as java.awt.Frame?, "Select File or Folder", FileDialog.LOAD) }

        coroutineScope.launch {
            fileDialog.isVisible = true
            fileDialog.toFront()
            fileDialog.requestFocus()

            fileDialog.isVisible = false

            val chosenFile = File(fileDialog.directory, fileDialog.file)
            filePath = chosenFile.absolutePath
        }
    }
    if (filePath != "") {
        return filePath
    }
    return ""
}
