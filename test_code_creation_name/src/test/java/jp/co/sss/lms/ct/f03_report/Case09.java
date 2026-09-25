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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 * @author 峠 伸治
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	private final int PORT = 8080;
	
	private final String LOGIN_URL = "http://localhost:" + PORT + "/lms";
	private final String SELECT_ELEMENT_DAY = "2022年10月2日(日)";
	private final String SET_TEXT = "メロスゎ激おこプンプン丸。マジぁりぇなぃし。ぜったい、ぃじわるの王をたぉさなくちゃってぉもった。メロスにゎ政治がゎからなぃ。\r\n"
									+ "メロスゎ、村の牧人なんだょ。笛を吹き、羊Cと遊んで暮して来た。ケド、邪悪に対してゎ、人一倍に敏感なンだょね。\r\n"
									+ "きょう未明メロスは村を出発し、野トカ山トカ越え、メッチャはなれたコノ「シラ勹ス」の市にゃって来た。\r\n"
									+ "メロスにはｐａｐａも、ｍａｍａも無い。ダァも無い。ティーンの、草食系な妹と二人暮しだ。\r\n"
									+ "この妹は、村の或る律気なイケメンを、近々、ダンナとして迎える事になっていた。結婚式も間近なンだょ。\r\n"
									+ "メロスは、オトナ女子の服トカ祝宴の「⊇〃ちξぅ」やらを買いに、はるばる市にやって来たのだ。\r\n"
									+ "まず、そのハッピースピリチュアルアイテムを買い集め、それから街をアゲアゲに歩いた。メロスにはズッ友があった。セリヌンティウスである。\r\n"
									+ "今ゎシラ勹スの市で、石工をしている。そのBestFriendを、これから訪ねてみるつもりなのだ。\r\n"
									+ "最近会ってなかったしだから、会うのがマジ楽しみ。歩いているうちにメロスは、まちの様子がなンか怪しく思った。マジひっそりしている。\r\n"
									+ "もう日も落ちて、まちの暗いのは当りまえなんだケド、なんか、夜のせいだけとかじゃあなくて、市全体が、マジ寂しい。\r\n"
									+ "オトナ女子のメロスも、ちょっぴり不安になってきたし。\r\n"
									+ "そのへんの若者を逆ナンして、何かあったのか、二年まえに来たときは、夜デモまちはチョー賑やかであったはずなんだケド、と質問した。\r\n"
									+ "若者は、首をブンブン丸して答えなかった。しばらく歩いてオッサンに逢い、こんどはもっと、キレ気味に質問した。\r\n"
									+ "オッサンは答えなかった。メロスは質問を重ねた。オッサンは、バリトンボイスで、ちょっと答えた。\r\n"
									+ "「王様は、人を殺します。」\r\n"
									+ "「なんで殺しちゃうの？ワケわかめだし」\r\n"
									+ "「悪心を抱いている、というのですが、誰もそんな、悪心を持っては居りませぬ。」\r\n"
									+ "「たくさん死ンじゃったの？マジありぇなぃし」\r\n"
									+ "「はい、はじめは王様の妹婿さまを。それから、御自身のお世嗣を。それから、妹さまを。それから、妹さまの御子さまを。それから、皇后さまを。それから、賢臣のアレキス様を。」\r\n"
									+ "「ＣＨＯ→バビッた～国王は気ぃ悪いの？」\r\n"
									+ "「いいえ、乱心ではございませぬ。人を、信ずる事が出来ぬ、というのです。このごろは、臣下の心をも、お疑いになり、少しく派手な暮しをしている者には、人質ひとりずつ差し出すことを命じて居ります。御命令を拒めば十字架にかけられて、殺されます。きょうは、六人殺されました。」\r\n"
									+ "　聞いて、メロスは激怒した。「カム着火インフェルノーォォォォオオオウ。マジ生かして置けないし。」\r\n"
									+ "　メロスは、単純みたいな。買い物を、背負ったままで、のそのそ王城にはいって行った。たちまちメロスは、警備員に捕まった。マジ滑稽。調べられて、メロスのポーチからは短刀（カミソリ）が出て来たので、騒ぎが大きくなってしまった。メロスは、王の前に引き出された。\r\n"
									+ "「この短刀で何をするつもりであったか。言え！メンヘラ！」暴君ディオニスは静かに、けれども威厳を以て問いつめた。その王の顔はファンデ塗りたくったみたいで、眉間の皺は、マジシワシワだった。\r\n"
									+ "「市をテメーの手から救うし。」とメロスは悪びれずに答えた。\r\n"
									+ "「おまえがか？」王は、チョイワラ。「仕方の無いやつじゃ。おまえには、わしの孤独がわからぬ。」\r\n"
									+ "「言ぅなゃ！」とメロスは、激おこスティックファイナリアリティぷんぷんドリームして反論した。「人の心を疑うトカ、マジ恥ずべき悪徳だし。王は、ミンナを疑ってるょ。」\r\n"
									+ "「疑うのが、正当の心構えなのだと、わしに教えてくれたのは、おまえたちだ。人の心は、あてにならない。人間は、もともと私慾のかたまりさ。信じては、ならぬ。」暴君は落着いて呟き、ほっと溜息をついた。「わしだって、平和を望んでいるのだが。」\r\n"
									+ "「なんの為の平和さ。自分の地位を守りたいの？マジ笑えるし」こんどはメロスが煽った。「罪の無い人を殺して、何が平和だし。（なんかよくわかんないけどアタシマジカッコイイ）」\r\n"
									+ "「だまれ、下賤の者。」王は、さっと顔を挙げて言った。「口では、どんな清らかな事でも言える。わしには、人の腹綿の奥底が見え透いてならぬ。おまえだって、いまに、磔になってから、泣いて詫びたって聞かぬぞ。」\r\n"
									+ "「ああ、王マヂパネェ。自惚てれば？私は、ちゃんと覚悟決めてんだけど。命乞いとかゼッタイしないし。でも――」と言いかけて、メロスは足もとに視線を落してチョットためらい、「でも、私に情をかけたいンだったら、処刑までに三日間の猶予を与えて下さい。妹の結婚式に出なきゃなんです。三日のうちに、私は村で結婚式を挙げさせて、ぜったぃ、ここぇ帰って来ますカラ。」\r\n"
									+ "「ばかな。」と暴君は、ダミ声で低く笑った。「とんでもない嘘を言うわい。逃がした小鳥が帰って来るというのか。」";
	
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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();
		
		pageLoadTimeout(30);
		assertEquals("ユーザー詳細", webDriver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		List<WebElement> tables = webDriver.findElements(By.className("table-hover"));
		scrollBy(String.valueOf(tables.get(0).getSize().getHeight()));
		
		List<WebElement> reports = tables.get(2).findElements(By.tagName("tr"));
		isFinished:
		for(WebElement report : reports) {
			List<WebElement> tdTags = report.findElements(By.tagName("td"));
			if (tdTags.size() > 0 && tdTags.get(0).getText().equals(SELECT_ELEMENT_DAY)
					&& tdTags.get(1).getText().equals("週報【デモ】")) {
					for(WebElement imputTag : tdTags.get(4).findElements(By.className("btn-default"))) {
						if(imputTag.getAttribute("value").equals("修正する")) {
							imputTag.click();
							break isFinished;
						}
					}
			}
		}
		
		pageLoadTimeout(30);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		webDriver.findElement(By.id("intFieldName_0")).clear();
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("intFieldName_0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("ITリテラシー①");
		WebElement element = webDriver.findElement(By.id("intFieldValue_0"));
		
		Select select = new Select(element);
		
		select.selectByValue("");
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("intFieldValue_0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		WebElement element = webDriver.findElement(By.id("intFieldValue_0"));
		Select select = new Select(element);
		select.selectByValue("2");
		
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("たいへんよくできました");
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("11");
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_0")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_1")).clear();
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_0")).getAttribute("class"));
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_1")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		
		webDriver.findElement(By.id("content_0")).sendKeys("5");;
		
		List<WebElement> tables = webDriver.findElements(By.className("bs-component"));
		scrollBy(String.valueOf(tables.get(1).getSize().getHeight()));
		
		webDriver.findElement(By.id("content_1")).sendKeys(SET_TEXT);
		webDriver.findElement(By.id("content_2")).sendKeys(SET_TEXT);
		
		webDriver.findElement(By.className("btn-primary")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_1")).getAttribute("class"));
		assertEquals("form-control errorInput", webDriver.findElement(By.id("content_2")).getAttribute("class"));
		
		getEvidence(new Object() {});
	}

}
