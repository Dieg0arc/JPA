package co.edu.cue.jpa.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Categoria {
    private Integer categoria_id;
    private String categoria;
}