package br.edu.esc.tp1.selenium;

import br.edu.esc.tp1.selenium.pages.FormularioProdutoPage;
import br.edu.esc.tp1.selenium.pages.ListagemProdutosPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;
    private ListagemProdutosPage listagemPage;
    private FormularioProdutoPage formularioPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
        listagemPage = new ListagemProdutosPage(driver, baseUrl);
        formularioPage = new FormularioProdutoPage(driver, baseUrl);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Listagem carrega e exibe link para novo produto")
    void listagemCarrega() {
        listagemPage.abrir();
        assertTrue(driver.getCurrentUrl().contains("/produtos"));
        listagemPage.clicarNovoProduto();
        assertTrue(formularioPage.formularioVisivel());
    }

    @ParameterizedTest(name = "Cadastro válido: id={0}, nome={1}")
    @CsvSource({
            "10, Produto A, Descrição A, 99.99, 5",
            "20, Produto B, Descrição B, 0, 0",
            "30, Notebook, Descrição notebook, 2500.00, 10"
    })
    @DisplayName("Cadastro de produto com dados válidos redireciona e exibe mensagem de sucesso")
    void cadastroComDadosValidos(String id, String nome, String descricao, String preco, String qtd) {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher(id, nome, descricao, preco, qtd);
        formularioPage.salvar();
        assertNotNull(listagemPage.getMensagemSucesso());
        assertTrue(listagemPage.contemProdutoNaListagem(nome));
    }

    @Test
    @DisplayName("Ao enviar formulário com ID já existente, produto é atualizado e exibe sucesso")
    void enviarFormularioComIdExistenteAtualizaProduto() {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher("1", "Primeiro", "Desc 1", "10", "1");
        formularioPage.salvar();
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher("1", "Atualizado", "Desc 2", "20", "2");
        formularioPage.salvar();
        assertNotNull(listagemPage.getMensagemSucesso());
        assertTrue(listagemPage.contemProdutoNaListagem("Atualizado"));
    }

    @ParameterizedTest(name = "Entrada inválida: id={0}, nome={1}, esperadoErro={2}")
    @CsvSource({
            "'', Nome, Desc, 10, 1, ID",
            "2, '', Descrição, 10, 1, Nome",
            "3, Nome, '', 10, 1, Descrição",
            "4, Nome, Desc, -5, 1, Preço",
            "5, Nome, Desc, 10, -1, Quantidade"
    })
    @DisplayName("Formulário com dados inválidos exibe mensagem de erro")
    void formularioComDadosInvalidosExibeErro(String id, String nome, String descricao, String preco, String qtd, String esperadoNaMensagem) {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher(id, nome, descricao, preco, qtd);
        formularioPage.salvar();
        String erroForm = formularioPage.getMensagemErro();
        String erroLista = listagemPage.getMensagemErro();
        String erro = erroForm != null ? erroForm : erroLista;
        assertTrue(erro != null && (erro.contains(esperadoNaMensagem) || erro.contains("inválido") || erro.contains("obrigatório")),
                "Esperado mensagem contendo '" + esperadoNaMensagem + "' ou 'inválido'/'obrigatório', obtido: " + erro);
    }

    @Test
    @DisplayName("Excluir produto redireciona para listagem e exibe sucesso")
    void excluirProdutoExibeSucesso() {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher("100", "Para Excluir", "Desc", "1", "1");
        formularioPage.salvar();
        listagemPage.abrir();
        assertTrue(listagemPage.contemProdutoNaListagem("Para Excluir"));
        listagemPage.clicarExcluirPrimeiro();
        assertTrue(listagemPage.getMensagemSucesso() != null && listagemPage.getMensagemSucesso().contains("sucesso"));
    }

    @Test
    @DisplayName("Editar produto persiste alterações e exibe sucesso")
    void editarProdutoPersisteAlteracoes() {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        formularioPage.preencher("200", "Original", "Desc Original", "50", "3");
        formularioPage.salvar();
        listagemPage.abrir();
        listagemPage.clicarEditarPrimeiro();
        formularioPage.preencher("200", "Editado", "Desc Editada", "75", "7");
        formularioPage.salvar();
        listagemPage.abrir();
        assertTrue(listagemPage.contemProdutoNaListagem("Editado"));
    }

    @Test
    @DisplayName("Navegação entre listagem e formulário funciona")
    void navegacaoEntrePaginas() {
        listagemPage.abrir();
        listagemPage.clicarNovoProduto();
        assertTrue(formularioPage.formularioVisivel());
        formularioPage.cancelar();
        assertTrue(driver.getCurrentUrl().endsWith("/produtos"));
    }
}
