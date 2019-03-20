package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class ServerWorker {
    private Vector<ClientHandler> clients = new Vector<>();

    public ServerWorker() {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                ClientHandler client = new ClientHandler(this, socket);
                clients.add(client);
                System.out.println("[" + client.getTime() + "] " + client.getName() + " connected");
                broadcastMessage("[" + client.getTime() + "] System: " + client.getName() + " connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String msg){
        Iterator<ClientHandler> i = clients.iterator();
        while(i.hasNext()) {
            ClientHandler c = i.next();

            if (c.isSockeClosed()){
                i.remove();
            } else {
                c.sendMessage(msg);
            }
        }
    }

}
