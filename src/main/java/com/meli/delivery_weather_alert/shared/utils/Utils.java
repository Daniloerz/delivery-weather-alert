package com.meli.delivery_weather_alert.shared.utils;

import com.meli.delivery_weather_alert.shared.exceptions.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utils {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String input, String internalKey) {
        try {
            SecretKeySpec key = new SecretKeySpec(internalKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new CryptoException("Error al encriptar", e);
        }
    }

    public static String decrypt(String encryptedInput, String internalKey) {
        try {
            SecretKeySpec key = new SecretKeySpec(internalKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CryptoException("Error al desencriptar", e);
        }
    }
}
