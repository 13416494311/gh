package com.ruoyi.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {

    private static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMwSwaReHYXR/63yd18tTHhrEAb8qS5Pc3tsTZBBP" +
            "10MWZ4eeH4il8xhC4+GpYXuXpLuxKlJlYN69JJoUybaaQoVyWX+sD2Kic3Eoz8ovLuHAg33E0jU53HZ+4p8uknHlhnlJs0drdnVoG/" +
            "CjpD90tEDnu2aPoFP4LtPgbawuOrwIDAQAB";
    private static String privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIzBLBpF4dhdH/rfJ3Xy1MeGsQBvypLk" +
            "9ze2xNkEE/XQxZnh54fiKXzGELj4alhe5eku7EqUmVg3r0kmhTJtppChXJZf6wPYqJzcSjPyi8u4cCDfcTSNTncdn7iny6SceWGeUmzR" +
            "2t2dWgb8KOkP3S0QOe7Zo+gU/gu0+BtrC46vAgMBAAECgYEAgrC+NWrbh1z4W6Sl7CNbpCOQ55nlGr2UOdvvLJ1Xty9wujACHa5F3T++" +
            "uTCW9ZSvnvLdnWYad8nEbIRium8LbudT8MWSp431Awv+dg8LMenj1xuhybIOPvxMuaR76WPl5jQsOyoN5BYWXHlSxiky4DLYIAl+CaJi3" +
            "4vd2WNbMjECQQDuj2jwtOmQhGBfDzD625QlABZUO0rKV9eh2xwvQEqh3s2LGlGJ3/vBvaLVyxWW2hvLVwljR0ubHpTojxODdPvHAkEAlw" +
            "tbQlM5FCLvsdMCm5muLeLP8l3aXVf1yahXJruUvjlJdrNAm9EbLrffh6GZSBjZgJc1cGEMkMYO657Nu87F2QJBAK6olJ5W9gH2yVAPoT6" +
            "o8Oe+DpsQ5gsLETbRYus9WOb2ZuqHFFerg3kvTD7W9zGA8oSHbrofAr3JQCCcUB8Y0RUCQFSPZQ3irafaTzrMmT8ncF38BtL3PBLZ9Z5q" +
            "AjCK/6DnTXuIqvkQvhkPk7LJA5lbYBkfjilWC8TZPO0oQKGOBzECQQCIG/YviBWhCJJgOPaHdge6SV/d0vm4E1jFCnCH7FDd0TR0kF/pD" +
            "SoFoRdoXZdCLCEozlQ2C9fcgN4YqZ9OwDq3";

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        //genKeyPair();
        System.out.println(encrypt("dj",publicKey));
        System.out.println(encrypt("万登枫",privateKey));

    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str,String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str,String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
