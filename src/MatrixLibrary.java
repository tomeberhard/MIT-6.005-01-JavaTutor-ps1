public class MatrixLibrary {
    //
    // Public interface
    //
    
    /** Mat represents an immutable matrix product. */
    public interface Mat {
        /** @return true only if this expression produces an identity matrix */
        public boolean isIdentity();
        /** @return an equivalent expression that may compute faster */
        public Mat optimize();
        
        /** @return the product of all the scalars in this expression */
        public Mat scalars();

        /** @return the product of all the matrices in this expression.
         * times(scalars(), matrices()) is equivalent to this expression. */
        public Mat matrices();
    }      

    /** identity for all matrix computation */
    public static final Mat I = new Identity();
    
    /** @return a matrix product consisting of just the scalar value */
    public static Mat make(double value) {
        return new Scalar(value);
    }
    
    /** @return a matrix product consisting of just the matrix given */
    public static Mat make(double[][] array) {
        return new Array(array);
    }
    
    
    /** @return a product of two matrix expressions.  Requires m1 and m2 to be dimension-compatible,
     *  i.e. either m1.#cols = m2.#rows, or either m1 or m2 is scalar. */
    public static Mat times(Mat m1, Mat m2) {
        if (m1.isIdentity()) return m2;
        else if (m2.isIdentity()) return m1;
        else return new Product(m1, m2);        
    }
    
    
    //
    // Private implementation
    //
    
    // Datatype definition:
    //   MatExpr = Identity + Scalar(double) + Array2D(double[][]) + Product(MatExpr, MatExpr)
    
    private static class Identity implements Mat {
        public Identity() {
        }
        public Mat optimize() { return this; }
        public boolean isIdentity() { return true; }
        public Mat scalars() { return this; }
        public Mat matrices() { return this; }
        public String toString() {
            return "I";
        }
    }
    
    private static class Scalar implements Mat {
        final double value;
        // rep invariant:
        //    true
        
        public Scalar(double value) {
            this.value = value;
        }
        public Mat optimize() { return this; }
        public boolean isIdentity() { return value == 1; }
        public Mat scalars() { return this; }
        public Mat matrices() { return I; }
        public String toString() {
            return "" + value;
        }
    }
    
    private static class Array implements Mat {
        final double[][] array;
        // rep invariant:
        //    array.length > 0 && array[i] are all equal nonzero length
        
        public Array(double[][] array) {
            this.array = array;  // danger! why?
        }
        public Mat optimize() { return this; }
        public boolean isIdentity() { 
            for (int row = 0; row < array.length; row++) {
                for (int col = 0; col < array[row].length; ++col) {
                    double expected = (row == col) ? 1 : 0;
                    if (array[row][col] != expected) return false;
                }
            }
            return true;
        }
        public Mat scalars() { return I; }
        public Mat matrices() { return this; }
        public String toString() {
            return "[" + array.length + "x" + array[0].length + "]";
        }
    }
    
    private static class Product implements Mat {
        final Mat m1;
        final Mat m2;
        // rep invariant:
        //    m1's column count == m2's row count or m1 is scalar or m2 is scalar
        
        public Product(Mat m1, Mat m2) {
            this.m1 = m1;
            this.m2 = m2;
        }
        public boolean isIdentity() { 
            return m1.isIdentity() && m2.isIdentity();
        }
        public Mat optimize() {
            return times(scalars(), matrices());
        }
        public Mat scalars() {
            return times(m1.scalars(), m2.scalars());
        }
        public Mat matrices() {
            return times(m1.matrices(), m2.matrices());
        }
        public String toString() {
            return "(" + m1.toString() + " * " + m2.toString() + ")";
        }
    }

    public static void main(String[] args) {
        Mat m;
        
        m = times(make(5), make(new double[200][50]));
        System.out.println(m);
        System.out.println(m.optimize());

        m = times(make(5), times(make(new double[200][50]), make(3)));
        System.out.println(m);
        System.out.println(m.optimize());
    }
}