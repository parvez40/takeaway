package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LieferendoHomePage {

	WebDriver driver;

	By searchField = By.xpath(".//input[@id='imysearchstring']");
	By searchButton = By.className("submitBtn");
	By suggestions = By.className("autoCompleteDropDown");
	By searchResultCount = By.xpath("//div[@class='title restaurants-counter']");
	By homeLogo = By.className("go-back-button");
	By blankSearchError = By.id("ideliveryareaerror");
	By searchPlaceholder = By.xpath(".//input[@id='imysearchstring']");

	public LieferendoHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterTextInSearchField(String text) {

		driver.findElement(searchField).clear();
		driver.findElement(searchField).sendKeys(text);

	}

	public void clickSearchSubmitButton() {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
		element.click();

	}

	public void pressEnterInSearch() throws InterruptedException {
		
		Thread.sleep(1000);
		driver.findElement(searchField).sendKeys(Keys.ENTER);

	}

	public String suggestionsText() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(suggestions));

		return element.getText();

	}
	
	public String counterText() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultCount));

		return element.getText();

	}
	
	public void clickHomeLogo() {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(homeLogo));
		element.click();
	}
	
	public String blankSearchError() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(blankSearchError));

		return element.getText();

	}
	
	public String searchFieldPlaceholder() {

		return driver.findElement(searchPlaceholder).getAttribute("placeholder");

	}

}
