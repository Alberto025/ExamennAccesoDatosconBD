package org.example.dao;

import org.example.model.Usuario;
import org.example.model.Pelicula; // Asegúrate de que esta clase esté importada.
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Esta clase implementa el patrón DAO para manejar operaciones con el objeto Propietario.
public class UsuarioDAO implements DAO<Usuario> {

    // Conexión a la base de datos.
    Connection con;

    // Constructor que recibe la conexión a la base de datos.
    public UsuarioDAO(Connection c) {
        con = c;
    }

    // Método para encontrar todos los propietarios.
    @Override
    public List<Usuario> findAll() {
        List<Usuario> listaPropietarios = new ArrayList<>(); // Lista para almacenar resultados.

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM propietarios")) {
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta.

            // Itera sobre los resultados y crea objetos Propietario a partir de los datos obtenidos.
            while (rs.next()) {
                Usuario p = new Usuario();
                p.(rs.getInt("id")); // Establece el ID del propietario.
                p.setNombre(rs.getString("nombre")); // Establece el nombre del propietario.
                p.setEdad(rs.getString("edad")); // Establece la edad del propietario.
                // Aquí puedes cargar la lista de perros asociados, si es necesario.
                listaPropietarios.add(p); // Agrega el propietario a la lista.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones relacionadas con SQL.
        }

        return listaPropietarios; // Devuelve la lista de propietarios.
    }

    // Encuentra un propietario por su ID.
    @Override
    public Propietario findById(Integer id) {
        // Comprobar si id es null antes de continuar
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        Propietario propietario = null; // Inicializa el propietario como nulo.

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM propietarios WHERE id = ?")) {
            ps.setInt(1, id); // Establece el ID en la consulta.
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta.

            // Si encuentra un resultado, crea un objeto Propietario con los datos obtenidos.
            if (rs.next()) {
                propietario = new Propietario();
                propietario.setId(rs.getInt("id"));
                propietario.setNombre(rs.getString("nombre"));
                propietario.setEdad(rs.getString("edad"));
                // Aquí puedes cargar la lista de perros asociados, si es necesario.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }

        return propietario; // Devuelve el propietario encontrado o null.
    }

    // Guarda un nuevo propietario en la base de datos.
    @Override
    public void save(Propietario propietario) {
        // Asegurarse de que el propietario no sea null
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no puede ser null");
        }

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO propietarios(nombre, edad) VALUES (?, ?)")) {
            ps.setString(1, propietario.getNombre()); // Establece el nombre.
            ps.setString(2, propietario.getEdad()); // Establece la edad.

            ps.executeUpdate(); // Ejecuta la inserción en la base de datos.
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }

    // Actualiza un propietario existente en la base de datos.
    @Override
    public void update(Propietario propietario) {
        // Asegurarse de que el propietario no sea null y que tenga un ID válido
        if (propietario == null || propietario.getId() == null) {
            throw new IllegalArgumentException("El propietario no puede ser null y debe tener un ID válido");
        }

        try (PreparedStatement ps = con.prepareStatement("UPDATE propietarios SET nombre = ?, edad = ? WHERE id = ?")) {
            ps.setString(1, propietario.getNombre()); // Actualiza el nombre.
            ps.setString(2, propietario.getEdad()); // Actualiza la edad.
            ps.setInt(3, propietario.getId()); // Establece el ID del propietario.

            ps.executeUpdate(); // Ejecuta la actualización.
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }

    // Elimina un propietario de la base de datos.
    @Override
    public void delete(Propietario propietario) {
        // Asegurarse de que el propietario no sea null y que tenga un ID válido
        if (propietario == null || propietario.getId() == null) {
            throw new IllegalArgumentException("El propietario no puede ser null y debe tener un ID válido");
        }

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM propietarios WHERE id = ?")) {
            ps.setInt(1, propietario.getId()); // Establece el ID del propietario.

            ps.executeUpdate(); // Ejecuta la eliminación.
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }
}
