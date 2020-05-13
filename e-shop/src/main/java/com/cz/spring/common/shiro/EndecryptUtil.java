package com.cz.spring.common.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;


public class EndecryptUtil {
    public static final String DEFAULT_SALT = "easyweb";

    /**
     * 
     *
     * @param password 
     */
    public static String encrytMd5(String password) {
        return new Md5Hash(password).toHex();
    }

    /**
     * 
     *
     * @param password       
     * @param salt           
     * @param hashIterations 
     * @return
     */
    public static String encrytMd5(String password, String salt, int hashIterations) {
        String password_cipherText = new Md5Hash(password, salt, hashIterations).toHex();
        return password_cipherText;
    }

    public static String encrytMd5(String password, int hashIterations) {
        return encrytMd5(password, DEFAULT_SALT, hashIterations);
    }

}
