import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class PrimeGenerator {

    private static final int PRIME_BITS = 2000;

    public List<BigInteger> getPrimes(int size) {
        System.out.println("About to find " + size + " primes using parallel processing.");

        // Use parallel processing for better performance
        int numThreads = Math.min(size, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        List<Future<BigInteger>> futures = new ArrayList<>();
        
        try {
            // Submit tasks to generate primes in parallel
            for (int i = 0; i < size; i++) {
                futures.add(executor.submit(new PrimeTask()));
            }
            
            // Collect results
            List<BigInteger> primes = new ArrayList<>(size);
            for (Future<BigInteger> future : futures) {
                primes.add(future.get());
            }
            
            System.out.println("Found all " + primes.size() + " primes.");
            return primes;
            
        } catch (Exception e) {
            throw new RuntimeException("Error generating primes", e);
        } finally {
            executor.shutdown();
        }
    }
    
    // Callable task for generating a single prime
    private static class PrimeTask implements Callable<BigInteger> {
        @Override
        public BigInteger call() {
            // Use ThreadLocalRandom for better performance in multithreaded environment
            // Generate a random odd number to start with (even numbers > 2 can't be prime)
            BigInteger candidate;
            do {
                byte[] randomBytes = new byte[PRIME_BITS / 8];
                ThreadLocalRandom.current().nextBytes(randomBytes);
                candidate = new BigInteger(1, randomBytes);
                
                // Ensure the number is odd (set the least significant bit)
                candidate = candidate.setBit(0);
                // Ensure it's the right bit length (set the most significant bit)
                candidate = candidate.setBit(PRIME_BITS - 1);
                
            } while (candidate.bitLength() != PRIME_BITS);
            
            // Find the next probable prime with higher certainty
            return candidate.nextProbablePrime();
        }
    }
    
    // Alternative method using streams for even better performance in some cases
    public List<BigInteger> getPrimesStream(int size) {
        System.out.println("About to find " + size + " primes using parallel streams.");
        
        List<BigInteger> primes = Collections.synchronizedList(new ArrayList<>());
        
        // Use parallel stream for concurrent execution
        java.util.stream.IntStream.range(0, size)
            .parallel()
            .forEach(i -> {
                BigInteger prime = generateOptimizedPrime();
                primes.add(prime);
            });
        
        System.out.println("Found all " + primes.size() + " primes.");
        return primes;
    }
    
    // Optimized prime generation method
    private BigInteger generateOptimizedPrime() {
        // Pre-generate a good starting candidate
        BigInteger candidate = generateOptimizedCandidate();
        return candidate.nextProbablePrime();
    }
    
    // Generate a well-formed candidate number
    private BigInteger generateOptimizedCandidate() {
        byte[] randomBytes = new byte[PRIME_BITS / 8];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        
        BigInteger candidate = new BigInteger(1, randomBytes);
        
        // Ensure it's odd (primes > 2 are odd)
        candidate = candidate.setBit(0);
        
        // Ensure it has the correct bit length
        candidate = candidate.setBit(PRIME_BITS - 1);
        
        // Additional optimization: avoid numbers divisible by small primes
        while (isDivisibleBySmallPrimes(candidate)) {
            candidate = candidate.add(BigInteger.valueOf(2)); // Add 2 to keep it odd
        }
        
        return candidate;
    }
    
    // Quick check against small primes to avoid obvious composites
    private boolean isDivisibleBySmallPrimes(BigInteger number) {
        int[] smallPrimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        
        for (int prime : smallPrimes) {
            if (number.remainder(BigInteger.valueOf(prime)).equals(BigInteger.ZERO)) {
                return true;
            }
        }
        return false;
    }
    
    // Original method for comparison (SLOW - for benchmarking only)
    public List<BigInteger> getPrimesOriginal(int size) {
        System.out.println("About to find " + size + " primes using original method.");

        List<BigInteger> primes = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            primes.add(new BigInteger(2000, new java.util.Random()).nextProbablePrime());
        }

        System.out.println("Found all " + primes.size() + " primes.");
        return primes;          
    }
}