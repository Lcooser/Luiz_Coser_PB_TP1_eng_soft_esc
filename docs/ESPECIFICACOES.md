# Especificações do Sistema

Sistema CRUD para gerenciar produtos. Permite criar, buscar, atualizar e deletar produtos no sistema.

## Entidade Produto

A entidade Produto tem os seguintes atributos:

- id (Long): identificador único, deve ser positivo
- nome (String): nome do produto, não pode ser vazio ou só espaços
- descricao (String): descrição do produto, também não pode ser vazia
- preco (BigDecimal): preço, tem que ser >= 0
- quantidadeEstoque (Integer): quantidade em estoque, também >= 0

### Regras importantes

- Todos os campos são obrigatórios (não aceita null)
- Nome e descrição não podem ser strings vazias (depois do trim)
- Preço e quantidade não podem ser negativos (mas podem ser zero)
- Produto é imutável - quando atualiza, cria uma nova instância
- Dois produtos são iguais se tiverem o mesmo ID

## Operações CRUD

### Criar produto

Cria um novo produto no sistema. Antes de criar, valida todos os dados.

**O que precisa:**
- ID que ainda não existe
- Todos os dados válidos

**O que acontece:**
- Produto é salvo
- Pode ser buscado depois pelo ID

**Erros possíveis:**
- IllegalArgumentException se algum dado for inválido
- ProdutoDuplicadoException se o ID já existir

### Buscar produto

Busca um produto pelo ID.

**O que precisa:**
- ID (pode ser qualquer coisa, até null)

**O que acontece:**
- Se encontrar, retorna o produto dentro de um Optional
- Se não encontrar, retorna Optional vazio

**Erros:**
- Nenhum, sempre retorna algo (mesmo que vazio)

### Listar todos

Retorna todos os produtos cadastrados.

**O que precisa:**
- Nada

**O que acontece:**
- Retorna uma lista (pode estar vazia se não tiver produtos)

### Atualizar produto

Atualiza os dados de um produto que já existe. O ID não muda.

**O que precisa:**
- Produto com esse ID precisa existir
- Novos dados precisam ser válidos

**O que acontece:**
- Produto é atualizado no repositório
- Dados antigos são substituídos

**Erros possíveis:**
- IllegalArgumentException se dados inválidos
- ProdutoNaoEncontradoException se produto não existir

### Deletar produto

Remove um produto do sistema.

**O que precisa:**
- Produto com esse ID precisa existir

**O que acontece:**
- Produto é removido
- Não dá mais pra buscar ele depois

**Erros possíveis:**
- IllegalArgumentException se ID for null
- ProdutoNaoEncontradoException se produto não existir

## Tratamento de Erros

### Exceções

- ProdutoNaoEncontradoException: quando tenta fazer algo com produto que não existe
- ProdutoDuplicadoException: quando tenta criar produto com ID que já existe

### Validações

Todas as validações são feitas antes de modificar qualquer coisa. Assim garante que só dados válidos entram no sistema.

## Princípios que usei

### CQS (Command Query Separation)

Separei os métodos em dois tipos:
- Commands (mudam estado): criar, atualizar, deletar
- Queries (só consultam): buscarPorId, listarTodos, existe

### Imutabilidade

Os objetos Produto são imutáveis. Quando atualiza, cria um novo objeto em vez de modificar o antigo.

### Fail-Fast

As validações acontecem logo no início. Se tiver algo errado, já lança exceção na hora.
