import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Word {
    private static final char MIN_LETTER_CODE = 97;
    private static final char MAX_LETTER_CODE = 122;
    private static final String HASHED_PASSWORD = "4fd0101ea3d0f5abbe296ef97f47afec";

    private int length;
    private char[] letters;

    public Word(int length) {
        this.length = length;
        letters = new char[length];
        Arrays.fill(letters, MIN_LETTER_CODE);
    }

    public Word(Word first, Word second) {
        this.length = first.length + second.length;
        letters = ArrayUtils.addAll(first.letters, second.letters);
    }

    public boolean tryIterate(int lastIndexToIterate) {
        for (int i = length - 1; i >= lastIndexToIterate; i--) {
            if (letters[i] < MAX_LETTER_CODE) {
                letters[i]++;
                return true;
            }
            if (letters[i] == MAX_LETTER_CODE) {
                if (i == lastIndexToIterate) {
                    return false;
                } else {
                    for (int j = i; j < length; j++) {
                        letters[j] = MIN_LETTER_CODE;
                    }
                }
            }
        }
        return false;
    }

    public boolean tryIterate() {
        return tryIterate(0);
    }

    public boolean isPassword() {
        String hashedWord = HashCalculator.hash(toString());
        return HASHED_PASSWORD.equals(hashedWord);
    }

    public String toString() {
        return String.valueOf(letters);
    }


}
