package br.edu.esc.tp1.exception;

public class ProdutoDuplicadoException extends RuntimeException {
    
    private final Long produtoId;
    
    public ProdutoDuplicadoException(Long produtoId) {
        super(String.format("Produto com ID %d já existe no sistema", produtoId));
        this.produtoId = produtoId;
    }
    
    public Long getProdutoId() {
        return produtoId;
    }
}
