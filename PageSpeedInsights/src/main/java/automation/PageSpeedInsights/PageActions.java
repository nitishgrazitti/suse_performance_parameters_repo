package automation.PageSpeedInsights;

import static automation.PageSpeedInsights.Waits.*;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageActions extends StartDriver {

	/*
	 * Description: Verify the attribute value of an element
	 */
	public static void validateAttribute(WebElement element, String valueToBeCompared) {
		String actualvalue = element.getAttribute("title");
		try {
			Assert.assertEquals(actualvalue, valueToBeCompared);

		} catch (Exception e) {
			System.out.println("its not working ");
		}
	}

	/*
	 * Description: Scroll to bottom of the page
	 */
	public static void scrollToBottomOfPage() {
		JavascriptExecutor executor = (JavascriptExecutor) StartDriver.driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/*
	 * Description: Scroll to paticular element
	 */
	public static void executeJavaScriptScroll(WebElement element) throws InterruptedException {
		Waits.waitUntilClickable(element);
//		TODO Scroll to view 
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		((JavascriptExecutor) StartDriver.driver).executeScript(scrollElementIntoMiddle, element);
	}

	/*
	 * Description: Java script function for click
	 */
	public static void executeJavaScriptClick(WebElement element) throws InterruptedException {
		WebDriverWait wait10sec = new WebDriverWait(StartDriver.driver, 10);
		wait10sec.pollingEvery(Duration.ofSeconds(1));
		JavascriptExecutor executor = (JavascriptExecutor) StartDriver.driver;

		int attempts = 0;

		while (attempts < 20) {

			try {
				wait10sec.until(ExpectedConditions.elementToBeClickable(element));
				executor.executeScript("arguments[0].click();", element);
				break;
			} catch (StaleElementReferenceException e) {

			}
			attempts++;
		}

		if (attempts < 19) {

		} else {

		}
	}

	/**
	 * Description: Scroll to element and performing click 
	 */
	public static void clickElement(WebElement element, String elementName) throws InterruptedException {
		if (waitUntilClickable(element) != null) {
			try {
				assertTrue(verifyWebElementPresent(element), "----> Element is present");
				scrollToElement(element);
				element.click();
//				log.info("Clicked on: "+elementName);
			} catch (AssertionError e) {
				assertTrue(verifyWebElementPresent(element), "----> Element is not present");
//				log.info("Unable to click on: "+elementName);
			}
		} else {
			Assert.fail("Element not found");
		}
	}

	/**
	 * Description: Scroll to element particular element
	 */
	public static void scrollToElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)");
		} catch (Exception e) {
			System.out.println("Scroll till the failed");
		}
	}


	
	/*
	 * Description:Verifying Text 
	 */
	public void verifyAlertText(WebElement element, String expectedText) {
		String actualText = element.getAttribute("innerHTML");
		System.out.println(actualText);
		Assert.assertTrue(actualText.contains(expectedText));
	}

	/*
	 * Description: function for enter text inside the fields
	 */
	public static void fillText(WebElement element, String stringToEnter) throws InterruptedException {
		element.clear();
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			executeJavaScriptClick(element);
			element.sendKeys(stringToEnter);
		}
	}

	public static String getFirstSelectedValueFromDropdown(WebElement element) {
		Select dropdown = new Select(element);
		String dropdownDefaultText = dropdown.getFirstSelectedOption().getText();
		return dropdownDefaultText;
	}

	public static int randomDate() {
		Random ran = new Random();
		return ran.nextInt(30) + 1;
	}

	public static void selectFromDropdownByVisibletext(WebElement element, String wantedOption) {
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(wantedOption);
		}

		else {
			System.out.println("Dropdown is not visible");
		}

	}

	public static String defaultValueOfDropdown(WebElement dropdownElement) {
		Select select = new Select(dropdownElement);
		WebElement option = select.getFirstSelectedOption();
		String defaultItem = option.getText();

		return defaultItem;

	}

	public static void allDropdownOptions(String selectXpath, ArrayList<String> options) {
		int size = driver.findElements(By.xpath(selectXpath)).size();

		for (int i = 1; i <= size; i++) {
			options.add(driver.findElement(By.xpath(selectXpath + "[" + i + "]")).getText());
		}

	}

	public static boolean isClickable(WebElement el) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 6);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void hover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();

	}
	
	/*
	 * Description: Mouse hover on element and click on the element
	 */
	public static void mouseHoverAndClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click().build().perform();
	}

	public static void closeCurrentTabAndMoveToParentTab(String parentWin) {
		((JavascriptExecutor) driver).executeScript("window.close()");
		// driver.close();
		driver.switchTo().window(parentWin);
		driver.switchTo().defaultContent();
	}

	public static void selectFromDropdownByIndex(WebElement element, int index) {
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByIndex(index);
		}
	}

	public static void selectFromDropdownByValue(WebElement element, String wantedOption) {
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByValue(wantedOption);
		}
	}

	public static void clearText(WebElement element) {
		String clearText = Keys.chord(Keys.CONTROL, "a");
		element.sendKeys(clearText);
		element.sendKeys(Keys.BACK_SPACE);
	}

	/**
	 * Description :File upload by using the image path
	 * 
	 * 
	 * @param imagePath
	 */
	public static void uploadImage(String imagePath) {
		StringSelection stringSelection = new StringSelection(imagePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

