# Spring Security JWT JPA

Este proyecto es una API RESTful construida con Spring Boot 4.0.2 y Java 25 que implementa autenticación y autorización segura mediante Spring Security y **JSON Web Tokens (JWT)**. Utiliza JPA (Hibernate) para la persistencia de datos en una base de datos PostgreSQL.

## 🚀 Tecnologías

- **Java 25**
- **Spring Boot 4.0.2**
  - Spring Security
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **PostgreSQL** (Driver)
- **JJWT (Java JWT)** 0.11.5
- **SpringDoc OpenAPI** (Swagger UI) 3.0.1
- **Flyway Migration**
- **Lombok**

### 📦 Dependencias y Versiones Clave

| Dependencia           | Versión   | Notas                  |
| :-------------------- | :-------- | :--------------------- |
| **Java**              | 25        | Requiere JDK 25        |
| **Spring Boot**       | 4.0.2     | Core framework         |
| **SpringDoc OpenAPI** | 3.0.1     | Documentación Swagger  |
| **Flyway**            | (Managed) | Gestión de migraciones |
| **JJWT**              | 0.11.5    | Autenticación JWT      |

## 📋 Prerrequisitos

Asegúrate de tener instalado:

- Java JDK 21 o superior (se recomienda 25 según `pom.xml`).
- Maven.
- PostgreSQL.

## ⚙️ Instalación y Configuración

1.  **Clonar el repositorio:**

    ```bash
    git clone <url-del-repositorio>
    cd security-jwt-jpa
    ```

2.  **Configurar Base de Datos:**

    Abre el archivo `src/main/resources/application.properties` (o `application.yml`) y configura tus credenciales de PostgreSQL:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=validate
    ```

    > **Nota:** Al usar Flyway, se recomienda usar `validate` o `none` para que Hibernate no modifique el esquema, ya que Flyway se encarga de ello.

3.  **Migración de Base de Datos:**

    El proyecto utiliza **Flyway** para gestionar el esquema de la base de datos. Al iniciar la aplicación, Flyway automáticamente:
    - Creará las tablas necesarias.
    - Insertará los datos iniciales (como el `ROLE_USER` definido en `V2__insert_initial_roles.sql`).

    **No es necesario ejecutar scripts SQL manualmente.**

4.  **Ejecutar la Aplicación:**

    ```bash
    mvn spring-boot:run
    ```

## 🔌 Endpoints de la API

### Autenticación (`/api/auth`)

| Método | Endpoint             | Descripción                                                |
| :----- | :------------------- | :--------------------------------------------------------- |
| `POST` | `/api/auth/register` | Registra un nuevo usuario. Asigna `ROLE_USER` por defecto. |
| `POST` | `/api/auth/login`    | Autentica un usuario y devuelve un token JWT.              |

### Documentación (Swagger UI)

Una vez iniciada la aplicación, puedes explorar y probar la documentación interactiva en:

- 👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

## 📝 Ejemplos de Uso

### 1. Registrar Usuario

**Request:** `POST /api/auth/register`

```json
{
  "username": "usuario_ejemplo",
  "password": "password123"
}
```

**Response (201 Created):**

```text
Usuario registrado exitosamente
```

### 2. Login

**Request:** `POST /api/auth/login`

```json
{
  "username": "usuario_ejemplo",
  "password": "password123"
}
```

**Response (200 OK):**

```text
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTY...
```

> **Nota:** Utiliza este token en el header `Authorization: Bearer <token>` para acceder a endpoints protegidos.

### � Manejo de Errores

La API implementa un manejo de errores centralizado (Global Exception Handling) para devolver respuestas JSON claras y consistentes en lugar de trazas de error.

**Ejemplo de respuestas de error:**

**401 Unauthorized (Token inválido o login fallido):**

```json
{
  "error": "No autorizado",
  "message": "Usuario o contraseña incorrectos"
}
```

**400 Bad Request (Validación de datos):**

```json
{
  "username": "no debe estar vacío",
  "password": "el tamaño debe estar entre 8 y 20"
}
```

## �📂 Estructura del Proyecto

- `api/controllers`: Controladores REST (ej. `AuthController`) y Manejador Global (`GlobalExceptionHandler`).
- `api/models`: Entidades JPA (`UserEntity`, `Role`).
- `api/repositories`: Repositorios Spring Data (`UserRepository`, `RoleRepository`).
- `api/security`: Configuración de JWT y Spring Security (`SecurityConfig`, `JwtUtil`, `JwtAuthenticationFilter`, `JwtAuthenticationEntryPoint`).
- `api/dto`: Data Transfer Objects (`LoginDto`, `RegisterDto`).
