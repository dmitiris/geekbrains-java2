package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler {
    private ServerWorker server;
    private Socket socket;
    private String nick;
    static private Integer counter = 0;

    private DataInputStream in;
    private DataOutputStream out;

    String getTime(){
        Date date = new Date();
        String strDateFormat = "hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }

    String getName(){
        return nick;
    }

    void sendMessage(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isSockeClosed(){
        return socket.isClosed();
    }

    public ClientHandler(ServerWorker server, Socket socket) {
        this.server = server;
        this.socket = socket;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth")) {
                            String[] tokens = str.split(" ");

                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (server.isConnected(newNick)) {
                                    sendMessage(String.format("Пользователь %s уже подключён", newNick));
                                } else {
                                    sendMessage("/authok");
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    break;
                                }
                            } else {
                                sendMessage("Неверный логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.equalsIgnoreCase("/exit")){
                            out.writeUTF("/servclose");
                            System.out.println("[" + getTime() + "] " + nick + " left");
                            break;
                        } else if (str.startsWith("/w")){
                            String[] message = str.split(" ");
                            if (message.length > 2) {
                                StringBuilder privateMessage = new StringBuilder();
                                String recipient = message[1];
                                String sender = ClientHandler.this.nick;
                                if (recipient.equalsIgnoreCase(sender)) {
                                    sendMessage("В этом чате нельзя общаться с умными людьми (попытка отправить сообщение самому себе)");
                                } else if (server.isConnected(recipient)) {
                                    privateMessage.append(String.format("%s [%s to %s]", getTime(), sender, recipient));

                                    for (int i = 2; i < message.length; i++) {
                                        privateMessage.append(" ");
                                        privateMessage.append(message[i]);
                                    }
                                    server.sendPrivateMessage(recipient, privateMessage.toString());
                                    sendMessage(privateMessage.toString());
                                } else {
                                    sendMessage(String.format("Пользователь %s не в сети или его не существует", message[1]));
                                }

                            }

                        } else {
                            server.broadcastMessage("[" + getTime() + "] " + nick + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("[" + getTime() + "] " + nick + " suddenly left");
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unscribe(ClientHandler.this);
                    server.broadcastMessage("[" + getTime() + "] System: " + nick + " left");

                }

            }
        }).start();
    }
}
