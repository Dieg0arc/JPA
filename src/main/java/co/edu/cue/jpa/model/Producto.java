package co.edu.cue.jpa.model;


import lombok.*;


import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Producto {
    private Integer id;
    private String nombre;
    private int precio;
    private Date fecha_registro;
    private Categoria categoria;
    private String sku;
}
