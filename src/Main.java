import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        TelephoneDirectory telDir = new TelephoneDirectory();
        telDir.add("Иванов", "01");
        telDir.add("Петров", "02");
        telDir.add("Сидоров", "03");
        telDir.add("Иванов", "04");
        telDir.add("Сидоров", "05");
        telDir.get("Сидоров");
    }
}


class TelephoneDirectory{

    HashMap<String, ArrayList<String>> telDir = new HashMap<>();


    public void add(String name, String phone) {
        ArrayList<String> currentName;
        currentName = telDir.getOrDefault(name, new ArrayList<>());
        currentName.add(phone);
        telDir.put(name, currentName);
    }

    public void get(String search){
        ArrayList<String> currentName;
        int counter = 0;
        currentName = telDir.getOrDefault(search, new ArrayList<>());
        System.out.println("Имя: " + search);
        if (currentName.size() > 0) {
            for (String phone : currentName) {
                System.out.println("Телефон " + ++counter + ": " + phone);
            }
        } else {
            System.out.println("Не найдено ни одного телефона");
        }
    }
}

