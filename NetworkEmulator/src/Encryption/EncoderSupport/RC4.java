package Encryption.EncoderSupport;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RC4 {
    public static Byte @NotNull [] encrypt(File file) throws IOException {
        int[] keyin = new int[256];
        int[] key = new int[44];
        StringBuilder encr = new StringBuilder();
        FileWriter fw = new FileWriter(file);
        for(int i = 0; i < key.length; i++){
            key[i] = (int) Math.round(Math.random());
        }
        for(int x = 0; x < keyin.length; x++){
            keyin[x] = x;
        }
        int rand = 0;
        for(int x = 0; x < 256; x++){
            rand = (rand + keyin[x] + key[x%key.length]) % 256;
            keyin[x] = keyin[rand];
        }

        int key1 = 0;
        int key2 = 0;
        int keyindex;
        int loop = 0;
        ArrayList<Integer> keyset = new ArrayList<>();
        int temp;
        while(loop < 257){
            key1 = (key1 + 1) % 256;
            key2 = (key2 + keyin[key1]) % 256;
            temp = keyin[key1];
            keyin[key1] = keyin[key2];
            keyin[key2] = temp;
            keyindex = (keyin[key1] + keyin[key2]) % 256;
            keyset.add(keyin[keyindex]);
            loop++;
        }
        ArrayList<Byte> cipher = new ArrayList<>();
        int pos = 0;
        Scanner filein = new Scanner(file);
        while(filein.hasNextByte()){
            Byte b = filein.nextByte();
            cipher.add((byte) (b ^ keyset.get(pos % keyset.size())));
            pos++;
        }
        for(Byte b : cipher){
            fw.write(b);
        }
        fw.close();
        Byte[] bytekey = new Byte[key.length];
        for(int i = 0; i < key.length; i++){
            bytekey[i] = (byte) key[i];
        }
        return bytekey;
    }
    public static void decrypt(File file, ArrayList<Byte> key) throws IOException {
        FileWriter fw = new FileWriter(file);
        Scanner filein = new Scanner(file);
        ArrayList<Byte> plain = new ArrayList<>();
        int pos = 0;
        while(filein.hasNextByte()){
            Byte b = filein.nextByte();
            plain.add((byte) (b ^ key.get(pos % key.size())));
            pos++;
        }
        for(Byte b : plain){
            fw.write(b);
        }
        fw.close();
    }
}
