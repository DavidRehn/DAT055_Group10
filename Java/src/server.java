package src;

import java.io.IOException;

public class server{
    public static void main(String[] args) {
        try {
            ServerThread serverThread = new ServerThread();
            serverThread.start();
        } catch (IOException e) {
        }
    }
}




