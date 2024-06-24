import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

@Composable
@Preview
fun Apps() {
    var currentPage by remember { mutableStateOf(Pages.StartPage) }
    var userData by remember { mutableStateOf("") }

    MaterialTheme {
        when (currentPage) {
            Pages.StartPage -> {
                StartPage(
                    onClick = {data ->
                        userData = data
                        currentPage = Pages.ViewDbPage
                    },
                )
            }

            Pages.ViewDbPage -> {
                ViewDbPage(
                    someFunc = {
                        currentPage = Pages.StartPage
                    },
                    userData
                )
            }
        }
    }
}

@Composable
fun StartPage(onClick: (String) -> Unit) {
    val userData = "какие-то данные"

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Hello")
        Button(
            content = {
                Text("Open DB")
            },
            onClick = { onClick(userData) },
        )
    }
}

@Composable
fun ViewDbPage(someFunc: () -> Unit, userData: String) {
    var userData by remember { mutableStateOf(listOf<List<String>>()) }
    LaunchedEffect(Unit) {
        userData = fetchUsersFromDatabase()
    }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxSize().weight(1f),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.onSurface,
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                item {
                    TableHeader()
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
                items(userData) { user ->
                    TableRow(user)
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = someFunc,
        ) {
            Text("Back")
        }
    }
}


@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colors.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("ID", modifier = Modifier.weight(1f), color = Color.White, textAlign = TextAlign.Center)
        Text("Username", modifier = Modifier.weight(2f), color = Color.White, textAlign = TextAlign.Center)
        Text("Email", modifier = Modifier.weight(2f), color = Color.White, textAlign = TextAlign.Center)
        Text("First Name", modifier = Modifier.weight(2f), color = Color.White, textAlign = TextAlign.Center)
        Text("Last Name", modifier = Modifier.weight(2f), color = Color.White, textAlign = TextAlign.Center)
        Text("Is Active", modifier = Modifier.weight(1f), color = Color.White, textAlign = TextAlign.Center)
    }
}


@Composable
fun TableRow(user: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(user[0], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(user[1], modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
        Text(user[2], modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
        Text(user[3], modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
        Text(user[4], modifier = Modifier.weight(2f), textAlign = TextAlign.Center)
        Text(user[5], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
    }
}

fun fetchUsersFromDatabase(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    try {
        DataBase.getConnection().use { connection ->
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM auth_user")
            while (resultSet.next()) {
                val row = listOf(
                    resultSet.getInt("id").toString(),
                    resultSet.getString("username"),
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getBoolean("is_active").toString()
                )
                result.add(row)
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return result
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
