# Casos de Teste

## Técnicas que usei

### Partição de Equivalência

Separei os dados em grupos:

**ID:**
- Válido: números positivos
- Inválido: negativos, zero, null

**Nome:**
- Válido: strings não vazias (depois do trim)
- Inválido: null, string vazia, só espaços

**Preço:**
- Válido: >= 0
- Inválido: < 0, null

**Quantidade:**
- Válido: >= 0
- Inválido: < 0, null

### Análise de Limites

Testei os valores nos limites:
- Preço: 0 (limite), valores grandes
- Quantidade: 0 (limite), valores grandes  
- Nome: 1 caractere (mínimo), strings longas

## Tabela de Decisão - Criar Produto

| ID Existe? | Nome OK? | Desc OK? | Preço >= 0? | Qtd >= 0? | Resultado |
|------------|----------|----------|-------------|-----------|-----------|
| Não | Sim | Sim | Sim | Sim | Cria produto |
| Sim | - | - | - | - | Erro: duplicado |
| - | Não | - | - | - | Erro: nome inválido |
| - | Sim | Não | - | - | Erro: desc inválida |
| - | Sim | Sim | Não | - | Erro: preço inválido |
| - | Sim | Sim | Sim | Não | Erro: qtd inválida |

## Tabela de Decisão - Atualizar

| Produto Existe? | Dados Válidos? | Resultado |
|-----------------|----------------|-----------|
| Sim | Sim | Atualiza |
| Não | - | Erro: não encontrado |
| Sim | Não | Erro: dados inválidos |

## Gráfico Causa-Efeito - Criar

Causas:
- C1: ID não existe
- C2: Nome válido
- C3: Descrição válida
- C4: Preço >= 0
- C5: Quantidade >= 0

Efeitos:
- E1: Produto criado
- E2: Erro duplicado
- E3: Erro nome inválido
- E4: Erro desc inválida
- E5: Erro preço inválido
- E6: Erro quantidade inválida

Regras:
- Se C1 E C2 E C3 E C4 E C5 → E1
- Se não C1 → E2
- Se não C2 → E3
- Se não C3 → E4
- Se não C4 → E5
- Se não C5 → E6

## Casos de Teste

### Criar Produto

**CT-001: Criar com dados válidos**
- Entrada: ID=1, Nome="Notebook", Desc="Dell", Preço=2500.00, Qtd=10
- Esperado: Produto criado
- Tipo: Positivo

**CT-002: ID duplicado**
- Entrada: ID=1 (já existe)
- Esperado: ProdutoDuplicadoException
- Tipo: Negativo

**CT-003: Nome null**
- Entrada: Nome=null
- Esperado: IllegalArgumentException
- Tipo: Negativo

**CT-004: Nome vazio**
- Entrada: Nome=""
- Esperado: IllegalArgumentException
- Tipo: Negativo

**CT-005: Preço negativo**
- Entrada: Preço=-10.00
- Esperado: IllegalArgumentException
- Tipo: Negativo

**CT-006: Preço zero**
- Entrada: Preço=0.00
- Esperado: Produto criado
- Tipo: Limite

### Buscar Produto

**CT-007: Buscar existente**
- Entrada: ID=1 (existe)
- Esperado: Optional com produto
- Tipo: Positivo

**CT-008: Buscar inexistente**
- Entrada: ID=999
- Esperado: Optional vazio
- Tipo: Negativo

**CT-009: ID null**
- Entrada: ID=null
- Esperado: Optional vazio
- Tipo: Limite

### Atualizar Produto

**CT-010: Atualizar existente**
- Entrada: ID=1, novos dados válidos
- Esperado: Produto atualizado
- Tipo: Positivo

**CT-011: Atualizar inexistente**
- Entrada: ID=999
- Esperado: ProdutoNaoEncontradoException
- Tipo: Negativo

### Deletar Produto

**CT-012: Deletar existente**
- Entrada: ID=1
- Esperado: Produto removido
- Tipo: Positivo

**CT-013: Deletar inexistente**
- Entrada: ID=999
- Esperado: ProdutoNaoEncontradoException
- Tipo: Negativo

## Testes com Jqwik

Usei Jqwik pra testar propriedades automaticamente. Os testes validam:

- Igualdade: produtos com mesmo ID são iguais
- Imutabilidade: atualizar não muda o objeto original
- Consistência: CRUD mantém tudo consistente
- Validação: dados inválidos sempre geram exceção

## Cobertura

A meta é 80% de cobertura. Os testes cobrem:
- Todos os métodos públicos
- Todos os caminhos de decisão
- Casos de sucesso e erro
- Valores limites
