package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VocabularyWordCollectionTest {

    @Test
    public void testAddWordAndSize() {
        VocabularyWordCollection collection = new VocabularyWordCollection();
        assertEquals(0, collection.size());

        VocabularyWord w = VocabularyWord.builder()
                .englishWord("test")
                .portugueseTranslation("teste")
                .difficulty("beginner")
                .cefrLevel("A1")
                .build();

        collection.addWord(w);
        assertEquals(1, collection.size());

        IIterator it = collection.createIterator();
        assertTrue(it.hasNext());
        VocabularyWord r = (VocabularyWord) it.next();
        assertEquals("test", r.getEnglishWord());
    }
}
