package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.CategoriaDTO;
import com.github.cidarosa.ms.produto.dto.CategoriaRequestDTO;
import com.github.cidarosa.ms.produto.dto.CategoriaResponseDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoDTO;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.entities.Produto;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaDTO::new).toList();
    }
    private void copyDtoToCategoria(CategoriaRequestDTO categoriaDTO, Categoria categoria){
        categoria.setName(categoriaDTO.getNome());
    }
    @Transactional(readOnly = true)
    public CategoriaDTO findCategoriaById (Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(

                () -> new ResourceNotFoundException("A categoria não foi achada Id:" +id)
        );
        return new CategoriaDTO(categoria);
    }
    @Transactional
    public CategoriaResponseDTO saveCategoria (CategoriaRequestDTO categoriaDTO){
        Categoria categoria = new Categoria();

        copyDtoToCategoria(categoriaDTO, categoria);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO inputDto){
        try{
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDto, categoria);
            categoria = categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(categoria);
        } catch (EntityNotFoundException e){
                throw new EntityNotFoundException("Não existe a categoria: "+id);
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoriaById(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        try {
            categoriaRepository.deleteById(id);
        } catch (
                DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível excluir Categoria. Existem podutos associados a ela");
        }
    }
}
