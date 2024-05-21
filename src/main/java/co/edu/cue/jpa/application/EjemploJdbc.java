package co.edu.cue.jpa.application;


import co.edu.cue.jpa.model.Categoria;
import co.edu.cue.jpa.model.Producto;
import co.edu.cue.jpa.services.CatalogoServicio;
import co.edu.cue.jpa.services.Servicio;

import java.sql.Date;
import java.sql.SQLException;

public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {
        Servicio servicio = new CatalogoServicio();
        System.out.println("============= listar =============");
        servicio.listar().forEach(System.out::println);
        Categoria categoria = new Categoria();
        categoria.setCategoria("Iluminación");
        Producto producto = new Producto();
        producto.setNombre("Lámpara led escritorio");
        producto.setPrecio(990);
        producto.setFecha_registro(new Date());
        producto.setSku("abcdefgh12");
        servicio.guardarProductoConCategoria(producto, categoria);
        System.out.println("Producto guardado con éxito: " +
                producto.getId());
        servicio.listar().forEach(System.out::println);
    }

}