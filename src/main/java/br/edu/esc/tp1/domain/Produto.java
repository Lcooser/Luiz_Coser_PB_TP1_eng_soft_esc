package br.edu.esc.tp1.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Produto {
    
    private final Long id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;
    private final Integer quantidadeEstoque;
    
    private Produto(Long id, String nome, String descricao, 
                   BigDecimal preco, Integer quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public static Produto criar(Long id, String nome, String descricao, 
                               BigDecimal preco, Integer quantidadeEstoque) {
        validarDados(nome, descricao, preco, quantidadeEstoque);
        return new Produto(id, nome, descricao, preco, quantidadeEstoque);
    }
    
    private static void validarDados(String nome, String descricao, 
                                    BigDecimal preco, Integer quantidadeEstoque) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto não pode ser vazia");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço deve ser maior ou igual a zero");
        }
        if (quantidadeEstoque == null || quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque deve ser maior ou igual a zero");
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public Produto atualizar(String novoNome, String novaDescricao, 
                            BigDecimal novoPreco, Integer novaQuantidade) {
        return criar(this.id, novoNome, novaDescricao, novoPreco, novaQuantidade);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Produto{id=%d, nome='%s', preco=%s, estoque=%d}", 
                           id, nome, preco, quantidadeEstoque);
    }
}
