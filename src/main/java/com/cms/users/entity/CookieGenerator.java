package com.cms.users.entity;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Base64;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.razorvine.pickle.Opcodes;

public class CookieGenerator {
	private final String prefix;
	private final String suffix;
	private final String cookieSecret = "8e045a51e4b102ea803c06f92841a1fb";
	private final String b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(cookieSecret));

	String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();

		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	String calculateHMACSHA256(byte[] key, byte[] data)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);
		return toHexString(mac.doFinal(data));
	}

	private byte[] utf8Bytes(String data) {
		return data.getBytes(StandardCharsets.UTF_8);
	}
	
	byte[] pickle0dumpsCMS(String username, String password, String timestamp) {
	    ByteArrayOutputStream bos=new ByteArrayOutputStream();
	    byte[] newLine = utf8Bytes("\n");
	    try {
		    bos.write(Opcodes.MARK); bos.write(Opcodes.UNICODE); bos.write(utf8Bytes(username)); bos.write(newLine);
		    bos.write(Opcodes.PUT); bos.write(utf8Bytes("0")); bos.write(newLine);
		    bos.write(Opcodes.UNICODE); bos.write(utf8Bytes("plaintext:${password}")); bos.write(newLine);
		    bos.write(Opcodes.PUT); bos.write(utf8Bytes("1")); bos.write(newLine);
		    bos.write(Opcodes.FLOAT); bos.write(utf8Bytes(timestamp)); bos.write(newLine);
		    bos.write(Opcodes.TUPLE); bos.write(Opcodes.PUT); bos.write(utf8Bytes("2")); bos.write(newLine);
		    bos.write(Opcodes.STOP);
		    bos.flush();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return bos.toByteArray();
	}
	
	String tornadoSignedValueField(String token ) {
	    Formatter formatter = new Formatter();

	    formatter.format("%d:%s", token.length(), token);

	    return formatter.toString();
	}
	
	String tornadoCreateSignedValue(String secret, String name, String value, String timestamp) {
		String signature = "";
		String[] toSign = {"0", timestamp, name, value};
		
		for(String s:toSign){
			s = tornadoSignedValueField(s);
		 }
		
	    toSign[0] = "2";
	    toSign[toSign.length] = ""; 

	    String toSignStr = String.join("|", toSign);
	    
	    try {
	    	signature = calculateHMACSHA256(utf8Bytes(secret), utf8Bytes(toSignStr));
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    return toSignStr + signature;
	}
	
	public CookieGenerator(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;

		long epochNow = Instant.now().getEpochSecond();
		// String cookieCoder =
		// V2TornadoCookieCodec.builder().withTimestamp(epochNow).withSecretKey(b64CookieSecret).build();
		// String signedCookie = cookieCoder.encodeCookie(new Cookie
		// ("${contestSlug}_login" , pickledHashStr ))
	}

}
