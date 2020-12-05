package com.e.pkugrouper.Managers;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtils {
    private PublicKey publickey;
    public PublicKey getPublicKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public String encrypto(String text) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publickey);
            byte secretBytes[] = cipher.doFinal(text.getBytes());
            String secretText = Base64.getEncoder().encodeToString(secretBytes);
            return secretText;
        } catch (Exception e) {
            throw new RuntimeException("encrypto failed!");
        }
    }

    public RSAUtils(){
        try {
            publickey = getPublicKey("public_key.der");
        } catch (Exception e) {
            throw new RuntimeException("get public_key failed");
        }
    }
}
