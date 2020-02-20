import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class TestPasswordHacking {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = newFixedThreadPool(3);
        while (true) {
            Future<Boolean> future = executor.submit(new HashProcessor());
            if (future.get()) {
                future.cancel(true);
                executor.shutdown();
                break;
            }
        }
        executor.shutdown();
    }
}

