package br.edu.esc.tp1.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade Produto")
class ProdutoTest {
    
    @Test
    @DisplayName("Deve criar produto com dados válidos")
    void deveCriarProdutoComDadosValidos() {
        Produto produto = Produto.criar(1L, "Notebook", "Notebook Dell", 
                                       new BigDecimal("2500.00"), 10);
        
        assertNotNull(produto);
        assertEquals(1L, produto.getId());
        assertEquals("Notebook", produto.getNome());
        assertEquals("Notebook Dell", produto.getDescricao());
        assertEquals(new BigDecimal("2500.00"), produto.getPreco());
        assertEquals(10, produto.getQuantidadeEstoque());
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando nome é nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, null, "Descrição", 
                              new BigDecimal("100.00"), 5)
        );
        
        assertTrue(exception.getMessage().contains("Nome"));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "   ", "Descrição", 
                              new BigDecimal("100.00"), 5)
        );
        
        assertTrue(exception.getMessage().contains("Nome"));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando descrição é nula")
    void deveLancarExcecaoQuandoDescricaoNula() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "Nome", null, 
                              new BigDecimal("100.00"), 5)
        );
        
        assertTrue(exception.getMessage().contains("Descrição"));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando preço é negativo")
    void deveLancarExcecaoQuandoPrecoNegativo() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "Nome", "Descrição", 
                              new BigDecimal("-10.00"), 5)
        );
        
        assertTrue(exception.getMessage().contains("Preço"));
    }
    
    @Test
    @DisplayName("Deve aceitar preço zero")
    void deveAceitarPrecoZero() {
        Produto produto = Produto.criar(1L, "Produto Grátis", "Descrição", 
                                       BigDecimal.ZERO, 5);
        
        assertEquals(BigDecimal.ZERO, produto.getPreco());
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando quantidade é negativa")
    void deveLancarExcecaoQuandoQuantidadeNegativa() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "Nome", "Descrição", 
                              new BigDecimal("100.00"), -1)
        );
        
        assertTrue(exception.getMessage().contains("Quantidade"));
    }
    
    @Test
    @DisplayName("Deve aceitar quantidade zero")
    void deveAceitarQuantidadeZero() {
        Produto produto = Produto.criar(1L, "Produto", "Descrição", 
                                       new BigDecimal("100.00"), 0);
        
        assertEquals(0, produto.getQuantidadeEstoque());
    }
    
    @Test
    @DisplayName("Dois produtos com mesmo ID devem ser iguais")
    void produtosComMesmoIdDevemSerIguais() {
        Produto produto1 = Produto.criar(1L, "Produto 1", "Desc 1", 
                                        new BigDecimal("100.00"), 5);
        Produto produto2 = Produto.criar(1L, "Produto 2", "Desc 2", 
                                        new BigDecimal("200.00"), 10);
        
        assertEquals(produto1, produto2);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }
    
    @Test
    @DisplayName("Produtos com IDs diferentes não devem ser iguais")
    void produtosComIdsDiferentesNaoDevemSerIguais() {
        Produto produto1 = Produto.criar(1L, "Produto", "Desc", 
                                        new BigDecimal("100.00"), 5);
        Produto produto2 = Produto.criar(2L, "Produto", "Desc", 
                                        new BigDecimal("100.00"), 5);
        
        assertNotEquals(produto1, produto2);
    }
    
    @Test
    @DisplayName("Deve atualizar produto mantendo imutabilidade")
    void deveAtualizarProdutoMantendoImutabilidade() {
        Produto original = Produto.criar(1L, "Nome Antigo", "Desc Antiga", 
                                       new BigDecimal("100.00"), 5);
        
        Produto atualizado = original.atualizar("Nome Novo", "Desc Nova", 
                                                new BigDecimal("200.00"), 10);
        
        assertNotSame(original, atualizado);
        assertEquals("Nome Antigo", original.getNome());
        assertEquals("Nome Novo", atualizado.getNome());
        assertEquals(1L, atualizado.getId());
    }
}
