package com.ailinguo.iterator;

import com.ailinguo.model.VocabularyWord;

public interface VocabularyCollection {

    IIterator createIterator();

    void addWord(VocabularyWord word);
    
    int size();
}
