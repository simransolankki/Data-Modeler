package com.thinking.machines.tmdmodel.utilities;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class Encryptor
{
 private static SecretKeySpec secretKey;
 private static byte[] key;
 public static void setKey(String myKey)
 {
 MessageDigest sha = null;
 try {
 key = myKey.getBytes("UTF-8");
 sha = MessageDigest.getInstance("SHA-1");
 key = sha.digest(key);
 key = Arrays.copyOf(key, 16);
 secretKey = new SecretKeySpec(key, "AES");
 }
 catch (NoSuchAlgorithmException e) {
 e.printStackTrace(); // remove after testing
 }
 catch (UnsupportedEncodingException e) {
 e.printStackTrace(); // remove after testing
 }
 }
 public static String encrypt(String strToEncrypt, String secret)
 {
 try
 {
 setKey(secret);
System.out.println("idhr1");
 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
System.out.println("idhr2");
 cipher.init(Cipher.ENCRYPT_MODE, secretKey);
System.out.println("idhr3");
System.out.println(Base64.getEncoder());
 return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
 }
 catch (Exception e)
 {
 System.out.println("Error while encrypting: " + e.toString()); // remove after testing
 }
 return null;
 }
 public static String decrypt(String strToDecrypt, String secret)
 {
 try
 {
 setKey(secret);
 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
 cipher.init(Cipher.DECRYPT_MODE, secretKey);
 return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
 }
 catch (Exception e)
 {
 System.out.println("Error while decrypting: " + e.toString()); // remove after testing
 }
 return null;
 }
}
