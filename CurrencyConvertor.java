// ...existing code...
public class CurrencyConvertor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java CurrencyConvertor <amount>");
            return;
        }
        try {
            double amount = Double.parseDouble(args[0]);
            usdToInr(amount);
            System.out.println("Converted to INR: " + usdToInr(amount));
            double usdToEur = 0.92; // example rate
            System.out.printf("USD %.2f = EUR %.2f%n", amount, amount * usdToEur);
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount");
        }
    }

/**
 * Converts USD to INR at a fixed rate.
 * @param usd The amount in US Dollars
 * @return The equivalent amount in Indian Rupees
 */
    public static double usdToInr(double usd) {
        double rate = 82.75; // example rate
        return usd * rate;
    }
}



