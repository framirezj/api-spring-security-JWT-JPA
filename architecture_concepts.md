# Piezas Arquitectónicas de una API Segura

Esta guía resume los componentes conceptuales que usamos en este proyecto. No importa si usas Java (Spring), JavaScript (Express/NestJS), Python (FastAPI/Django) o Go; siempre necesitarás estas piezas equivalentes.

## 1. 🚪 El Guardián (Middleware / Filter)

- **En este proyecto:** `JwtAuthenticationFilter`.
- **Concepto:** Un interceptor que se ejecuta **antes** de que la petición llegue a tu código.
- **Función:** Verifica "¿Traes credenciales válidas?". Si no, a veces rechaza ahí mismo o deja pasar como "anónimo".
- **Equivalente en otros lados:** Middlewares en Express/Node.js, Interceptores en NestJS, Decorators en Python.

## 2. 🔐 El Gestor de Seguridad (Security Config / Guard)

- **En este proyecto:** `SecurityConfig`.
- **Concepto:** El policía de tráfico. Define las reglas del juego.
- **Función:** Dice qué rutas son públicas (`/login`) y cuáles privadas (`/api/**`). Configura CORS (quién puede llamarme) y CSRF.
- **Equivalente en otros lados:** Guards en NestJS, Middleware de Auth en Laravel, decoradores `@login_required` en Flask.

## 3. 🎫 El Utilitario de Tokens (Token Service / Provider)

- **En este proyecto:** `JwtUtil`.
- **Concepto:** La máquina de imprimir y validar billetes.
- **Función:**
  - **Firmar (Sign):** Crear un token con un secreto (`SECRET_KEY`).
  - **Verificar (Verify):** Validar si un token es auténtico y no ha expirado.
- **Equivalente en otros lados:** Librerías como `jsonwebtoken` (Node), `PyJWT` (Python).

## 4. 🧠 El Controlador (Controller / Handler)

- **En este proyecto:** `AuthController`.
- **Concepto:** El recepcionista que atiende al cliente final.
- **Función:** Recibe la petición HTTP (JSON), llama a la lógica necesaria y devuelve la respuesta HTTP. **No debería tener mucha lógica compleja**, solo orquestar.

## 5. 📦 El Modelo de Datos (Entity / Model)

- **En este proyecto:** `UserEntity`, `Role`.
- **Concepto:** La representación de tus datos en la base de datos.
- **Función:** Mapea una tabla SQL a un objeto en tu código (ORM).

## 6. 💾 El Acceso a Datos (Repository / DAO)

- **En este proyecto:** `UserRepository`.
- **Concepto:** El bibliotecario.
- **Función:** Es el único que toca la base de datos directamente (`save`, `find`, `delete`). Aísla tu código de las consultas SQL puras.

## 7. 📦 El Objeto de Transferencia (DTO - Data Transfer Object)

- **En este proyecto:** `LoginDto`, `RegisterDto`.
- **Concepto:** La caja de envío.
- **Función:** Define exactamente qué datos esperas recibir o enviar. Sirve para validar (ej: "el password debe tener 6 caracteres") sin ensuciar tu Modelo de base de datos.

## 8. 🛡️ El Manejador de Errores (Global Exception Handler)

- **En este proyecto:** `GlobalExceptionHandler` y `JwtAuthenticationEntryPoint`.
- **Concepto:** La red de seguridad.
- **Función:** Atrapa cualquier error no controlado y lo convierte en una respuesta amigable.
  - _Handler_: Para errores de lógica.
  - _Entry Point_: Para errores de autenticación inicial.
