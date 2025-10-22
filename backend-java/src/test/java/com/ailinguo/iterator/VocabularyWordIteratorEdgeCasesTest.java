package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VocabularyWordIteratorEdgeCasesTest {

    @Test
    public void testIteratorSeesAdditionsAfterCreation() {
        VocabularyWordCollection collection = new VocabularyWordCollection();

        IIterator it = collection.createIterator();

        // iterator criado antes de adicionarmos elementos
        assertFalse(it.hasNext());

        VocabularyWord w = VocabularyWord.builder()
                .englishWord("later")
                .portugueseTranslation("depois")
                .difficulty("beginner")
                .cefrLevel("A1")
                .build();

        collection.addWord(w);

        // Como a implementação atual passa referência à lista interna, o iterador verá a adição
        assertTrue(it.hasNext(), "Iterator deve ver elementos adicionados após sua criação na implementação atual");
        VocabularyWord r = (VocabularyWord) it.next();
        assertEquals("later", r.getEnglishWord());
    }

    @Test
    public void testMultipleIteratorsIndependentPositions() {
        VocabularyWord w1 = VocabularyWord.builder().englishWord("a").portugueseTranslation("a").difficulty("beginner").cefrLevel("A1").build();
        VocabularyWord w2 = VocabularyWord.builder().englishWord("b").portugueseTranslation("b").difficulty("beginner").cefrLevel("A1").build();
        VocabularyWord w3 = VocabularyWord.builder().englishWord("c").portugueseTranslation("c").difficulty("beginner").cefrLevel("A1").build();

        VocabularyWordCollection collection = new VocabularyWordCollection(List.of(w1, w2, w3));

        IIterator it1 = collection.createIterator();
        IIterator it2 = collection.createIterator();

        // avançar it1 uma posição
        assertTrue(it1.hasNext());
        VocabularyWord r1 = (VocabularyWord) it1.next();
        assertEquals("a", r1.getEnglishWord());

        // it2 ainda deve começar do início
        assertTrue(it2.hasNext());
        VocabularyWord r2 = (VocabularyWord) it2.next();
        assertEquals("a", r2.getEnglishWord());

        // avançar it1 mais
        assertTrue(it1.hasNext());
        VocabularyWord r1b = (VocabularyWord) it1.next();
        assertEquals("b", r1b.getEnglishWord());

        // it2 permanece na sua própria posição (após consumir um elemento, ao chamar next novamente retornará 'b')
        assertTrue(it2.hasNext());
        VocabularyWord r2b = (VocabularyWord) it2.next();
        assertEquals("b", r2b.getEnglishWord());
    }

    @Test
    public void testGetWordsReturnsDefensiveCopy() {
        VocabularyWordCollection collection = new VocabularyWordCollection();
        VocabularyWord w = VocabularyWord.builder().englishWord("x").portugueseTranslation("x").difficulty("beginner").cefrLevel("A1").build();
        collection.addWord(w);

        // obter cópia da lista
        var copy = collection.getWords();
        assertEquals(1, copy.size());

        // modificar a cópia não deve afetar a coleção
        copy.clear();
        assertEquals(1, collection.size(), "Limpar a cópia não deve alterar a coleção interna");
    }
}
