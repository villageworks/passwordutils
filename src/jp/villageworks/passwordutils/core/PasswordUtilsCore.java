package jp.villageworks.passwordutils.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * パスワード生成ユーティリティ
 * @author tutor
 * 出典元：【Java SE 8限定】安全なパスワードを生成する方法 {@link https://www.casleyconsulting.co.jp/blog/engineer/153/}
 */
public class PasswordUtilsCore {

	/**
	 * クラス定数
	 */
	// ハッシュ化アルゴリズム：ハッシュ化アルゴリズムには「PBKDF2（HMAC-SHA-256との併用）」を設定した。
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256"; // 
	// ストレッチング回数
	private static final int ITERATION_COUNT = 10000;
	// 生成される鍵の長さ
	private static final int KEY_LENGTH = 256;
	
	/**
	 * 平文パスワードとソルトから安全なパスワードを生成する。
	 * @param password 平文パスワード
	 * @param salt     ソルト（パスワード生成に際して付加する文字列）
	 * @return         安全なパスワード
	 */
	protected static String createHashedPassword(String password, String salt) {
		// PBKeyの生成に必要な値を生成
		char[] passCharAry = password.toCharArray();
		byte[] hashedSalt = getHashedSalt(salt);
		

		// PBKDF2WithHmacSHA256アルゴリズムの秘密鍵を変換するオブジェクトの生成
		try {
			// PBKeyを生成
			PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);
			// 秘密鍵を扱うための変数
			SecretKeyFactory skf = null;
			skf = SecretKeyFactory.getInstance(ALGORITHM);
			
			// 秘密鍵のインタフェースを取得
			// すべての秘密鍵のインタフェースを扱える
			SecretKey secretKey = null;
			// PBKeyの鍵仕様で秘密鍵の生成
			secretKey = skf.generateSecret(keySpec);

			// 鍵（秘密鍵）を一時符号化形式
			byte[] passByteAry = secretKey.getEncoded();

			// 生成されたバイト配列を16進数の文字列に変換
			StringBuffer sf = new StringBuffer(64);
			for (byte b : passByteAry) {
				sf.append(String.format("%02x", b & 0XFF));
			}

			return sf.toString();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
            throw new RuntimeException(e);
		}


	}

	/**
	 * ハッシュ化したソルトを生成する。
	 * @param salt 対象となるソルト
	 * @return     ハッシュ化されたソルト
	 */
	private static byte[] getHashedSalt(String salt) {
        try {
        	// SHA-256アルゴリズムによるメッセージダイジェストオブジェクトを生成
    		MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("SHA-256");
            // ソルトでダイジェストを更新
            messageDigest.update(salt.getBytes());
            // ハッシュ計算の実行と返却
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}

}
