# 🚀 Quick Start Guide

## 5-Minute Setup

### Step 1: Ensure MySQL is Running ✅

```powershell
# Open MySQL and verify
mysql -u root -p
# Password: uvmysql@2006

# Run this to create database if it doesn't exist:
CREATE DATABASE IF NOT EXISTS uvbase;
EXIT;
```

### Step 2: Build the Project 🔨

```powershell
cd c:\Users\Shubham Vishwakarma\Desktop\news_project
mvn clean install
```

### Step 3: Run the Application ▶️

```powershell
mvn spring-boot:run
```

Wait for the message: `✅ APPLICATION STARTED SUCCESSFULLY`

### Step 4: Open Browser 🌐

1. **Home Page**: http://localhost:8080/
2. **View News**: http://localhost:8080/news
3. **Fetch News**: http://localhost:8080/fetch

### Step 5: Check Console Output 📋

Look for these success messages:

```
🚀 APPLICATION STARTED SUCCESSFULLY
📥 Fetching news from API on startup...
📰 Fetched X articles from API
✅ Successfully saved X news items to database
```

---

## ❌ Troubleshooting Quick Fixes

### ❌ "Connection refused" / Database Error
→ **Fix**: Make sure MySQL is running
```powershell
# Windows - check MySQL in Services
# Or restart MySQL
net stop MySQL80
net start MySQL80
```

### ❌ "Failed to load resource: 404"
→ **Fix**: This is just missing external resources, ignore it
→ The app still works fine!

### ❌ No news showing on /news page
→ **Step 1**: Visit http://localhost:8080/fetch
→ **Step 2**: Wait 5 seconds and go back to /news
→ **Step 3**: Check console for errors

### ❌ Port 8080 already in use
→ **Fix**: Edit `src/main/resources/application.properties`
```properties
server.port=8081
```

---

## 🎯 What to See

### Home Page (http://localhost:8080)
- Beautiful gradient background
- "View Latest News" button
- "Fetch New Headlines" button

### News Page (http://localhost:8080/news)
- List of news articles
- Title, description, image
- Link to full article
- Source information

### Console Output
- ✅ News fetched count
- ✅ Database save confirmation
- 🔄 Scheduler running messages

---

## 📊 Verify Database Setup

```powershell
# Open MySQL command line
mysql -u root -puvmysql@2006

# Check database
USE uvbase;
SHOW TABLES;

# Count news articles
SELECT COUNT(*) FROM news;

# View sample articles
SELECT title, source FROM news LIMIT 5;

EXIT;
```

---

## 🔄 Scheduled Updates

After app starts:
- ⏰ First fetch: **Immediately** (on startup)
- ⏰ Next fetch: **1 hour later**
- ⏰ Then every: **1 hour after that**

Manual fetch anytime: Visit http://localhost:8080/fetch

---

## 📝 Important Files

| File | Purpose |
|------|---------|
| `pom.xml` | Dependencies |
| `application.properties` | Database & port config |
| `NewsProjectApplication.java` | Main app |
| `style.css` | Web page styling |
| `index.html` | Home page |
| `news.html` | News display page |

---

## ✨ Common Commands

```powershell
# Build only (no run)
mvn clean install

# Run application
mvn spring-boot:run

# Clean project
mvn clean

# Run tests
mvn test

# Stop application
# Press: Ctrl + C in the terminal
```

---

## 💡 Pro Tips

✅ **Tip 1**: Always ensure MySQL is running before starting the app

✅ **Tip 2**: If no news shows, click "Fetch Latest News" button

✅ **Tip 3**: Watch the console - it shows exactly what's happening

✅ **Tip 4**: Use Ctrl+F5 in browser to hard refresh (clear cache)

✅ **Tip 5**: Database is persistent - news stays even after app restart

---

**Still having issues?** 
→ Check `TROUBLESHOOTING.md` for detailed solutions
→ Check `README.md` for complete documentation

**Ready?** 
→ Run: `mvn spring-boot:run`
→ Visit: http://localhost:8080
→ Enjoy! 🎉
