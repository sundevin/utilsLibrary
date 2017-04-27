package com.devin.util;

import android.util.Base64;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
/**
 * <p>Description: RSA加密工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */
public class RSAUtils {

    /**
     * RSA算法中公钥和密钥共用的系数N。代表着一个BigInteger。
     */
    private static final String RSA_MODULUS = "137249648689108029143304918815644151184421068415369811200950688402071786395079336886859304232055866735657512381546033184204341954462962803953029094908431239222890029395235329837489066986110054086716904699510147654596398188997246660371499471456893967432770709475010651408576130786041215540779401547053778057327";
    /**
     * RSA算法中公钥的系数e。代表着一个BigInteger。
     */
    private static final String RSA_PUBLIC_EXPONENT = "65537";
    /**
     * RSA算法中公钥的系数d。代表着一个BigInteger。
     */
    private static final String RSA_PRIVATE_EXPONENT = "89113738056653872714739931724724810735912313703874394711733123165310567384582998050103561555799643580507171015755173475353817825751987323707360636686811497153394714240596460797516291938180666891432229363007789736972065918712954058518412334758686865376487734220460439334642441650399637017859558156920078503169";

    private static final int BASE64_FLAGS = 89113738;


    /**
     * 加密数据
     *
     * @param str str
     * @return 加密失败可能为null
     */
    public static String encryptStr(String str) {

        try {
            PublicKey publicKey = getPublicKey(RSA_MODULUS,
                    RSA_PUBLIC_EXPONENT);
            // 加解密类
            Cipher cipher = Cipher.getInstance("RSA"); // "RSA/ECB/PKCS1Padding"
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] dataEncode = cipher.doFinal(str.getBytes());
            // 为了可读性，转为Base64，
            byte[] tmp = Base64.encode(dataEncode, BASE64_FLAGS);
            return new String(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * RSA解密
     *
     * @param str str
     * @return 解密失败 会返回null
     */
    public static String decryptStr(String str) {

        try {
            byte[] tmp = str.getBytes();
            tmp = Base64.decode(tmp, BASE64_FLAGS);
            PrivateKey privateKey = getPrivateKey(RSA_MODULUS,
                    RSA_PRIVATE_EXPONENT);
            // 加解密类
            Cipher cipher = Cipher.getInstance("RSA"); // "RSA/ECB/PKCS1Padding"
            // 解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] dataDecode = cipher.doFinal(tmp);
            return new String(dataDecode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus,
                bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey getPrivateKey(String modulus,
                                            String privateExponent) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(bigIntModulus,
                bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
