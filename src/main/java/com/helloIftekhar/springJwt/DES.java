package com.helloIftekhar.springJwt;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

import static com.helloIftekhar.springJwt.service.JwtService.SECRET_KEY;

public class DES {

    static final String secretKey = SECRET_KEY; // Replace with your secret key
    private static final String DES_IV = "12345678";
    private static final String TRIPLE_DES_ALGORITHM = "DESede";
    private static final String TRIPLE_DES_CIPHER_ALGORITHM = "DESede/CBC/PKCS5Padding";


    public static String tripleDESEncrypt(String data) {
        try {
            // Triple DES encryption
            byte[] keyBytes = hexStringToByteArray(secretKey);
            DESedeKeySpec desedeKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(TRIPLE_DES_ALGORITHM);
            SecretKey desedeKey = secretKeyFactory.generateSecret(desedeKeySpec);
            Cipher desedeCipher = Cipher.getInstance(TRIPLE_DES_CIPHER_ALGORITHM);
            desedeCipher.init(Cipher.ENCRYPT_MODE, desedeKey, new IvParameterSpec(DES_IV.getBytes()));
            byte[] encryptedBytes = desedeCipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }


    // Helper method to convert a hexadecimal string to byte array
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        System.out.println(data);
        return data;
    }



    public static String tripleDESDecrypt(String encryptedData) {
        try {
            // Decode the encrypted data from Base64
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

            // Triple DES decryption
            byte[] keyBytes = hexStringToByteArray(secretKey);
            DESedeKeySpec desedeKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(TRIPLE_DES_ALGORITHM);
            SecretKey desedeKey = secretKeyFactory.generateSecret(desedeKeySpec);
            Cipher desedeCipher = Cipher.getInstance(TRIPLE_DES_CIPHER_ALGORITHM);
            desedeCipher.init(Cipher.DECRYPT_MODE, desedeKey, new IvParameterSpec(DES_IV.getBytes()));
            byte[] decryptedBytes = desedeCipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
