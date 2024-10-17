package org.example.dao;

import org.example.model.Pelicula;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Esta clase implementa el patrón DAO para manejar operaciones con el objeto Perro.
public class PeliculaDAO implements DAO<Pelicula> {

    // Conexión a la base de datos.
    Connection con;

    // Constructor que recibe la conexión a la base de datos.
    public PeliculaDAO(Connection c) {
        con = c;
    }

    // Método para encontrar todos los perros en la base de datos.
    @Override
    public List<Pelicula> findAll() {
        List<Pelicula> listaPelis = new ArrayList<>(); // Lista para almacenar resultados.

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM perros")) {
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta.

            // Itera sobre los resultados y crea objetos Perro a partir de los datos obtenidos.
            while (rs.next()) {
                Pelicula peli = new Pelicula();
                peli.setId(rs.getInt("id")); // Establece el ID del perro.
                peli.setTitulo(rs.getString("titulo")); // Establece el nombre del perro.
                peli.setGenero(rs.getInt("genero")); // Establece la edad del perro.
                peli.setAnho(rs.getString("año")); // Establece la raza del perro.
                listaPelis.add(peli); // Agrega el perro a la lista.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones relacionadas con SQL.
        }

        return listaPelis; // Devuelve la lista de perros.
    }

    // Encuentra una peli  por su ID.
    @Override
    public Pelicula findById(Integer id) {
        // Comprobar si id es null antes de continuar
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        Pelicula pelicula = null;
        String query = "SELECT * FROM perros WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Si se encuentra un perro, se crea el objeto Perro
            if (rs.next()) {
                pelicula = new Pelicula();
                pelicula.set(rs.getInt("id"));
                pelicula.setNombre(rs.getString("nombre"));
                pelicula.setEdad(rs.getInt("edad"));
                pelicula.setRaza(rs.getString("raza"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar perro por ID", e);
        }

        return pelicula; // Puede ser null si no se encuentra
    }

    @Override
    public void save(Pelicula pelicula) {

    }

    @Override
    public void update(Pelicula pelicula) {

    }

    @Override
    public void delete(Pelicula pelicula) {

    }

    // Guarda un nuevo perro en la base de datos.
    @Override
    public void save(Pelicula pelicula) {
        // Asegurarse de que el perro no sea null
        if (pelicula == null) {
            throw new IllegalArgumentException("El perro no puede ser null");
        }

        String query = "INSERT INTO perros(nombre, edad, raza) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pelicula.getTitulo()); // Establece el nombre.
            ps.setInt(2, pelicula.getEmail()); // Establece la edad.
            ps.setString(3, pelicula.getanho()); // Establece la raza.

            ps.executeUpdate(); // Ejecuta la inserción en la base de datos.

            // Obtener el ID generado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    perro.setId(generatedKeys.getInt(1)); // Establecer el ID generado
                } else {
                    throw new SQLException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }

    // Actualiza un perro existente en la base de datos.
    @Override
    public void update(Pelicula perro) {
        // Asegurarse de que el perro no sea null y que tenga un ID válido
        if (perro == null || perro.getId() == null) {
            throw new IllegalArgumentException("El perro no puede ser null y debe tener un ID válido");
        }

        try (PreparedStatement ps = con.prepareStatement("UPDATE perros SET nombre = ?, edad = ?, raza = ? WHERE id = ?")) {
            ps.setString(1, perro.getNombre()); // Actualiza el nombre.
            ps.setInt(2, perro.getEdad()); // Actualiza la edad.
            ps.setString(3, perro.getRaza()); // Actualiza la raza.
            ps.setInt(4, perro.getId()); // Establece el ID del perro.

            ps.executeUpdate(); // Ejecuta la actualización.
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }

    // Elimina un perro de la base de datos.
    @Override
    public void delete(Pelicula perro) {
        // Asegurarse de que el perro no sea null y que tenga un ID válido
        if (perro == null || perro.getId() == null) {
            throw new IllegalArgumentException("El perro no puede ser null y debe tener un ID válido");
        }

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM perros WHERE id = ?")) {
            ps.setInt(1, perro.getId()); // Establece el ID del perro.

            ps.executeUpdate(); // Ejecuta la eliminación.
        } catch (SQLException e) {
            throw new RuntimeException(e); // Maneja excepciones de SQL.
        }
    }
}
