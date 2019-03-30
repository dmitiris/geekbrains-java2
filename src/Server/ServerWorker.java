package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ServerWorker {
    private ConcurrentHashMap<String, ClientHandler> clientMap = new ConcurrentHashMap<>();

    public ServerWorker() {
        ServerSocket server = null;
        Socket socket = null;

        try {
            DataBaseService.connect();
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
            DataBaseService.disconnect();
        }
    }

    public void broadcastMessage(ClientHandler from, String msg){
        for (ClientHandler c : clientMap.values()) {
            if (!c.checkBlackList(from.getName())){
                c.sendMessage(msg);
            }

        }
    }

    public void subscribe(ClientHandler client) {
        clientMap.put(client.getName(), client);
        System.out.println(String.format("[%s] System: %s connected", client.getTime(), client.getName()));
        broadcastMessage(client, String.format("[%s] System: %s connected", client.getTime(), client.getName()));
    }

    public void unscribe(ClientHandler client) {
        clientMap.remove(client.getName());
    }

    public boolean isConnected(String newNick) {
        return clientMap.containsKey(newNick);
    }

    public void sendPrivateMessage(ClientHandler from, String nickname, String message) {
        ClientHandler to = clientMap.get(nickname);
        if (to.checkBlackList(from.getName())) {
            from.sendMessage(String.format("Пользователь %s добавил вас в чёрный список", nickname));
        } else {
            to.sendMessage(message);
            from.sendMessage(message);
        }
    }
}
