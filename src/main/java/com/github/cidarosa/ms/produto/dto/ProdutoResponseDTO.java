package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoResponseDTO {
private Long id;


private String nome;
private String descricao;
private Double valor;
private CategoriaResponseDTO categoria;

    public ProdutoResponseDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
        categoria = new CategoriaResponseDTO(produto.getCategoria());
    }
}

