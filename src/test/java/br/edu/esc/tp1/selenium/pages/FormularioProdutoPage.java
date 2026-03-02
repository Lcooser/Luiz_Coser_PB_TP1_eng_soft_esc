package br.edu.esc.tp1.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FormularioProdutoPage extends BasePage {

    public static final String PATH_NOVO = "/produtos/novo";

    @FindBy(id = "form-produto")
    private WebElement formulario;

    @FindBy(id = "id")
    private WebElement campoId;

    @FindBy(id = "nome")
    private WebElement campoNome;

    @FindBy(id = "descricao")
    private WebElement campoDescricao;

    @FindBy(id = "preco")
    private WebElement campoPreco;

    @FindBy(id = "quantidadeEstoque")
    private WebElement campoQuantidadeEstoque;

    @FindBy(css = "button[type='submit']")
    private WebElement botaoSalvar;

    @FindBy(className = "btn-cancelar")
    private WebElement linkCancelar;

    @FindBy(id = "mensagem-erro")
    private WebElement mensagemErro;

    @FindBy(id = "mensagem-sucesso")
    private WebElement mensagemSucesso;

    public FormularioProdutoPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void abrirNovo() {
        navigateTo(PATH_NOVO);
    }

    public void abrirEditar(Long id) {
        navigateTo("/produtos/editar/" + id);
    }

    public void preencher(String id, String nome, String descricao, String preco, String quantidadeEstoque) {
        if (id != null) {
            campoId.clear();
            campoId.sendKeys(id);
        }
        if (nome != null) {
            campoNome.clear();
            campoNome.sendKeys(nome);
        }
        if (descricao != null) {
            campoDescricao.clear();
            campoDescricao.sendKeys(descricao);
        }
        if (preco != null) {
            campoPreco.clear();
            campoPreco.sendKeys(preco);
        }
        if (quantidadeEstoque != null) {
            campoQuantidadeEstoque.clear();
            campoQuantidadeEstoque.sendKeys(quantidadeEstoque);
        }
    }

    public void salvar() {
        botaoSalvar.click();
    }

    public void cancelar() {
        linkCancelar.click();
    }

    public boolean formularioVisivel() {
        return formulario.isDisplayed();
    }

    public String getMensagemErro() {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mensagem-erro")));
            return el.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMensagemSucesso() {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mensagem-sucesso")));
            return el.getText();
        } catch (Exception e) {
            return null;
        }
    }
}
