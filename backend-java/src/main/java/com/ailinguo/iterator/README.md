# Padrão Iterator - GoF

## 📦 Arquivos do Padrão

### 1. `IIterator.java` (Interface)
```java
public interface IIterator {
    public boolean hasNext();
    public Object next();
}
```

### 2. `VocabularyCollection.java` (Interface Aggregate)
```java
public interface VocabularyCollection {
    IIterator createIterator();
    void addWord(VocabularyWord word);
    int size();
}
```

### 3. `VocabularyWordIterator.java` (ConcreteIterator)
- Implementa `IIterator`
- Itera sobre `List<VocabularyWord>`
- Mantém posição atual da iteração

### 4. `VocabularyWordCollection.java` (ConcreteAggregate)
- Implementa `VocabularyCollection`
- Gerencia a coleção de palavras
- Cria iteradores para percorrer a coleção

## 🌐 Endpoints Disponíveis

### 1. Iterar por Categoria
```bash
GET /api/vocabulary/words/iterate/category/{categoryId}
```
**Exemplo:**
```bash
curl http://localhost:8080/api/vocabulary/words/iterate/category/1
```
**Retorna:** Detalhes completos de cada palavra (posição, inglês, português, dificuldade, nível CEFR, XP)

### 2. Iterar por Nível CEFR
```bash
GET /api/vocabulary/words/iterate/cefr/{cefrLevel}
```
**Exemplo:**
```bash
curl http://localhost:8080/api/vocabulary/words/iterate/cefr/A1
```
**Retorna:** Lista formatada de palavras do nível

## 💻 Exemplo de Uso

```java
// Buscar palavras do banco
List<VocabularyWord> words = vocabularyWordRepository.findByCategoryId(categoryId);

// Criar coleção com padrão Iterator
VocabularyWordCollection collection = new VocabularyWordCollection(words);

// Criar iterator
IIterator iterator = collection.createIterator();

// Iterar sobre as palavras
while (iterator.hasNext()) {
    VocabularyWord word = (VocabularyWord) iterator.next();
    System.out.println(word.getEnglishWord());
}
```

## 🚀 Como Testar

### 1. Iniciar o Backend
```bash
cd backend-java
mvn spring-boot:run
```

### 2. Testar os Endpoints
```bash
# Iterar por categoria
curl http://localhost:8080/api/vocabulary/words/iterate/category/1

# Iterar por nível CEFR
curl http://localhost:8080/api/vocabulary/words/iterate/cefr/A1
```

## ✨ Benefícios

- **Encapsulamento**: A lógica de iteração fica separada da coleção
- **Uniformidade**: Interface consistente para percorrer coleções
- **Múltiplos Iteradores**: Vários iteradores simultâneos na mesma coleção
- **Responsabilidade Única**: Cada classe tem uma responsabilidade específica

## 📚 Referência

**Design Patterns: Elements of Reusable Object-Oriented Software** (GoF)

O padrão Iterator fornece uma maneira de acessar sequencialmente elementos de um agregado sem expor sua representação subjacente.
