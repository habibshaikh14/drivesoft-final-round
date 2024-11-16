# Project Setup Guide

## Overview

This guide provides detailed instructions on setting up and running the project. It covers the installation of **Java
17+**, **MySQL**, how to restore the database from a backup file, run the **Spring Boot** application, and use **Postman** to test the API endpoints.

---

## Prerequisites

Before setting up the project, ensure the following tools are installed:

1. **Java 17+**
2. **MySQL**
3. **Maven**
4. **Postman** (for API testing)

---

## Step 1: Install Java 17+

### Windows

1. Go to the official Java website: [Adoptium Temurin](https://adoptium.net/temurin/releases/).
2. Download the Windows installer for **Java 17+**.
3. Run the installer and follow the instructions, ensuring to select the option to set `JAVA_HOME` during installation.
4. After installation, verify by running the following command in **Command Prompt**:
   ```
   java -version
   ```

   You should see an output like: `openjdk version "17.x.x"`.

### Linux/Mac

1. Download the `.tar.gz` file for **Java 17+** from [Adoptium Temurin](https://adoptium.net/temurin/releases/).
2. Extract the file to a directory (e.g., `/usr/lib/jvm` for Linux).
3. Set the `JAVA_HOME` environment variable:

   Example for **Linux/Mac**:
   ```bash
   export JAVA_HOME=/path/to/jdk-17
   export PATH=$PATH:$JAVA_HOME/bin
   ```

4. After installation, verify by running the following command:
   ```bash
   java -version
   ```

   The output should show the installed version, such as `openjdk version "17.x.x"`.

---

## Step 2: Install MySQL

### Windows

1. Go to the official MySQL website and download the **MySQL Installer**:  
   [MySQL Installer for Windows](https://dev.mysql.com/downloads/installer/).
2. Choose the **Full Setup** for a complete installation of MySQL Server, Workbench, and other components.
3. Run the installer and follow the prompts. During installation, make sure to set the **root password** and note it for later use.
4. After installation, MySQL should start automatically. If not, you can start it via **MySQL Workbench** or from the **Windows Services** panel.
5. To verify installation, open **Command Prompt** and run:
   ```bash
   mysql -u root -p
   ```
   Enter the **root password** when prompted to access the MySQL command line.

### Linux / macOS

#### Ubuntu/Debian:

1. Update the apt package index:
   ```bash
   sudo apt-get update
   ```

2. Install MySQL Server:
   ```bash
   sudo apt-get install mysql-server
   ```

3. You'll be prompted to set a **root password**during installation. Ensure you remember this password.
4. After installation, start the MySQL service:
   ```bash
   sudo systemctl start mysql
   ```
5. Verify MySQL installation by logging into MySQL:
   ```bash
   mysql -u root -p
   ```
   Enter the **root password** when prompted.

#### macOS:

1. Install MySQL using Homebrew (if Homebrew is not installed, follow instructions on [brew.sh](https://brew.sh/)):
   ```bash
   brew install mysql
   ```

2. Start the MySQL service:
   ```bash
   brew services start mysql
   ```

3. Verify the installation by logging into MySQL:
   ```bash
   mysql -u root -p
   ```

   Enter the **root password** when prompted.

---

## Step 3: Install Maven

### Windows

1. Go to the official **Maven** website: [Apache Maven Download](https://maven.apache.org/download.cgi).
2. Download the **Binary zip archive** (e.g., `apache-maven-3.x.x-bin.zip`).
3. Extract the zip file to a directory (e.g., `C:\Program Files\Apache\Maven`).
4. Set the `MAVEN_HOME` environment variable:
   - Right-click **This PC** (or **My Computer**) and select **Properties**.
   - Click on **Advanced system settings** > **Environment Variables**.
   - Add a new **System variable**:
      - Variable name: `MAVEN_HOME`
      - Variable value: the path to your Maven directory (e.g., `C:\Program Files\Apache\Maven`).
   - Find the `Path` variable in **System variables** and click **Edit**.
   - Add the `bin` directory inside the Maven directory to the path (e.g., `C:\Program Files\Apache\Maven\bin`).
5. After installation, verify Maven by running the following command in **Command Prompt**:
   ```bash
   mvn -version
   ```

   You should see the version information for Maven.

### Linux / macOS

#### Ubuntu/Debian:

1. Update the apt package index:
   ```bash
   sudo apt-get update
   ```

2. Install Maven:
   ```bash
   sudo apt-get install maven
   ```

3. Verify Maven installation:
   ```bash
   mvn -version
   ```

   You should see the version information for Maven.

#### macOS (via Homebrew):

1. If you haven't already installed Homebrew, follow the instructions at [brew.sh](https://brew.sh/).
2. Install Maven:
   ```bash
   brew install maven
   ```

3. Verify Maven installation:
   ```bash
   mvn -version
   ```

   You should see the version information for Maven.

---

## Step 4: Restore the Database from Backup

1. **Locate the Backup File**:
   The backup file is located in the `src/main/resources/backup` folder of the project. The backup contains both the database schema and the data.

2. **Navigate to the Backup Folder**:
   Open a terminal and navigate to the `src/main/resources/backup` folder in the project directory.

3. **Restore the Database**:
   Run the following command to restore the database from the backup file:
   ```bash
   mysql -u root -p < drivesoft_data_dump.sql
   ```
   Enter the **root password** when prompted.

   Since the backup file contains the schema creation, you do **not** need to create the database manually. The backup will handle everything.

---

## Step 5: Modify Database Connection Settings

1. Open the `application.properties` file located in `src/main/resources` in your project.
2. Ensure the following properties are set to the correct values:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root
   ```

   Replace `root` with the appropriate MySQL username and password if you used different credentials during MySQL installation.

---

## Step 6: Run the Spring Boot Application

1. **Navigate to the Project Directory**:
   Open a terminal and navigate to the root folder of the project.

2. **Build the Project**:
   If you haven't already built the project, run the following Maven command:
   ```bash
   mvn clean install
   ```

3. **Start the Application**:
   Run the following Maven command to start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**:
   Once the application is running, you can access it by navigating to:
   ```
   http://localhost:8080
   ```

---

## Step 7: Install and Configure Postman

### 1. Download and Install Postman

1. Go to the official Postman website: [Postman Download](https://www.postman.com/downloads/).
2. Download and install the appropriate version for your operating system.

### 2. Import the Postman Collection

1. Open **Postman** after installation.
2. In the **Postman** interface, click the **Import** button in the top-left corner.
3. Select the **Folder** option and navigate to the `src/main/resources/postman_collection` folder in the project directory.
4. Select the `DriveSoft.postman_collection.json` collection file from the folder.
5. Click **Import** to add the collection to your Postman workspace.

### 3. Use the Postman Collection

1. After importing the collection, you can see the pre-configured API requests in Postman.
2. Make sure the Spring Boot application is running before executing the requests.
3. Run the API requests in Postman to test the endpoints.
4. The Login request needs to be run first to obtain the JWT token, which is then used in subsequent requests.
5. The GetAllAccounts request can be used to test the API after obtaining the JWT token. This will return a list of accounts from the database.
