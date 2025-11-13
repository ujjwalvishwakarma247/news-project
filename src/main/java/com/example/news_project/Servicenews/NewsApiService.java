package com.example.news_project.Servicenews;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import com.example.news_project.Entity.News;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsApiService {

    // Indian news RSS feeds
    private final List<String> RSS_FEEDS = List.of(
        "https://feeds.indianexpress.com/indianexpress/",  // Indian Express
        "https://www.hindustantimes.com/feeds/",             // Hindustan Times
        "https://www.thehindu.com/?service=rss",             // The Hindu
        "https://feeds.dwnews.com/rss/en/india",             // DW News India
        "https://feeds.bloomberg.com/markets/news.rss"       // Bloomberg
    );

    public List<News> fetchNews() {
        List<News> allNews = new ArrayList<>();
        
        System.out.println("\n🔄 Starting RSS feed fetch...");
        
        for (String feedUrl : RSS_FEEDS) {
            try {
                System.out.println("📡 Fetching from: " + feedUrl);
                List<News> feedNews = parseFeed(feedUrl);
                allNews.addAll(feedNews);
                System.out.println("✅ Got " + feedNews.size() + " articles from this feed");
            } catch (Exception e) {
                System.err.println("⚠️ Error fetching from " + feedUrl + ": " + e.getMessage());
            }
        }
        
        System.out.println("📰 Total articles fetched: " + allNews.size() + "\n");
        return allNews;
    }

    private List<News> parseFeed(String feedUrl) throws Exception {
        List<News> newsList = new ArrayList<>();
        
        try {
            URI uri = new URI(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(uri.toURL()));
            
            System.out.println("   Feed Title: " + feed.getTitle());
            
            int count = 0;
            for (SyndEntry entry : feed.getEntries()) {
                if (count >= 10) break; // Limit to 10 articles per feed
                
                News news = new News();
                
                news.setTitle(entry.getTitle() != null ? entry.getTitle() : "No Title");
                news.setDescription(entry.getDescription() != null ? 
                    entry.getDescription().getValue() : "No Description");
                news.setUrl(entry.getLink() != null ? entry.getLink() : "#");
                
                // Images removed per request — do not store image URLs
                news.setImageUrl(null);
                
                news.setSource(feed.getTitle() != null ? feed.getTitle() : "Unknown Source");
                
                // Assign category manually based on content keywords
                news.setCategory(assignCategory(news.getTitle(), news.getDescription()));
                
                // Parse publication date
                if (entry.getPublishedDate() != null) {
                    Instant instant = entry.getPublishedDate().toInstant();
                    news.setPublishedAt(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
                } else {
                    news.setPublishedAt(LocalDateTime.now());
                }
                
                newsList.add(news);
                count++;
                System.out.println("   ✓ " + news.getTitle().substring(0, Math.min(50, news.getTitle().length())));
            }
            
        } catch (IOException e) {
            System.err.println("   ❌ Connection error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ❌ Parse error: " + e.getMessage());
        }
        
        return newsList;
    }

    // Manually assign category based on keywords in title/description
    private String assignCategory(String title, String description) {
        String text = ((title != null ? title : "") + " " + (description != null ? description : "")).toLowerCase();
        
        // Sports: cricket, football, basketball, tennis, hockey, badminton, player, team, match, tournament, game, league, championship, bollywood actors playing, etc.
        if (containsKeywords(text, "cricket", "football", "soccer", "basketball", "tennis", "hockey", "badminton", "player", "team", "match", "tournament", "game", "league", "championship", "ipl", "world cup", "premier league")) {
            return "sports";
        }
        
        // Technology: tech, software, app, computer, digital, ai, artificial intelligence, machine learning, blockchain, crypto, bitcoin, ethereum, startup, innovation, software, coding, programming
        if (containsKeywords(text, "technology", "tech", "software", "app", "computer", "digital", "ai", "artificial intelligence", "machine learning", "blockchain", "crypto", "bitcoin", "ethereum", "startup", "innovation", "coding", "programming", "algorithm", "database", "server")) {
            return "technology";
        }
        
        // Health: health, medical, doctor, hospital, covid", "covid-19", "vaccine", "disease", "drug", "medicine", "virus", "pandemic", "treatment", "cure", "patient", "hospital", "clinic", "health care", "doctor", "nurse", "healthcare
        if (containsKeywords(text, "health", "medical", "doctor", "hospital", "covid", "covid-19", "vaccine", "disease", "drug", "medicine", "virus", "pandemic", "treatment", "cure", "patient", "clinic", "healthcare", "nursing", "pharma", "pharmaceutical")) {
            return "health";
        }
        
        // Business: business, company, corporate, market, economy, finance, stock, trade, commerce, industry, profit, revenue, sales, deal, merger, bank, investment, shares, stock market, trading, entrepreneurship
        if (containsKeywords(text, "business", "company", "corporate", "market", "economy", "finance", "stock", "trade", "commerce", "industry", "profit", "revenue", "sales", "deal", "merger", "bank", "investment", "shares", "stock market", "trading", "entrepreneurship", "money", "earn", "income")) {
            return "business";
        }
        
        // Entertainment: movie, film, actor, actress, music, singer, show, entertainment, celebrity, hollywood, bollywood, award, cinema, drama, serial, web series, netflix, youtube, actor, actress, star, performance
        if (containsKeywords(text, "movie", "film", "actor", "actress", "music", "singer", "show", "entertainment", "celebrity", "hollywood", "bollywood", "award", "cinema", "drama", "serial", "web series", "netflix", "youtube", "star", "performance", "song", "album", "concert")) {
            return "entertainment";
        }
        
        // Politics: politics, political, election, government, minister, parliament, congress, vote, politician, law, policy, legislation, president, prime minister, cabinet, parliament, congress, senate, voting, campaign, election
        if (containsKeywords(text, "politics", "political", "election", "government", "minister", "parliament", "congress", "vote", "politician", "law", "policy", "legislation", "president", "prime minister", "cabinet", "senate", "voting", "campaign", "democratic", "party")) {
            return "politics";
        }
        
        // Default category
        return "general";
    }

    // Helper method to check if text contains any of the keywords
    private boolean containsKeywords(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

