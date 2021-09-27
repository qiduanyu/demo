package com.example.demo.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Coder {
    /**
     * @param text 明文
     * @param key 密钥
     * @return 密文
     */
    // 带秘钥加密
    public static String md5(String text, String key) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为:" + md5str);
        return md5str;
    }

    // 不带秘钥加密
    public static String md52(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        System.out.println("MD52加密后的字符串为:" + md5str + "\t长度：" + md5str.length());
        return md5str;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     */
    // 根据传入的密钥进行验证
    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5str = md5(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    /**
     * 去盐方法
     * @param password 前端传入密码
     * @param salt 盐
     * @return 去盐后md5加密的密码
     */
    public static String removeSalt(String password ,Integer salt) {
        StringBuffer result=new StringBuffer();
        String[] pwdChars=password .split("_") ;
        for(String e:pwdChars){
            int asc=Integer.parseInt(e);
            result.append((char)(asc^salt));
        }
        return result.toString();// md5 加密之后的值
    }

    //452_403_452_454_403_405_404_415_454_407_453_414_405_404_415_405_407_451_452_452_402_407_414_454_401_449_400_402_415_403_414_453_

    // 测试
    public static void main(String[] args) throws Exception {
        // String str =
        // "181115.041650.10.88.168.148.2665.2419425653_1";181115.040908.10.88.181.118.3013.1655327821_1
        String str = "181115.040908.10.88.181.118.3013.1655327821_1";
        System.out.println("加密的字符串：" + str + "\t长度：" + str.length());
        MD5Coder.md52(str);
        System.out.println("----------------------------");
        String s = removeSalt("452_403_452_454_403_405_404_415_454_407_453_414_405_404_415_405_407_451_452_452_402_407_414_454_401_449_400_402_415_403_414_453_", 423);
        System.out.println(s);
    }


}

