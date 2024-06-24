import androidx.compose.runtime.Composable

fun ManagerDrawerContent(onLogout: (Pages) -> Unit){

}

@Composable
fun ManagerMainPage(onLogout: (Pages) -> Unit) {
    MainScaffold(
        title = "Менеджер",
        onLogout = onLogout
    ) {

    }
}
