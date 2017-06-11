package com.javarush.task.task32.task3211;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/* 
Целостность информации
*/

public class Solution {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {

        byte[] outStreamBuffer = byteArrayOutputStream.toByteArray();
        byte[] md5Buffer = md5.getBytes();

        MessageDigest md5Disgest = MessageDigest.getInstance("MD5");

        md5Disgest.update(outStreamBuffer);
        byte[] outStreamBufferMD5Digest = md5Disgest.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < outStreamBufferMD5Digest.length; i++)
            sb.append(Integer.toString((outStreamBufferMD5Digest[i] & 0xff) + 0x100, 16).substring(1));

        return sb.toString().equals(md5);
    }
}
