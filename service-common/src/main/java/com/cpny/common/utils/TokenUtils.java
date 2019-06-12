package com.cpny.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class TokenUtils {

	//加解密key
	private static final String ENCRYPT_KEY = "f4ba33c8e5a9e819d12237ca";

	//分隔符
	private static final String SEPARATOR = " ";

	/**
	 * 加密
	 * 
	 * @param content
	 *            待加密内容
	 * @return
	 */
	public static String encrypt(String content){
		try{
			content = content + SEPARATOR + System.nanoTime();
			KeyGenerator kgen=KeyGenerator.getInstance("AES");			
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(ENCRYPT_KEY.getBytes());
			kgen.init(128,secureRandom);
			SecretKey secretKey=kgen.generateKey();
			byte[] enCodeFormat=secretKey.getEncoded();
			SecretKeySpec secretKeySpec=new SecretKeySpec(enCodeFormat,"AES");
			Cipher cipher=Cipher.getInstance("AES");
			byte[] byteContent=content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
			byte[] byteRresult=cipher.doFinal(byteContent);
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<byteRresult.length;i++){
				String hex=Integer.toHexString(byteRresult[i]&0xFF);
				if(hex.length()==1){
					hex='0'+hex;
				}
				sb.append(hex.toUpperCase());
			}
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @return
	 */
	public static String decrypt(String content){
		if(content.length()<1){
			return null;
		}
		byte[] byteRresult=new byte[content.length()/2];
		for(int i=0;i<content.length()/2;i++){
			int high=Integer.parseInt(content.substring(i*2,i*2+1),16);
			int low=Integer.parseInt(content.substring(i*2+1,i*2+2),16);
			byteRresult[i]=(byte)(high*16+low);
		}
		try{
			KeyGenerator kgen=KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(ENCRYPT_KEY.getBytes());
			kgen.init(128,secureRandom);
			SecretKey secretKey=kgen.generateKey();
			byte[] enCodeFormat=secretKey.getEncoded();
			SecretKeySpec secretKeySpec=new SecretKeySpec(enCodeFormat,"AES");
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
			byte[] result=cipher.doFinal(byteRresult);
			return new String(result).split(SEPARATOR)[0];
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		String key = "login_cache_1";
		System.out.println(encrypt(key));
		System.out.println(decrypt(encrypt(key)));
	}

}