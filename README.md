<br />
<div align="center">
  <h3 align="center">DELIVERY WEATHER NOTIFICATION APP</h3>
  <p align="center">
    Aplicación backend que permite notificar por correo electrónico a los compradores si su entrega presentará un retraso por condiciones climaticas, utilizando una integración con una API externa de pronóstico meteorológico.
 </p>
<p>    
Adicional, permite consultar el historial de notificaciones enviadas por posibles retrasos a un correo específico.
</p>
</div>

---

### 🛠️ Built With

* ![Java](https://img.shields.io/badge/java-17-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-9.0-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=postgresql&logoColor=white)
* ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
* ![JavaMailSender](https://img.shields.io/badge/JavaMailSender-007396?style=for-the-badge&logo=maildotru&logoColor=white)

---

## 💻 Recommended Tools

* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
* [Postman](https://www.postman.com/downloads/)

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 17
- Gradle 9.x
- PostgreSQL

### 📦 Installation

1. Clona el repositorio:
   ```bash
   git clone <URL_DEL_REPO>
   cd delivery-weather-notification-app
   ```
2. La aplicación necesita conectarse a una base de datos que almacena información clave, como el correo electrónico de origen y la API key utilizada para consultar el servicio de clima. Para habilitar esta funcionalidad, es necesario configurar los siguientes valores dinámicos en el archivo src/main/resources/application.yml
   ```yaml
   DB_HOST
   DB_PORT
   DB_NAME
   DB_USER
   DB_PASSWORD
   INTERNAL_KEY
   ```

3. Ejecuta la aplicación:
    - Desde IntelliJ: clic derecho sobre `DeliveryWeatherNotificationApp` → **Run**
    - O desde consola:
      ```bash
      ./gradlew bootRun
      ```

---

## 📫 Endpoints

- **Swagger UI:**  
  [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html)

- **Login endpoint:**  
  `POST http://localhost:8090/login`

  #### Body de ejemplo:
  ```json
  {
      "username": "<USERNAME>",
      "password": "<PASSWORD>"
  }
  ```

---

## 🛡️ Circuit Breaker

El sistema implementa un **circuit breaker** para proteger la integración con la API externa de clima. Este patrón se encarga de:

- Evaluar las últimas 5 llamadas realizadas.
- Activarse si al menos 3 llamadas han sido ejecutadas y el 50% o más fallan.
- Cuando se activa, se corta el acceso por 10 segundos (estado abierto).
- Luego, pasa a un estado de prueba (medio abierto), donde permite 2 llamadas de verificación.
- Si ambas llamadas son exitosas, se reactiva el acceso normal.

Esta implementación garantiza la resiliencia ante caídas o lentitud en el servicio externo.

---

## ✅ Tests

- Ejecuta todos los tests desde IntelliJ:
    - Clic derecho en la carpeta `test` → **Run tests with coverage**
- Asegúrate de revisar los porcentajes de cobertura en los archivos de interés.

---