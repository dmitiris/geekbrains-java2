

public class Main {
    public static void main(String[] strings){
        String[][] test = {
                {"1", "2", "3", "4"},
                {"1", "3", "3", "4"},
                {"1", "2", null, "4"},
                {"1", "2", "3", "20"}
        };

        try {
            System.out.println(getSum(test));
        } catch (MyArrayDataException | MyArraySizeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getSum(String[][] array) throws MyArrayDataException, MyArraySizeException {
        int index = 1;
        int sum = index;
        int row = index;
        int column;
        // По ТЗ сказано сначала определить, что подан массив 4x4 так что сначала проверяем
        if (array.length != 4) throw new MyArraySizeException(
                "В массиве не четыре ряда, а "  + array.length);
        for (String[] innerArray : array) {
            if (innerArray.length != 4) throw new MyArraySizeException(
                    "В ряду " + row + " не четыре столбца, а " + innerArray.length);
            row++;
        }
        // А теперь переходим к содержимому
        row = index;
        for (String[] innerArray : array) {
            column = index;
            for (String cell : innerArray) {
                try {
                    sum += Integer.parseInt(cell);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(e.getMessage(), column, row);
                }
                column++;
            }
            row++;
        }

        return sum;
    }


}