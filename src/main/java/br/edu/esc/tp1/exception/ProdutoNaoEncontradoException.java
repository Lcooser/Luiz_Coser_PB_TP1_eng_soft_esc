package br.edu.esc.tp1.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    
    private final Long produtoId;
    
    public ProdutoNaoEncontradoException(Long produtoId) {
        super(String.format("Produto com ID %d não foi encontrado", produtoId));
        this.produtoId = produtoId;
    }
    
    public Long getProdutoId() {
        return produtoId;
    }
}
