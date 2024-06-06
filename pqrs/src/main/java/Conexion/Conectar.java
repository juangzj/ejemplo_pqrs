package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan Goyes
 */
public class Conectar {

    private static Connection conexion;
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_peticiones";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "admin";

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establece la conexión y almacénala en la variable conexion
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos");
            } else {
                System.out.println("No se pudo establecer conexión a la base de datos");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el controlador JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    public Connection darConexion() {
        return conexion;
    }
}
