import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    // Constructs a term given the associated query string, having weight 0.
    public Term(String query) {
        //check for valid query
        if(query == null)
            throw new NullPointerException("Null query");

        //initialize if valid
        this.query = query;
        this.weight = 0;
    }

    // Constructs a term given the associated query string and weight.
    public Term(String query, long weight) {
        //check if query is valid
        if(query == null)
            throw new NullPointerException("Null query");

        //initialize if valid
        this.query = query;
        this.weight = weight;
    }

    // Returns a string representation of this term.
    public String toString() {
        return String.format("%d\t%s", weight, query);
    }

    // Returns a comparison of this term and other by query.
    public int compareTo(Term other) {
        return this.query.compareTo(other.query);
    }

    // Returns a comparator for comparing two terms in reverse order of their weights.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Returns a comparator for comparing two terms by their prefixes of length r.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    // Reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            //build in java method return 0 if equal, -1 if less, and 1 if greater than
            return Long.compare(w.weight, v.weight);
        }
    }

    // Prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private final int r;

        // Constructs a new prefix order given the prefix length.
        PrefixOrder(int r) {
            //r cannot be less than zero
            if(r < 0)
                throw new IllegalArgumentException("Illegal r");
            //initialize r if valid
            this.r = r;
        }

        // Returns a comparison of terms v and w by their prefixes of length r.
        public int compare(Term v, Term w) {
            String a = v.query.substring(0, Math.min(r, v.query.length()));
            String b = w.query.substring(0, Math.min(r, w.query.length()));
            //String method compareTo is already built into java
            return a.compareTo(b);
        }
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
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
