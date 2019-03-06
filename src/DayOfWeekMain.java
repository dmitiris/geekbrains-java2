enum DayOfWeek {
    MONDAY(true), TUESDAY(true), WEDNESDAY(true),
    THURSDAY(true), FRIDAY(true),
    SATURDAY(false), SUNDAY(false);
    private boolean isWorkDay;

    DayOfWeek(boolean isWorkDay) {
        this.isWorkDay = isWorkDay;
    }

    public boolean isWorkDay() {
        return isWorkDay;
    }
}


public class DayOfWeekMain {

    public static void main(final String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.SUNDAY));
    }

    private static int getWorkingHours(DayOfWeek dayOfWeek) {
        int workingHours = 40;
        if(dayOfWeek.isWorkDay()) {
            return workingHours - dayOfWeek.ordinal() * 8;
        } else {
            System.out.println("Сейчас выходной: " + dayOfWeek.name());
            return 0;
        }


    }

//    public static int getWorkingHours(enum dayOfWeek) {
//
//    }
}