import java.time.DayOfWeek;
import java.time.LocalDate;

public class Friday13Counter {
    /**
     * Counts how many times the 13th of the month fell on a Friday between two dates (inclusive).
     * @param startYear the starting year (inclusive)
     * @param endYear the ending year (inclusive)
     * @return number of Friday the 13ths
     */
    public static int countFriday13ths(int startYear, int endYear) {
        int count = 0;
        for (int year = startYear; year <= endYear; year++) {
            for (int month = 1; month <= 12; month++) {
                LocalDate date = LocalDate.of(year, month, 13);
                if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int result = countFriday13ths(2000, 2019);
        System.out.println("Number of Friday the 13ths from 2000 to 2019: " + result);
    }
}
