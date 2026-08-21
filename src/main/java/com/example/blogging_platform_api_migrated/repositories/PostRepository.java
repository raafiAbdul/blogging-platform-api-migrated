package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "select distinct p from Post p left join fetch p.tags " +
            "where lower(p.title) like lower(concat('%', :term, '%')) " +
            "or lower(p.content) like lower(concat('%', :term, '%')) " +
            "or lower(p.category) like lower(concat('%', :term, '%')) " +
            "or p.id in (" +
            "       select p2.id from Post p2 left join p2.tags t2" +
            "       where lower(t2) like lower(concat('%', :term, '%'))" +
            ")")
    List<Post> findByTerm(@Param("term") String term);
}

// lower(concat('%', :term, '%')) - lower case "% :term %"
// left join - left outer join
// fetch - removes n+1 problem by fetching the lazily
//      fetched tags table in one query (with the parent table)
// p.tags t - gives the column p.tags an identifier
