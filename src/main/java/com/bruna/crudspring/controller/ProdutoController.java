package com.bruna.crudspring.controller;


import com.bruna.crudspring.entity.Produto;
import com.bruna.crudspring.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private  final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody CreateProdutoDTO createProdutoDTO) {
        var produtoId = produtoService.createProduto(createProdutoDTO);

        return ResponseEntity.created(URI.create("api/produtos/" + produtoId.toString())).build();
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable String produtoId) {
        var produto = produtoService.getProdutoById(produtoId);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return  ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Produto>> listProdutos(){
       var produtos = produtoService.listProdutos();
       return ResponseEntity.ok(produtos);
    }

    @PutMapping(("/{produtoId}"))
    public ResponseEntity<Void> updateProdutoById(@PathVariable("produtoId") String produtoId,
                                   @RequestBody UpdateProdutoDTO updateProdutoDTO){
        produtoService.updateProdutoById( produtoId, updateProdutoDTO);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deleteById(@PathVariable("produtoId") String produtoId) {
        produtoService.deleteById(produtoId);

        return ResponseEntity.noContent().build();

    }
}
