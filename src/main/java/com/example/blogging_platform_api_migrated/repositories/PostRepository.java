package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "select distinct p from Post p left join fetch p.tags t" +
            " where lower(p.title) like lower(concat('%', :term, '%'))" +
            " or lower(content) like lower(concat('%', :term, '%'))" +
            " or lower(category) like lower(concat('%', :term, '%'))" +
            " or lower(t) like lower(concat('%', :term, '%'))")
    List<Post> findByTerm(@Param("term") String term);
}

// lower(concat('%', :term, '%')) - lower case "% :term %"
// left join - left outer join
// fetch - removes n+1 problem by fetching the lazily
//      fetched tags table in one query (with the parent table)
// p.tags t - gives the column p.tags an identifier
