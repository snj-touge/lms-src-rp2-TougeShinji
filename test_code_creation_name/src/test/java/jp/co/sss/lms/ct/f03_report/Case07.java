package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 * @author 峠 伸治
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 未提出の研修日の「詳細」ボタンをクリック
		// 親の要素(「○○コース」と書かれている部分、孫要素に研修日が存在する)
		List<WebElement> primarys = webDriver.findElements(By.className("panel-primary"));
		isFinished:
		for(WebElement primary : primarys) {
			// 子要素(コースの研修内容部分、子要素に研修日が存在する)
			List<WebElement> sctionList = primary.findElements(By.tagName("tr"));
			for(WebElement sction : sctionList) {
				// 孫要素(研修日部分、日付や研修の詳細、レポート登録情報、詳細ページへの遷移ボタンがある)
				// レポート登録の要素が未提出のものを検索
				if(sction.findElement(By.className("w10per")).getText().equals("未提出")) {
					// 「詳細」要素をクリック
					sction.findElement(By.className("btn-default")).click();
					break isFinished;
				}
				// 見切れて取得できなくなるため移動
				scrollBy(String.valueOf(sction.getSize().getHeight()));
			}
		}
		
		pageLoadTimeout(30);
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		webDriver.findElement(By.xpath("//input[@type='submit']")).click();
		
		pageLoadTimeout(30);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		
		webDriver.findElement(By.className("form-control")).sendKeys("テスト");
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("提出済み日報【デモ】を確認する", webDriver.findElement(By.xpath("//input[@type='submit']")).getAttribute("value"));
		
		getEvidence(new Object() {});
	}

}
