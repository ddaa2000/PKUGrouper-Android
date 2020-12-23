package com.e.pkugrouper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtils {
    private PublicKey publickey;
    private PrivateKey privatekey;
    private byte[] public_keyBytes;
    private byte[] private_keyBytes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PublicKey getPublicKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public PrivateKey getPrivateKey(String filename) throws Exception {
        Path path = Paths.get(filename);
        byte[] keyBytes = Files.readAllBytes(path);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypto(String text) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publickey);
            byte secretBytes[] = cipher.doFinal(text.getBytes());
            String secretText = Base64.getEncoder().encodeToString(secretBytes);
            return secretText;
        } catch (Exception e) {
            throw new RuntimeException("encrypto failed!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypto(String text) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privatekey);
            byte originBytes[] = cipher.doFinal(text.getBytes());
            String originText = Base64.getEncoder().encodeToString(originBytes);
            return originText;
        } catch (Exception e) {
            throw new RuntimeException("decrypto failed!");
        }
    }

    public byte[] getpublicKeyBytes(){
        return public_keyBytes;
    }
    public byte[] getprivateKeyBytes() { return private_keyBytes;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RSAUtils(){
        try {
            publickey = getPublicKey("public_new.der");
        } catch (Exception e) {
            throw new RuntimeException("get public key failed");
        }

        try {
            privatekey = getPrivateKey("private_new.der");
        } catch (Exception e) {
            throw new RuntimeException("get private key failed");
        }
    }
}
