package com.dwlarson.joshua.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.dwlarson.joshua.Network.Packets.EncryptionKeyRequest;
import com.dwlarson.joshua.Network.Packets.EncryptionKeyResponse;

public class Encryption {

    private static final Random secure = new SecureRandom();
    private static final Random random = new Random();
    private static KeyPair keys;
    private static Key aesKey;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static EncryptionKeyRequest encryptRequest(KeyPair keyPair) throws NoSuchAlgorithmException {
    	if (keys == null) {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            keys = keyGen.generateKeyPair();
        }
    	if (aesKey == null) {
    		KeyGenerator kGen = KeyGenerator.getInstance("AES");
    		kGen.init(128, new SecureRandom());
    		aesKey = kGen.generateKey();
    	}
        
        String hash = Long.toString(random.nextLong(), 16);
        byte[] pubKey = keyPair.getPublic().getEncoded();
        byte[] verify = new byte[4];
        random.nextBytes(verify);
        return new EncryptionKeyRequest(hash, pubKey, verify);
    }

    public static Key getSecret(EncryptionKeyResponse resp, EncryptionKeyRequest request) throws BadPaddingException, IllegalBlockSizeException,
            IllegalStateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
    	
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA/ECB/NoPadding", "BC");
	        cipher.init(Cipher.WRAP_MODE, keys.getPublic());
	        byte [] encryptSymKey = cipher.wrap(aesKey);
	        cipher.init(Cipher.UNWRAP_MODE, keys.getPrivate());
	        Key decryptedKey = cipher.unwrap(encryptSymKey, "AES", Cipher.SECRET_KEY);
	        System.out.println("Key Length: " + decryptedKey.getEncoded().length*8 + " bits");
	        return decryptedKey;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
        /*cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
        byte[] decrypted = cipher.doFinal(resp.getVerifyToken());

        if (!Arrays.equals(request.getVerifyToken(), decrypted)) {
            throw new IllegalStateException("Key pairs do not match!");
        }
        
        cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
        byte[] shared = resp.getSharedSecret();
        byte[] secret = cipher.doFinal(shared);

        return new SecretKeySpec(secret, "AES");*/
    }

    public static boolean isAuthenticated(String username, String connectionHash, SecretKey shared) throws NoSuchAlgorithmException, IOException {
        String encName = URLEncoder.encode(username, "UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        for (byte[] bit : new byte[][]{connectionHash.getBytes("ISO_8859_1"), shared.getEncoded(), keys.getPublic().getEncoded()}) {
            sha.update(bit);
        }

        String encodedHash = URLEncoder.encode(new BigInteger(sha.digest()).toString(16), "UTF-8");
        String authURL = "http://session.minecraft.net/game/checkserver.jsp?user=" + encName + "&serverId=" + encodedHash;
        String reply;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(authURL).openStream()))) {
            reply = in.readLine();
        }

        return "YES".equals(reply);
    }

    public static CFBBlockCipher getCipher(boolean forEncryption, Key shared) {
        CFBBlockCipher cip = new CFBBlockCipher(new AESFastEngine(), 8);
        cip.init(forEncryption, new ParametersWithIV(new KeyParameter(shared.getEncoded()), shared.getEncoded(), 0, 16));
        return cip;
    }

    public static SecretKey getSecret() {
        byte[] rand = new byte[32];
        secure.nextBytes(rand);
        return new SecretKeySpec(rand, "AES");
    }

    public static PublicKey getPubkey(EncryptionKeyRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(request.getPublicKey()));
    }

    public static byte[] encrypt(Key key, byte[] b) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher hasher = Cipher.getInstance("RSA");
        hasher.init(Cipher.ENCRYPT_MODE, key);
        return hasher.doFinal(b);
    }
    
    public static byte[] decrypt(Key key, byte[] b) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher hasher = Cipher.getInstance("RSA");
        hasher.init(Cipher.DECRYPT_MODE, key);
        return hasher.doFinal(b);
    }

    public static byte[] getShared(SecretKey key, PublicKey pubkey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubkey);
        return cipher.doFinal(key.getEncoded());
    }
}