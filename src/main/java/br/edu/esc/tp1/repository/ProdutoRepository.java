package br.edu.esc.tp1.repository;

import br.edu.esc.tp1.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    
    void criar(Produto produto);
    
    Optional<Produto> buscarPorId(Long id);
    
    List<Produto> listarTodos();
    
    void atualizar(Produto produto);
    
    void deletar(Long id);
    
    boolean existe(Long id);
}
