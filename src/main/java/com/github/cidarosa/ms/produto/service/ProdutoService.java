package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.ProdutoDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoRequestDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoResponseDTO;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.entities.Produto;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import com.github.cidarosa.ms.produto.repositories.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoDTO::new).toList();
    }
    @Transactional
    public ProdutoDTO findProdutoById(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso não encontrado .ID:" + id)
        );
        return new ProdutoDTO(produto);
    }
    @Transactional
    public ProdutoResponseDTO saveProduto (ProdutoRequestDTO produtoDTO){
            Produto produto = new Produto();

            copyDtoToProduct(produtoDTO,produto);
            produto = produtoRepository.save(produto);
            return new ProdutoResponseDTO(produto);
    }
    private void copyDtoToProduct (ProdutoRequestDTO requestDTO, Produto produto){
        produto.setNome(requestDTO.getNome());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setValor(requestDTO.getValor());

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId()).orElseThrow(
                () -> new DatabaseException("Não foi possivel salvar Produto. Categoria inexistente ID: " + requestDTO.getCategoriaId()+")")
        );

        produto.setCategoria(categoria);
    }
    @Transactional
    public ProdutoResponseDTO updateProduto(Long id, ProdutoRequestDTO requestDTO){

           Produto produto = produtoRepository.getReferenceById(id);
           copyDtoToProduct(requestDTO, produto);
           produto = produtoRepository.save(produto);
           return new ProdutoResponseDTO(produto);
    }
    @Transactional
    public void deleteProduto(Long id){
        if(!produtoRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
