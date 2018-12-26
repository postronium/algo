package s12;

import java.util.List;

public class WavyListSearching {

    /** minLocation
     *
     * @param t a wavy list
     * @return index of the minimal element in the list
     */
    public static <E extends Comparable<E>> int minLocation(List<E> t) {
        final int n = t.size();
        int jump = n;
        int i = 0; // i is the "current location"; initially any index can be chosen
        while (true) {
            jump = (jump == 1) ? 1 : (jump / 2);
            int next = (i + 1) % n, prev = (i - 1 + n) % n;
            boolean prevIsLower = isLower(t, prev, i);
            boolean nextIsLower = isLower(t, next, i);

            if (prevIsLower && nextIsLower){
                if (isLower(t, prev, next)) i = prev;
                else i = next;
            }
            else if (prevIsLower) i = prev;
            else if (nextIsLower) i = next;
            else break;
            
        }
        return i;
    }

    public static <E extends Comparable<E>> boolean isLower(List<E> t, int i, int j){
        return t.get(i).compareTo(t.get(j)) < 0;
    }
}