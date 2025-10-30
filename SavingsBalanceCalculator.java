import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SavingsBalanceCalculator {
    /**
     * Calculates the balance of a savings account with daily compounding interest.
     * @param principal Initial deposit amount
     * @param annualRate Annual interest rate (e.g., 0.0201 for 2.01%)
     * @param startDate Date of account opening
     * @param endDate Date to calculate balance for
     * @return Final balance
     */
    public static double calculateBalance(double principal, double annualRate, LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        double dailyRate = Math.pow(1 + annualRate, 1.0/365) - 1;
        return principal * Math.pow(1 + dailyRate, days);
    }

    public static void main(String[] args) {
        double principal = 1000.0;
        double annualRate = 0.0201;
        LocalDate startDate = LocalDate.of(2024, 3, 16);
        LocalDate endDate = LocalDate.of(2025, 10, 26); // today
        double balance = calculateBalance(principal, annualRate, startDate, endDate);
        System.out.printf("Balance as of %s: $%.2f\n", endDate, balance);
    }
}
