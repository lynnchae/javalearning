package org.lynn.consistentHash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class Name : org.lynn.consistentHash
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/20 16:07
 */
public class ConsistentHashFunction {

    private static byte[] md5(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var6) {
            throw new IllegalStateException(var6.getMessage(), var6);
        }
        md5.reset();
        byte[] bytes;
        try {
            bytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException(var5.getMessage(), var5);
        }
        md5.update(bytes);
        return md5.digest();
    }

    private static long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[0 + number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

}
