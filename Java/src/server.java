package src;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server{
    public static void main(String[] args) {
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        try {
            ServerThread serverThread = new ServerThread();
            serverThread.start();
        } catch (IOException e) {
        }
    }
}




