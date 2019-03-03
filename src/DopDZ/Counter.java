package DopDZ;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Counter {
    public static  void main(String[] args) {
        String filename = "Dop_DZ.txt";
        List<String> lines = readFile(filename);
        int[] vowel_counter = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            for (char character : lines.get(i).toCharArray()) {
                if (character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u') {
                    vowel_counter[i]++;
                }
            }
            System.out.println("Line " + i + ": " + vowel_counter[i]);
        }
    }

    private static List<String> readFile(String filename) {
        List<String> result = new ArrayList<>();
    	try {
    	    RandomAccessFile file = new RandomAccessFile(filename, "r");
            String str;
            while ((str = file.readLine()) != null) {
                result.add(str);
            }
            file.close();

    	} catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
