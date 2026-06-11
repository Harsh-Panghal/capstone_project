# ✈️ BlazeDemo E2E Test Automation Framework

An enterprise-grade, highly scalable, and robust test automation framework built to validate the end-to-end flight booking workflow of the BlazeDemo application. This project demonstrates advanced QA engineering practices, moving beyond basic scripting to intelligent automation.

## 🚀 Key Features & Innovations

* **Algorithmic Dynamic Interaction:** Implemented a custom logic engine to dynamically parse live HTML WebTables, extract string prices, convert them to real numbers, and automatically select the absolute **Cheapest Flight** available.
* **State Bridge & Smart Defect Handling:** Passes runtime contextual data (Flight Number, Airline, Price) across pages. Uses TestNG `SoftAssert` to catch application data-integrity defects without halting the CI/CD pipeline execution.
* **Auto-Healing Architecture:** Integrated a custom `IRetryAnalyzer` to intercept sudden network glitches or server timeouts and seamlessly retry the execution, drastically reducing script flakiness.
* **Automated Evidence Collection:** Custom `ITestListener` automatically captures and routes screenshots for both **Failed** steps and **Success Proofs** (Booking IDs) into designated directories.
* **Rich Interactive Dashboards:** Integrated ExtentReports (Spark Reporter) with dark theme configuration for visually appealing, detailed, and audit-ready execution dashboards.
* **Thread-Safe Parallel Execution:** Natively supports high-speed parallel testing by encapsulating `WebDriver` instances within Java's `ThreadLocal` memory space.
* **CI/CD Ready:** Fully configured with a Declarative Jenkinsfile (`Jenkinsfile`) for automated code pull, build, execution, and artifact archiving.
* **Agile Driven:** Developed alongside a fully active Jira Scrum Dashboard to log, track, and resolve identified application defects.

## 🛠️ Technology Stack

* **Language:** Java (JDK 8+)
* **Core Automation:** Selenium WebDriver (v4.18.1)
* **Test Engine & Assertions:** TestNG (v7.9.0)
* **Data-Driven Framework:** Apache POI (Excel)
* **Build Management:** Maven
* **Reporting:** Extent Reports (v5.1.1)
* **CI/CD Integration:** Jenkins
* **Project Management:** Jira (Scrum)

## 📁 Framework Architecture & Layering

The framework strictly adheres to the **Page Object Model (POM)** design pattern, keeping locators completely decoupled from the core test logic.

    📦 BlazeDemo
     ┣ 📂 src/main/java
     ┣ 📂 src/test/java
     ┃ ┣ 📂 base         # Engine: BaseTest, DriverFactory (ThreadLocal)
     ┃ ┣ 📂 pages        # Blueprints: HomePage, FlightResultsPage, PurchasePage, ConfirmationPage
     ┃ ┣ 📂 tests        # Core Logic: BlazeDemoE2ETest
     ┃ ┣ 📂 utils        # Connectors: ConfigReader, ExcelReader, ExtentReportManager
     ┃ ┗ 📂 listeners    # Monitors: TestListener, RetryAnalyzer
     ┣ 📂 src/test/resources
     ┃ ┣ 📜 config.properties  # Environment Variables
     ┃ ┣ 📜 BlazeDemoData.xlsx # Test Data Inputs     ┃
     ┣ 📂 reports
     ┃ ┣ 📂 Screenshots        # Auto-generated visual proofs
     ┃ ┗ 📜 SparkReport.html   # Interactive Dashboard
     ┣ 📜 Jenkinsfile          # CI/CD Declarative Pipeline
     ┗ 📜 pom.xml              # Maven Dependencies
     ┗ 📜 testng.xml           # Suite Execution & Group Control

## ⚙️ Setup & Execution

### 1. Local Execution via IDE
1. Clone the repository: `git clone <your-repo-link>`
2. Open the project in IntelliJ IDEA or Eclipse.
3. Allow Maven to download the dependencies from `pom.xml`.
4. Right-click on `testng.xml` -> **Run 'BlazeDemo Capstone Suite'**.

### 2. Execution via Maven Command Line
Navigate to the root directory of the project and execute:
    mvn clean test

*Note: You can execute specific groups (e.g., E2E, Regression) by modifying the `<groups>` tag in the `testng.xml`.*

### 3. Execution via Jenkins (CI/CD)
1. Ensure Jenkins is installed with Maven configured.
2. Create a new **Pipeline** job.
3. Select **Pipeline script from SCM** and provide this repository URL.
4. Set the script path to `Jenkinsfile`.
5. Click **Build Now**. The pipeline will check out the code, run the tests, and archive the `reports/` folder directly to the Jenkins dashboard.

## 📊 Agile & Defect Tracking
During the development and execution of this framework, **8 defects** (including Data Integrity, Form Validation, and Routing logic) were identified and actively logged into a Jira Scrum Board to simulate a real-world SDLC loop.

---
*Developed as a Capstone Engineering Project by Harsh Choudhary.*