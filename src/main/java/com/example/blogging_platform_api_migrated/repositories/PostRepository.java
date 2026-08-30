package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "select distinct p from Post p left join p.tags t " +
            "where lower(p.title) like lower(concat('%', :term, '%')) " +
            "or lower(p.content) like lower(concat('%', :term, '%')) " +
            "or lower(p.category) like lower(concat('%', :term, '%')) " +
            "or lower(t) like lower(concat('%', :term, '%')) ",
            countQuery = "select count(p) from Post p left join fetch p.tags t " +
                    "where lower(p.title) like lower(concat('%', :term, '%')) " +
                    "or lower(p.content) like lower(concat('%', :term, '%')) " +
                    "or lower(p.category) like lower(concat('%', :term, '%')) " +
                    "or lower(t) like lower(concat('%', :term, '%'))")
    List<Post> findByTerm(@Param("term") String term, Pageable pageable);
}
