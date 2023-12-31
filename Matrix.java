
final public class Matrix {
	
	// Fields
	
    private final int M;             // number of rows
    private final int N;             // number of columns
    public final double[][] data;   // M-by-N array

    // Constructors

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on another one, padded with zeros & identifying value (KDV)
    public Matrix(int M, int N, Matrix A, int identifyingValue) {
        
    	this.M = M;
        this.N = N;
        this.data = new double[M][N];
        
        int MN = Math.min(M, N);
        for (int i = 0; i < MN; i++)
        	this.data[i][i] = identifyingValue;
      
        int M0 = Math.min(M, A.M);
        int N0 = Math.min(N, A.N);
        for (int i = 0; i < M0; i++)
            for (int j = 0; j < N0; j++)
                    this.data[i][j] = A.data[i][j];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
            	this.data[i][j] = data[i][j];
    }
    
    // create coordinate matrix (row) from FractionalAddress (KDV)
 	public Matrix(FractionalAddress address){
 		this.M = 1;
 		this.N = Cube.DIMENSION + 1;
 		this.data = new double[M][N];
 		for(int i = 0; i < Cube.DIMENSION; i++)
 			this.data[0][i] = address.getCoordinate(i);
 		this.data[0][Cube.DIMENSION] = 1;
 	}

    // create coordinate matrix (row) from Address3D (KDV)
 	public Matrix(Address3D address){
 		this.M = 1;
 		this.N = Cube.DIMENSION + 1;
 		this.data = new double[M][N];
 		for(int i = 0; i < Cube.DIMENSION; i++)
 			this.data[0][i] = address.getCoordinate(i);
 		this.data[0][Cube.DIMENSION] = 1;
 	}

    // copy constructor
    private Matrix(Matrix A) { this(A.data); }

    
    // Functions
    
    public int getM() {
        return this.M;
    }

    
    public int getN() {
        return this.N;
    }

    
    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.random();
        return A;
    }

    
    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }
    
    
    // create and return matrix with attached array (KDV)
//    public Matrix attachArray(double[] array) {
//        if (array.length < M) throw new RuntimeException("Illegal array dimension.");
//        Matrix A = new Matrix(M, N + 1);
//        for (int i = 0; i < M; i++){
//            for (int j = 0; j < N; j++)
//                A.data[i][j] = this.data[i][j];
//            A.data[i][N] = array[i];
//        }
//        return A;
//    }

//    // create and return matrix 1-by-(M+1) from matrix (KDV)
//    public Matrix nPlus1Column(int i) {
//        Matrix C = new Matrix(N + 1, 1);
//        for (int j = 0; j < N; j++) 
//        	C.data[j][0] = data[i][j];
//        C.data[N][0] = 1;
//        return C;
//    }

    
    // swap rows i and j
    public void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    
    // transition row (KDV)
    public Matrix transitionRow(Matrix transitionMatrix) {
		return this.times(transitionMatrix);
    }

    
    // create point from row matrix (KDV)
    public Point createPoint() {
    	Point point = new Point(0, 0, 0);
    	for(int i = 0; i < Cube.DIMENSION; i++)
    		point.setCoordinate(i, this.data[0][i]);
    	return point;
    }

    
    // swap columns i and j in identity matrix (KDV)
    public Matrix swapIdentity(int i, int j) {
        Matrix A = this;
    	for(int w = 0; w < Math.min(M, N); w++){
    		double temp = A.data[i][w];
    		A.data[i][w] = A.data[j][w];
    		A.data[j][w] = -temp;
    	}
    	return A;
    }
    
    
    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    
    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }


     // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    
    // does A = B exactly?
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    
    // return C = A * B
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }
   

//    // return Y = A * X (KDV)
//    public Point times_Vector(Vector vector�) {
//    	Matrix columnX = new Matrix(Cube.DIMENSION, 1);
//		columnX.data[0][0] = vector�.dx;
//		columnX.data[1][0] = vector�.dy;
//		columnX.data[2][0] = vector�.dz;
//		Matrix columnY = this.times(columnX);
//		return new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);
//	}


    // return x = A^-1 b, assuming A is square and has full rank
    public Matrix solve(Matrix rhs) {
        if (M != N || rhs.M != N || rhs.N != 1)
            throw new RuntimeException("Illegal matrix dimensions.");

        // create copies of the data
        Matrix A = new Matrix(this);
        Matrix b = new Matrix(rhs);

        // Gaussian elimination with partial pivoting
        for (int i = 0; i < N; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;
            A.swap(i, max);
            b.swap(i, max);

            // singular
            if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix is singular.");

            // pivot within b
            for (int j = i + 1; j < N; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

            // pivot within A
            for (int j = i + 1; j < N; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < N; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }

        // back substitution
        Matrix x = new Matrix(N, 1);
        for (int j = N - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < N; k++)
                t += A.data[j][k] * x.data[k][0];
            x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
        }
        return x;

    }

    
    // print matrix to standard output
    public void show() {
        for (int i = 0; i < M; i++) {
//            for (int j = 0; j < N; j++)
//                StdOut.printf("%9.4f ", data[i][j]);
//            StdOut.println();
        }
    }

}
