public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        Main c = new Main();
        c.second();
        c.first();
    }

    void replace(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    void first() {
        // 1) Создают одномерный длинный массив
        float[] arr = new float[size];
        // 2) Заполняют этот массив единицами
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)1;
        }
        // 3) Засекают время выполнения
        long a = System.currentTimeMillis();
        // 4) Проходят по всему массиву и для каждой ячейки считают новое значение
        replace(arr);
        // 5) Проверяется время окончания метода System.currentTimeMillis()
        System.currentTimeMillis();
        // 6) В консоль выводится время работы
        System.out.println("1 thread: " + (System.currentTimeMillis() - a));
    }

    void second() {
        // 1) Создают одномерный длинный массив
        float[] arr = new float[size];
        // 2) Заполняют этот массив единицами
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)1;
        }
        // 3) Засекают время выполнения
        long a = System.currentTimeMillis();
        float[] part1 = new float[h];
        float[] part2 = new float[h];

        // 4) Проходят по всему массиву и для каждой ячейки считают новое значение
        // Разбивка массива
        System.arraycopy(arr, 0, part1, 0, h);
        System.arraycopy(arr, h, part2, 0, h);

        // Поток 1
        Thread t1 = new Thread(() -> {
            replace(part1);
        });
        t1.start();
        // Поток 2
        replace(part2);
        // Ждём поток 1
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Объединение массива
        System.arraycopy(part1, 0, arr, 0, h);
        System.arraycopy(part2, 0, arr, h, h);

        // 5) Проверяется время окончания метода System.currentTimeMillis()
        System.currentTimeMillis();
        // 6) В консоль выводится время работы
        System.out.println("2 threads: " + (System.currentTimeMillis() - a));
    }
}





