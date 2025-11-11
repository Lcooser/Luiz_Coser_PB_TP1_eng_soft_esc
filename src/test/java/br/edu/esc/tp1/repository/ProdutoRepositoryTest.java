package br.edu.esc.tp1.repository;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.exception.ProdutoDuplicadoException;
import br.edu.esc.tp1.exception.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Repositório de Produtos")
class ProdutoRepositoryTest {
    
    private ProdutoRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new ProdutoRepositoryImpl();
    }
    
    @Test
    @DisplayName("Deve criar produto com sucesso")
    void deveCriarProdutoComSucesso() {
        Produto produto = criarProdutoTeste(1L);
        
        repository.criar(produto);
        
        Optional<Produto> encontrado = repository.buscarPorId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals(produto, encontrado.get());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao criar produto duplicado")
    void deveLancarExcecaoAoCriarProdutoDuplicado() {
        Produto produto = criarProdutoTeste(1L);
        repository.criar(produto);
        
        ProdutoDuplicadoException exception = assertThrows(
            ProdutoDuplicadoException.class,
            () -> repository.criar(produto)
        );
        
        assertEquals(1L, exception.getProdutoId());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao criar produto nulo")
    void deveLancarExcecaoAoCriarProdutoNulo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> repository.criar(null)
        );
    }
    
    @Test
    @DisplayName("Deve buscar produto existente por ID")
    void deveBuscarProdutoExistentePorId() {
        Produto produto = criarProdutoTeste(1L);
        repository.criar(produto);
        
        Optional<Produto> encontrado = repository.buscarPorId(1L);
        
        assertTrue(encontrado.isPresent());
        assertEquals(produto, encontrado.get());
    }
    
    @Test
    @DisplayName("Deve retornar vazio ao buscar produto inexistente")
    void deveRetornarVazioAoBuscarProdutoInexistente() {
        Optional<Produto> encontrado = repository.buscarPorId(999L);
        
        assertTrue(encontrado.isEmpty());
    }
    
    @Test
    @DisplayName("Deve retornar vazio ao buscar com ID nulo")
    void deveRetornarVazioAoBuscarComIdNulo() {
        Optional<Produto> encontrado = repository.buscarPorId(null);
        
        assertTrue(encontrado.isEmpty());
    }
    
    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosOsProdutos() {
        repository.criar(criarProdutoTeste(1L));
        repository.criar(criarProdutoTeste(2L));
        repository.criar(criarProdutoTeste(3L));
        
        List<Produto> produtos = repository.listarTodos();
        
        assertEquals(3, produtos.size());
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos")
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        List<Produto> produtos = repository.listarTodos();
        
        assertTrue(produtos.isEmpty());
    }
    
    @Test
    @DisplayName("Deve atualizar produto existente")
    void deveAtualizarProdutoExistente() {
        Produto original = criarProdutoTeste(1L);
        repository.criar(original);
        
        Produto atualizado = Produto.criar(1L, "Nome Atualizado", 
                                           "Desc Atualizada", 
                                           new BigDecimal("300.00"), 20);
        repository.atualizar(atualizado);
        
        Optional<Produto> encontrado = repository.buscarPorId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals("Nome Atualizado", encontrado.get().getNome());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        Produto produto = criarProdutoTeste(999L);
        
        ProdutoNaoEncontradoException exception = assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> repository.atualizar(produto)
        );
        
        assertEquals(999L, exception.getProdutoId());
    }
    
    @Test
    @DisplayName("Deve deletar produto existente")
    void deveDeletarProdutoExistente() {
        Produto produto = criarProdutoTeste(1L);
        repository.criar(produto);
        
        repository.deletar(1L);
        
        Optional<Produto> encontrado = repository.buscarPorId(1L);
        assertTrue(encontrado.isEmpty());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        ProdutoNaoEncontradoException exception = assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> repository.deletar(999L)
        );
        
        assertEquals(999L, exception.getProdutoId());
    }
    
    @Test
    @DisplayName("Deve retornar true quando produto existe")
    void deveRetornarTrueQuandoProdutoExiste() {
        repository.criar(criarProdutoTeste(1L));
        
        assertTrue(repository.existe(1L));
    }
    
    @Test
    @DisplayName("Deve retornar false quando produto não existe")
    void deveRetornarFalseQuandoProdutoNaoExiste() {
        assertFalse(repository.existe(999L));
    }
    
    @Test
    @DisplayName("Deve retornar false quando ID é nulo")
    void deveRetornarFalseQuandoIdNulo() {
        assertFalse(repository.existe(null));
    }
    
    private Produto criarProdutoTeste(Long id) {
        return Produto.criar(id, "Produto " + id, "Descrição " + id, 
                            new BigDecimal("100.00"), 10);
    }
}
