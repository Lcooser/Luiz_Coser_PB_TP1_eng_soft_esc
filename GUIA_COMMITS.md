# Guia de Commits - Estrutura Sugerida

Este documento sugere uma sequência de commits que demonstra o desenvolvimento incremental do projeto, seguindo boas práticas de versionamento.

## Sequência de Commits Sugerida

### Commit 1: Estrutura inicial do projeto
```bash
git add pom.xml .gitignore
git commit -m "feat: adiciona estrutura base do projeto Maven

- Configuração do pom.xml com dependências (JUnit, Jqwik, JaCoCo)
- Arquivo .gitignore para arquivos gerados
- Configuração do compilador Java 17"
```

### Commit 2: Entidade do domínio
```bash
git add src/main/java/br/edu/esc/tp1/domain/Produto.java
git commit -m "feat: implementa entidade Produto com validações

- Classe imutável com factory method
- Validações de nome, descrição, preço e quantidade
- Método de atualização que mantém imutabilidade
- Implementação de equals e hashCode baseado em ID"
```

### Commit 3: Exceções customizadas
```bash
git add src/main/java/br/edu/esc/tp1/exception/
git commit -m "feat: adiciona exceções customizadas para tratamento de erros

- ProdutoNaoEncontradoException
- ProdutoDuplicadoException
- Mensagens de erro claras e específicas"
```

### Commit 4: Interface do repositório
```bash
git add src/main/java/br/edu/esc/tp1/repository/ProdutoRepository.java
git commit -m "feat: define interface do repositório seguindo CQS

- Separação entre Commands (criar, atualizar, deletar) e Queries (buscar, listar)
- Documentação clara de cada método
- Uso de Optional para retornos que podem ser vazios"
```

### Commit 5: Implementação do repositório
```bash
git add src/main/java/br/edu/esc/tp1/repository/ProdutoRepositoryImpl.java
git commit -m "feat: implementa repositório em memória

- Implementação usando ConcurrentHashMap
- Validações de entrada em todos os métodos
- Tratamento de casos nulos e inválidos"
```

### Commit 6: Camada de serviço
```bash
git add src/main/java/br/edu/esc/tp1/service/ProdutoService.java
git commit -m "feat: implementa camada de serviço

- Orquestração das operações CRUD
- Validação de dependências (repositório não nulo)
- Encapsulamento da lógica de criação de produtos"
```

### Commit 7: Interface de linha de comando
```bash
git add src/main/java/br/edu/esc/tp1/ui/ConsoleUI.java
git commit -m "feat: implementa interface de linha de comando

- Menu interativo para operações CRUD
- Tratamento de entrada do usuário
- Mensagens claras de sucesso e erro"
```

### Commit 8: Classe principal
```bash
git add src/main/java/br/edu/esc/tp1/Main.java
git commit -m "feat: adiciona classe principal para inicialização

- Configuração das dependências
- Inicialização da interface de usuário"
```

### Commit 9: Testes unitários da entidade
```bash
git add src/test/java/br/edu/esc/tp1/domain/ProdutoTest.java
git commit -m "test: adiciona testes unitários para entidade Produto

- Testes de criação com dados válidos
- Testes de validação (nome, descrição, preço, quantidade)
- Testes de igualdade e imutabilidade
- Cobertura de casos limites (zero, valores negativos)"
```

### Commit 10: Testes unitários do repositório
```bash
git add src/test/java/br/edu/esc/tp1/repository/ProdutoRepositoryTest.java
git commit -m "test: adiciona testes unitários para repositório

- Testes de todas as operações CRUD
- Testes de casos de erro (produto não encontrado, duplicado)
- Validação de comportamento com dados nulos
- Testes de listagem e verificação de existência"
```

### Commit 11: Testes baseados em propriedades - Domínio
```bash
git add src/test/java/br/edu/esc/tp1/domain/ProdutoPropertyTest.java
git commit -m "test: adiciona testes baseados em propriedades com Jqwik

- Geração automática de dados de teste
- Validação de propriedades matemáticas
- Testes de limites e valores extremos
- Validação de imutabilidade e igualdade"
```

### Commit 12: Testes baseados em propriedades - Repositório
```bash
git add src/test/java/br/edu/esc/tp1/repository/ProdutoRepositoryPropertyTest.java
git commit -m "test: adiciona testes de propriedades para repositório

- Validação de invariantes do repositório
- Testes de consistência das operações CRUD
- Geração de cenários complexos automaticamente"
```

### Commit 13: Documentação de especificações
```bash
git add docs/ESPECIFICACOES.md
git commit -m "docs: adiciona especificações detalhadas do sistema

- Descrição da entidade e seus atributos
- Especificação de cada operação CRUD
- Regras de negócio e pré/pós-condições
- Documentação de exceções e tratamento de erros"
```

### Commit 14: Documentação de casos de teste
```bash
git add docs/CASOS_DE_TESTE.md
git commit -m "docs: adiciona documentação de casos de teste

- Técnicas utilizadas (partição de equivalência, análise de limites)
- Tabelas de decisão para operações CRUD
- Gráficos de causa-efeito
- Casos de teste detalhados com resultados esperados"
```

### Commit 15: README e documentação final
```bash
git add README.md
git commit -m "docs: adiciona README com instruções do projeto

- Estrutura do projeto
- Como compilar e executar
- Como verificar cobertura de testes
- Lista de características implementadas"
```

## Dicas

1. **Faça commits frequentes**: Commite após cada funcionalidade completa
2. **Mensagens claras**: Use o padrão conventional commits (feat, test, docs, etc.)
3. **Commits atômicos**: Cada commit deve representar uma mudança lógica completa
4. **Teste antes de commitar**: Certifique-se de que os testes passam antes de cada commit

## Verificação

Após todos os commits, você pode verificar o histórico com:
```bash
git log --oneline
```

E verificar a cobertura de testes com:
```bash
mvn clean test jacoco:report
```

