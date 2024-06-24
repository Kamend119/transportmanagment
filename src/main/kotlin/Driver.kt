import DataBase.getActiveTripsByDriver
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DriverMainPage(onLogout: (Pages) -> Unit, driverID: Int) {

    var activeTrips by remember { mutableStateOf(listOf<List<String>>()) }

    LaunchedEffect(Unit) {
        activeTrips = getActiveTripsByDriver(driverID.toBigInteger(), "driver_user", "driver1")
    }

    MainScaffold(
        title = "Водитель",
        onLogout = onLogout
    ) {
        Text("Active Trips", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TableHeader(headers = listOf("ID", "Дата и время отправления", "Адрес"))
            LazyColumn {
                items(activeTrips.size) { index ->
                    val trip = activeTrips[index]
                    Row {
                        TableCell(text = trip[0])
                        TableCell(text = trip[1])
                        TableCell(text = trip[2])
                    }
                }
            }
        }
    }
}
