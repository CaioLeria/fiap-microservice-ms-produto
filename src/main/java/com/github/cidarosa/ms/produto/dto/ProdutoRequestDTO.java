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
public class ProdutoRequestDTO {


@NotBlank(message = "Campo nome é necessário")
@Size(min = 3, max = 100, message = "nome deve ter no minimo 3 caracteres e no maximo 100")
private String nome;

@NotBlank(message = "Campo de descricção requerido")
@Size(min=10, message = "A descioção deve er no minimo 10 caracters")
private String descricao;

@NotNull(message = "o valor deve ser preenchido")
@Positive(message = "O valor deve ser positivo")
private Double valor;

@NotNull(message = "Adicione uma categoria válida")
private Long categoriaId;

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.valor = produto.getValor();
        this.categoriaId = produto.getCategoria().getId();
    }
}

