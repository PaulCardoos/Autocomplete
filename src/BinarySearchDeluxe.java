import java.util.Arrays;
import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if(a == null || key == null || c == null)
            throw new NullPointerException("Null a, key, or c");
        //now we implement a regular binary search, Key[] needs to be sorted
        int low = 0;
        int high = a.length - 1;
        int mid;
        //well save the match key index
        int index = -1;
        while(low <= high){
            mid = low + (high - low) / 2;
            if(c.compare(key, a[mid]) == 0){
                index = mid;
                //this would mean key is first element in the list
                if(index == 0) return index;
                //prev element is not the same so we found first element
                if(c.compare(key, a[index - 1]) != 0) return index;
                //change high bound and the last index still exists
                high = mid + 1;
            }
            else if(c.compare(key, a[mid]) < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if(a == null || key == null || c == null)
            throw new NullPointerException("Null a, key, or c");
        //now we implement a regular binary search, Key[] needs to be sorted
        int low = 0;
        int high = a.length - 1;
        int mid;
        //well save the match key index
        int index = -1;
        while(low <= high){
            mid = low + (high - low) / 2;
            if(c.compare(key, a[mid]) == 0){
                index = mid;
                //this would mean key is last element in the list
                if(index == a.length - 1) return index;
                //next element is not the same as key so we found last element
                if(c.compare(key, a[index + 1]) != 0) return index;
                //change low bound and the last index still exists
                low = mid;
            }
            else if(c.compare(key, a[mid]) < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
