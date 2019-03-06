class MyArrayDataException extends Exception {
    MyArrayDataException(String message, int column, int row) {
        super(
                "Невозомжно преобразовать данные в ячейке (" + column + "," + row + ") " +
                        "ожидается число, а получили: " + message
        );
    }
}
