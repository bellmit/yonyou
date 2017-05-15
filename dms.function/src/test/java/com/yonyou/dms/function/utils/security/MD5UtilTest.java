package com.yonyou.dms.function.utils.security;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;


public class MD5UtilTest {

    @Test
    public void testValidPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertTrue(MD5Util.validPassword("123456", MD5Util.getEncryptedPwd("123456")));
    }

    @Test
    public void testGetEncryptedPwd() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(MD5Util.getEncryptedPwd("123456"));
    }

}
