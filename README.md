# Documentación del Proyecto: **Sistema de Cambio de Moneda**

#### **Descripción General**
Este proyecto es un sistema que permite realizar operaciones de tipo de cambio entre monedas. Integra funcionalidades como validación de usuarios en un servicio externo, manejo de seguridad mediante JWT, auditoría funcional, y operaciones CRUD para tipos de cambio almacenados en una base de datos H2.

---

#### **Requerimientos Funcionales**
1. **Realizar Tipo de Cambio**:
    - Permite calcular el monto final en una moneda de destino a partir de una moneda de origen, utilizando un tipo de cambio almacenado.
    - Entrada requerida: `userId`, `monto`, `monedaOrigen`, `monedaDestino`.
    - Se registra el nombre del usuario que realiza la solicitud.
    - Incluye auditoría funcional: registra usuario, monto inicial, monto final, tipo de cambio y fecha del proceso.

2. **Validación de Usuarios**:
    - Consume el servicio REST público `https://gorest.co.in/` para validar si el usuario existe.
    - Si el `userId` no existe, se retorna un mensaje indicando el error.

3. **Gestión de Tipos de Cambio**:
    - Registro de nuevos tipos de cambio.
    - Actualización de tipos de cambio existentes.
    - Búsqueda de tipos de cambio por ID o por combinación de moneda de origen y destino.
    - Listado de todos los tipos de cambio almacenados.

4. **Autenticación y Seguridad**:
    - Se utiliza JWT para la autenticación.
    - Roles definidos:
        - `ADMIN`: Acceso total (GET, POST, PUT, DELETE).
        - `USER`: Solo acceso a métodos GET.

---

#### **Requerimientos No Funcionales**
- **Lenguaje**: Java 11.
- **Framework**: Spring Boot 2.7.8.
- **Base de Datos**: H2 (persistencia en memoria).
- **Programación Reactiva**: WebFlux y R2DBC.
- **Seguridad**: Autenticación basada en JWT.
- **Documentación API**: Swagger (integrado con SpringDoc OpenAPI).
- **Pruebas**: Postman y soporte para pruebas unitarias e integración.
- **Manejo de Excepciones**: Control centralizado de errores mediante `@ControllerAdvice`.

---

#### **Dependencias Principales**
- **Spring Boot Starter WebFlux**: Soporte para programación reactiva.
- **Spring Boot Starter Security**: Manejo de seguridad.
- **Spring Boot Starter Validation**: Validación de datos de entrada.
- **R2DBC y H2**: Persistencia reactiva.
- **JWT**: Autenticación mediante JSON Web Tokens.
- **Swagger OpenAPI**: Documentación de la API.
- **Lombok**: Reducción del código boilerplate (getters, setters, constructores).
- **DevTools**: Herramientas de desarrollo para recarga rápida.
- **Test**: Herramientas para pruebas reactivas.

---

#### **Instalación**
1. Clonar el repositorio:
   ```bash
   git clone <repositorio>
   cd <repositorio>
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

4. Acceder a la aplicación:
    - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
      **JDBC URL**: `jdbc:h2:mem:testdb`

---

#### **Estructura del Proyecto**
- **`controller`**:
    - Endpoints para las APIs de Experiencia (`/api/experiencia`) y Soporte (`/api/soporte`).
    - Control de autenticación (`/auth`).

- **`service`**:
    - Lógica de negocio para validar usuarios, gestionar tipos de cambio y realizar auditorías.

- **`repository`**:
    - Interfaz para el acceso a datos reactivos con R2DBC.

- **`security`**:
    - Configuración de seguridad JWT y manejo de roles.

- **`exception`**:
    - Manejo centralizado de excepciones con mensajes descriptivos para cada caso.

---

#### **API Endpoints**
1. **Autenticación**
    - **Registro de usuario (`/auth/signup`)**: Crear usuarios con roles.
    - **Inicio de sesión (`/auth/login`)**: Generar JWT.

2. **Experiencia (`/api/experiencia`)**
    - **POST `/tipocambio`**: Realizar un tipo de cambio.

3. **Soporte (`/api/soporte`)**
    - **GET `/tipocambio`**: Listar tipos de cambio.
    - **GET `/tipocambio/{id}`**: Buscar tipo de cambio por ID.
    - **POST `/tipocambio`**: Registrar un nuevo tipo de cambio.
    - **PUT `/tipocambio/{id}`**: Actualizar un tipo de cambio.

---

#### **Pruebas**
- **Registro de usuario (Postman)**:
   ```bash
   curl --location 'http://localhost:8080/auth/signup' \
   --header 'Content-Type: application/json' \
   --data '{
       "username": "admin",
       "password": "adminpass",
       "role": "ROLE_ADMIN",
       "name": "Jerry"
   }'
   ```

- **Inicio de sesión (Postman)**:
   ```bash
   curl --location 'http://localhost:8080/auth/login' \
   --header 'Content-Type: application/json' \
   --data '{
       "username": "admin",
       "password": "adminpass"
   }'
   ```

- **Realizar tipo de cambio**:
   ```bash
   curl --location 'http://localhost:8080/api/experiencia/tipocambio' \
   --header 'Authorization: Bearer <token>' \
   --header 'Content-Type: application/json' \
   --data '{
       "userId": 123,
       "monto": 100,
       "monedaOrigen": "USD",
       "monedaDestino": "PEN"
   }'
   ```

---

#### **Consideraciones**
- El sistema requiere autenticación JWT para todos los endpoints excepto los de autenticación.
- La auditoría funcional se realiza automáticamente registrando cada operación en la base de datos.
- La validación de usuarios se realiza consumiendo el servicio de `https://gorest.co.in/`.

--- 

