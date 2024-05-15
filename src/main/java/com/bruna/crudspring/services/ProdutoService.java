package com.bruna.crudspring.services;

import com.bruna.crudspring.controller.CreateProdutoDTO;
import com.bruna.crudspring.controller.UpdateProdutoDTO;
import com.bruna.crudspring.entity.Produto;
import com.bruna.crudspring.repository.ProdutoRepository;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public UUID createProduto(CreateProdutoDTO createProdutoDTO) {

        //converse de DTO -> Entity

        var entity = new Produto(
                UUID.randomUUID(),
                createProdutoDTO.name(),
                createProdutoDTO.category(),
                Instant.now(),
                null);

         var produtoSaved = produtoRepository.save(entity);
         return produtoSaved.getProdutoId();
    }

    public Optional<Produto> getProdutoById(String produtoId){

        return produtoRepository.findById(UUID.fromString(produtoId));
    }

    public List<Produto> listProdutos(){
        return produtoRepository.findAll();
    }


    public void updateProdutoById(String produtoId, UpdateProdutoDTO updateProdutoDTO) {
        var produtosExists = produtoRepository.existsById(UUID.fromString(produtoId));

        //converse de DTO -> Entity

        var produtoEntity = produtoRepository.findById(UUID.fromString(produtoId));

        if(produtoEntity.isPresent()) {
            var produto = produtoEntity.get();

            if (updateProdutoDTO.category() !=null){
                produto.setCategory(updateProdutoDTO.category());
            }

            /*fazer condicao set aqui....*/

            produtoRepository.save(produto);

        }


    }


   
    public  void deleteById(String produtoId){
        var produtosExists = produtoRepository.existsById(UUID.fromString(produtoId));
        if(produtosExists) {
            produtoRepository.deleteById(UUID.fromString(produtoId));
        }

    }

}