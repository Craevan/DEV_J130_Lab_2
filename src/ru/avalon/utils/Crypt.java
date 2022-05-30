package ru.avalon.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

public class Crypt {

    public static byte[] crypt(int mode, byte[] value, String secret) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, secretKey);
        return cipher.doFinal(value);
    }

    public static HashMap<String, String> getCredentials() {
        File credentialsData = new File("src/ru/avalon/resources/pwd.key");
        String login;
        String password;
        if (credentialsData.exists()) {
            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in))) {
                //TODO
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try (BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in))) {
                //TODO
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //todo
        return new HashMap<>();
    }
}
