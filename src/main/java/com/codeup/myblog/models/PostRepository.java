package com.codeup.myblog.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
public interface PostRepository extends  JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}
