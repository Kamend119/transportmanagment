import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBasePostgres {
    private const val URL = "jdbc:postgresql://localhost:5432/transportmanagments"
    private const val Users = "postgres"
    private const val Password = "OpHypLoic"

    init {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, Users, Password)
    }
}
