Smart Logistics System – Backend

This is the Spring Boot backend for the Smart Logistics System.
It provides REST APIs to manage logistics and shipment data and serves as the core service for the frontend application.

🚀 Live Backend API (Render)
👉 https://smart-logistics-backend.onrender.com/api/shipments

⚠️ Important Usage Note
This backend service is hosted on Render Free Plan.

If inactive, the service may take 30–60 seconds to start.
Please open the API URL once and wait for the response before accessing the frontend.

📊 Project Presentation
👉 Project PPT (Gamma AI):
https://gamma.app/docs/Smart-Logistics-System-hq5yx3ca9plb8bn

🛠️ Tech Stack
Java  
Spring Boot  
REST APIs  
Maven  
Docker  
GitHub Actions (CI/CD)  
SonarCloud  
Render (Backend Deployment)

▶️ Run Locally
Bash

git clone https://github.com/devops-project-bca/smart-logistics-backend.git  
cd smart-logistics-backend  
mvn clean install  
mvn spring-boot:run  

Backend runs at:

http://localhost:8080

🔍 SonarCloud
SonarCloud analysis configured  
Static code analysis enabled  
Quality Gate monitored via GitHub Actions

🐳 Docker Support
The backend application is containerized using Docker.

Build Docker image:

docker build -t smart-logistics-backend .

Run Docker container:

docker run -d -p 8080:8080 --name smart-backend smart-logistics-backend

🔗 Frontend
👉 Frontend Repository:
https://github.com/devops-project-bca/smart-logistics-frontend

👉 Live Frontend URL (Vercel):
https://smart-logistics-frontend-mocha.vercel.app

👉 Live Frontend URL (Azure):
https://gray-dune-0b862e200.4.azurestaticapps.net
