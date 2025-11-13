package com.example.news_project.Seduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.news_project.NewsServicerepo.NewsService;

@Component
public class NewsScheduler {
    private final NewsService newsService;

    public NewsScheduler(NewsService newsService) {
        this.newsService = newsService;
    }

    // Run immediately on startup, then every 1 hour
    @Scheduled(initialDelay = 0, fixedRate = 3600000)
    public void updateNews() {
        try {
            System.out.println("\n📅 [Scheduler] Triggering news update...");
            newsService.updateNewsFromApi();
            System.out.println("✅ [Scheduler] News update completed\n");
        } catch (Exception e) {
            System.err.println("❌ [Scheduler] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
