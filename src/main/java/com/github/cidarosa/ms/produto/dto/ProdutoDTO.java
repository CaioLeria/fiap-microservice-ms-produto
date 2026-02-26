package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoDTO {
private Long id;
private String nome;
private String descricao;
private Double valor;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.valor = produto.getValor();
    }
}

