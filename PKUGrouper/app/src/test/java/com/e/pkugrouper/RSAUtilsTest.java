package com.e.pkugrouper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class RSAUtilsTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Test Begin!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test End!");
    }

    @Test
    public void en() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        RSAUtils rsaUtils = new RSAUtils();
        String password = "myx";
        String passwordRSA = rsaUtils.encrypto(password);
        String password_origin = rsaUtils.decrypto(passwordRSA);
        assertEquals(password, password_origin);
    }
}