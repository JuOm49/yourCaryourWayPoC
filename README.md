# Your Car Your Way - PoC

## Description
Proof of Concept (PoC) for the **Your Car Your Way** application.

---

## Prerequisites
- Node.js (for Angular)
- Java 21+ (for Spring Boot)
- MySQL (for the database)

---

## Database Setup

To use this project, you need to set up the MySQL database using the provided SQL script.

### Steps to Initialize the Database

1. **Ensure MySQL is installed** on your system.
2. **Open a MySQL client** (e.g., MySQL Workbench, command line, or any other MySQL client).
3. **Run the following SQL script located in the sql folder** to create the database and tables:
   open and execute the file `script_your_car_your_way_db.sql` using your MySQL client.

## Installation & Setup

### 1. Clone the Project
Switch to the **Main** branch:

git clone <https://github.com/JuOm49/yourCaryourWayPoC.git>
cd your_car_your_way
git checkout Main

## Run FrontEnd
```bash
cd your_car_your_way/front/yourCarYourWay
npm install
ng serve
````

The frontEnd will start on the default port: http://localhost:4200

##Run BackEnd
```bash
cd your_car_your_way/back/api
mvn spring-boot\:run
```

The backend will start on the default port: http://localhost:8080


