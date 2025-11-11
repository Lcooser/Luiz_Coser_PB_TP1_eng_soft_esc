package br.edu.esc.tp1;

import br.edu.esc.tp1.repository.ProdutoRepository;
import br.edu.esc.tp1.repository.ProdutoRepositoryImpl;
import br.edu.esc.tp1.service.ProdutoService;
import br.edu.esc.tp1.ui.ConsoleUI;

public class Main {
    
    public static void main(String[] args) {
        ProdutoRepository repository = new ProdutoRepositoryImpl();
        ProdutoService service = new ProdutoService(repository);
        ConsoleUI ui = new ConsoleUI(service);
        
        ui.iniciar();
    }
}
