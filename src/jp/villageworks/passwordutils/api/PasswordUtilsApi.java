package jp.villageworks.passwordutils.api;

import jp.villageworks.passwordutils.core.PasswordUtilsCore;

/**
 * パスワード生成ユーティリティのAPIクラス
 * @author tutor
 */
public class PasswordUtilsApi extends PasswordUtilsCore{
	/**
	 * パスワードとソルトからハッシュ化されたパスワードを取得する。
	 * @param rawPassword 平文のパスワード
	 * @param salt ソルト（ハッシュ化する際に付加する情報）
	 * @return ハッシュ化されたパスワード
	 */
	public static String createPassword(String rawPassword, String salt) {
		return createHashedPassword(rawPassword, salt);
	}

}
