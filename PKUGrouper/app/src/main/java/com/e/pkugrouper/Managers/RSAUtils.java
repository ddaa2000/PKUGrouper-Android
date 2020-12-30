package com.e.pkugrouper.Managers;
import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


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
    private byte[] public_keyBytes = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -56, 109, -128, -23, 45, -52, -115, -104, -33, 85, 60, 69, 55, 52, -55, 101, -78, 117, 36, 98, -120, -11, -122, -97, 1, 127, -81, -52, 125, 10, -51, -11, -120, -2, 58, 124, -119, 124, 61, 125, -13, 90, 100, -108, 42, -115, 117, 95, 35, -6, -23, 113, -75, 77, 81, -95, -27, -128, 127, 70, 44, 112, -44, -81, 117, 112, 40, 115, -13, 76, 93, 63, -102, -60, -21, -4, -1, -36, 15, -38, 95, -55, 18, -114, 46, 85, 104, -82, -92, 14, -98, -87, -89, 57, 36, 28, 63, -28, -105, 66, -56, -29, 127, 66, 51, 2, 68, -103, 80, -97, -101, 51, 22, -99, -3, 33, -6, -34, -15, -94, -98, 25, -31, 85, 95, -97, -110, 103, 2, 3, 1, 0, 1};
    private byte[] private_keyBytes = {48, -126, 2, 120, 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4, -126, 2, 98, 48, -126, 2, 94, 2, 1, 0, 2, -127, -127, 0, -56, 109, -128, -23, 45, -52, -115, -104, -33, 85, 60, 69, 55, 52, -55, 101, -78, 117, 36, 98, -120, -11, -122, -97, 1, 127, -81, -52, 125, 10, -51, -11, -120, -2, 58, 124, -119, 124, 61, 125, -13, 90, 100, -108, 42, -115, 117, 95, 35, -6, -23, 113, -75, 77, 81, -95, -27, -128, 127, 70, 44, 112, -44, -81, 117, 112, 40, 115, -13, 76, 93, 63, -102, -60, -21, -4, -1, -36, 15, -38, 95, -55, 18, -114, 46, 85, 104, -82, -92, 14, -98, -87, -89, 57, 36, 28, 63, -28, -105, 66, -56, -29, 127, 66, 51, 2, 68, -103, 80, -97, -101, 51, 22, -99, -3, 33, -6, -34, -15, -94, -98, 25, -31, 85, 95, -97, -110, 103, 2, 3, 1, 0, 1, 2, -127, -127, 0, -64, 116, 117, -66, 114, 93, -30, -15, -110, 50, 52, 103, 122, 53, 83, -42, 53, 96, 74, -119, -37, 93, 119, -31, 34, -3, 74, -1, -98, 106, 75, -114, -93, -9, 10, -114, 31, 46, -113, -107, 107, -95, -74, -58, -105, 125, -79, -32, -93, -102, 1, -93, 80, 38, 104, 102, 127, 11, -66, 42, -47, -19, -98, 43, 17, -3, 28, -69, 126, 78, 64, 30, 62, 14, -30, 33, -49, -106, 74, 121, -109, 64, 68, -123, 61, 32, 116, -88, -110, -116, -49, -10, -91, -23, -29, 125, 54, 42, -87, -36, 18, -3, 99, -69, -33, -109, 56, -109, -41, -60, 65, 76, 58, -56, 3, 60, 0, 88, -109, -9, -21, 23, 123, 85, 69, -127, 62, 81, 2, 65, 0, -24, -125, 65, 86, 25, -91, 91, 124, 8, -46, 65, -121, -113, 75, 54, -56, -2, -62, 34, -36, 71, 60, -33, 38, 56, -99, -61, -40, -78, 13, 11, -3, -2, -108, -24, -32, 14, 73, -34, -102, 121, 19, 102, -67, 43, 82, -65, 119, -5, 93, -114, 72, -3, -64, -65, 42, -61, 26, 39, 63, -96, 27, 2, -5, 2, 65, 0, -36, -84, -119, 40, -55, -9, -105, -83, 35, 108, 111, -36, 59, 59, -41, -83, 91, 24, -72, -5, 112, 2, -106, 77, 97, 57, 120, -126, -57, 118, 61, 127, -83, 97, -16, 54, 19, 47, -12, 52, -2, -79, -67, 117, 61, 104, 40, -110, -37, 114, -80, 108, -123, 94, -99, -19, 65, 4, -37, -25, 65, 102, 50, -123, 2, 65, 0, -120, -95, 48, -112, -106, -53, 107, -127, -20, 113, 25, 30, -66, -98, 117, 24, 17, 84, 37, 70, 9, -6, -124, 42, -94, -108, 90, -108, -120, -76, 79, -6, -74, 81, -20, -78, 84, -45, 37, -94, -30, 72, -5, 83, 104, 20, -36, -4, 117, 82, 89, -99, 28, -73, -39, -34, 23, -3, -45, 88, 121, 13, -58, 103, 2, 64, 121, -106, -97, 100, 88, 20, 18, 0, -107, -6, 19, -112, 87, 26, -37, -11, 106, -78, 46, -118, 107, -25, 33, 5, -75, -62, 66, -62, 92, 64, -84, 22, 3, 100, 105, 68, -126, 65, -13, -34, -105, 79, 59, -62, -77, 121, 6, -110, 20, 84, 23, 29, 84, 71, 19, -31, 59, -25, -1, 122, 102, 48, 119, 57, 2, 65, 0, -34, -80, -40, 49, -24, -122, -20, 85, -22, -119, -17, -14, 23, 93, -63, -54, 3, -69, -52, -23, 92, -98, -39, 110, -14, 70, 100, -55, 8, -83, -57, 8, 118, 20, 93, -66, -112, 87, 59, 44, -48, -49, -101, -70, 26, -56, -40, -113, -60, 27, -72, 93, 28, -33, 82, 84, -70, 17, 2, -11, 104, 80, -50, 83};

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(public_keyBytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(private_keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypto(String text) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publickey);
            byte text_byte[] = text.getBytes();
            int inputLength = text_byte.length;
            int MAX_ENCRYPT_BLOCK = 117;
            // 标识
            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(text_byte, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(text_byte, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            String secretText = Base64.getEncoder().encodeToString(resultBytes);
            return secretText;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("encrypto failed!");
        }
    }
    //RSA/ECB/PKCS1Padding
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypto(String text) {
        try {
            byte text_byte[] = Base64.getDecoder().decode(text);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privatekey);
            int inputLength = text_byte.length;
            int MAX_DECRYPT_BLOCK = 128;
            // 标识
            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(text_byte, offSet, MAX_DECRYPT_BLOCK);
                    offSet += MAX_DECRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(text_byte, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            return new String(resultBytes);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("decrypto failed!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RSAUtils(){
        try {
            publickey = getPublicKey();
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("get public key failed");
        }

        try {
            privatekey = getPrivateKey();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("get private key failed");
        }
    }
}
