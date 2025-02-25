# AREP-basic-server
# Servidor HTTP en Java

## Funcionalidad del Código

Este código implementa un servidor HTTP básico en Java, capaz de manejar peticiones **GET** y **POST**. Sus principales características son:

- Escucha en el puerto **8080**.
- Retorna un archivo **HTML** ubicado en `controller/index.html` cuando accede a la raíz (`/`).
- Maneja solicitudes con parámetros en la URL (`/hello?name=Juan`).
- Responde a solicitudes **POST** con parámetros enviados en el cuerpo (`/hellopost`).
- Retorna **404 Not Found** si la ruta no es reconocida.

## Pasos para Ejecutarlo

### 1. Tener instalado Java

- Asegúrate de tener **Java 8 o superior** instalado.
- Verifica con:
  ```sh
  java -version
  ```
- Si no lo tienes, instálalo desde [https://adoptium.net/](https://adoptium.net/).

### 2. Compilar el código

- Guarda el código como `SimpleHttpServer.java`.
- Compila con:
  ```sh
  javac SimpleHttpServer.java
  ```

### 3. Ejecutar el servidor

- Ejecuta:
  ```sh
  java SimpleHttpServer
  ```
- Debería mostrar:
  ```sh
  Servidor HTTP corriendo en http://localhost:8080
  ```

### 4. Crear el archivo HTML

- Asegúrate de que exista **"controller/index.html"** en el mismo directorio que el código.
- Un ejemplo simple de `index.html`:
  ```html
  <html>
  <head><title>Servidor Simple</title></head>
  <body><h1>Bienvenido al Servidor HTTP</h1></body>
  </html>
  ```

### 5. Probar el servidor

- **Abrir en el navegador:** [http://localhost:8080](http://localhost:8080)
- **Probar endpoints con **********************\`\`********************** o navegador:**
  ```sh
  curl http://localhost:8080/hello?name=Juan
  ```
  ```sh
  curl -X POST -d "name=Juan" http://localhost:8080/hellopost
  ```
- **Esperado:**
  - `"Hello, Juan!"` en `/hello`
  - `"Hello from POST, Juan!"` en `/hellopost`
    damelo en formato mk
