import androidx.compose.runtime.Composable

fun AdministratorDrawerContent(onLogout: (Pages) -> Unit){

}

@Composable
fun AdministratorMainPage(onLogout: (Pages) -> Unit) {
    MainScaffold(
        title = "Администратор",
        onLogout = onLogout
    ) {

    }
}