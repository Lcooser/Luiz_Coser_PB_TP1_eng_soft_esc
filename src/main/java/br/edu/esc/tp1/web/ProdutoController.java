package br.edu.esc.tp1.web;

import br.edu.esc.tp1.domain.Produto;
import br.edu.esc.tp1.exception.ProdutoDuplicadoException;
import br.edu.esc.tp1.exception.ProdutoNaoEncontradoException;
import br.edu.esc.tp1.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "produtos/listagem";
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("produto", new ProdutoForm());
        return "produtos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Produto> produto = produtoService.buscarProduto(id);
        if (produto.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/produtos";
        }
        Produto p = produto.get();
        model.addAttribute("produto", new ProdutoForm(p.getId(), p.getNome(), p.getDescricao(),
                p.getPreco() != null ? p.getPreco().toString() : "",
                p.getQuantidadeEstoque() != null ? p.getQuantidadeEstoque().toString() : ""));
        return "produtos/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute ProdutoForm form, Model model, RedirectAttributes redirectAttributes) {
        try {
            Long id = form.getId() != null && !form.getId().trim().isEmpty() ? Long.parseLong(form.getId()) : null;
            if (id == null) {
                model.addAttribute("erro", "ID é obrigatório.");
                model.addAttribute("produto", form);
                return "produtos/formulario";
            }
            BigDecimal preco = form.getPreco() != null && !form.getPreco().trim().isEmpty()
                    ? new BigDecimal(form.getPreco()) : null;
            Integer qtd = form.getQuantidadeEstoque() != null && !form.getQuantidadeEstoque().trim().isEmpty()
                    ? Integer.parseInt(form.getQuantidadeEstoque()) : null;

            if (produtoService.buscarProduto(id).isPresent()) {
                produtoService.atualizarProduto(id, form.getNome(), form.getDescricao(), preco, qtd);
                redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso.");
            } else {
                produtoService.criarProduto(id, form.getNome(), form.getDescricao(), preco, qtd);
                redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso.");
            }
            return "redirect:/produtos";
        } catch (NumberFormatException e) {
            model.addAttribute("erro", "ID, preço ou quantidade inválidos.");
            model.addAttribute("produto", form);
            return "produtos/formulario";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("produto", form);
            return "produtos/formulario";
        } catch (ProdutoDuplicadoException e) {
            model.addAttribute("erro", "Já existe um produto com este ID.");
            model.addAttribute("produto", form);
            return "produtos/formulario";
        }
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.deletarProduto(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso.");
        } catch (ProdutoNaoEncontradoException e) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }

    public static class ProdutoForm {
        private String id;
        private String nome;
        private String descricao;
        private String preco;
        private String quantidadeEstoque;

        public ProdutoForm() {}

        public ProdutoForm(Long id, String nome, String descricao, String preco, String quantidadeEstoque) {
            this.id = id != null ? id.toString() : "";
            this.nome = nome;
            this.descricao = descricao;
            this.preco = preco;
            this.quantidadeEstoque = quantidadeEstoque;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public String getPreco() { return preco; }
        public void setPreco(String preco) { this.preco = preco; }
        public String getQuantidadeEstoque() { return quantidadeEstoque; }
        public void setQuantidadeEstoque(String quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    }
}
