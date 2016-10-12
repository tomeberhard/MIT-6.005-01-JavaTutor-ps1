package warmup;

import java.util.Set;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        assert(!((a==0 && (b==0) && (c==0))));
        
        Set<Integer> roots = new HashSet<Integer>();
        
        // TODO: deal with a == 0
        
        // attempt using the quadratic formula
        // x = (-b +/- sqrt(b^2 - 4ac)) / 2a
        
        
        double bminus4ac = ((double)b*(double)b - (4.0 * (double)a * (double)c));

        // TODO: deal with bminus4ac < 0
        
        long r1 = ((long)-b + (long)Math.sqrt(bminus4ac)) / (2 * (long(a));
        long r2 = ((long)-b - (long)Math.sqrt(bminus4ac)) / (2 * (long(a));

        roots.add(r1);
        roots.add(r2);
        
        return roots;
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 3);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
