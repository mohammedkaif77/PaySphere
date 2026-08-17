**PaySphere**

PaySphere is a Spring Boot-based fintech payment application designed to manage and process payment transactions through REST APIs.

The project also demonstrates a complete DevOps CI/CD workflow using GitHub, Jenkins, Maven, Docker, and AWS EC2.

**🚀 Project Overview**

PaySphere provides REST APIs for managing payment transactions and stores payment data in MySQL.

The application is built with Java and Spring Boot and is deployed on an AWS EC2 instance.

The project focuses not only on application development but also on automating the build, testing, and deployment process using DevOps practices.

---

**🏗️ Architecture**

                    Developer
                        |
                        | git push
                        v
                     GitHub
                        |
                        | Webhook
                        v
                  +-------------+
                  |   Jenkins   |
                  |    :8080    |
                  +------+------+
                         |
                  Jenkins Pipeline
                         |
             +-----------+-----------+
             |           |           |
             v           v           v
          Checkout     Maven       Tests
                         |
                         v
                    Docker Build
                         |
                         v
                  Docker Container
                         |
                         v
                  PaySphere API
                      :8090
                         |
                         v
                     MySQL
                      :3306
-

**Technology Stack**

**Application**

* Java 21
* Spring Boot 4
* Spring Data JPA
* REST APIs
* Hibernate
* Maven
* MySQL

**DevOps**
* Git
* GitHub
* Jenkins
* Jenkins Pipeline
* Docker
* AWS EC2
* Linux / Ubuntu
* Bash

**Testing**
* JUnit
* Mockito
* Spring Boot Test

**CI/CD Pipeline**

Every change pushed to the `main` branch triggers the Jenkins pipeline through a GitHub webhook.

**Pipeline stages**
Git Push
   ↓
GitHub
   ↓
Webhook
   ↓
Jenkins
   ↓
Checkout
   ↓
Maven Build
   ↓
Automated Tests
   ↓
Docker Build
   ↓
Docker Container
   ↓
PaySphere

**Jenkins stages**

1. **Checkout**

   * Retrieves the latest source code from GitHub.

2. **Build**

   * Builds the Spring Boot application using Maven.

3. **Test**

   * Runs automated unit and application context tests.

4. **Docker Build**

   * Creates a Docker image containing the PaySphere application.

5. **Deploy**

   * Runs the PaySphere application inside a Docker container.


**Docker**

PaySphere is containerized using Docker.

**Dockerfile**

dockerfile
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/paysphere-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]


**Build the application**

bash
mvn clean package


**Build Docker image**

bash
docker build -t paysphere:1.0 .


**Run Docker container**

```bash
docker run -d \
  --name paysphere \
  -p 8090:8090 \
  paysphere:1.0
```

### Check running containers

```bash
docker ps
```

### View application logs

```bash
docker logs paysphere
```

---

# ☁️ AWS Deployment

The application is deployed on an Ubuntu-based AWS EC2 instance.

### Infrastructure

```text
AWS EC2
 |
 +-- Jenkins :8080
 |
 +-- PaySphere :8090
 |
 +-- MySQL :3306
```

The AWS Security Group allows the required application and administration traffic.

---
**Database**

PaySphere uses MySQL for persistent payment data.

Database:
paysphere

Example connection:
jdbc:mysql://localhost:3306/paysphere
The application uses Spring Data JPA and Hibernate for database operations.

**API**

**Get all payments**
GET /api/payments
Example:
```bash
curl http://localhost:8090/api/payments
```

Response when no payments exist:
json
[]


**Testing**

The project includes automated tests using JUnit and Spring Boot Test.

Run tests:

bash
mvn test


Example test result:

Tests run: 4
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS


**⚙️ Local Setup**

**Prerequisites**

Install:

* Java 21
* Maven
* MySQL
* Git
* Docker

**Clone Repository**

bash
git clone https://github.com/mohammedkaif77/PaySphere.git

bash
cd PaySphere

**Configure Database**

Create the database:
CREATE DATABASE paysphere;


Update the database configuration in:
src/main/resources/application.properties

**Build**
mvn clean package

**Run Application**
java -jar target/paysphere-0.0.1-SNAPSHOT.jar

Application runs on:
http://localhost:8090

API:
http://localhost:8090/api/payments


**🔐 Security Notes**

Do not commit passwords, API keys, database credentials, private keys, or other secrets to GitHub.

For production deployments, use:

* Environment variables
* AWS Secrets Manager
* Jenkins Credentials
* Secret management solutions

**📈 Future Improvements**

The project can be extended with:

* Terraform infrastructure provisioning
* Ansible configuration management
* Kubernetes deployment
* AWS ECR
* AWS RDS
* Prometheus and Grafana monitoring
* Centralized logging
* HTTPS with Nginx
* SonarQube code quality analysis
* Trivy container vulnerability scanning
* Blue/Green or Rolling deployments
* Automated rollback


 **👨‍💻 Author**
**Mohammed Kaif**

DevOps & Cloud Engineer
Bengaluru, India

**Skills:**
AWS | Jenkins | Docker | Kubernetes | Terraform
Git | GitHub | Linux | Maven | Ansible
Java | Spring Boot | MySQL | CI/CD

**⭐ Project Highlights**

* Automated CI/CD pipeline using Jenkins
* GitHub webhook-based automated builds
* Maven-based application build
* Automated JUnit/Spring Boot testing
* Dockerized Spring Boot application
* AWS EC2 deployment
* MySQL database integration
* Linux server administration
* REST API development
* End-to-end DevOps workflow

```

