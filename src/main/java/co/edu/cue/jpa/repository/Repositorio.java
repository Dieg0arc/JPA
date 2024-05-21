package co.edu.cue.jpa.repository;



import co.edu.cue.jpa.model.Categoria;
import co.edu.cue.jpa.model.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T> listar() throws SQLException;
    T porId(int id) throws SQLException;

    T guardar(T t) throws SQLException;
    void eliminar(int id) throws SQLException;
    List<T> listarCategoria() throws SQLException;
    Categoria porIdCategoria(int id) throws SQLException;
    Categoria guardarCategoria(Categoria categoria) throws SQLException;
    void eliminarCategoria(int id) throws SQLException;
    void guardarProductoConCategoria(Producto producto, Categoria categoria)
            throws SQLException;

    void setConn(Connection conn);
}