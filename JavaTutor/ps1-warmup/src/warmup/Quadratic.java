package warmup;

import java.util.Set;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

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
        
        // deal with special cases 
        if ((a==0) && (b==0)) {                    // 0 0 1
                // we asserted earlier that c!=0, so the equation c=0 has no solution, return empty set
                return roots;
        }
        else if ((a==0) && (b!=0) && (c==0)) {     // 0 1 0 
            // bx = 0 only holds for x=0. 
            roots.add(0);
            return roots;            
        } 
        else if ((a==0) && (b!=0) && (c!=0)) {     // 0 1 1 
            // bx + c = 0 --> x = -c/b
            int root = -c / b;
            // only add the root as a solution if it's an integer
            if ( b*root+c == 0) {
                roots.add(root);
            }
            return roots;
        }
        else if ((a!=0) && (b==0) && (c==0)) {     // 1 0 0
            // ax^2 = 0 only holds for x=0
            roots.add(0);
            return roots;
        }
        else if ((a!=0) && (b==0) && (c!=0)) {     // 1 0 1
            // ax^2 + c = 0 --> only has solutions if (-c/a)>0
            int rightSide = -c/a;
            if ( 0 < rightSide ) {
                rightSide = (int)Math.sqrt(rightSide);
                // check it
                if (a*rightSide*rightSide + c == 0) {
                    roots.add(rightSide);
                    roots.add(-rightSide);
                }
            }
            return roots;
        }
        else if ((a!=0) && (b==0) && (c!=0)) {     // 1 1 0
            // ax^2 + bx = 0 ==> x(ax+b) ==> has solutions x=0, x=-b/a
            roots.add(0);
            int rightSide = -b/a;
            // check it
            if (a*rightSide+b == 0) {
                roots.add(rightSide);
            }
            return roots;
        }
        
                
        // attempt using the quadratic formula
        // x = (-b +/- sqrt(b^2 - 4ac)) / 2a
        
        double bminus4ac = ((double)b*(double)b - (4.0 * (double)a * (double)c));

        // deal with bminus4ac < 0
        if (bminus4ac < 0) {
            return roots;
        }
        
        double squareRootTerm = Math.sqrt(bminus4ac);
        
        double realRoot;
        double epsilon = 0.000001;
        double error;
        
        long r1 = ((long)-b + (long)squareRootTerm) / (2 * (long)a);
        // check that a real root wasn't truncated
        realRoot = ((double)-b + squareRootTerm) / (2.0 * (double)a);
        error = Math.abs(realRoot - (double)r1);
        if ( error < epsilon ) {
            roots.add((int)r1);
        }
        
        long r2 = ((long)-b - (long)Math.sqrt(bminus4ac)) / (2 * (long)a);
        // check that a real root wasn't truncated
        realRoot = ((double)-b - squareRootTerm) / (2.0 *(double)a);
        error = Math.abs(realRoot-(double)r2);
        if ((error < epsilon) && (r1 != r2)) {
            roots.add((int)r2);
        }
        
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
