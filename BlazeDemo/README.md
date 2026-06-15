# ✈️ BlazeDemo Automation Framework

An enterprise-grade End-to-End (E2E) test automation framework for the BlazeDemo flight booking application. Built on the Page Object Model (POM) design pattern, this project emphasizes scalability, thread-safe parallel execution, and seamless CI/CD integration.

## 🛠️ Technology Stack
* **Core:** Java, Selenium WebDriver, TestNG
* **Build & Data:** Maven, Apache POI (Data-Driven Testing)
* **Design Patterns:** Page Object Model (POM), Singleton (`ThreadLocal`)
* **Reporting:** ExtentReports
* **DevOps:** Jenkins (Declarative Pipeline), Docker (`selenium/standalone-chrome`)

## 🚀 Core Features
* **Dynamic WebTable Parsing:** Custom algorithm to extract, parse, and automatically select the cheapest flight without relying on hardcoded locators.
* **Thread-Safe Execution:** Encapsulated `WebDriver` instances within Java's `ThreadLocal` memory space for flake-free, isolated parallel testing.
* **Containerized CI/CD:** Zero-dependency, headless execution using a custom `Dockerfile` and automated through a `Jenkinsfile` pipeline.
* **Smart Defect Capture:** Custom `ITestListener` triggers automated screenshots upon failure, embedding them directly into interactive Extent Reports.
* **Contextual Data Validation:** Utilizes TestNG `SoftAssert` to track and verify data integrity (e.g., matching flight prices and airlines) across page navigations.

## 📁 Architecture Overview

    📦 BlazeDemo
     ┣ 📂 src/main/java/base      # DriverFactory (ThreadLocal), BaseTest
     ┣ 📂 src/main/java/pages     # POM Classes (HomePage, PurchasePage, etc.)
     ┣ 📂 src/main/java/utils     # ConfigReader, ExcelReader, ExtentManager
     ┣ 📂 src/test/java/tests     # E2E & Negative Test Suites
     ┣ 📂 src/test/java/listeners # TestListener, RetryAnalyzer
     ┣ 📂 reports                 # Extent HTML Reports & Error Screenshots
     ┣ 📜 Dockerfile              # Container Configuration
     ┣ 📜 Jenkinsfile             # CI/CD Pipeline
     ┣ 📜 testng*.xml             # Suite Execution Control
     ┗ 📜 pom.xml                 # Maven Dependencies

## ⚙️ Execution Guide

### 1. Local Execution (Maven)
Run the positive E2E suite or the negative defect-hunting suite directly via terminal:

```bash
mvn clean test -DsuiteXmlFile=testng.xml
mvn clean test -DsuiteXmlFile=testng-negative.xml
```

### 2. Containerized Execution (Docker)
Execute tests headlessly inside an isolated Docker container without requiring local browser installations:

```bash
docker build -t blazedemo-framework .
docker run blazedemo-framework
```

### 3. CI/CD Integration (Jenkins)
1. Create a **Pipeline** job in Jenkins.
2. Point the **Pipeline script from SCM** to this repository.
3. The `Jenkinsfile` will automatically orchestrate code checkout, Maven build, Docker execution, and Extent Report artifact archiving.

## 📊 Quality Assurance & Defect Tracking
Beyond happy-path testing, this framework includes a dedicated Negative Testing Suite. It successfully captured **7 critical application defects**, including authentication blockers (419 Page Expired errors), severe form validation bypasses, and data integrity mismatches, mirroring a real-world Agile SDLC tracking process.

---
*Developed as a Capstone Project for the Wipro NextGen Associate Program by Harsh Choudhary.*