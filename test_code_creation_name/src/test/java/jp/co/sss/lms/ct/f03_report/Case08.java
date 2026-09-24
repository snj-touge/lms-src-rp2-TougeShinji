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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	private final String SELECT_ELEMENT_DAY = "2022年10月2日(日)";
	private final String IMPRESSION = "週報の記述例です。";
	
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
				if(sction.findElement(By.className("w20per")).getText().equals(SELECT_ELEMENT_DAY)) {
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
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]")).click();
		
		pageLoadTimeout(30);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		
		webDriver.findElement(By.id("content_1")).clear();
		webDriver.findElement(By.id("content_1")).sendKeys(IMPRESSION);
		
		List<WebElement> bsComponents = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(bsComponents.get(1).getSize().getHeight()));
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();
		
		pageLoadTimeout(30);
		assertEquals("ユーザー詳細", webDriver.getTitle());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		
		List<WebElement> tables = webDriver.findElements(By.className("table-hover"));
		scrollBy(String.valueOf(tables.get(0).getSize().getHeight()));
		
		List<WebElement> reports = tables.get(2).findElements(By.tagName("tr"));
		isFinished:
		for(WebElement report : reports) {
			List<WebElement> tdTags = report.findElements(By.tagName("td"));
			if (tdTags.size() > 0 && tdTags.get(0).getText().equals(SELECT_ELEMENT_DAY)
					&& tdTags.get(1).getText().equals("週報【デモ】")) {
					for(WebElement imputTag : tdTags.get(4).findElements(By.tagName("input"))) {
						if(imputTag.getAttribute("value").equals("詳細"))
							imputTag.click();
						break isFinished;
					}
			}
		}
		
		pageLoadTimeout(30);
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());
		
		tables = webDriver.findElements(By.className("table-hover"));
		//報告レポート欄の所感を参照
		for(WebElement trTag : tables.get(2).findElements(By.tagName("tr"))) {
			if(trTag.findElement(By.tagName("th")).getText().equals("所感")) {
				assertEquals(IMPRESSION, trTag.findElement(By.tagName("td")).getText());
				break;
			}
		}
		
		getEvidence(new Object() {});
	}

}
