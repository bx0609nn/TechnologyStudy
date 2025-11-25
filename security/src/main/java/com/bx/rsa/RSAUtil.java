package com.bx.rsa;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/11/25 9:36
 * @description RSA算法工具类
 */
public class RSAUtil {
    private static final String RSA_ALGORITHM = "RSA";

    private static final String CHARSET = "UTF-8";

    public static final String PWD_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIk6W/Mgb6GWCJaw40flNtA2uv8wYRX9rjdBVk9xI8fI1agBEsgmFJWj9qPNJg82l7XfO+odvdSjXrz6Nhe6wRD1gsAZVpI3Mie2xRppacwbCXeK3tXuvdQ45im/+UXAZX4G1hmSSONHHw3iB6w+eqAJo7HUS6IEsG6bT4iYHuUwIDAQAB";

    public static final String PWD_PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMiTpb8yBvoZYIlrDjR+U20Da6/zBhFf2uN0FWT3Ejx8jVqAESyCYUlaP2o80mDzaXtd876h291KNevPo2F7rBEPWCwBlWkjcyJ7bFGmlpzBsJd4re1e691DjmKb/5RcBlfgbWGZJI40cfDeIHrD56oAmjsdRLogSwbptPiJge5TAgMBAAECgYAzFgf9LozAXn5MAcxYamRtO5XN9qPxAO0LSaG5WYR3i3GlP0EgiQSmXioQsPUUZGrVVRSj1S3equwY54XBanJkYeOBwKAPNRyVBvsKzpiGwYFH3eAAKHX0NtvCNM6XYWuEphWcy3A5vrJ90ytVHtFGfF9cjCsP7p+1Zgus5nIxYQJBAPv36QWFeNAPrqy3BGFwI558oibZjUq4uQj/8IxCilKpHo3kIXtJTMem4PPrG2XNrZ3XYYL5D6rX4PptRcsm/ecCQQDLyTtAviqGncKlDX21fIWfAAmzIKjUPnr16OpqRiV1WfDY6YVMtbF17iT+IODWbVYMqc5x38FgadK4ufEpewa1AkA6o5NjaZCYO04Xe/BVDNf9RlwmVnL/dMX7RjUhL0spuZoWw4TXQFPSExA/M/QIWTmShlF3PadbxyngnhWkFcr5AkBCDgE51Co+pkZgb8YOnMVHK6D5Qh3XZkU/DndlaUVdC4FJHjCKE97o5f9xrECU4K3ivuve93NcCBE4tXTZSDp9AkEA+c4hCN9h1Zaa85Y43262BvNdO4lSeYDfn8Ydt7DQEk0jWcMQbbmTZZlIhC0Xa5xv1XruleIvnGnhohVdgMoiGg==";

    /*
     * 用于存储随机产生的公钥与私钥
     */
    private static Map<Integer, String> KEY_CACHE = new HashMap<>();

    /**
     * @param
     * @return
     * @description 随机生成公钥私钥密钥对
     */
    private static void generateKeyPair() throws NoSuchAlgorithmException {
        // 1.KeyPairGenerator 类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);

//        // 2.初始化密钥对生成器，密钥大小为 96-1024 位
//        keyPairGen.initialize(1024, new SecureRandom());
        // 2.或者初始化密钥对生成器，密钥大小指定为 1024 位
        keyPairGen.initialize(1024);

        // 3.生成一个密钥对，保存在 keyPair 中
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 4.得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 5.得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 6.得到私钥字符串
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("publicKeyString = " + publicKeyString);

        // 7.得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("privateKeyString = " + privateKeyString);

        // 8.将公钥和私钥保存到 Map
        KEY_CACHE.put(0, publicKeyString);
        KEY_CACHE.put(1, privateKeyString);
    }

    /**
     * @param data 明文字符串
     * @param publicKey Base64编码的公钥字符串
     * @return String Base64编码的密文字符串
     * @description 公钥加密
     */
    private static String encrypt(String data, String publicKey) throws Exception {
        // 1.将Base64编码的公钥字符串解码为字节数组
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        // 2.创建X.509公钥规范对象，封装公钥的字节数据
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        // 3.获取RSA算法的密钥工厂实例，KeyFactory用于在密钥规范和实际密钥对象之间进行转换
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        // 4.根据X.509规范生成RSA公钥对象，并转换为可以被Java使用的RSAPublicKey对象
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        // 5.创建RSA加密器
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        // 6.初始化为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 7.将明文字符串转换为UTF-8编码的字节数组，然后使用公钥执行RSA加密
        byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
        // 8.将加密后的字节数组转换为Base64编码的字符串
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * @param data Base64编码的密文字符串
     * @param privateKey privateKey Base编码的私钥字符串
     * @return String 明文
     * @description 私钥解密
     */
    private static String decrypt(String data, String privateKey) throws Exception {
        // 1.将密文字符串进行Base64解码得到密文字节数组
        byte[] inputByte = Base64.getDecoder().decode(data);
        // 2.将Base64编码的私钥字符串解码为字节数组
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        // 3.创建PKCS#8私钥规范对象，封装私钥的字节数据(与公钥的X.509格式对应，PKCS#8用于私钥)
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        // 4.获取RSA算法的秘钥工厂实例，KeyFactory用于在密钥规范和实际密钥对象之间进行转换
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        // 5.根据PKCS#8规范生成RSA私钥对象，并转换为可以被Java使用的RSAPrivateKey对象
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        // 6.创建RSA解密器
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        // 7.初始化为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 8.使用私钥执行RSA解密
        byte[] bytes = cipher.doFinal(inputByte);
        // 9.将解密后的字节数组按UTF-8编码转换为字符串并返回
        return new String(bytes, CHARSET);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        generateKeyPair();
        //明文
        String originData = "你好20948hishf987&*……&%_fas发所发生的飞";
        try {
            //加密得到密文
            String encryData = encrypt(originData, PWD_PUBLIC_KEY);
            //打印密文
            System.out.println("encryData = " + encryData);
            //对密文进行解密得到明文
            String decryData = decrypt(encryData, PWD_PRIVATE_KEY);
            //打印明文
            System.out.println("decryData = " + decryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
