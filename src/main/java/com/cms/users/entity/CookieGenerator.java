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
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.cms.users.ApplicationProperties;
import com.jossemargt.cookietwist.tornado.transform.TornadoCookieCodec;
import com.jossemargt.cookietwist.tornado.transform.impl.V2TornadoCookieCodec;

import net.razorvine.pickle.Opcodes;


public class CookieGenerator {
	private final String contestSlug = "con_test";
	private String b64CookieSecret;
	@Autowired
	private ApplicationProperties appProperties;

	public CookieGenerator() {
		//this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(cookieSecret));
		//System.out.println(appProperties.getCookieSecret());
	}

	String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();

		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		String fm = formatter.toString();

		formatter.close();

		return fm;
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
			bos.write(utf8Bytes("plaintext:${password}"));
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

	String tornadoSignedValueField(String token) {
		Formatter formatter = new Formatter();

		formatter.format("%d:%s", token.length(), token);

		String fm = formatter.toString();

		formatter.close();

		return fm;
	}

	String tornadoCreateSignedValue(String secret, String name, String value, String timestamp) {
		String signature = "";
		String[] toSign = { "0", timestamp, name, value };

		for (String s : toSign) {
			s = tornadoSignedValueField(s);
		}

		toSign[0] = "2";
		toSign[toSign.length] = "";

		String toSignStr = String.join("|", toSign);

		try {
			signature = calculateHMACSHA256(utf8Bytes(secret), utf8Bytes(toSignStr));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toSignStr + signature;
	}

	public void generateCookie(String user, String password) {
		this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(this.appProperties.getCookieSecret()));
		System.out.println(this.appProperties.getUrlRedirect());
		long epochNow = Instant.now().getEpochSecond();

		byte[] pickledHashBytes = pickle0dumpsCMS(user, password, String.valueOf(epochNow));
		String pickledHashStr = new String(pickledHashBytes, StandardCharsets.UTF_8);

		// String cookieValue = tornadoCreateSignedValue(b64CookieSecret,
		// "con_test_login", Base64.getEncoder().encodeToString(pickledHashBytes),
		// String.valueOf(epochNow));

		// -------------- Using Tornado secure cookies port
		TornadoCookieCodec cookieCoder = null;
		try {
			cookieCoder = V2TornadoCookieCodec.builder().withTimestamp(epochNow).withSecretKey(b64CookieSecret).build();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		Cookie signedCookie = cookieCoder.encodeCookie(new Cookie(contestSlug + "_login", pickledHashStr));
		System.out.println(signedCookie.getValue());
	}
}
