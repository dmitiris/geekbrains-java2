package Server;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BlackList {
    private List<String> blacklist;
    private ClientHandler client;

    public BlackList(ClientHandler client) {
        this.client = client;
        this.blacklist = new ArrayList<>();
    }


    @Override
    public String toString() {
        return this.blacklist.toString();
    }

    public boolean contains(String nick) {
        return blacklist.contains(nick);
    }

    public void load() {
        this.blacklist = DataBaseService.blacklistLoad(this.client.getName());
    }

    public void remove(String nick) {
        if (this.contains(nick)) {
            this.blacklist.remove(nick);
            DataBaseService.blacklistRemove(this.client.getName(), nick);
        }
    }

    public void add(String nick) {
        if (!this.contains(nick)) {
            this.blacklist.add(nick);
            DataBaseService.blacklistAdd(this.client.getName(), nick);
        }
    }
}
