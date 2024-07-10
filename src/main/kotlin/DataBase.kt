import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBasePostgres {
    private const val URL = "jdbc:postgresql://localhost:5432/transportmanagment"
    private const val USER = "postgres"
    private const val PASSWORD = "OpHypLoic"

    init {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            println("PostgreSQL Driver not found. Include it in your library path.")
        }
    }

    @Throws(SQLException::class)
    fun getConnection(): Connection {
        println("Connecting to the database...")
        return DriverManager.getConnection(URL, USER, PASSWORD).also {
            println("Successfully connected to the database.")
        }
    }
}