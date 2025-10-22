package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class VocabularyWordIteratorTest {

    @Test
    public void testIterationReturnsAllWords() {
        VocabularyWord w1 = VocabularyWord.builder()
                .englishWord("hello")
                .portugueseTranslation("olá")
                .difficulty("beginner")
                .cefrLevel("A1")
                .build();

        VocabularyWord w2 = VocabularyWord.builder()
                .englishWord("world")
                .portugueseTranslation("mundo")
                .difficulty("beginner")
                .cefrLevel("A1")
                .build();

        List<VocabularyWord> list = new ArrayList<>();
        list.add(w1);
        list.add(w2);

        VocabularyWordCollection collection = new VocabularyWordCollection(list);
        IIterator it = collection.createIterator();

        assertTrue(it.hasNext());
        VocabularyWord r1 = (VocabularyWord) it.next();
        assertEquals("hello", r1.getEnglishWord());

        assertTrue(it.hasNext());
        VocabularyWord r2 = (VocabularyWord) it.next();
        assertEquals("world", r2.getEnglishWord());

        assertFalse(it.hasNext());
    }

    @Test
    public void testNextThrowsWhenNoMoreElements() {
        VocabularyWordCollection empty = new VocabularyWordCollection();
        IIterator it = empty.createIterator();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }
}
