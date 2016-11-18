package com.uban.rent.util.encry;


import android.util.Log;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESCrypt {
	public static SecretKeyFactory secretKeyFactory = null;

	public static final String CIPHER = "DES/CBC/PKCS5Padding";
	//static final String CIPHER = "DES/CBC/PKCS5Padding";

	static {
		try {
			secretKeyFactory = SecretKeyFactory.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static final String UTF8 = "UTF-8";

	/*
	 * 对象缓存的容器
	 */
	static abstract class Cache {
		private final Map<Object, Object> innerCache = new HashMap<Object, Object>();

		protected abstract Object createValue(Object key) throws Exception;

		public Object get(Object key) throws Exception {
			Object value;
			synchronized (innerCache) {
				value = innerCache.get(key);
				if (value == null) {
					value = new CreationPlaceholder();
					innerCache.put(key, value);
				}
			}

			if (value instanceof CreationPlaceholder) {
				synchronized (value) {
					CreationPlaceholder progress = (CreationPlaceholder) value;
					if (progress.value == null) {
						progress.value = createValue(key);
						synchronized (innerCache) {
							innerCache.put(key, progress.value);
						}
					}
					return progress.value;
				}
			}
			return value;
		}

		static final class CreationPlaceholder {
			Object value;
		}
	}

	/*
	 * hex->str & str->hex
	 */
	public static byte[] stringToHex(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String hexToString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2) {
				hexString.append("0");
			}
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	public static byte[] _convertKeyIv(String text) throws IOException {
		if (text.length() == 8) {
			return text.getBytes(UTF8);
		}
		if (text.startsWith("0x") && text.length() == 32) {
			byte[] result = new byte[8];
			for (int i=0; i<text.length(); i+=2) {
				if (text.charAt(i++) == '0' && text.charAt(i++) == 'x') {
					try {
						result[i/4] = (byte) Integer.parseInt(text.substring(i, i+2), 16);
					} catch (Exception e) {
						throw new IOException("TXT '" + text + "' is invalid!");
					}
				}
			}
			return result;
		}
		throw new IOException("TXT '" + text + "' is invalid!");
	}

	/*
	 * SecretKey & IvParameterSpec的缓存
	 */
	private static Cache SecretKeySpecs = new Cache() {
		protected Object createValue(Object key) throws Exception {
			SecretKey secretKeyObj = null;
			try {
				secretKeyObj = secretKeyFactory.generateSecret(new DESKeySpec(_convertKeyIv((String)key)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return secretKeyObj;
		}
	};

	private static Cache IvParamSpecs = new Cache() {
		protected Object createValue(Object key) throws Exception {
			IvParameterSpec ivObj = null;
			ivObj = new IvParameterSpec(_convertKeyIv((String)key));
			return ivObj;
		}
	};

	/*
	 * 加密&解密
	 */
	public static String encrypt(String text, String authKey, String authIv) {
		SecretKey secretKeyObj = null;
		IvParameterSpec ivObj = null;
		try {
			secretKeyObj = (SecretKey) SecretKeySpecs.get(authKey);
			ivObj = (IvParameterSpec) IvParamSpecs.get(authIv);
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] data = null;
		try {
			data = text.getBytes(UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] authToken = null;
		try {
			authToken = encrypt(data, secretKeyObj, ivObj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hexToString(authToken);
	}
	
	public static String encryptGBK(String text, String authKey, String authIv) {
		SecretKey secretKeyObj = null;
		IvParameterSpec ivObj = null;
		try {
			secretKeyObj = (SecretKey) SecretKeySpecs.get(authKey);
			ivObj = (IvParameterSpec) IvParamSpecs.get(authIv);
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] data = null;
		try {
			data = text.getBytes("GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] authToken = null;
		try {
			authToken = encrypt(data, secretKeyObj, ivObj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hexToString(authToken);
	}

	public static byte[] encrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(data);
	}

	public static String decrypt(String hexString, String authKey, String authIv) throws Exception {
		SecretKey secretKeyObj = null;
		IvParameterSpec ivObj = null;
		try {
			secretKeyObj = (SecretKey) SecretKeySpecs.get(authKey);
			ivObj = (IvParameterSpec) IvParamSpecs.get(authIv);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String text = decrypt(hexString, secretKeyObj, ivObj);
		return text;
	}

	public static String decrypt(String message, SecretKey secretKey, IvParameterSpec iv) throws Exception {
		byte[] data = stringToHex(message);
		return decrypt(data, secretKey, iv);
	}

	public static String decrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(data);
		return new String(retByte);
	}
	/**
	 * 获取加密随机字符串
	 * @throws Exception
	 */
	public static String getKeyStr(){
		try {
			return UUID.randomUUID().toString().substring(0, 8);
		} catch (Exception e) {
			Log.e("desc","获取密钥失败");
		}
		return"";
	}
	public static void main(String[] args) throws Exception {
//		IvParameterSpec spec = new IvParameterSpec(DESCrypt._convertKeyIv("00000000"));
//		SecretKey secret = DESCrypt.secretKeyFactory.generateSecret(new DESKeySpec(DESCrypt._convertKeyIv("00000000")));
//		byte[] encrypt = DESCrypt.encrypt("13900000000".getBytes(), secret, spec);
//		System.out.println(encrypt.toString());
//		System.out.println(DESCrypt.decrypt(encrypt.toString(), "00000000", "00000000"));
//		System.err.println(DESCrypt.decrypt(stringToHex("6754345aeb4337a3"), secret, spec));

	}
}
