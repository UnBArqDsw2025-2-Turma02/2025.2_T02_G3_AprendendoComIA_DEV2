package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;
import java.util.List;
import java.util.NoSuchElementException;

public class VocabularyWordIterator implements IIterator {
    
    private final List<VocabularyWord> words;
    private int currentPosition;
    
    public VocabularyWordIterator(List<VocabularyWord> words) {
        this.words = words;
        this.currentPosition = 0;
    }
    
    @Override
    public boolean hasNext() {
        return currentPosition < words.size();
    }
    
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais palavras na coleção");
        }
        return words.get(currentPosition++);
    }
}
