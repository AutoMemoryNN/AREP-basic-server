import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

public class SimpleHttpServer {

    private static final String ROOT = "view/index.html";

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor HTTP corriendo en http://localhost:" + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 OutputStream out = clientSocket.getOutputStream()) {

                String requestLine = in.readLine();
                if (requestLine == null) continue;

                String[] tokens = requestLine.split(" ");
                if (tokens.length < 2) continue;

                String method = tokens[0];
                String path = tokens[1];

                // Leer encabezados y obtener content-length
                Map<String, String> headers = new HashMap<>();
                String line;
                int contentLength = 0;
                while (!(line = in.readLine()).isEmpty()) {
                    if (line.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(line.split(":")[1].trim());
                    }
                    if (line.startsWith("Content-Type:")) {
                        headers.put("Content-Type", line.split(":")[1].trim());
                    }
                }

                Map<String, String> params = new HashMap<>();

                // Manejo de parámetros en la URL (GET)
                if (path.contains("?")) {
                    String[] parts = path.split("\\?", 2);
                    path = parts[0];
                    params.putAll(parseQueryParams(parts[1]));
                }

                // Leer cuerpo si es un POST
                if (method.equals("POST") && contentLength > 0) {
                    char[] body = new char[contentLength];
                    in.read(body);
                    String bodyStr = new String(body);
                    params.putAll(parseQueryParams(bodyStr));  // Parsea los datos enviados en el body
                }

                String response;
                String contentType = "text/plain";

                if ("/".equals(path) && method.equals("GET")) {
                    File file = new File(ROOT);
                    if (file.exists()) {
                        response = new String(Files.readAllBytes(file.toPath()));
                        contentType = "text/html";
                    } else {
                        response = "404 Not Found";
                    }
                } else if ("/hello".equals(path) && method.equals("GET")) {
                    String name = params.getOrDefault("name", "Guest");
                    response = "Hello, " + name + "!";
                } else if ("/hellopost".equals(path) && method.equals("POST")) {
                    String name = params.getOrDefault("name", "Guest");
                    response = "Hello from POST, " + name + "!";
                } else {
                    response = "404 Not Found";
                }

                // Enviar respuesta HTTP
                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + response.length() + "\r\n" +
                        "Access-Control-Allow-Origin: *\r\n" +
                        "\r\n" +
                        response;
                out.write(httpResponse.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> parseQueryParams(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                params.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return params;
    }
}
