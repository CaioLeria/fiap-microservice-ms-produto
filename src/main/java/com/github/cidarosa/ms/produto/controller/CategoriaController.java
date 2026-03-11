package com.github.cidarosa.ms.produto.controller;

import com.github.cidarosa.ms.produto.dto.CategoriaDTO;
import com.github.cidarosa.ms.produto.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(){
        List<CategoriaDTO> categorias = categoriaService.findAllCategorias();
        return ResponseEntity.ok(categorias);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {

        CategoriaDTO categoriaDto = categoriaService.findCategoriaById(id);

        return ResponseEntity.ok(categoriaDto);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody @Valid CategoriaDTO categoriaDto) {

        categoriaDto = categoriaService.saveCategoria(categoriaDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDto) {

        categoriaDto = categoriaService.updateCategoria(id, categoriaDto);

        return ResponseEntity.ok(categoriaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long id){

        categoriaService.deleteCategoriaById(id);

        return ResponseEntity.noContent().build();
    }
}
