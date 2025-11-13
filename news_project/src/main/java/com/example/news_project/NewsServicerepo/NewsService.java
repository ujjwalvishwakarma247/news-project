package com.example.news_project.NewsServicerepo;

import org.springframework.stereotype.Service;

import com.example.news_project.Entity.News;
import com.example.news_project.Repositary.NewsRepository;
import com.example.news_project.Servicenews.NewsApiService;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsApiService newsApiService;

    public NewsService(NewsRepository newsRepository, NewsApiService newsApiService) {
        this.newsRepository = newsRepository;
        this.newsApiService = newsApiService;
    }

    public void updateNewsFromApi() {
        try {
            System.out.println("🔄 Starting news update from API...");
            List<News> newsList = newsApiService.fetchNews();

            if (newsList.isEmpty()) {
                System.err.println("⚠️ No news fetched from API");
                return;
            }

            // Filter out duplicates by URL before saving
            List<News> toSave = new java.util.ArrayList<>();
            for (News n : newsList) {
                String url = n.getUrl();
                if (url == null || url.trim().isEmpty()) {
                    // skip items without a valid URL
                    continue;
                }
                try {
                    if (!newsRepository.existsByUrl(url)) {
                        toSave.add(n);
                    } else {
                        System.out.println("⚠️ Skipping duplicate article (exists): " + url);
                    }
                } catch (Exception ex) {
                    // In case repository check fails, fall back to saving
                    System.err.println("⚠️ Error checking duplicate for URL: " + url + ", will skip. " + ex.getMessage());
                }
            }

            if (toSave.isEmpty()) {
                System.out.println("ℹ️ No new articles to save (all duplicates or invalid)");
                return;
            }

            newsRepository.saveAll(toSave);
            System.out.println("✅ Successfully saved " + toSave.size() + " new news items to database");
        } catch (Exception e) {
            System.err.println("❌ Error updating news: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Filtered fetch with search and category
    public List<News> getAllNews(String search, String category) {
        List<News> all = newsRepository.findAll();
        java.util.stream.Stream<News> stream = all.stream();

        if (search != null && !search.trim().isEmpty()) {
            String s = search.trim().toLowerCase();
            stream = stream.filter(n -> (
                (n.getTitle() != null && n.getTitle().toLowerCase().contains(s)) ||
                (n.getDescription() != null && n.getDescription().toLowerCase().contains(s)) ||
                (n.getSource() != null && n.getSource().toLowerCase().contains(s))
            ));
        }

        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("all")) {
            String c = category.trim().toLowerCase();
            stream = stream.filter(n -> n.getCategory() != null && n.getCategory().toLowerCase().equals(c));
        }

        return stream.toList();
    }
}

