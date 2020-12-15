package com.e.pkugrouper.Managers;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtils {
    private PublicKey publickey;
    private byte[] keyBytes = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3,
            -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -67, 94, -78, 86, 113, 102,
            -29, -48, -72, 67, 7, 51, 120, -69, 1, 14, 44, 15, -44, 36, -87, -126, -110,
            70, -17, -107, 88, 13, -2, -89, -94, -35, -17, -29, 84, 90, 113, 71, -96, -9,
            127, 59, 33, -98, -2, 80, 52, 30, 62, 4, -97, -21, -115, 21, -101, 111, 16,
            -119, -47, -40, -13, 63, 27, 11, 75, 87, 73, -7, -80, -16, 106, 6, 86, 103,
            -40, 77, 92, 4, 83, -85, -126, -76, -69, 28, 58, 58, 94, 99, 24, 63, 78, 119,
            -84, 57, -97, -101, -79, -55, -95, 36, -9, -9, -99, -67, -116, 12, -122, -40,
            72, -60, 47, -18, 0, 52, 105, -38, -109, 82, -70, -36, 50, 45, 125, -37, 80,
            -103, -57, 103, 2, 3, 1, 0, 1};
    public PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    public byte[] getKeyBytes(){
        return keyBytes;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public RSAUtils(){
        try {
            publickey = getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("get public_key failed");
        }
    }
}
