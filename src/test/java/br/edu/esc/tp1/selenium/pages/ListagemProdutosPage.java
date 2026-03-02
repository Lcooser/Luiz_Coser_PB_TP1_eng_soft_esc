package br.edu.esc.tp1.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ListagemProdutosPage extends BasePage {

    public static final String PATH = "/produtos";

    @FindBy(id = "mensagem-sucesso")
    private WebElement mensagemSucesso;

    @FindBy(id = "mensagem-erro")
    private WebElement mensagemErro;

    @FindBy(css = "a[href='/produtos/novo']")
    private WebElement linkNovoProduto;

    @FindBy(css = "a[href^='/produtos/editar/']")
    private List<WebElement> linksEditar;

    @FindBy(css = "button.btn-excluir")
    private List<WebElement> botoesExcluir;

    @FindBy(css = "#tabela-produtos tbody tr")
    private List<WebElement> linhasProdutos;

    @FindBy(id = "tabela-produtos")
    private WebElement tabelaProdutos;

    public ListagemProdutosPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void abrir() {
        navigateTo(PATH);
    }

    public void clicarNovoProduto() {
        linkNovoProduto.click();
    }

    public boolean tabelaVisivel() {
        return tabelaProdutos.isDisplayed();
    }

    public int quantidadeProdutosNaTabela() {
        return linhasProdutos.size();
    }

    public String getMensagemSucesso() {
        List<WebElement> el = driver.findElements(By.id("mensagem-sucesso"));
        return el.isEmpty() ? null : el.get(0).getText();
    }

    public String getMensagemErro() {
        List<WebElement> el = driver.findElements(By.id("mensagem-erro"));
        return el.isEmpty() ? null : el.get(0).getText();
    }

    public void clicarEditarPrimeiro() {
        if (!linksEditar.isEmpty()) {
            linksEditar.get(0).click();
        }
    }

    public void clicarExcluirPrimeiro() {
        if (!botoesExcluir.isEmpty()) {
            botoesExcluir.get(0).click();
        }
    }

    public boolean contemProdutoNaListagem(String nome) {
        return linhasProdutos.stream()
                .anyMatch(linha -> linha.getText().contains(nome));
    }
}
