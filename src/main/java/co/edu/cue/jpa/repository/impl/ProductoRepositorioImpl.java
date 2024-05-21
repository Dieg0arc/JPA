package co.edu.cue.jpa.repository.impl;

import co.edu.cue.jpa.model.Categoria;
import co.edu.cue.jpa.model.Producto;
import co.edu.cue.jpa.repository.Repositorio;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
public class ProductoRepositorioImpl implements Repositorio<Producto> {
    private Connection conn;
    public ProductoRepositorioImpl(Connection conn) {
        this.conn = conn;
    }
    public Connection getConn() {
        return conn;
    }
    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public ProductoRepositorioImpl() {
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p " +
                     "inner join categorias as c ON (p.categoria_id = c.id)"))
        {
            while (rs.next()) {
                Producto p = crearProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }
    @Override
    public Producto porId(int id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.*, " +
                "c.nombre as categoria FROM productos as p " +
                "inner join categorias as c ON (p.categoria_id = c.id) " +
                "WHERE p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        }
        return producto;
    }
    @Override
    public Producto guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId() != null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=?, sku=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos(nombre, precio, categoria_id, sku, fecha_registro) VALUES(?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getCategoria_id());
            stmt.setString(4, producto.getSku());
            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(5, producto.getId());
            } else {
                stmt.setDate(5, new
                        Date(producto.getFecha_registro().getTime()));
            }
            stmt.executeUpdate();
            if (producto.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setId(rs.getInt(1));
                    }
                }
            }
            return producto;
        }
    }


    @Override
    public List<Producto> listarCategoria() throws SQLException {
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
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFecha_registro(rs.getDate("fecha_registro"));
        p.setSku(rs.getString("sku"));
        Categoria categoria = new Categoria();
        categoria.setCategoria_id(rs.getInt("categoria_id"));
        categoria.setCategoria(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }

}