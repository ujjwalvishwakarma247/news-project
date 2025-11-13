package com.example.news_project;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.news_project.NewsServicerepo.NewsService;

@Component
public class AppStartupListener {

    private final NewsService newsService;

    public AppStartupListener(NewsService newsService) {
        this.newsService = newsService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🚀 APPLICATION STARTED SUCCESSFULLY");
        System.out.println("=".repeat(60));
        
        try {
            System.out.println("\n📥 Fetching news from API on startup...");
            newsService.updateNewsFromApi();
            System.out.println("\n✅ Startup news fetch completed!");
        } catch (Exception e) {
            System.err.println("\n❌ Error during startup news fetch: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n📌 Access the app at: http://localhost:8080");
        System.out.println("📰 View news at: http://localhost:8080/news");
        System.out.println("🔄 Fetch news manually: http://localhost:8080/fetch");
        System.out.println("=".repeat(60) + "\n");
    }
}
