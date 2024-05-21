package co.edu.cue.jpa.repository.impl;

import co.edu.cue.jpa.model.Categoria;
import co.edu.cue.jpa.model.Producto;
import co.edu.cue.jpa.repository.Repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl2 implements Repositorio<Categoria> {
    private Connection conn;
    public CategoriaRepositorioImpl2(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl2() {
    }

    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {
            while (rs.next()) {
                categorias.add(crearCategoria(rs));
            }
        }
        return categorias;
    }
    @Override
    public Categoria porId(int id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM  " +
                "categorias as c WHERE c.id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = crearCategoria(rs);
                }
            }
        }
        return categoria;
    }
    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        String sql = null;
        if (categoria.getCategoria_id() != null && categoria.getCategoria_id() > 0) {
            sql = "UPDATE categorias SET nombre=? WHERE id=?";
        } else {
            sql = "INSERT INTO categorias(nombre) VALUES(?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getCategoria());
            if (categoria.getCategoria_id() != null && categoria.getCategoria_id() > 0) {
                stmt.setLong(2, categoria.getCategoria_id());
            }
            stmt.executeUpdate();
            if (categoria.getCategoria_id() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setCategoria_id(rs.getInt(1));
                    }
                }
            }
        }
        return categoria;
    }
    @Override
    public List<Categoria> listarCategoria() throws SQLException {
        return null;
    }

    @Override
    public Categoria porIdCategoria(int id) throws SQLException {
        return null;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        return null;
    }

    @Override
    public void eliminarCategoria(int id) throws SQLException {

    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    private Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setCategoria_id(rs.getInt("id"));
        c.setCategoria(rs.getString("nombre"));
        return c;
    }

}