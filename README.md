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
- **SpringDoc OpenAPI** (Swagger UI) 2.3.0
- **Lombok**

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
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  **Insertar Roles Iniciales:**

    El sistema requiere que el rol `ROLE_USER` exista en la base de datos antes de registrar usuarios. Ejecuta el siguiente SQL en tu base de datos:

    ```sql
    INSERT INTO roles (name) VALUES ('ROLE_USER');
    ```

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
