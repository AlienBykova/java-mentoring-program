import java.util.concurrent.Callable;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class HashProcessor implements Callable<Boolean> {

    private static final String PASSWORD = "4fd0101ea3d0f5abbe296ef97f47afec";

//    private String letterCombination;
//
//    public HashProcessor(String letterCombination) {
//        this.letterCombination = letterCombination;
//    }

    @Override
    public Boolean call() {
        String actual = BruteForcer.computeNextCombination();
        System.out.println("Processing combination ".concat(actual).concat(" from thread ").concat(Thread.currentThread().getName()));
        String md5Hex = md5Hex(actual);
        System.out.println("Combination ".concat(actual) + " is password: " + md5Hex.equals(PASSWORD));
        return md5Hex.equals(PASSWORD);
    }
}
