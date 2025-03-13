package Encryption.EncoderSupport;

import org.jetbrains.annotations.NotNull;

/**
 * Creates a matrix given either two {@code Integers} for dimensions or a {@code Byte} matrix.
 */
public class Matrix {
    private Byte[][] bytematrix;
    public Matrix(int a, int b){
        this.bytematrix = new Byte[a][b];
    }
    public Matrix(Byte[][] matrix){
        this.bytematrix = matrix;
    }
    public Matrix(@NotNull Matrix matrix){
        this.bytematrix = matrix.getMatrix();
    }


    /**
     * Replaces a value at a given cell with a given {@code int} value. Denoted as matrix[col][row].
     * @param row {@code int} Row value
     * @param col {@code int} Column value
     * @param val {@code int} Item to Replace
     */
    public void setByte(int row, int col, Byte val){
        bytematrix[row][col] = val;
    }

    public void addByte(int loc, Byte val, MatrixType mt){
        if(mt == MatrixType.COLUMN){
            for(int x = 0; x < bytematrix[val].length; x++){
                if(bytematrix[val][x] != null){
                    bytematrix[val][x] = val;
                    return;
                }
            }
            Byte[][] temp = bytematrix;
            bytematrix = new Byte[bytematrix.length+1][bytematrix[0].length];
            for(int r = 0; r < temp.length; r++){
                System.arraycopy(temp[r], 0, bytematrix[r], 0, temp[r].length);
            }
            bytematrix[bytematrix.length-1][loc] = val;
        }
        else if(mt == MatrixType.ROW) {
            for(int x = 0; x < bytematrix[val].length; x++){
                if(bytematrix[val][x] != null){
                    bytematrix[val][x] = val;
                    return;
                }
            }
            Byte[][] temp = bytematrix;
            bytematrix = new Byte[bytematrix.length][bytematrix[0].length+1];
            for(int r = 0; r < temp.length; r++){
                System.arraycopy(temp[r], 0, bytematrix[r], 0, temp[r].length);
            }
            bytematrix[loc][bytematrix[loc].length-1] = val;
        }
    }
    /**
     * Replaces all values with null
     */
    public void clearMatrix(){
        for(Byte[] r : bytematrix){
            for(Byte c : r){
                c = null;
            }
        }
    }

    /**
     * Returns {@code Byte} matrix. All unfilled spaces are buffered with {@code null}.
     * @return {@code Byte Matrix}
     */
    public Byte[][] getMatrix() {
        return bytematrix;
    }

    /**
     * Returns a {@code Byte} at a given cell location (index 0).
     * @param r {@code int} row
     * @param c {@code int} column
     * @return {@code Byte} value at cell.
     */
    public Byte getByte(int r, int c){
        return bytematrix[r][c];
    }

    /**
     * Checks if cell is empty ({@code null}).
     * @param r {@code int} row
     * @param c {@code int} column
     * @return {@code true} if cell is null, {@code false} otherwise.
     */
    public boolean isEmpty(int r, int c){
        return bytematrix[r][c] == null;
    }

    /**
     * Checks if table is empty (only comprised of {@code null} values).
     * @return {@code true} if table is empty, {@code false} otherwise.
     */
    public boolean isEmptyTable(){
        for(Byte[] r : bytematrix){
            for(Byte c : r){
                if(c != null) return false;
            }
        }
        return true;
    }

    public String toString(){
        int max = 0;
        int current;
        for(Byte[] a: bytematrix){
            max = Math.max(a.length, max);
        }
        StringBuilder sb = new StringBuilder();
        for(Byte[] a: bytematrix){
            current = a.length;
            sb.append('{');
            for (Byte i : a) {
                sb.append(i);
                sb.append(", ");
            }
            if(current < max){
                sb.append("-, ".repeat(max - current));
            }
            sb.replace(sb.length()-2, sb.length(), "");
            sb.append("}\n");
        }
        return sb.toString();
    }
}