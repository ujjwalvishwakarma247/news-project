package com.example.news_project.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news_project.Entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	boolean existsByUrl(String url);
}

