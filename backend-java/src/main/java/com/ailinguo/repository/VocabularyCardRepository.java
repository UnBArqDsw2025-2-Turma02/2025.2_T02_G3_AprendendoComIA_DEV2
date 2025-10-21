package com.ailinguo.repository;

import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyCardRepository extends MongoRepository<VocabularyCard, String> {
    List<VocabularyCard> findByCefrLevel(User.CefrLevel level);
}

