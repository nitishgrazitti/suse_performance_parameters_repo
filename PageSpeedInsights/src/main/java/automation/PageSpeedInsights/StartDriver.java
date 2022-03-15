package automation.PageSpeedInsights;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StartDriver {
	static long startTime;
	public static WebDriver driver;

	public static void openBrowser() {
//		System.setProperty("webdriver.chrome.driver", Constants.win_chromePath);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().setPosition(new Point(1600, 0));
		driver.manage().window().maximize();
		driver.get("https://pagespeed.web.dev/");
		startTime = System.currentTimeMillis();
	}

}
