package com.helloIftekhar.springJwt;

import com.helloIftekhar.springJwt.service.JwtService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.helloIftekhar.springJwt.service.JwtService.SECRET_KEY;

public class AES {

    static final String secretKey = SECRET_KEY; // Replace with your secret key
    private static final String AES_ALGORITHM = "AES";
    private static final String DES_ALGORITHM = "DES";
    private static final String DES_CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    private static final String DES_IV = "12345678"; // You may change the initialization vector

    public static String encrypt(String data) {
        try {
            // AES encryption
            byte[] keyBytes = hexStringToByteArray(secretKey);
            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher aesCipher = Cipher.getInstance(AES_ALGORITHM);
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] aesEncryptedBytes = aesCipher.doFinal(data.getBytes());


            // DES encryption
            DESKeySpec desKeySpec = new DESKeySpec(secretKey.getBytes());
            SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey desKey = desKeyFactory.generateSecret(desKeySpec);
            Cipher desCipher = Cipher.getInstance(DES_CIPHER_ALGORITHM);
            desCipher.init(Cipher.ENCRYPT_MODE, desKey, new IvParameterSpec(DES_IV.getBytes()));
            byte[] desEncryptedBytes = desCipher.doFinal(aesEncryptedBytes);

            return Base64.getEncoder().encodeToString(desEncryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
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


    public static String decrypt(String encryptedData) {
        try {
            // Decode the encrypted data from Base64
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

            // DES decryption
            DESKeySpec desKeySpec = new DESKeySpec(secretKey.getBytes());
            SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey desKey = desKeyFactory.generateSecret(desKeySpec);
            Cipher desCipher = Cipher.getInstance(DES_CIPHER_ALGORITHM);
            desCipher.init(Cipher.DECRYPT_MODE, desKey, new IvParameterSpec(DES_IV.getBytes()));
            byte[] desDecryptedBytes = desCipher.doFinal(encryptedBytes);

            // AES decryption
            byte[] keyBytes = hexStringToByteArray(secretKey);
            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher aesCipher = Cipher.getInstance(AES_ALGORITHM);
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] aesDecryptedBytes = aesCipher.doFinal(desDecryptedBytes);

            return new String(aesDecryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
