package automation.PageSpeedInsights;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static automation.PageSpeedInsights.Waits.*;
import static org.testng.Assert.assertTrue;
import static automation.PageSpeedInsights.DynamicWebElements.*;
import static automation.PageSpeedInsights.PageActions.*;
import static automation.PageSpeedInsights.ExcelOperations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main extends StartDriver {
	static ArrayList<String> urlsFromExcel = new ArrayList<String>();
	static String columnIncrement;
	WebElement element;

	public static void main(String[] args) throws Exception {
		openBrowser();
		readDataFromExcel();
		getPerformanceParameters();
	}

	public static void getPerformanceParameters() throws Exception {
		System.out.println("------------Fetching Performance Parameters of URLs -----------------");
		for (int i = 0; i < urlsFromExcel.size(); i++) {
			String url = urlsFromExcel.get(i);
			assertTrue(verifyWebElementPresent(driver.findElement(By.xpath("//input[@name='url']"))));
			fillText(driver.findElement(By.xpath("//input[@name='url']")), url);
			assertTrue(verifyWebElementPresent(getWebElementByText("Analyze")));
			executeJavaScriptClick(getWebElementByText("Analyze"));
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			assertTrue(verifyWebElementPresent(
					driver.findElement(By.xpath("//span[text()='Desktop']//ancestor::button"))));
			clickElement(driver.findElement(By.xpath("//span[text()='Desktop']//ancestor::button")), "test");
			try {
				scrollToElement(
						driver.findElement(By.xpath("//*[contains(text(),'Emulated Desktop with Lighthouse')]")));
			} catch (Exception e) {
				driver.navigate().refresh();
				System.out.println("Exception handled");
				assertTrue(verifyWebElementPresent(
						driver.findElement(By.xpath("//span[text()='Desktop']//ancestor::button"))));
				executeJavaScriptClick(driver.findElement(By.xpath("//span[text()='Desktop']//ancestor::button")));
				scrollToElement(
						driver.findElement(By.xpath("//*[contains(text(),'Emulated Desktop with Lighthouse')]")));
			}
			assertTrue(verifyWebElementPresent(
					driver.findElement(By.xpath("//*[contains(text(),'Emulated Desktop with Lighthouse')]"))));
			String firstContentfulPaint = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=1]"))
					.getText();
			writeDataToExcel(0, i + 1, 1, firstContentfulPaint);

			String speedIndex = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=2]"))
					.getText();
			writeDataToExcel(0, i + 1, 2, speedIndex);

			String largestContentfulPaint = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=3]"))
					.getText();
			writeDataToExcel(0, i + 1, 3, largestContentfulPaint);

			String timeToInteractive = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=4]"))
					.getText();
			writeDataToExcel(0, i + 1, 4, timeToInteractive);

			String totalBlockingTime = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=5]"))
					.getText();
			writeDataToExcel(0, i + 1, 5, totalBlockingTime);

			String cumulativeLayoutShift = driver.findElement(By.xpath(
					"(//*[contains(text(),'Emulated Desktop with Lighthouse')]//ancestor::div[@class='lh-category']//div[@class='lh-metric__value'])[position()=6]"))
					.getText();
			writeDataToExcel(0, i + 1, 6, cumulativeLayoutShift);

			int j = i + 1;
			System.out.println(j + " URL done");
			driver.navigate().to("https://pagespeed.web.dev/");
		}
		System.out.println("------------Added Performance Paremeters to Excel -----------------");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		long minutes = totalTime / 1000 / 60;
		long seconds = totalTime / 1000 % 60;
		String executionTime = "Execution time is: " + minutes + " minutes and " + seconds + " seconds";
		writeDataToExcel(0, urlsFromExcel.size() + 2, 0, executionTime);
		System.out.println(executionTime);
	}
}