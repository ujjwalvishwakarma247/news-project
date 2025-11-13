# 🔍 NEWS PROJECT TROUBLESHOOTING GUIDE

## Step 1: Verify Database Connection

### Check MySQL is Running
```bash
# Windows - Open Command Prompt/PowerShell and check MySQL
mysql -u root -p
# Enter password: uvmysql@2006
# Check if database exists:
SHOW DATABASES;
USE uvbase;
SHOW TABLES;
```

### If database doesn't exist, create it:
```sql
CREATE DATABASE uvbase;
```

---

## Step 2: Check Application Startup Logs

After restart, look for these messages in the console:

### ✅ SUCCESS Indicators:
```
🚀 APPLICATION STARTED SUCCESSFULLY
📥 Fetching news from API on startup...
📰 Fetched X articles from API
✅ Added: [Article Title]
✅ Successfully saved X news items to database
📌 Access the app at: http://localhost:8080
```

### ❌ ERROR Indicators - What They Mean:

#### Error 1: Database Connection Failed
```
Error: Cannot get a connection, pool error Timeout waiting for idle object
```
**Fix:** Check MySQL is running and credentials are correct in `application.properties`

#### Error 2: API Response Error
```
❌ API Response is null
❌ Error fetching news: [error message]
```
**Fix:** 
- Check internet connection
- Check API key is valid: `a99294ad2c134c498f042429f8862e44`
- Verify news API is not rate-limited

#### Error 3: @EnableScheduling Not Found
```
No bean of type 'EnableScheduling' found
```
**Fix:** Already fixed - we added `@EnableScheduling` to the main class

---

## Step 3: Manual Testing

### Test 1: Trigger News Fetch Manually
1. Go to: **http://localhost:8080/fetch**
   - This should fetch news and redirect to the news page
   - Check console for debug messages

### Test 2: View News in Database
1. Go to: **http://localhost:8080/news**
   - Should display all news from database
   - If empty, something is wrong with data fetch or save

### Test 3: Direct Database Query
```sql
USE uvbase;
SELECT COUNT(*) as total_news FROM news;
SELECT title, source FROM news LIMIT 5;
```

---

## Step 4: Common Issues & Solutions

### Issue: "Fetched 0 articles"
- API might be returning empty response
- Check API key validity
- Try accessing API directly in browser:
  ```
  https://newsapi.org/v2/top-headlines?country=in&apiKey=a99294ad2c134c498f042429f8862e44
  ```

### Issue: "Successfully saved 0 news items"
- `fetchNews()` returned empty list
- See Issue above

### Issue: Articles fetched but not showing in /news page
- Data saved to database but Hibernate not finding them
- **Solutions:**
  1. Clear database: `TRUNCATE TABLE news;`
  2. Restart application
  3. Go to `/fetch` again to reload

### Issue: Port 8080 already in use
- **Solution:** Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

---

## Step 5: Enable Debug Logging

Add to `application.properties`:
```properties
logging.level.root=INFO
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE
spring.jpa.show-sql=true
```

---

## ⚠️ Quick Checklist

- [ ] MySQL is running
- [ ] Database `uvbase` exists
- [ ] Table `news` exists (auto-created by Hibernate)
- [ ] Application starts without errors
- [ ] Console shows "✅ APPLICATION STARTED SUCCESSFULLY"
- [ ] Visit http://localhost:8080/fetch manually
- [ ] Check /news page shows data
- [ ] Verify database has records: `SELECT COUNT(*) FROM news;`

---

## 🆘 If Still Not Working

Please share:
1. **Full console output** from application startup
2. **Error messages** (if any)
3. **MySQL version** and status
4. **Result of:** `SELECT COUNT(*) FROM news;`

