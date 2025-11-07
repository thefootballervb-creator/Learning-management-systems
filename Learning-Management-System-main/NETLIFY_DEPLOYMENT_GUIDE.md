# Netlify Frontend Deployment Guide

## Netlify Configuration Settings

Fill in the Netlify deployment settings as follows:

### Required Settings:

1. **Branch to deploy**: `main` ✅ (already set)

2. **Base directory**: 
   ```
   Learning-Management-System-main/frontend
   ```
   *(This tells Netlify where your frontend code is located)*

3. **Build command**: 
   ```
   npm install && npm run build
   ```
   *(Installs dependencies and builds the React app)*

4. **Publish directory**: 
   ```
   build
   ```
   *(This is where Create React App outputs the production build)*

5. **Functions directory**: 
   ```
   netlify/functions
   ```
   *(Leave as default - you're not using Netlify functions)*

## Environment Variables

After deployment, add this environment variable in Netlify:

1. Go to **Site settings** → **Environment variables**
2. Add:
   ```
   REACT_APP_API_BASE_URL = https://your-backend-service.onrender.com
   ```
   *(Replace with your actual Render backend URL)*

## Important Notes

- The `netlify.toml` file in the frontend folder is already configured for SPA routing
- After setting the environment variable, you'll need to **trigger a new deploy** for it to take effect
- The build process will take 2-3 minutes

## Deployment Steps

1. **Connect Repository**:
   - Go to Netlify dashboard
   - Click "Add new site" → "Import an existing project"
   - Connect your GitHub repository

2. **Configure Build Settings**:
   - Use the settings above
   - Netlify will auto-detect some settings, but verify them

3. **Add Environment Variable**:
   - Add `REACT_APP_API_BASE_URL` with your backend URL
   - This must be set before the build for it to work

4. **Deploy**:
   - Click "Deploy site"
   - Wait for build to complete (check logs for any errors)

5. **Verify**:
   - Once deployed, visit your Netlify URL
   - Check browser console for any API connection errors
   - Test login and course browsing

## Troubleshooting

### Build Fails
- Check build logs for specific errors
- Ensure Node.js version is 18+ (Netlify auto-detects)
- Verify all dependencies are in `package.json`

### API Connection Errors
- Verify `REACT_APP_API_BASE_URL` is set correctly
- Check that your Render backend is running
- Ensure CORS is configured on the backend

### Routes Not Working
- The `netlify.toml` file handles SPA routing
- If routes fail, check that the redirect rule is present

## Quick Reference

```
Base directory: Learning-Management-System-main/frontend
Build command: npm install && npm run build
Publish directory: build
Environment variable: REACT_APP_API_BASE_URL = https://your-backend.onrender.com
```

