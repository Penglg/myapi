package com.lgp.myapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @description: 签名工具
 * @auther: 赖高鹏
 * @date: 2024/2/5 12:14
 */
public class SignUtils {

    /**
     * 生成签名
     * @param body body
     * @param secretKey 密钥
     * @return 签名
     */
    public static String getSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + '.' + secretKey;
        return md5.digestHex(content);
    }
}
