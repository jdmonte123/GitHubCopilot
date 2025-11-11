import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        
        System.out.println("=== Performance Comparison ===");
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println();
        
        // Test the optimized parallel method
        System.out.println("1. Testing optimized parallel method:");
        long start1 = System.currentTimeMillis();
        List<BigInteger> results1 = primeGenerator.getPrimes(100);
        long end1 = System.currentTimeMillis();
        System.out.println("Parallel method time: " + (end1 - start1) + " ms");
        System.out.println("First prime found: " + results1.get(0));
        System.out.println("Bit length of first prime: " + results1.get(0).bitLength());
        System.out.println();
        
        // Test the stream-based method
        System.out.println("2. Testing stream-based parallel method:");
        long start2 = System.currentTimeMillis();
        List<BigInteger> results2 = primeGenerator.getPrimesStream(100);
        long end2 = System.currentTimeMillis();
        System.out.println("Stream method time: " + (end2 - start2) + " ms");
        System.out.println("First prime found: " + results2.get(0));
        System.out.println("Bit length of first prime: " + results2.get(0).bitLength());
        System.out.println();
        
        // Test original method for comparison (smaller sample size due to slowness)
        System.out.println("3. Testing original method (smaller sample for comparison):");
        long start3 = System.currentTimeMillis();
        primeGenerator.getPrimesOriginal(10); // Only 10 primes for comparison
        long end3 = System.currentTimeMillis();
        System.out.println("Original method time (10 primes): " + (end3 - start3) + " ms");
        System.out.println("Estimated time for 100 primes: " + ((end3 - start3) * 10) + " ms");
        System.out.println();
        
        // Performance summary
        System.out.println("=== Performance Summary ===");
        System.out.println("Executor-based parallel (100 primes): " + (end1 - start1) + " ms");
        System.out.println("Stream-based parallel (100 primes): " + (end2 - start2) + " ms");
        System.out.println("Original method (estimated 100 primes): " + ((end3 - start3) * 10) + " ms");
        
        long fastest = Math.min(end1 - start1, end2 - start2);
        long originalEstimate = (end3 - start3) * 10;
        double speedup = (double) originalEstimate / fastest;
        
        System.out.println("Fastest optimized method: " + fastest + " ms");
        System.out.println("Speedup vs original: " + String.format("%.2fx", speedup));
    }
}