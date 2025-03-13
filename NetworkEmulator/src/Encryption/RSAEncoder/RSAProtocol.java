package Encryption.RSAEncoder;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class RSAProtocol {
    /**
     * Encrypts a given file using RSAEncoder.RSAProtocol.
     * @param filein File {@code Scanner}
     * @param e Encryption {@code BigInteger}
     * @param n Shared {@code BigInteger}
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> encrypt(@NotNull Scanner filein, BigInteger e, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        while(filein.hasNextLine()){
            String line = filein.nextLine();
            for(int x = 0; x < line.length(); x++){
                char c = line.charAt(x);
                BigInteger enc = BigInteger.valueOf(c).modPow(e, n); //c^e%n
                charset = new ArrayList<>();
                charset.add(enc);
                charset.add(n);
                al.add(charset);
            }
            //newline
            charset = new ArrayList<>();
            charset.add(BigInteger.valueOf(10).modPow(e, n));
            charset.add(n);
            al.add(charset);
        }
        al.remove(al.size()-1);
        filein.close();
        return al;
    }

    /**
     * Encrypts a line of text using RSAEncoder.RSAProtocol.
     * @param string {@code String} to encrypt
     * @param e {@code BigInteger} Public Key 1/2
     * @param n {@code BigInteger} Public Key 2/2
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> encrypt(@NotNull String string, BigInteger e, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        for(int x = 0; x < string.length(); x++) {
            char c = string.charAt(x);
            BigInteger enc = BigInteger.valueOf(c).modPow(e, n);
            charset = new ArrayList<>();
            charset.add(enc);
            charset.add(n);
            al.add(charset);
        }
        return al;
    }

    /**
     * Encrypts a series of integers using RSAEncoder.RSAProtocol.
     * @param i {@code ArrayList<BigInteger>} containing input integers
     * @param e {@code BigInteger} Public Key 1/2
     * @param n {@code BigInteger} Public Key 2/2
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> encrypt(@NotNull ArrayList<BigInteger> i, BigInteger e, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        for(BigInteger in : i) {
            BigInteger enc = in.modPow(e, n);
            charset = new ArrayList<>();
            charset.add(enc);
            charset.add(n);
            al.add(charset);
        }
        return al;
    }

    /**
     * Decrypts a given file using RSAEncoder.RSAProtocol.
     * @param filein Input File's {@code Scanner}
     * @param d {@code BigInteger} Private Key 1/2
     * @param n {@code BigInteger} Private Key 2/2
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> decrypt(@NotNull Scanner filein, BigInteger d, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        while(filein.hasNextLine()) {
            String line = filein.nextLine();
            for(int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                BigInteger dec = BigInteger.valueOf(c).modPow(d, n);
                charset = new ArrayList<>();
                charset.add(dec);
                charset.add(n);
                al.add(charset);
            }
            //newline
            charset = new ArrayList<>();
            charset.add(BigInteger.valueOf(10).modPow(d, n));
            charset.add(n);
            al.add(charset);
        }
        al.remove(al.size()-1);
        filein.close();
        return al;
    }
    /**
     * Decrypts a line of text using RSAEncoder.RSAProtocol.
     * @param string {@code String} to encrypt
     * @param d {@code BigInteger} Private Key 1/2
     * @param n {@code BigInteger} Private Key 2/2
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> decrypt(@NotNull String string, BigInteger d, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        for(int x = 0; x < string.length(); x++){
            char c = (x == string.length()-1) ? string.substring(x).charAt(0): string.substring(x, x+1).charAt(0);
            BigInteger dec = BigInteger.valueOf(c).modPow(d, n);
            charset = new ArrayList<>();
            charset.add(dec);
            charset.add(n);
            al.add(charset);
        }
        return al;
    }
    /**
     * Encrypts a series of integers using RSAEncoder.RSAProtocol.
     * @param i {@code ArrayList<BigInteger>} containing input integers
     * @param d {@code BigInteger} Private Key 1/2
     * @param n {@code BigInteger} Private Key 2/2
     * @return Encrypted ArrayList as an {@code ArrayList<ArrayList<BigInteger>>} formatted as (Character(as BigInteger), n). Example is [[23, 4], [251, 4]].
     */
    public ArrayList<ArrayList<BigInteger>> decrypt(@NotNull ArrayList<BigInteger> i, BigInteger d, BigInteger n) {
        ArrayList<ArrayList<BigInteger>> al = new ArrayList<>();
        ArrayList<BigInteger> charset;
        for(BigInteger integer : i){
            BigInteger dec = integer.modPow(d, n);
            charset = new ArrayList<>();
            charset.add(dec);
            charset.add(n);
            al.add(charset);
        }
        return al;
    }
}