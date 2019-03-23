package AConsoleClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;
        Scanner msg;



        final String IP_ADDRESS = "localhost";
        final int PORT = 8190;

        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            msg = new Scanner(System.in);

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
                    System.out.println("Server: " + str);
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
            if(!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}