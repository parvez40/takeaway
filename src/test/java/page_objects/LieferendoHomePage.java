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
	By autoCompleteDropdown = By.className("autoCompleteDropDownContent");
	By searchFieldTextInResult = By.className("atom-dropdown-text");
	By searchResultCount = By.xpath("//div[@class='title restaurants-counter']");
	By homeLogo = By.className("go-back-button");
	By blankSearchError = By.id("ideliveryareaerror");
	By searchPlaceholder = By.xpath(".//input[@id='imysearchstring']");

	public LieferendoHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement waitForElement(By content) {

		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(content));
		return element;
	}

	public void enterTextInSearchField(String text) {

		driver.findElement(searchField).clear();
		driver.findElement(searchField).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(searchField).sendKeys(text);
	}

	public void clickSearchSubmitButton() {

		WebElement element = waitForElement(searchButton);
		element.click();

	}

	public void clickAutoCompleteDropdownContent() {

		WebElement element = waitForElement(autoCompleteDropdown);
		element.click();

	}

	public String autoCompleteContentText() {

		WebElement element = waitForElement(autoCompleteDropdown);
		return element.getText();

	}

	public void pressEnterInSearch() throws InterruptedException {

		Thread.sleep(200);
		driver.findElement(searchField).sendKeys(Keys.ENTER);

	}

	public String suggestionsText() {

		WebElement element = waitForElement(suggestions);

		return element.getText();

	}

	public String counterText() {

		WebElement element = waitForElement(searchResultCount);
		return element.getText();

	}

	public void clickHomeLogo() {

		WebElement element = waitForElement(homeLogo);
		element.click();
	}

	public String blankSearchError() {

		WebElement element = waitForElement(blankSearchError);
		return element.getText();

	}

	public String searchFieldPlaceholder() {

		return driver.findElement(searchPlaceholder).getAttribute("placeholder");

	}

	public String searchFieldInputText() {

		return driver.findElement(searchField).getAttribute("value");

	}

	public String searchFieldTextInResultPage() {

		return driver.findElement(searchFieldTextInResult).getText();

	}

}
