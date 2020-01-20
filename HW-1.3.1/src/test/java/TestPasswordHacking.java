import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestPasswordHacking {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
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

