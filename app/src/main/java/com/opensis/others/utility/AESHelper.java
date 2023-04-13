package com.opensis.others.utility;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
    static int KEY_SIZE = 256;
    static int IV_SIZE = 128;
    static String HASH_CIPHER = "AES/CBC/PKCS7Padding";
    static String AES = "AES";
    static String CHARSET_TYPE = "UTF-8";
    private static final String secretKeyInstance = "PBKDF2WithHmacSHA1";
    private static final int pswdIterations = 100;

    // Seriously crypto-js, what's wrong with you?


    /**
     * Encrypt
     * @param password passphrase
     * @param plainText plain string
     */
    public static String encrypt(String password,String plainText) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] saltBytes = generateSalt(16);
        byte[] key = new byte[KEY_SIZE/8];
        byte[] iv = generateSalt(IV_SIZE/8);



        SecretKeySpec keyS = new SecretKeySpec(getRaw(password, saltBytes), "AES");

        Cipher cipher = Cipher.getInstance(HASH_CIPHER);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keyS, ivSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes(CHARSET_TYPE));

        // Thanks kientux for this: https://gist.github.com/kientux/bb48259c6f2133e628ad
        // Create CryptoJS-like encrypted !

        byte[] sBytes = cipherText;
        byte[] b = new byte[sBytes.length + saltBytes.length + cipherText.length];
        System.arraycopy(sBytes, 0, b, 0, sBytes.length);
        System.arraycopy(saltBytes, 0, b, sBytes.length, saltBytes.length);
        System.arraycopy(cipherText, 0, b, sBytes.length + saltBytes.length, cipherText.length);

        byte[] bEncode = Base64.encode(b,Base64.DEFAULT);

        return new String(bEncode);
    }





    private static byte[] generateSalt(int length) {
        Random r = new SecureRandom();
        byte[] salt = new byte[length];
        r.nextBytes(salt);
        return salt;
    }

    private static byte[] getRaw(String plainText, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyInstance);
            KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt, pswdIterations, KEY_SIZE);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
