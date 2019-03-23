package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.HashMap;

public class ServerWorker {
    private Vector<ClientHandler> clients = new Vector<>();
    private HashMap<String, ClientHandler> clientMap = new HashMap<>();

    public ServerWorker() {
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
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
            AuthService.disconnect();
        }
    }

    public void broadcastMessage(String msg){
        for (ClientHandler c : clients) {
            c.sendMessage(msg);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        clientMap.put(client.getName(), client);
        System.out.println("[" + client.getTime() + "] " + client.getName() + " connected");
        broadcastMessage("[" + client.getTime() + "] System: " + client.getName() + " connected");
    }

    public void unscribe(ClientHandler client) {
        clients.remove(client);
        clientMap.remove(client.getName());
    }

    public boolean isConnected(String newNick) {
        return clientMap.containsKey(newNick);
    }

    public void sendPrivateMessage(String nickname, String message) {
        ClientHandler client = clientMap.get(nickname);
        client.sendMessage(message);
    }
}
