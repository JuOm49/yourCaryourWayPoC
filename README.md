# Your Car Your Way - PoC

## Description
Proof of Concept (PoC) for the **Your Car Your Way** application.

---

## Prerequisites
- Node.js (for Angular)
- Java 21+ (for Spring Boot)
- MySQL (for the database)

---

## Installation & Setup

### 1. Clone the Project
Switch to the **Main** branch:
```bash

git clone <REPO_URL>
cd your-car-your-way
git checkout Main

## Run FrontEnd
cd your_car_your_way/front/yourCarYourWay
npm install
ng serve
The frontEend will start on the default port: http://localhost:4200

##Run BackEnd

cd your_car_your_way/back/api
mvn spring-boot\:run
The backend will start on the default port: http://localhost:8080
