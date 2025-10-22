package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;
import java.util.ArrayList;
import java.util.List;

public class VocabularyWordCollection implements VocabularyCollection {
    
    private final List<VocabularyWord> words;
    
    public VocabularyWordCollection() {
        this.words = new ArrayList<>();
    }
    
    public VocabularyWordCollection(List<VocabularyWord> words) {
        this.words = new ArrayList<>(words);
    }
    
    @Override
    public IIterator createIterator() {
        return new VocabularyWordIterator(words);
    }
    
    @Override
    public void addWord(VocabularyWord word) {
        words.add(word);
    }
    
    @Override
    public int size() {
        return words.size();
    }
    
    public List<VocabularyWord> getWords() {
        return new ArrayList<>(words);
    }
}
