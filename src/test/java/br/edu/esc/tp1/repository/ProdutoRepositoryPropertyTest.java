package br.edu.esc.tp1.repository;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.exception.ProdutoDuplicadoException;
import br.edu.esc.tp1.exception.ProdutoNaoEncontradoException;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoRepositoryPropertyTest {
    
    @Property
    void produtoCriadoDeveSerEncontradoPorId(
            @ForAll @Positive long id,
            @ForAll @StringLength(min = 1, max = 50) String nome) {
        
        Assume.that(nome != null && !nome.trim().isEmpty());
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto produto = Produto.criar(id, nome, "Descrição", 
                                        new BigDecimal("100.00"), 10);
        
        repository.criar(produto);
        Optional<Produto> encontrado = repository.buscarPorId(id);
        
        assertTrue(encontrado.isPresent());
        assertEquals(produto, encontrado.get());
    }
    
    @Property
    void listaDeveConterTodosOsProdutosCriados(
            @ForAll @Size(min = 1, max = 100) Set<@Positive Long> ids) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        
        for (Long id : ids) {
            Produto produto = Produto.criar(id, "Produto " + id, "Desc", 
                                           new BigDecimal("100.00"), 10);
            repository.criar(produto);
        }
        
        List<Produto> todos = repository.listarTodos();
        
        assertEquals(ids.size(), todos.size());
        Set<Long> idsEncontrados = todos.stream()
            .map(Produto::getId)
            .collect(Collectors.toSet());
        assertEquals(ids, idsEncontrados);
    }
    
    @Property
    void naoDeveSerPossivelCriarProdutoDuplicado(
            @ForAll @Positive long id) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto produto = Produto.criar(id, "Nome", "Desc", 
                                       new BigDecimal("100.00"), 10);
        
        repository.criar(produto);
        
        ProdutoDuplicadoException exception = assertThrows(
            ProdutoDuplicadoException.class,
            () -> repository.criar(produto)
        );
        
        assertEquals(id, exception.getProdutoId());
    }
    
    @Property
    void produtoDeletadoNaoDeveSerEncontrado(
            @ForAll @Positive long id) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto produto = Produto.criar(id, "Nome", "Desc", 
                                       new BigDecimal("100.00"), 10);
        
        repository.criar(produto);
        repository.deletar(id);
        
        Optional<Produto> encontrado = repository.buscarPorId(id);
        assertTrue(encontrado.isEmpty());
        assertFalse(repository.existe(id));
    }
    
    @Property
    void atualizacaoDeveModificarProdutoExistente(
            @ForAll @Positive long id,
            @ForAll @StringLength(min = 1, max = 50) String nomeOriginal,
            @ForAll @StringLength(min = 1, max = 50) String nomeNovo) {
        
        Assume.that(nomeOriginal != null && !nomeOriginal.trim().isEmpty());
        Assume.that(nomeNovo != null && !nomeNovo.trim().isEmpty());
        Assume.that(!nomeOriginal.equals(nomeNovo));
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto original = Produto.criar(id, nomeOriginal, "Desc", 
                                        new BigDecimal("100.00"), 10);
        
        repository.criar(original);
        
        Produto atualizado = Produto.criar(id, nomeNovo, "Nova Desc", 
                                           new BigDecimal("200.00"), 20);
        repository.atualizar(atualizado);
        
        Optional<Produto> encontrado = repository.buscarPorId(id);
        assertTrue(encontrado.isPresent());
        assertEquals(nomeNovo, encontrado.get().getNome());
    }
    
    @Property
    void naoDeveSerPossivelAtualizarProdutoInexistente(
            @ForAll @Positive long id) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto produto = Produto.criar(id, "Nome", "Desc", 
                                       new BigDecimal("100.00"), 10);
        
        ProdutoNaoEncontradoException exception = assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> repository.atualizar(produto)
        );
        
        assertEquals(id, exception.getProdutoId());
    }
    
    @Property
    void naoDeveSerPossivelDeletarProdutoInexistente(
            @ForAll @Positive long id) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        
        ProdutoNaoEncontradoException exception = assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> repository.deletar(id)
        );
        
        assertEquals(id, exception.getProdutoId());
    }
    
    @Property
    void metodoExisteDeveRetornarTrueApenasParaProdutosCriados(
            @ForAll @Positive long idExistente,
            @ForAll @Positive long idInexistente) {
        
        Assume.that(idExistente != idInexistente);
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Produto produto = Produto.criar(idExistente, "Nome", "Desc", 
                                       new BigDecimal("100.00"), 10);
        
        repository.criar(produto);
        
        assertTrue(repository.existe(idExistente));
        assertFalse(repository.existe(idInexistente));
    }
    
    @Property
    void operacoesCrudDevemManterConsistencia(
            @ForAll @Size(max = 50) List<@Positive Long> ids) {
        
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        Set<Long> idsUnicos = ids.stream().collect(Collectors.toSet());
        
        for (Long id : idsUnicos) {
            Produto produto = Produto.criar(id, "Produto " + id, "Desc", 
                                           new BigDecimal("100.00"), 10);
            repository.criar(produto);
            assertTrue(repository.existe(id));
        }
        
        assertEquals(idsUnicos.size(), repository.listarTodos().size());
        
        for (Long id : idsUnicos) {
            repository.deletar(id);
            assertFalse(repository.existe(id));
        }
        
        assertTrue(repository.listarTodos().isEmpty());
    }
}
