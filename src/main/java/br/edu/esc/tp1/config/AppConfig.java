package br.edu.esc.tp1.config;

import br.edu.esc.tp1.repository.ProdutoRepository;
import br.edu.esc.tp1.repository.ProdutoRepositoryImpl;
import br.edu.esc.tp1.service.ProdutoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProdutoRepository produtoRepository() {
        return new ProdutoRepositoryImpl();
    }

    @Bean
    public ProdutoService produtoService(ProdutoRepository produtoRepository) {
        return new ProdutoService(produtoRepository);
    }
}
