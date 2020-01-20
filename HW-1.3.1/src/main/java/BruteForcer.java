import static java.lang.String.valueOf;
import static java.util.Arrays.copyOf;

public class BruteForcer {

    private static final String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static char[] alphabet = LOWER_CASE_ALPHABET.toCharArray();
    private static int alphabetLength = alphabet.length;
    private static int[] indices = new int[1];
    private static char[] combination = new char[1];

    public static String computeNextCombination() {
        combination[0] = alphabet[indices[0]];
        String nextCombination = valueOf(combination);

        if (indices[0] < alphabetLength - 1) {
            indices[0]++;
        } else {
            for (int i = 0; i < indices.length; i++) {
                if (indices[i] < alphabetLength - 1) {
                    indices[i]++;
                    combination[i] = alphabet[indices[i]];
                    break;
                } else {
                    indices[i] = 0;
                    combination[i] = alphabet[indices[i]];

                    if (i == indices.length - 1) {
                        indices = copyOf(indices, indices.length + 1);
                        combination = copyOf(combination, combination.length + 1);
                        combination[combination.length - 1] = alphabet[indices[indices.length - 1]];
                        break;
                    }
                }
            }
        }

        return nextCombination;
    }


}
