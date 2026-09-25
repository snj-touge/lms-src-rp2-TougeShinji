package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {
	
	private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	
	private final String SET_TEXT = "メロスは激怒した。"
									+ "必ず、かの邪智暴虐の王を除かなければならぬと決意した。"
									+ "メロスには政治がわからぬ。メロスは、村の牧人である。"
									+ "笛を吹き、羊と遊んで暮して来た。"
									+ "けれども邪悪に対しては、人一倍に敏感であった。";

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
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		
		Select select = new Select(webDriver.findElement(By.id("startMinute0")));
		select.selectByIndex(0);

		scrollBy(String.valueOf(webDriver.findElement(By.tagName("table")).getSize().getHeight()));
		
		webDriver.findElement(By.name("complete")).click();
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		assertEquals("form-control errorInput",webDriver.findElement(By.id("startMinute0")).getAttribute("class"));
		assertEquals("* 出勤時間が正しく入力されていません。", webDriver.findElement(By.className("error")).getText());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		Select select = new Select(webDriver.findElement(By.id("startHour0")));
		select.selectByIndex(0);

		scrollBy(String.valueOf(webDriver.findElement(By.tagName("table")).getSize().getHeight()));
		
		webDriver.findElement(By.name("complete")).click();
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", webDriver.findElement(By.className("error")).getText());
		assertEquals("form-control errorInput",webDriver.findElement(By.id("startHour0")).getAttribute("class"));
		assertEquals("form-control errorInput",webDriver.findElement(By.id("startMinute0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		
		Select select = new Select(webDriver.findElement(By.id("startHour0")));
		select.selectByIndex(18);
		select = new Select(webDriver.findElement(By.id("startMinute0")));
		select.selectByIndex(1);
		
		select = new Select(webDriver.findElement(By.id("endHour0")));
		select.selectByIndex(0);
		select = new Select(webDriver.findElement(By.id("endMinute0")));
		select.selectByIndex(0);

		scrollBy(String.valueOf(webDriver.findElement(By.tagName("table")).getSize().getHeight()));
		
		webDriver.findElement(By.name("complete")).click();
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", webDriver.findElement(By.className("error")).getText());
		assertEquals("form-control errorInput",webDriver.findElement(By.id("endHour0")).getAttribute("class"));
		assertEquals("form-control errorInput",webDriver.findElement(By.id("endMinute0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		
		Select select = new Select(webDriver.findElement(By.id("endHour0")));
		select.selectByIndex(19);
		select = new Select(webDriver.findElement(By.id("endMinute0")));
		select.selectByIndex(1);
		
		select = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		select.selectByIndex(12);

		scrollBy(String.valueOf(webDriver.findElement(By.tagName("table")).getSize().getHeight()));
		
		webDriver.findElement(By.name("complete")).click();
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		assertEquals("* 中抜け時間が勤務時間を超えています。", webDriver.findElement(By.className("error")).getText());
		assertEquals("form-control errorInput",webDriver.findElement(By.name("attendanceList[0].blankTime")).getAttribute("class"));
	
		getEvidence(new Object() {});
	
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		
		Select select = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		select.selectByIndex(12);
		
		webDriver.findElement(By.name("attendanceList[0].note")).sendKeys(SET_TEXT);
		
		scrollBy(String.valueOf(webDriver.findElement(By.tagName("table")).getSize().getHeight()));
		
		webDriver.findElement(By.name("complete")).click();
		webDriver.switchTo().alert().accept();
		
		pageLoadTimeout(30);
		
		assertEquals("* 備考の長さが最大値(100)を超えています。", webDriver.findElement(By.className("error")).getText());
		assertEquals("form-control errorInput",webDriver.findElement(By.name("attendanceList[0].note")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

}
