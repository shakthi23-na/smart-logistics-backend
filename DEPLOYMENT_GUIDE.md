# 🚀 Smart Logistics Backend - Deployment Guide

## ✅ Configuration Complete

Your backend is now configured to work with the Vercel frontend at: `https://smart-logistics-frontend-mocha.vercel.app`

### What Was Fixed:

1. **✅ Package Structure** - Fixed all package declarations to match directory structure
2. **✅ CORS Configuration** - Global CORS config allows frontend to communicate with backend
3. **✅ Vercel Deployment** - Added `vercel.json` for Vercel deployment
4. **✅ Environment Support** - Added production and development configurations
5. **✅ Compilation** - All errors resolved, project compiles successfully

---

## 📝 Allowed Origins

Your backend is configured to accept requests from:
- `http://localhost:3000` (local React development)
- `http://localhost:5173` (local Vite development)
- `http://127.0.0.1:3000` (alternative local)
- `http://127.0.0.1:5173` (alternative local)
- `https://smart-logistics-frontend-mocha.vercel.app` (Vercel production)

---

## 🌐 Deploy to Vercel

### Option 1: Using Vercel CLI

```bash
# Install Vercel CLI
npm i -g vercel

# Login to Vercel
vercel login

# Deploy
vercel --prod
```

### Option 2: Connect GitHub Repository

1. Push your code to GitHub
2. Go to [https://vercel.com](https://vercel.com)
3. Click "New Project"
4. Select your repository
5. Vercel will auto-detect the Java project
6. Click "Deploy"

---

## 🔧 Environment Variables

If you need to add environment variables for production:

1. On Vercel Dashboard → Project Settings → Environment Variables
2. Add configuration as needed

---

## 🧪 Testing Locally

```bash
# Start the backend
mvn spring-boot:run

# Backend runs on: http://localhost:8080
# API Endpoints:
#   - GET    /api/shipments
#   - POST   /api/shipments
#   - GET    /api/shipments/{id}
#   - GET    /api/shipments/tracking/{trackingNumber}
#   - PUT    /api/shipments/{id}
#   - DELETE /api/shipments/{id}
```

---

## 📦 Database

Currently using **H2 in-memory database**

For production, consider upgrading to:
- PostgreSQL
- MySQL
- MongoDB

---

## 📋 Files Modified

- `ShipmentController.java` - Removed local CORS, using global config
- `ShipmentRepository.java` - Fixed package declaration
- `Shipment.java` - Fixed package declaration
- `SmartLogisticsBackendApplication.java` - Fixed package declaration
- `application.properties` - Added production support
- `application-prod.properties` - Created for production environment

## 🆕 Files Created

- `config/CorsConfig.java` - Global CORS configuration
- `vercel.json` - Vercel deployment configuration
- `DEPLOYMENT_GUIDE.md` - This guide

---

## ✨ Next Steps

1. **Deploy to Vercel** - Use the CLI or GitHub integration
2. **Test with Frontend** - Update frontend API URL to your Vercel backend URL
3. **Add Database** - Consider upgrading from H2 to a persistent database
4. **Monitor** - Check Vercel logs for any production issues

---

## 🆘 Troubleshooting

### CORS Errors
Add your frontend URL to the allowed origins in `config/CorsConfig.java`

### Build Failures
- Ensure Java 17+ is installed: `java --version`
- Run: `mvn clean install`

### Port Already in Use
Change port in `application.properties`: `server.port=8081`

---

## 🔗 Frontend Connection

Update your frontend API URL to your Vercel backend URL (after deployment):

```javascript
// Example in React
const API_URL = 'https://your-backend.vercel.app';

// Make requests
fetch(`${API_URL}/api/shipments`)
```

---

Happy shipping! 🚚
