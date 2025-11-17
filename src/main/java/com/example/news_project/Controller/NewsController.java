package com.example.news_project.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.news_project.NewsServicerepo.NewsService;

@Controller
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/fetch")
    public String fetchNews() {
        newsService.updateNewsFromApi();
        return "redirect:/news";
    }

    @GetMapping("/news")
    public String showNews(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "category", required = false, defaultValue = "all") String category,
        @RequestParam(value = "date", required = false, defaultValue = "alltime") String dateFilter,
        Model model) {
        
        model.addAttribute("newsList", newsService.getAllNews(search, category, dateFilter));
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("category", category != null ? category : "all");
        model.addAttribute("dateFilter", dateFilter != null ? dateFilter : "alltime");
        
        // Add available categories for filter UI
        model.addAttribute("categories", new String[]{"all", "general", "business", "technology", "health", "sports", "entertainment", "politics"});
        
        // Add available date filters
        model.addAttribute("dateFilters", new String[]{"today", "week", "month", "alltime"});
        
        return "news";
    }
}

