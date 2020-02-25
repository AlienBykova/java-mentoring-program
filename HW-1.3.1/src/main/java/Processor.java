import java.util.concurrent.Callable;

public class Processor implements Callable<String> {

    private int wordLength;
    private int lastIndexToIterate = 0;
    private Word wordToIterate;
    private static volatile boolean stop;

    public Processor(Word wordToIterate, int lastIndexToIterate) {
        this.wordToIterate = wordToIterate;
        this.lastIndexToIterate = lastIndexToIterate;
        stop = false;
    }

    public Processor(int wordLength) {
        this.wordLength = wordLength;
        stop = false;
    }


    private String resolvePassword() {
        while (!stop) {
            if (wordToIterate == null)
                wordToIterate = new Word(wordLength);
            do {
                if (wordToIterate.isPassword()) {
                    stop();
                    return wordToIterate.toString();
                }
            } while (wordToIterate.tryIterate(lastIndexToIterate) && !stop);
            return "";
        }
        return "";
    }

    private void stop() {
        stop = true;
    }

    @Override
    public String call() {
        return resolvePassword();
    }

}
