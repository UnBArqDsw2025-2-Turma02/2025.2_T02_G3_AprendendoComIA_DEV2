package com.ailinguo.repository;

import com.ailinguo.model.VocabularyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyCategoryRepository extends JpaRepository<VocabularyCategory, Long> {
    
    List<VocabularyCategory> findAllByOrderByNameAsc();
}
