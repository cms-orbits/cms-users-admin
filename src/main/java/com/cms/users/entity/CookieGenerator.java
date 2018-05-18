package com.cms.users.entity;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.time.Instant;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.cms.users.ApplicationProperties;
import com.jossemargt.cookietwist.tornado.transform.TornadoCookieCodec;
import com.jossemargt.cookietwist.tornado.transform.impl.V2TornadoCookieCodec;

import net.razorvine.pickle.Opcodes;

public class CookieGenerator {
	private String contestSlug = "new_contest1";
	private String b64CookieSecret;
	@Autowired
	private ApplicationProperties appProperties;
	private TornadoCookieCodec cookieCoder;

	public CookieGenerator() {
	}

	@PostConstruct
	public void populateMovieCache() {
		//Get app properties
		
	}

	private byte[] utf8Bytes(String data) {
		return data.getBytes(StandardCharsets.UTF_8);
	}

	byte[] pickle0dumpsCMS(String username, String password, String timestamp) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] newLine = utf8Bytes("\n");
		try {
			bos.write(Opcodes.MARK);
			bos.write(Opcodes.UNICODE);
			bos.write(utf8Bytes(username));
			System.out.println(username);
			bos.write(newLine);
			bos.write(Opcodes.PUT);
			bos.write(utf8Bytes("0"));
			bos.write(newLine);
			bos.write(Opcodes.UNICODE);
			bos.write(utf8Bytes(password));
			System.out.println(password);
			bos.write(newLine);
			bos.write(Opcodes.PUT);
			bos.write(utf8Bytes("1"));
			bos.write(newLine);
			bos.write(Opcodes.FLOAT);
			bos.write(utf8Bytes(timestamp));
			System.out.println(timestamp);
			bos.write(newLine);
			bos.write(Opcodes.TUPLE);
			bos.write(Opcodes.PUT);
			bos.write(utf8Bytes("2"));
			bos.write(newLine);
			bos.write(Opcodes.STOP);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public Cookie generateCookie(String user, String password) {
		long epochNow = Instant.now().getEpochSecond();
		
		byte[] pickledHashBytes = pickle0dumpsCMS(user, password, String.valueOf(epochNow));
		
		String pickledHashStr = new String(pickledHashBytes, StandardCharsets.UTF_8);
		
		this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(this.appProperties.getCookieSecret()));
		
		System.out.println("--------- ");
		System.out.println(pickledHashStr);
		System.out.println("--------- ");
		
		try {
			cookieCoder = V2TornadoCookieCodec.builder().withTimestamp(epochNow).withSecretKey(b64CookieSecret).build();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		Cookie signedCookie = cookieCoder.encodeCookie(new Cookie(contestSlug + "_login", pickledHashStr));
		Cookie ck = new Cookie(signedCookie.getName(), "\"" + signedCookie.getValue() + "\"");
		//System.out.println(cookieCoder.);
		return ck;
	}
}
