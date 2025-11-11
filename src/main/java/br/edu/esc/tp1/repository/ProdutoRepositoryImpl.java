package br.edu.esc.tp1.repository;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.exception.ProdutoDuplicadoException;
import br.edu.esc.tp1.exception.ProdutoNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    
    private final Map<Long, Produto> produtos;
    
    public ProdutoRepositoryImpl() {
        this.produtos = new ConcurrentHashMap<>();
    }
    
    @Override
    public void criar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        
        Long id = produto.getId();
        if (produtos.containsKey(id)) {
            throw new ProdutoDuplicadoException(id);
        }
        
        produtos.put(id, produto);
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(produtos.get(id));
    }
    
    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
    
    @Override
    public void atualizar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        
        Long id = produto.getId();
        if (!produtos.containsKey(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        
        produtos.put(id, produto);
    }
    
    @Override
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        if (!produtos.containsKey(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        
        produtos.remove(id);
    }
    
    @Override
    public boolean existe(Long id) {
        if (id == null) {
            return false;
        }
        return produtos.containsKey(id);
    }
}
