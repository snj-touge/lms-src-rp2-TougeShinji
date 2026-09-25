package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

	private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	
	//日付
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日(E)");
	//時間
	private final SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	
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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		
		webDriver.findElement(By.linkText("勤怠")).click();
		
		pageLoadTimeout(30);
		
		Alert alert = webDriver.switchTo().alert();
		if(alert != null) {
			alert.accept();
		}
		
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		webDriver.findElement(By.name("punchIn")).click();
		
		String nowTime = stf.format(new Date());
		//「打刻します。よろしいですか？」の処理
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		String today = sdf.format(new Date());
		
		for(WebElement trTag : webDriver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if(trTag.findElement(By.className("w160")).getText().equals(today)) {
				assertEquals(nowTime,trTag.findElements(By.className("w80")).get(0).getText());
				break;
			}
		}
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		webDriver.findElement(By.name("punchOut")).click();
		
		String nowTime = stf.format(new Date());
		//「打刻します。よろしいですか？」の処理
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		String today = sdf.format(new Date());
		
		for(WebElement trTag : webDriver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
			if(trTag.findElement(By.className("w160")).getText().equals(today)) {
				assertEquals(nowTime,trTag.findElements(By.className("w80")).get(1).getText());
				break;
			}
		}
		
		getEvidence(new Object() {});
	}

}
