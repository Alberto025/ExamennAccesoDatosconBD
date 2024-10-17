package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static Connection con = null;
    private static final String URL = "jdbc:mysql://localhost:3306/peliculas";
    private static final String user = "root";

    private static final String pass = System.getenv("MYSQL_ROOT_PASSWORD");

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, user, pass);
                System.out.println("Conexión establecida");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al conectar con la base de datos");
            }
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al cerrar la conexion");
            }
        }
    }
}