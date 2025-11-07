# Railway Database Configuration

Based on your Railway MySQL database, here are the environment variables you need to set in your Render web service:

## Environment Variables to Add

Add these to your **Render web service** (lms-backend) environment variables:

### Option 1: Use Internal URL (Recommended for Railway → Render)
```
SPRING_DATASOURCE_URL = jdbc:mysql://shuttle.proxy.rlwy.net:49106/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME = root
SPRING_DATASOURCE_PASSWORD = ZykDcNGiLSZViqpJiNZciWVbEqFKNsGP
```

### Option 2: Use Internal Railway URL (If both services are on Railway)
```
SPRING_DATASOURCE_URL = jdbc:mysql://mysql.railway.internal:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME = root
SPRING_DATASOURCE_PASSWORD = ZykDcNGiLSZViqpJiNZciWVbEqFKNsGP
```

## Steps to Configure

1. Go to your Render web service dashboard
2. Click on your `lms-backend` service
3. Go to the **"Environment"** tab
4. Click **"Add Environment Variable"**
5. Add each of the three variables above:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
6. Save and redeploy

## Important Notes

- **Use Public URL**: Since Render and Railway are different platforms, use the `MYSQL_PUBLIC_URL` (shuttle.proxy.rlwy.net:49106) for the connection
- **Database Name**: The database is named `railway` (not `learning_management`)
- **Port**: Use port `49106` from the public URL, not `3306`
- **SSL**: Set `useSSL=false` for Railway connections

## Verify Connection

After redeploying, check the logs for:
- ✅ `HikariPool-1 - Start completed.`
- ✅ `Started LearningManagementSystemApplication`
- ❌ If you see connection errors, double-check the URL and credentials

## Alternative: Update Database Name

If you want to use a different database name, you can:
1. Create a new database in Railway
2. Or update the URL to use `learning_management` if that database exists

The application will create tables automatically with `ddl-auto: update` setting.

