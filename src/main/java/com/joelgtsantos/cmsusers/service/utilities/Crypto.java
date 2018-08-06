package com.joelgtsantos.cmsusers.service.utilities;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.LoggerFactory;

import com.joelgtsantos.cmsusers.entity.Participation;
import com.joelgtsantos.cmsusers.entity.User;


public class Crypto {
	public static org.slf4j.Logger log = LoggerFactory.getLogger(Participation.class);
	
	/*
	 * 
	 * Encrypt the password with Cipher IV
	 */
	public static String encrypt(String password) {
		String newPassword = "";
		
		try {
			String pt = password;
			String key = "";//config.getKey();
			byte[] ivKey;
			int ivl = 16;

			//Creates 16bites array and filling with 0x00
			byte[] pt_pad = new byte[16];
			byte[] n1 = ("00").getBytes();
			byte[] n2 = new byte[16 - (pt.length() + 1) % 16];
			Arrays.fill(n2, (byte) 0);
			System.arraycopy(pt.getBytes(), 0, pt_pad, 0, pt.getBytes().length);
			System.arraycopy(n1, 0, pt_pad, pt.getBytes().length, n1.length);
			System.arraycopy(n2, 0, pt_pad, pt.getBytes().length + 1, n2.length);
			
			
			// Get random key
			ivKey = getRandomKey();
			IvParameterSpec iv = new IvParameterSpec(ivKey);
			
			// Hashing key
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        digest.update(key.getBytes("UTF-8"));
	        byte[] keyBytes = new byte[16];
	        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
	        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
			
	        // Encrypt
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			
			byte[] encrypted = cipher.doFinal(pt_pad);
			
			//Combine IV and encrypted part
			byte[] c = new byte[ivl + encrypted.length];
			System.arraycopy(ivKey, 0, c, 0, ivl);
			System.arraycopy(encrypted, 0, c, ivl, encrypted.length);
			
			newPassword = Base64.getEncoder().encodeToString(c).replace('+', '-').replace('/', '_').replace('=', '.');
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newPassword;
	}

	public static byte[] getRandomKey() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[16];		
		random.nextBytes(bytes);
		return bytes;
	}

	public static String encryptPlain(String password) {		
		return "plaintext:" + password;
	}
	
	public static boolean isValidPassword(String password, User user) {
		boolean isValid = false;
		
		if (password.contentEquals(user.getPassword()))
			isValid = true;
		
		return isValid;
	}
}
