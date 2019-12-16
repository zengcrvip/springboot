package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class RSAUtils {

	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "public_key";
	private static final String PRIVATE_KEY = "private_key";
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * generate a RSA public key and private key pair
	 *
	 * @return a map contains RSA public and private key,
     * pass this map to {@link #getPublicKey(Map)} and {@link #getPrivateKey(Map)} to get the public key and private key
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(2048);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		return decrypt(encryptedData, cipher);
	}

	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		return decrypt(encryptedData, cipher);
	}

	public static byte[] encryptByPublicKey(byte[] data, String publicKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		return encrypt(data, cipher);
	}

	public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		return encrypt(data, cipher);
	}

	public static String encryptByPublicKey(String text, String publicKey) throws Exception {
		byte[] bytes = text.getBytes("UTF-8");
		byte[] encrypted = encryptByPublicKey(bytes, publicKey);
		return Base64Utils.encode(encrypted);
	}

	public static String decryptByPrivateKey(String base64Text, String privateKey) throws Exception {
		byte[] bytes = Base64Utils.decode(base64Text);
		byte[] decrypted = decryptByPrivateKey(bytes, privateKey);
		return new String(decrypted, "UTF-8");
	}

	public static String encryptByPrivateKey(String text, String privateKey) throws Exception {
		byte[] encrypted = encryptByPrivateKey(text.getBytes("UTF-8"), privateKey);
		return Base64Utils.encode(encrypted);
	}

	public static String decryptByPublicKey(String text, String publicKey) throws Exception {
		byte[] bytes = Base64Utils.decode(text);
		byte[] decrypted = decryptByPublicKey(bytes, publicKey);
		return new String(decrypted, "UTF-8");
	}

	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encode(key.getEncoded());
	}

	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encode(key.getEncoded());
	}

	private static byte[] encrypt(byte[] data, Cipher cipher) throws Exception {
        return convertData(data, cipher, MAX_ENCRYPT_BLOCK);
    }

    private static byte[] decrypt(byte[] data, Cipher cipher) throws Exception {
        return convertData(data, cipher, MAX_DECRYPT_BLOCK);
    }

	private static byte[] convertData(byte[] data, Cipher cipher, int blockSize) throws Exception {
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > blockSize) {
				cache = cipher.doFinal(data, offSet, blockSize);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * blockSize;
		}
		byte[] result = out.toByteArray();
		out.close();
		return result;
	}

	public static void main(String[] args) {
		try {
//			Map<String, Object> map = genKeyPair();
//			System.out.println("PUBLIC_KEY:"+map.get(PUBLIC_KEY));
//			System.out.println("PRIVATE_KEY:"+map.get(PRIVATE_KEY));
			String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALm6ANxLtpuR19m26R+UKBc8GDnH\n" +
					"NrMBcQUNZQf5Aje+gRm/4aD7SvNdEuIeswg+PxZElnJ+9jgVKXRkNbZDMizTp3agXY+KTMzxwfSM\n" +
					"HLaISGGhC2yDEbh023nknfyOPX3JwSasa0VZr7gRyYy4XjVtVIfyUxqVJRmKkGhUN0EBAgMBAAEC\n" +
					"gYEAsf+cN4Ww22RpdNUilAtQaQRFS4qr1993zBnMTFnQUFCaNm159ukgKiF3qZM2jp3ppJPThKQ9\n" +
					"of5WHqC0AZxhBoQFggVsORfaVsWJgt9VX1OKs2r7q+y77yTL6aHgfw/u/u4k3sAsVxJbHuX+uj0d\n" +
					"AOqrAcglhpdj+u++2uCqmeECQQD3Rz/cdPwU3fQwWkRbxSlxmOvYIGy0NbD4pqDBTGG3kX695bsR\n" +
					"7//eaz24T5MnRId8M0L/0BqSZbDrpyXqRmFVAkEAwEb8EfeIM3sg6eafbxVoF9OVfNGSIIXA5+q9\n" +
					"lBAFinRXjSh5NEO6zd/sWDsVpkwnYqQPNRzj9zHQib4Nt0TQ/QJAKCdHtXLBpFnOVzOxOf2wFyQv\n" +
					"1+6b+D9nt1jHbdwLWhWkMQ3m7Ki+Mc3UPeOeD5hPL9nbHVMbnX4p1npT69D16QJAGAyI2KJyOaYr\n" +
					"3rWGuXke9OP07ZC0yzaPgzJcFdnPJ5sXnEM0rbHO+pMIV5zEP3GE95R3hngflzp2z9OimYqU7QJA\n" +
					"KUIoIZXHfR0l9ELpNJucze3w+dvYORCo4ERiqNJsXoa6pmdCJBRRRzd7aGud2MLcCYwixQmEass9\n" +
					"KRqSZnSwVg==";
			String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5ugDcS7abkdfZtukflCgXPBg5xzazAXEFDWUH\n" +
					"+QI3voEZv+Gg+0rzXRLiHrMIPj8WRJZyfvY4FSl0ZDW2QzIs06d2oF2PikzM8cH0jBy2iEhhoQts\n" +
					"gxG4dNt55J38jj19ycEmrGtFWa+4EcmMuF41bVSH8lMalSUZipBoVDdBAQIDAQAB";
			System.out.println("PUBLIC:"+publicKey);
			System.out.println("--------------------------");
			System.out.println("PRIVATE:"+privateKey);

			System.out.println("--------------------------");

			String encryptByPublicKey = encryptByPublicKey("123456", publicKey);
			System.out.println("public key加密："+encryptByPublicKey);
			String decryptByPrivateKey = decryptByPrivateKey(encryptByPublicKey, privateKey);
			System.out.println("private key解密："+decryptByPrivateKey);
		} catch (Exception e) {
			log.error("异常:{}", e);
		}
	}
}
