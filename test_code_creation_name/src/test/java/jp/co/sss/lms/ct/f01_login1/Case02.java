package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 * @author 峠 伸治
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	
	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		
		goTo(LOGIN_URL);

		assertEquals("ログイン | LMS", webDriver.getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		//異常チェック
		//間違った入力値でログイン
		webDriver.findElement(By.id("loginId")).sendKeys("wrong-id");
		webDriver.findElement(By.id("password")).sendKeys("wrong-password");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("* ログインに失敗しました。", webDriver.findElement(By.cssSelector(".error")).getText());
		getEvidence(new Object(){},"id_error");
		
		//ID未入力の場合
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("password")).sendKeys("ItTest2025");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("ログインIDは必須です。", webDriver.findElement(By.cssSelector(".error")).getText());
		getEvidence(new Object(){},"null_password");
		
		//パスワード未入力の場合
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("パスワードは必須です。", webDriver.findElement(By.cssSelector(".error")).getText());
		getEvidence(new Object(){},"null_id");
		
		//パスワードの入力上限を超えた場合
		webDriver.findElement(By.id("password")).sendKeys("abcdefghijklmnopqrstuvwxyz");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("パスワードの長さが最大値(20)を超えています。", webDriver.findElement(By.cssSelector(".error")).getText());
		getEvidence(new Object(){},"over_maxlength_password");
		
		//回数上限を超えた場合
		webDriver.findElement(By.id("password")).sendKeys("wrong-password");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("* 規定の回数を超えたため、アカウントにロックがかかりました。しばらくたってから再度お試しください。", webDriver.findElement(By.cssSelector(".error")).getText());
		getEvidence(new Object(){},"login_lock");
	}

}
