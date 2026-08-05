package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaResponseDTO {
    private Long id;
    private String nome;

    public CategoriaResponseDTO(Categoria categoria){
        id = categoria.getId();
        nome = categoria.getName();

    }
}
