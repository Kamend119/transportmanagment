import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBasePostgres {
    private const val url = "jdbc:postgresql://localhost:5432/transportmanagment"
    private const val user = "postgres"
    private const val password = "OpHypLoic"

    init {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun getConnection(): Connection {
        return DriverManager.getConnection(url, user, password)
    }
}