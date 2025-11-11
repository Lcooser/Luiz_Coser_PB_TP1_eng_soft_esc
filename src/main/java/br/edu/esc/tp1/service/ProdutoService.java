package br.edu.esc.tp1.service;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoService {
    
    private final ProdutoRepository repository;
    
    public ProdutoService(ProdutoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repositório não pode ser nulo");
        }
        this.repository = repository;
    }
    
    public void criarProduto(Long id, String nome, String descricao, 
                            BigDecimal preco, Integer quantidadeEstoque) {
        Produto produto = Produto.criar(id, nome, descricao, preco, quantidadeEstoque);
        repository.criar(produto);
    }
    
    public Optional<Produto> buscarProduto(Long id) {
        return repository.buscarPorId(id);
    }
    
    public List<Produto> listarProdutos() {
        return repository.listarTodos();
    }
    
    public void atualizarProduto(Long id, String nome, String descricao, 
                                BigDecimal preco, Integer quantidadeEstoque) {
        Produto produtoAtualizado = Produto.criar(id, nome, descricao, preco, quantidadeEstoque);
        repository.atualizar(produtoAtualizado);
    }
    
    public void deletarProduto(Long id) {
        repository.deletar(id);
    }
}
