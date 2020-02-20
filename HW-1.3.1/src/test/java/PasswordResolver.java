import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.time.temporal.ChronoUnit.SECONDS;

public class PasswordResolver {
    private static final int MAX_WORD_LENGTH = 6;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String answer = "";
        LocalTime start = LocalTime.now();
        ExecutorService pool = Executors.newFixedThreadPool(16);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 1; i < MAX_WORD_LENGTH / 2 + 1; i++) {
            Processor p = new Processor(i);
            Future<String> f = pool.submit(p);
            futures.add(f);
        }
        for (int i = 1; i < MAX_WORD_LENGTH / 2 + 1; i++) {
            Word firstPart = new Word(i);

            do {
                Word secondPart = new Word(MAX_WORD_LENGTH / 2);
                Word currentWord = new Word(firstPart, secondPart);
                Processor p = new Processor(currentWord, i);
                Future<String> f = pool.submit(p);
                futures.add(f);
            } while (firstPart.tryIterate());
        }

        for (Future<String> f : futures) {
            if (!"".equals(f.get())) {
                answer = f.get();
            }
        }
        pool.shutdown();
        System.out.println("Answer: " + answer);

        System.out.println("Time taken in seconds: " + start.until(LocalTime.now(), SECONDS));
    }
}
