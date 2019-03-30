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
//    private List<String> blacklist;
    private BlackList blacklist;
    private boolean isAlive;

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

    boolean checkBlackList(String nick) {
        return blacklist.contains(nick);
    }

    boolean isSockeClosed(){
        return socket.isClosed();
    }

    public ClientHandler(ServerWorker server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.isAlive = true;
        this.blacklist = new BlackList(ClientHandler.this);

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
                    Thread timecounter = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(12000);
                                isAlive = false;
                                out.writeUTF("/servclose");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    timecounter.setDaemon(true);
                    timecounter.start();

                    while (isAlive) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth")) {
                            String[] tokens = str.split(" ");

                            String newNick = DataBaseService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (server.isConnected(newNick)) {
                                    sendMessage(String.format("Пользователь %s уже подключён", newNick));
                                } else {
                                    timecounter.interrupt();
                                    sendMessage("/authok");
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    blacklist.load();
                                    break;
                                }
                            } else {
                                sendMessage("Неверный логин/пароль");
                            }
                        }
                    }
                    while (isAlive) {
                        String str = in.readUTF();
                        if (str.equalsIgnoreCase("/exit")){
                            out.writeUTF("/servclose");
                            System.out.println(String.format("[%s] %s left", getTime(), nick));
                            break;
                        } else if (str.startsWith("/w")){
                            String[] message = str.split(" ", 3);
                            if (message.length > 2) {
                                String recipient = message[1];
                                String sender = ClientHandler.this.nick;
                                if (recipient.equalsIgnoreCase(sender)) {
                                    sendMessage("В этом чате нельзя общаться с умными людьми (попытка отправить сообщение самому себе)");
                                } else if (server.isConnected(recipient)) {
                                    String privateMessage = String.format("%s [%s to %s] %s", getTime(), sender, recipient, message[2]);
                                    server.sendPrivateMessage(ClientHandler.this, recipient, privateMessage);
                                } else {
                                    sendMessage(String.format("Пользователь %s не в сети или его не существует", message[1]));
                                }
                            }
                        } else if (str.startsWith("/blacklist")){
                            String[] message = str.split(" ");
                            if (message.length == 1){
                                sendMessage(blacklist.toString());
                            } else if (message.length == 2) {
                                if (blacklist.contains(message[1])) {
                                    blacklist.remove(message[1]);
                                    sendMessage(String.format("Вы исключили пользователя %s из чёрного списка", message[1]));
                                } else {
                                    blacklist.add(message[1]);
                                    sendMessage(String.format("Вы добавили пользователя %s в чёрный список", message[1]));
                                }
                            }
                        } else if (str.startsWith("/")) {
                            sendMessage(String.format("Неправильная команда: %s", str));
                        } else {
                            server.broadcastMessage(ClientHandler.this, String.format("[%s] %s: %s", getTime(), nick, str));
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
                    server.broadcastMessage(ClientHandler.this, String.format("[%s] %s left", getTime(), nick));
                }

            }
        }).start();
    }
}
