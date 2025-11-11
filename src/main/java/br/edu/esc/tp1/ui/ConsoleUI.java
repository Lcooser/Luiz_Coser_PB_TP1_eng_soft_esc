package br.edu.esc.tp1.ui;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.service.ProdutoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {
    
    private final ProdutoService service;
    private final Scanner scanner;
    
    public ConsoleUI(ProdutoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciar() {
        exibirMensagemBemVindo();
        
        boolean continuar = true;
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            continuar = processarOpcao(opcao);
        }
        
        exibirMensagemDespedida();
        scanner.close();
    }
    
    private void exibirMensagemBemVindo() {
        System.out.println("========================================");
        System.out.println("   Sistema de Gerenciamento de Produtos");
        System.out.println("========================================\n");
    }
    
    private void exibirMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Criar produto");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Listar todos os produtos");
        System.out.println("4. Atualizar produto");
        System.out.println("5. Deletar produto");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private boolean processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarProduto();
            case 2 -> buscarProduto();
            case 3 -> listarProdutos();
            case 4 -> atualizarProduto();
            case 5 -> deletarProduto();
            case 0 -> {
                return false;
            }
            default -> {
                System.out.println("\nOpção inválida! Tente novamente.");
                return true;
            }
        }
        return true;
    }
    
    private void criarProduto() {
        System.out.println("\n--- Criar Produto ---");
        
        Long id = lerLong("ID: ");
        String nome = lerString("Nome: ");
        String descricao = lerString("Descrição: ");
        BigDecimal preco = lerBigDecimal("Preço: ");
        Integer quantidade = lerInt("Quantidade em estoque: ");
        
        try {
            service.criarProduto(id, nome, descricao, preco, quantidade);
            System.out.println("\n✓ Produto criado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao criar produto: " + e.getMessage());
        }
    }
    
    private void buscarProduto() {
        System.out.println("\n--- Buscar Produto ---");
        Long id = lerLong("ID do produto: ");
        
        Optional<Produto> produto = service.buscarProduto(id);
        if (produto.isPresent()) {
            exibirProduto(produto.get());
        } else {
            System.out.println("\n✗ Produto não encontrado!");
        }
    }
    
    private void listarProdutos() {
        System.out.println("\n--- Lista de Produtos ---");
        List<Produto> produtos = service.listarProdutos();
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            produtos.forEach(this::exibirProduto);
        }
    }
    
    private void atualizarProduto() {
        System.out.println("\n--- Atualizar Produto ---");
        Long id = lerLong("ID do produto: ");
        
        String nome = lerString("Novo nome: ");
        String descricao = lerString("Nova descrição: ");
        BigDecimal preco = lerBigDecimal("Novo preço: ");
        Integer quantidade = lerInt("Nova quantidade em estoque: ");
        
        try {
            service.atualizarProduto(id, nome, descricao, preco, quantidade);
            System.out.println("\n✓ Produto atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    private void deletarProduto() {
        System.out.println("\n--- Deletar Produto ---");
        Long id = lerLong("ID do produto: ");
        
        try {
            service.deletarProduto(id);
            System.out.println("\n✓ Produto deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao deletar produto: " + e.getMessage());
        }
    }
    
    private void exibirProduto(Produto produto) {
        System.out.println("\n" + produto);
    }
    
    private String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private Long lerLong(String prompt) {
        System.out.print(prompt);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para ID");
        }
    }
    
    private Integer lerInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para quantidade");
        }
    }
    
    private BigDecimal lerBigDecimal(String prompt) {
        System.out.print(prompt);
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para preço");
        }
    }
    
    private void exibirMensagemDespedida() {
        System.out.println("\nObrigado por usar o sistema!");
    }
}
