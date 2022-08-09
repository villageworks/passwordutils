package jp.villageworks.passwordutils.api;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PasswordUtilsApiTest {

	@Nested
	@DisplayName("PasswordUtil#createHashedPasswordメソッドのテストクラス")
	class CreateHashedPasswordTest {
		
		// 入力されたパスワードを想定
		String[] passwords = {"password01", "password02"};
		// ソルト（入力されたユーザ名）を想定
		String[] salts = {"ts0818", "Larry Wall"};
		
		@Test
		@DisplayName("【Test_01】ソルトが同じパスワードは一致する。")
		void test_01() {
			// setup
			List<String> expecteds = new ArrayList<>();
			expecteds.add("a5c09ba69630d73583588c935b2a3d88981449508833406d0fc0e2ad51be2c54");
			expecteds.add("c14e2d010ff6e719b26bd5e664a14af5df8849117e3e4bd6f49bf1e2679b8a05");
			// execute
			List<String> actuals = new ArrayList<>();
			actuals.add(PasswordUtilsApi.createPassword(passwords[0], salts[0]));
			actuals.add(PasswordUtilsApi.createPassword(passwords[1], salts[1]));
			// verify
			for (int i = 0; i < actuals.size(); i++) {
				String actual = actuals.get(i);
				String expected = expecteds.get(i);
				assertThat("インデックス " + i + " のパスワードは一致しませんでした。", actual, is(expected));
			}
		}
		@Test
		@DisplayName("【Test_02】ソルトが異なるパスワードは一致しない。")
		void test_02() {
			// setup
			List<String> expecteds = new ArrayList<>();
			expecteds.add("a5c09ba69630d73583588c935b2a3d88981449508833406d0fc0e2ad51be2c54");
			expecteds.add("c14e2d010ff6e719b26bd5e664a14af5df8849117e3e4bd6f49bf1e2679b8a05");
			// execute
			List<String> actuals = new ArrayList<>();
			actuals.add(PasswordUtilsApi.createPassword(passwords[0], salts[1]));
			actuals.add(PasswordUtilsApi.createPassword(passwords[1], salts[0]));
			// verify
			for (int i = 0; i < actuals.size(); i++) {
				String actual = actuals.get(i);
				String expected = expecteds.get(i);
				assertThat("インデックス " + i + " のパスワードは一致しました。", actual, is(not(expected)));
			}
		}
	}

}
