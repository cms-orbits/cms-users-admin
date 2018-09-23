package com.joelgtsantos.cmsusers.entity;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.joelgtsantos.cmsusers.security.ApplicationProperties;
import com.jossemargt.cookietwist.tornado.transform.TornadoCookieCodec;
import com.jossemargt.cookietwist.tornado.transform.impl.V2TornadoCookieCodec;

import net.razorvine.pickle.Opcodes;

public class CookieGenerator {
	private String contestSlug;
	private String b64CookieSecret;
	private long epochNow;
	@Autowired
	private ApplicationProperties appProperties;
	private TornadoCookieCodec cookieCoder;

	public CookieGenerator() {
	}

	@PostConstruct
	public void buildCookieEngine() {
		this.epochNow = Instant.now().getEpochSecond();
		
		//Get app properties
		this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(this.appProperties.getCookieSecret()));
		this.contestSlug = this.appProperties.getContestSlug();
		
		cookieCoder = V2TornadoCookieCodec.builder().withSecretKey(b64CookieSecret).build();
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
			bos.write(newLine);
			bos.write(Opcodes.PUT);
			bos.write(utf8Bytes("0"));
			bos.write(newLine);
			bos.write(Opcodes.UNICODE);
			bos.write(utf8Bytes(password));
			bos.write(newLine);
			bos.write(Opcodes.PUT);
			bos.write(utf8Bytes("1"));
			bos.write(newLine);
			bos.write(Opcodes.FLOAT);
			bos.write(utf8Bytes(timestamp));
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
		byte[] pickledHashBytes = pickle0dumpsCMS(user, password, String.valueOf(this.epochNow));
		
		String pickledHashStr = new String(pickledHashBytes, StandardCharsets.UTF_8);
		
		Cookie signedCookie = cookieCoder.encodeCookie(new Cookie(contestSlug + "_login", pickledHashStr));
		return signedCookie;
	}
}
