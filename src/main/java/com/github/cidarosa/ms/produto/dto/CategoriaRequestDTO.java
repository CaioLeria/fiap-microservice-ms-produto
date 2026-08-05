package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaRequestDTO {

    @NotBlank(message = "Campo nome deve ser preenchido")
    @Size(min = 3, max = 100, message = "O nome deve ter 3 a 100 caracteres")
    @Schema(example = "ferramentas")
    private String nome;

    public CategoriaRequestDTO(Categoria categoria){
        nome = categoria.getName();

    }
}
