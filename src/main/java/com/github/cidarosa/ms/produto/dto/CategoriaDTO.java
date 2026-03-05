package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaDTO {
private Long id;

    @NotBlank(message = "Campo nome deve ser preenchido")
    @Size(min = 3, max = 100, message = "O nome deve ter 3 a 100 caracteres")
    private String nome;

    public CategoriaDTO (Categoria categoria){
        id = categoria.getId();
        nome = categoria.getName();

    }
}
