package com.mycompany.servicerest;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
 
public class ServiceREST {
    private static final int PORT = 8080;
    public static final String WEB_ROOT = "target/classes/webroot";
 
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);
 
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
    }
}
 
class ClientHandler implements Runnable {
    private Socket clientSocket;
 
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
 
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];

            if (method.equals("GET")) {
                handleGetRequest(fileRequested, out, dataOut);
            } else if (method.equals("POST")) {
                handlePostRequest(fileRequested, in, out, dataOut);
    }

    } catch (IOException e) {
        e.printStackTrace();
    }
}
 
    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(ServiceREST.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);
 
        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);
            out.println(); 
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            out.println();
            out.flush();
            out.println("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }
 
    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }
 
    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) fileIn.close();
        }
        return fileData;
    }
    
    private void handlePostRequest(String fileRequested, BufferedReader in, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
    StringBuilder body = new StringBuilder();
    String line;

    // Leer el encabezado
    while ((line = in.readLine()) != null && !line.isEmpty()) {
        // Leer hasta la línea vacía que separa el encabezado del cuerpo
    }

    // Leer el cuerpo de la solicitud
    while (in.ready() && (line = in.readLine()) != null) {
        body.append(line);
    }

    if (fileRequested.equals("/hellopost")) {
        String requestBody = body.toString();

        // Si se espera JSON
        if (requestBody.startsWith("{")) {
            // Parsear el JSON manualmente o con una biblioteca JSON como Jackson o Gson
            String name = parseJson(requestBody, "name");  // Implementa parseJson para extraer el valor
            String responseMessage = "Hello, " + (name != null ? name : "World") + " from POST!";
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: application/json");
            out.println();
            out.println("{\"message\":\"" + responseMessage + "\"}");
            out.flush();
        } else {
            // Manejo de otros tipos de datos si es necesario
            String name = parseBody(requestBody, "name");
            String responseMessage = "Hello, " + (name != null ? name : "World") + " from POST!";
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: text/plain");
            out.println();
            out.println(responseMessage);
            out.flush();
        }
    } else {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-type: text/html");
        out.println();
        out.println("<html><body><h1>File Not Found</h1></body></html>");
        out.flush();
    }
}

private String parseJson(String json, String key) {
    // Método muy básico para parsear JSON, considera usar una biblioteca JSON como Gson o Jackson
    String search = "\"" + key + "\":\"";
    int start = json.indexOf(search);
    if (start != -1) {
        start += search.length();
        int end = json.indexOf("\"", start);
        if (end != -1) {
            return json.substring(start, end);
        }
    }
    return null;
}


private String parseBody(String body, String key) {
    String[] params = body.split("&");
    for (String param : params) {
        String[] keyValue = param.split("=");
        if (keyValue[0].equalsIgnoreCase(key)) {
            return keyValue[1];
        }
    }
    return null;
}
}