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
    private String name;
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
        return name;
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
        this.name = "Anonymus_" + ++counter;

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
                        if (str.equalsIgnoreCase("\\q")){
                            out.writeUTF("\\servclose");
                            System.out.println("[" + getTime() + "] " + name + " left");
                            break;
                        }
                        server.broadcastMessage( "[" + getTime() + "]" + name + ": " + str);
                    }
                } catch (IOException e) {
                    System.out.println("[" + getTime() + "] " + name + " suddenly left");
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
                    server.broadcastMessage("[" + getTime() + "]System: " + name + " left");

                }

            }
        }).start();
    }
}
