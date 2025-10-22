README de testes (Iterator)

Objetivo
-------
Este README descreve como rodar os testes que cobrem o padrão Iterator (`com.ailinguo.iterator`) e lista os casos de teste implementados.

Local dos testes
----------------
Os testes do Iterator estão em:
- src/test/java/com/ailinguo/iterator/

Testes implementados
--------------------
- `VocabularyWordIteratorTest`:
  - `testIterationReturnsAllWords`: verifica iteração normal sobre duas palavras.
  - `testNextThrowsWhenNoMoreElements`: verifica que `next()` lança `NoSuchElementException` ao final.

- `VocabularyWordCollectionTest`:
  - `testAddWordAndSize`: verifica `addWord`, `size` e `createIterator`.

- `VocabularyWordIteratorEdgeCasesTest`:
  - `testIteratorSeesAdditionsAfterCreation`: demonstra que o iterador criado antes de adicionar elementos vê as adições (comportamento da implementação atual que compartilha a lista interna).
  - `testMultipleIteratorsIndependentPositions`: garante que dois iteradores independentes mantêm posições separadas.
  - `testGetWordsReturnsDefensiveCopy`: verifica que `getWords()` retorna uma cópia defensiva (modificar a cópia não altera a coleção interna).

Como rodar apenas os testes do Iterator
-------------------------------------
No diretório `backend-java`, execute:

```bash
mvn -Dtest=com.ailinguo.iterator.*Test test
```

Isso executará somente as classes de teste cujo pacote corresponde a `com.ailinguo.iterator` e cujo nome termina com `Test`.

Notas
-----
- Os testes do Iterator foram escritos para não depender do contexto Spring (não usam `@SpringBootTest`) para serem rápidos e isolados.
- O comportamento observado em `testIteratorSeesAdditionsAfterCreation` é dependente da implementação atual (o iterador mantém referência à lista interna). Se mudarmos a implementação para cópia defensiva ao criar o iterador, o teste deverá ser ajustado.

Quer que eu adicione testes adicionais ou transformar algum desses testes para rodar em paralelo?