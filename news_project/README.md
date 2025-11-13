# 📰 News Aggregator Application

A Spring Boot application that fetches and displays the latest news headlines using the NewsAPI.

## ✨ Features

- ✅ Automatic news fetching from NewsAPI
- ✅ MySQL database storage
- ✅ Scheduled updates (every 1 hour)
- ✅ Beautiful, responsive web interface
- ✅ Manual news fetch trigger
- ✅ Real-time news display

## 🔧 Prerequisites

Before running the application, make sure you have:

- **Java 21** or higher
- **Maven 3.8.9** or higher
- **MySQL Server** running
- **Database created**: `uvbase`

## 📋 Database Setup

### 1. Create Database

```sql
CREATE DATABASE uvbase;
USE uvbase;
```

The `news` table will be created automatically by Hibernate.

### 2. Verify Connection

Update `application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/uvbase?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=uvmysql@2006
```

## 🚀 Getting Started

### Option 1: Using Maven (Command Line)

```powershell
# Navigate to project directory
cd c:\Users\Shubham Vishwakarma\Desktop\news_project

# Clean and build
mvn clean install

# Run the application
mvn spring-boot:run
```

### Option 2: Using the Batch Script

```powershell
# Double-click run.bat in the project root
# OR from PowerShell:
.\run.bat
```

### Option 3: Using IDE

- Open the project in VS Code or IntelliJ
- Run `NewsProjectApplication.java`
- The app will start on `http://localhost:8080`

## 📱 Application URLs

After starting the application, access:

| URL | Purpose |
|-----|---------|
| `http://localhost:8080/` | Home page with welcome message |
| `http://localhost:8080/news` | View all news from database |
| `http://localhost:8080/fetch` | Manually fetch latest news |

## 🔄 How It Works

1. **Application Starts** → Triggers initial news fetch
2. **Data Fetched** → NewsAPI provides latest headlines from India
3. **Data Stored** → News articles saved to MySQL database
4. **Display** → News shown on `/news` page with beautiful cards
5. **Scheduled Update** → Every 1 hour, new articles are fetched

## 📊 Database Query Examples

```sql
-- Check total news
SELECT COUNT(*) as total_news FROM news;

-- View latest news
SELECT title, source, published_at FROM news ORDER BY published_at DESC LIMIT 10;

-- Clear all news (if needed)
TRUNCATE TABLE news;
```

## 🐛 Troubleshooting

### Issue: 404 Errors on Web Page
- **Solution**: Clear browser cache (Ctrl+Shift+Delete)
- Or use Ctrl+F5 to hard refresh

### Issue: Database Connection Failed
- **Check**: MySQL is running
- **Check**: Database `uvbase` exists
- **Check**: Credentials in `application.properties` are correct

### Issue: No News Displaying
1. Check console for error messages
2. Visit `http://localhost:8080/fetch` to trigger manual fetch
3. Check database: `SELECT COUNT(*) FROM news;`

### Issue: Port 8080 Already in Use
- **Solution**: Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

## 📝 Console Output Example

When the app starts successfully, you should see:

```
============================================================
🚀 APPLICATION STARTED SUCCESSFULLY
============================================================

📥 Fetching news from API on startup...
📰 Fetched 38 articles from API
✅ Added: Indian Startup Raises $50 Million Funding
✅ Added: Tech Innovation Trends for 2025
✅ Successfully saved 38 news items to database

📌 Access the app at: http://localhost:8080
📰 View news at: http://localhost:8080/news
🔄 Fetch news manually: http://localhost:8080/fetch
============================================================
```

## 📂 Project Structure

```
news_project/
├── src/main/java/com/example/news_project/
│   ├── NewsProjectApplication.java         # Main app entry point
│   ├── AppStartupListener.java              # Startup initialization
│   ├── Controller/
│   │   └── NewsController.java              # HTTP endpoints
│   ├── Entity/
│   │   └── News.java                        # Data model
│   ├── Repository/
│   │   └── NewsRepository.java              # Database access
│   ├── Service/
│   │   └── NewsService.java                 # Business logic
│   ├── NewsApiService.java                  # API integration
│   └── Scheduler/
│       └── NewsScheduler.java               # Scheduled tasks
├── src/main/resources/
│   ├── application.properties                # App config
│   ├── templates/
│   │   ├── index.html                       # Home page
│   │   └── news.html                        # News page
│   └── static/
│       └── style.css                        # Styling
├── pom.xml                                   # Dependencies
├── run.bat                                   # Windows startup script
├── TROUBLESHOOTING.md                        # Troubleshooting guide
└── README.md                                 # This file
```

## 🛠 Technology Stack

- **Backend**: Spring Boot 3.5.7
- **Database**: MySQL
- **Frontend**: HTML5, CSS3, Thymeleaf
- **API**: NewsAPI.org
- **Build Tool**: Maven
- **Java Version**: 21

## 📦 Dependencies

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf
- mysql-connector-java
- org.json (for API parsing)

## 🔐 API Information

- **API Provider**: NewsAPI.org
- **Endpoint**: `https://newsapi.org/v2/top-headlines`
- **Region**: India
- **Update Frequency**: Every 1 hour

## 📞 Support

If you encounter issues:

1. Check `TROUBLESHOOTING.md` file
2. Review console error messages
3. Verify MySQL is running
4. Ensure internet connection is active
5. Check database credentials

## 📄 License

This is a demo project for educational purposes.

## 👨‍💻 Author

News Aggregator Application

---

**Last Updated**: November 2025

**Status**: ✅ Production Ready
