package AConsoleServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class MainServer {
    public static void main(String[] args) {
        ServerSocket server;
        Socket socket;

        try {
            server = new ServerSocket(8190);
            System.out.println("Server started");

            socket = server.accept();
            System.out.println("Client connected");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner msg = new Scanner(System.in);

            Thread write = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                    while (true){
                        String outgoing = msg.nextLine();
                        out.writeUTF(outgoing);
                        if (outgoing.equalsIgnoreCase("\\q")) {
                            break;
                        }
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            write.setDaemon(true);
            write.start();
            try {
                while (true) {
                    String str = in.readUTF();
                    if (str.equalsIgnoreCase("\\q")) {
                        break;
                    }
                    System.out.println("Client: " + str);
                }
            } catch (SocketException e){
                System.out.println("Socket is closed");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSocket(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
