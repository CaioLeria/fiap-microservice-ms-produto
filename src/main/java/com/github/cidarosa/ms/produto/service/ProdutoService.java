package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.ProdutoDTO;
import com.github.cidarosa.ms.produto.entities.Produto;
import com.github.cidarosa.ms.produto.repositories.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoDTO::new).toList();
    }
    @Transactional
    public ProdutoDTO findProdutoById(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Recurso não encontrado .ID:" + id)
        );
        return new ProdutoDTO(produto);
    }
    @Transactional
    public ProdutoDTO saveProduto (ProdutoDTO produtoDTO){
        Produto produto = new Produto();

        copyDtoToProduct(produtoDTO,produto);
        produto = produtoRepository.save(produto);
        return new ProdutoDTO(produto);
    }
    private void copyDtoToProduct (ProdutoDTO produtoDTO, Produto produto){
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
    }
    @Transactional
    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO){
        try{
           Produto produto = produtoRepository.getReferenceById(id);
           copyDtoToProduct(produtoDTO, produto);
           produto = produtoRepository.save(produto);
           return new ProdutoDTO(produto);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }
    @Transactional
    public void deleteProduto(Long id){
        if(!produtoRepository.existsById(id)){
            throw new EntityNotFoundException("Recurso não encontrado. ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
