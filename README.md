# TP1 - Sistema CRUD de Produtos

Sistema CRUD em Java pra gerenciar produtos. Feito pro TP1 de Engenharia de Software.

## Estrutura

```
src/
├── main/java/br/edu/esc/tp1/
│   ├── domain/          Entidades
│   ├── repository/      Persistência
│   ├── service/         Lógica de negócio
│   ├── exception/       Exceções
│   ├── ui/              Interface CLI
│   └── Main.java        
└── test/java/br/edu/esc/tp1/
    ├── domain/          Testes da entidade
    └── repository/      Testes do repositório

docs/
├── ESPECIFICACOES.md    
└── CASOS_DE_TESTE.md   
```

## Tecnologias

- Java 17
- Maven
- JUnit 5
- Jqwik
- JaCoCo

## Como rodar

### Compilar
```bash
mvn clean compile
```

### Testes
```bash
mvn test
```

### Cobertura
```bash
mvn jacoco:report
```
Relatório em `target/site/jacoco/index.html`

### Executar
```bash
mvn exec:java
```

Ou no PowerShell:
```powershell
mvn exec:java "-Dexec.mainClass=br.edu.esc.tp1.Main"
```

## O que foi implementado

### Princípios
- Clean Code
- CQS (Command Query Separation)
- Imutabilidade
- Fail-Fast
- Exceções específicas

### Testes
- Testes unitários (JUnit)
- Testes de propriedades (Jqwik)
- Cobertura >= 80%
- Partição de equivalência
- Análise de limites
- Tabelas de decisão

### Qualidade
- Código organizado
- Nomes claros
- Sem valores mágicos
- Switch exaustivo (quando tem enum)

## Documentação

Veja a pasta `docs/`:
- Especificações
- Casos de teste
