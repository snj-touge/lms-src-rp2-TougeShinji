package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 * @author 峠 伸治
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {


	private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	private final String KEY_WORD = "金";
	
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
		WebElement loginButtonElement = webDriver.findElement(By.cssSelector(".btn-primary "));
		assertEquals("ログイン", loginButtonElement.getAttribute("value"));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("ItTest2025");
		webDriver.findElement(By.cssSelector(".btn-primary ")).click();
		
		pageLoadTimeout(30);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		
		webDriver.findElement(By.linkText("機能")).click();
		webDriver.findElement(By.linkText("ヘルプ")).click();

		pageLoadTimeout(30);
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		
		String originalWindow = webDriver.getWindowHandle();
		
		webDriver.findElement(By.linkText("よくある質問")).click();
		
		pageLoadTimeout(30);
		Set<String> allWindows = webDriver.getWindowHandles();
		 for (String windowHandle : allWindows) {
             if (!originalWindow.contentEquals(windowHandle)) {
            	 webDriver.switchTo().window(windowHandle);
                 break;
             }
         }
		
		pageLoadTimeout(30);
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		
	}
	
	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// キーワードを入力
		webDriver.findElement(By.id("form")).sendKeys(KEY_WORD);
		// 検索
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/form/fieldset/div[2]/div/input[1]")).click();
		
		pageLoadTimeout(30);
		// チェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("助成金書類の作成方法が分かりません", webDriver.findElement(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt/span[2]")).getText());
		assertEquals("1 件中 1 件から 1 件までを表示", webDriver.findElement(By.id("DataTables_Table_0_info")).getText());

		getEvidence(new Object(){});
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// クリアボタン
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/form/fieldset/div[2]/div/input[2]")).click();
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/form/fieldset/div[2]/div/input[1]")).click();
		
		pageLoadTimeout(30);
		// チェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("5 件中 1 件から 5 件までを表示", webDriver.findElement(By.id("DataTables_Table_0_info")).getText());

		getEvidence(new Object(){});
	}

}
