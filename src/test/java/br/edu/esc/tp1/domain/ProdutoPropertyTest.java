package br.edu.esc.tp1.domain;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoPropertyTest {
    
    @Property
    void produtoDeveSerCriadoComIdPositivo(@ForAll @Positive long id) {
        Produto produto = Produto.criar(id, "Nome", "Descrição", 
                                       new BigDecimal("100.00"), 10);
        
        assertEquals(id, produto.getId());
    }
    
    @Property
    void produtoDeveAceitarNomeNaoVazio(
            @ForAll @StringLength(min = 1, max = 100) String nome) {
        Assume.that(nome != null && !nome.trim().isEmpty());
        
        Produto produto = Produto.criar(1L, nome, "Descrição", 
                                       new BigDecimal("100.00"), 10);
        
        assertEquals(nome, produto.getNome());
    }
    
    @Property
    void produtoDeveAceitarPrecoZeroOuPositivo(
            @ForAll @BigRange(min = "0", max = "1000000") BigDecimal preco) {
        Produto produto = Produto.criar(1L, "Nome", "Descrição", preco, 10);
        
        assertTrue(produto.getPreco().compareTo(BigDecimal.ZERO) >= 0);
    }
    
    @Property
    void produtoDeveAceitarQuantidadeZeroOuPositiva(
            @ForAll @IntRange(min = 0, max = 1000000) int quantidade) {
        Produto produto = Produto.criar(1L, "Nome", "Descrição", 
                                       new BigDecimal("100.00"), quantidade);
        
        assertTrue(produto.getQuantidadeEstoque() >= 0);
    }
    
    @Property
    void produtosComMesmoIdDevemSerIguais(
            @ForAll @Positive long id,
            @ForAll @StringLength(min = 1, max = 50) String nome1,
            @ForAll @StringLength(min = 1, max = 50) String nome2,
            @ForAll @BigRange(min = "0") BigDecimal preco1,
            @ForAll @BigRange(min = "0") BigDecimal preco2) {
        
        Assume.that(nome1 != null && !nome1.trim().isEmpty());
        Assume.that(nome2 != null && !nome2.trim().isEmpty());
        
        Produto produto1 = Produto.criar(id, nome1, "Desc 1", preco1, 10);
        Produto produto2 = Produto.criar(id, nome2, "Desc 2", preco2, 20);
        
        assertEquals(produto1, produto2);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }
    
    @Property
    void produtosComIdsDiferentesNaoDevemSerIguais(
            @ForAll @Positive long id1,
            @ForAll @Positive long id2) {
        
        Assume.that(id1 != id2);
        
        Produto produto1 = Produto.criar(id1, "Nome", "Desc", 
                                        new BigDecimal("100.00"), 10);
        Produto produto2 = Produto.criar(id2, "Nome", "Desc", 
                                        new BigDecimal("100.00"), 10);
        
        assertNotEquals(produto1, produto2);
    }
    
    @Property
    void atualizacaoDeveManterImutabilidade(
            @ForAll @StringLength(min = 1, max = 50) String nomeOriginal,
            @ForAll @StringLength(min = 1, max = 50) String nomeNovo,
            @ForAll @BigRange(min = "0") BigDecimal precoOriginal,
            @ForAll @BigRange(min = "0") BigDecimal precoNovo) {
        
        Assume.that(nomeOriginal != null && !nomeOriginal.trim().isEmpty());
        Assume.that(nomeNovo != null && !nomeNovo.trim().isEmpty());
        
        Produto original = Produto.criar(1L, nomeOriginal, "Desc", 
                                        precoOriginal, 10);
        
        Produto atualizado = original.atualizar(nomeNovo, "Nova Desc", 
                                                precoNovo, 20);
        
        assertNotSame(original, atualizado);
        assertEquals(nomeOriginal, original.getNome());
        assertEquals(nomeNovo, atualizado.getNome());
    }
    
    @Property
    void deveLancarExcecaoParaPrecoNegativo(
            @ForAll @Negative BigDecimal precoNegativo) {
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "Nome", "Desc", precoNegativo, 10)
        );
        
        assertTrue(exception.getMessage().contains("Preço"));
    }
    
    @Property
    void deveLancarExcecaoParaQuantidadeNegativa(
            @ForAll @Negative int quantidadeNegativa) {
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Produto.criar(1L, "Nome", "Desc", 
                              new BigDecimal("100.00"), quantidadeNegativa)
        );
        
        assertTrue(exception.getMessage().contains("Quantidade"));
    }
    
    @Provide
    Arbitrary<BigDecimal> precosValidos() {
        return Arbitraries.bigDecimals()
            .between(BigDecimal.ZERO, new BigDecimal("1000000"))
            .ofScale(2)
            .shrinkTowards(BigDecimal.ZERO);
    }
    
    @Property
    void produtoDeveManterPrecisaoDecimalDoPreco(
            @ForAll("precosValidos") BigDecimal preco) {
        
        Produto produto = Produto.criar(1L, "Nome", "Desc", preco, 10);
        
        assertTrue(produto.getPreco().scale() <= 2);
        assertEquals(0, produto.getPreco().compareTo(
            preco.setScale(2, RoundingMode.HALF_UP)));
    }
}
