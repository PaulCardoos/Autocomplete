import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private final Term[] terms; //array of terms
    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        //data validation
        if(terms == null)
            throw new NullPointerException("terms is null");
        //defensive copy of terms
        this.terms = Arrays.copyOf(terms, terms.length);
        //lexicographical sort of this.terms
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        //data validation
        if(prefix == null)
            throw new NullPointerException("prefix is null");

        Term term = new Term(prefix);

        //comparator object for binary search
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());

        //index of the first term that starts with prefix
        int firstIndex = BinarySearchDeluxe.firstIndexOf(this.terms, term, prefixOrder);
        int lastIndex = BinarySearchDeluxe.lastIndexOf(this.terms, term, prefixOrder);

        //number of keys that match the prefix
        int N = lastIndex - firstIndex + 1;

        //initialize array match of length N
        Term[] matches = new Term[N];

        //loop through all terms that start with the prefix and store in array named matches
        System.arraycopy(this.terms, firstIndex, matches, 0, N);

        //sort matches in reverse order of weight
        Arrays.sort(matches, Term.byReverseWeightOrder());

        return matches;

    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
