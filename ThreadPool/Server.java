import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
   
    private static int totalRequests = 0;
    private static String lastIp = "None";
    private static final long startTime = System.currentTimeMillis();
     private final ExecutorService threadPool;  

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

public void handleClient(Socket clientSocket) {
    String threadName = Thread.currentThread().getName();
    System.out.println("[POOL] " + threadName + " is handling a new connection...");
    try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)
    ) {
        String requestLine = in.readLine();
        if (requestLine == null) return;

        System.out.println("Request: " + requestLine);
        totalRequests++;
        lastIp = clientSocket.getInetAddress().toString();

        if (requestLine.contains("GET /stats")) {
        long uptimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
        toSocket.println("HTTP/1.1 200 OK");
        toSocket.println("Content-Type: application/json");
        toSocket.println("");
        // Add "uptime" to the JSON string below
        toSocket.println("{\"total\": " + totalRequests + ", \"active\": " + Thread.activeCount() + 
                         ", \"lastIp\": \"" + lastIp + "\", \"uptime\": " + uptimeSeconds + "}");
    }
        else {
    try {
        
        java.nio.file.Path filePath = java.nio.file.Paths.get("www/index.html");
        
        if (java.nio.file.Files.exists(filePath)) {
            String content = new String(java.nio.file.Files.readAllBytes(filePath));
            
            // 2. IMPORTANT: Send to 'toSocket', NOT 'System.out'
            toSocket.println("HTTP/1.1 200 OK");
            toSocket.println("Content-Type: text/html");
            toSocket.println("Content-Length: " + content.length());
            toSocket.println(""); // Blank line is mandatory in HTTP
            toSocket.println(content);
        } else {
            // Fallback if file is missing
            toSocket.println("HTTP/1.1 404 Not Found");
            toSocket.println("");
            toSocket.println("<h1>File index.html not found in www folder</h1>");
        }
    } catch (Exception e) {
    toSocket.println("HTTP/1.1 404 Not Found");
    toSocket.println("Content-Type: text/html");
    toSocket.println("");
    toSocket.println("<div style='text-align:center; font-family:sans-serif; margin-top:50px;'>");
    toSocket.println("<h1 style='color: #ef4444;'>404 - Resource Not Found</h1>");
    toSocket.println("<p>The requested file could not be located on the Nexus server.</p>");
    toSocket.println("<a href='/'>Return to Dashboard</a></div>");
}   
    System.out.println("[SUCCESS] " + threadName + " completed request for " + lastIp);
}
    } catch (IOException ex) {
        System.out.println("[ERROR] " + threadName + " failed: " + ex.getMessage());
    }
}

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10; // Adjust the pool size as needed
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Use the thread pool to handle the client
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server exits
            server.threadPool.shutdown();
        }
    }
}
