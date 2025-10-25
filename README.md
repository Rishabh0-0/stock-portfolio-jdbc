## Features

- Create and manage multiple user portfolios
- Buy and sell stocks with real-time portfolio tracking
- View current holdings with detailed portfolio valuation
- Transaction history tracking
- User authentication with secure password hashing
- Wallet balance management
- Persist data via MySQL database
- JDBC-based DAO / repository layer for database interaction
- Complete CLI / console-based interface

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java |
| Database | MySQL |
| DB Access | JDBC |
| Build / Dependency Management | Maven (pom.xml) |
| Schema / DDL | `schema.sql` |
| Password Hashing | BCrypt |

## Getting Started

### Prerequisites

Ensure you have installed:

- Java (JDK 8 or above)  
- MySQL (or a compatible variant)  
- Maven

### Database Setup

1. **Create the database and tables:**
   ```bash
   mysql -u root -p < setup.sql
   ```
   
   Or manually run the SQL commands from `schema.sql` and `setup.sql`

2. **Configure database connection:**
   Update `src/main/resources/db.properties` with your MySQL credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/stock_portfolio
   db.username=your_username
   db.password=your_password
   db.driver=com.mysql.cj.jdbc.Driver
   ```

### Running the Application

1. **Compile and run:**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.portfolio.Main"
   ```

2. **Or run the JAR file:**
   ```bash
   mvn clean package
   java -jar target/stock-portfolio-jdbc-1.0-SNAPSHOT.jar
   ```

### Usage

The application provides a complete CLI interface with the following features:

#### Login Menu
- **Login**: Authenticate with email and password
- **Register**: Create a new user account
- **Exit**: Close the application

#### Main Menu (after login)
- **View Portfolio**: See all holdings with current values
- **Buy Stock**: Purchase stocks using wallet balance
- **Sell Stock**: Sell owned stocks
- **View All Stocks**: Browse available stocks
- **View Transaction History**: See all buy/sell transactions
- **Add Funds**: Add money to wallet
- **Logout**: Return to login menu

#### Sample Users
The application comes with sample users for testing:
- Email: `john@example.com`, Password: `password123`
- Email: `jane@example.com`, Password: `password123`
- Email: `bob@example.com`, Password: `password123`

#### Sample Stocks
The application includes popular stocks like AAPL, GOOGL, MSFT, TSLA, AMZN, META, NVDA, and NFLX with realistic prices.  
