package Encryption.AESEncoder;

import Encryption.EncoderSupport.Matrix;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class AESProtocol {
    public static void encrypt(File file) throws IOException {
        Scanner filein = new Scanner(file);
        ArrayList<Matrix> mlist = new ArrayList<>();
        Matrix matrix = new Matrix(4, 4);
        while(filein.hasNextLine()){
            byte[] byteset = filein.nextLine().getBytes();
            int pos = 0;
            boolean done = false;
            int span = byteset.length;
            while(pos < span){
                if (!matrix.isEmpty(3, 3)) {
                    mlist.add(matrix);
                    matrix.clearMatrix();
                }
                for(int x = 0; x < 4; x++){
                    for(int y = 0; y < 4; y++){
                        if(pos < span){
                            matrix.setByte(x, y, byteset[pos]);
                            pos++;
                        }
                        else{
                            done = true;
                            break;
                        }
                    }
                    if(done) break;
                }
                if(done) break;
            }
        }
        FileWriter fw = new FileWriter(file);
        for(Matrix m : mlist){
            for(int r = 0; r < 4; r++){
                for(int c = 0; c < 4; c++){
                    if(m.getByte(r, c) != null) fw.write(m.getByte(r, c));
                }
            }
        }
    }
    public static void decrypt(File file) throws FileNotFoundException {
        ArrayList<Matrix> mset = new ArrayList<>();
        Matrix m = new Matrix(4, 4);
        Scanner filein = new Scanner(file);
        while(filein.hasNextByte()){
            for(int x = 0; x < 4; x++){
                for(int y = 0; y < 4; y++){
                    if (filein.hasNextByte()){
                        m.setByte(x, y, filein.nextByte());
                    }
                }
            }
            mset.add(m);
        }
    }
    @Contract(pure = true)
    public static @NotNull BigInteger handshake(){
        return BigInteger.valueOf(3);
    }
}
