// This code mines one block at a time, this was used on my own machine with the following specs:
// Intel Core i5 10300h 
// 16GB RAM
// GTX 1650
// 1TB SSD
// Windows 10

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockchainMine {

    // Constants from the assignment
    private static final String PREVIOUS_HASH = ""; // <<<<<< HERE INPUT PREVIOUS HASH YOU WANT TO MINE
    // Example: "0000000000000000000a3b2c4f5e6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3"
    private static final String MINER_PSEUDONYM = "basedminerrr";
    private static final int DIFFICULTY = 26; // <<<<<< HERE INPUT DIFFICULTY YOU WANT TO MINE

    private static final AtomicBoolean found = new AtomicBoolean(false);
    private static long validNonce = 0;
    private static String validHash = null;

    public static void main(String[] args) {
        System.out.println("Starting mining with difficulty " + DIFFICULTY);
        long startTime = System.nanoTime();

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Start a miner process
        for (int i = 0; i < cores; i++) {
            executor.submit(new MinerTask());
        }

        // Wait until a valid block is found
        while (!found.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdownNow();

        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime) / 1e9;

        String block = validHash + MINER_PSEUDONYM + validNonce;
        System.out.println("Block mined!");
        System.out.println("Block: " + block);
        System.out.printf("Time taken: %.2f seconds%n", durationSeconds);
    }

    // Checks if the hash has at least 'difficulty' leading zero bits.
    private static boolean meetsDifficulty(byte[] hash, int difficulty) {
        int bitsChecked = 0;
        for (byte b : hash) {
            for (int i = 7; i >= 0; i--) {
                if (((b >> i) & 1) != 0) {
                    return bitsChecked >= difficulty;
                }
                bitsChecked++;
                if (bitsChecked >= difficulty)
                    return true;
            }
        }
        return true;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    // The mining task that runs on each process
    private static class MinerTask implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 algorithm not available - errorer");
            }
            while (!found.get() && !Thread.currentThread().isInterrupted()) {
                long nonce = random.nextLong();
                String data = PREVIOUS_HASH + MINER_PSEUDONYM + nonce;
                byte[] hashBytes = digest.digest(data.getBytes());
                if (meetsDifficulty(hashBytes, DIFFICULTY)) {
                    if (found.compareAndSet(false, true)) {
                        validNonce = nonce;
                        validHash = bytesToHex(hashBytes);
                    }
                    break;
                }
            }
        }
    }
}
