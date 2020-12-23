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
    private byte[] keyBytes = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -56, 109, -128, -23, 45, -52, -115, -104, -33, 85, 60, 69, 55, 52, -55, 101, -78, 117, 36, 98, -120, -11, -122, -97, 1, 127, -81, -52, 125, 10, -51, -11, -120, -2, 58, 124, -119, 124, 61, 125, -13, 90, 100, -108, 42, -115, 117, 95, 35, -6, -23, 113, -75, 77, 81, -95, -27, -128, 127, 70, 44, 112, -44, -81, 117, 112, 40, 115, -13, 76, 93, 63, -102, -60, -21, -4, -1, -36, 15, -38, 95, -55, 18, -114, 46, 85, 104, -82, -92, 14, -98, -87, -89, 57, 36, 28, 63, -28, -105, 66, -56, -29, 127, 66, 51, 2, 68, -103, 80, -97, -101, 51, 22, -99, -3, 33, -6, -34, -15, -94, -98, 25, -31, 85, 95, -97, -110, 103, 2, 3, 1, 0, 1};
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
