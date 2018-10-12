package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import page_objects.LieferendoHomePage;

public class SearchTest {

	WebDriver driver;
	private String testUrl = "http://www.lieferando.de";
	String projectLocation = System.getProperty("user.dir");
	String gecko_driver = "webdriver.gecko.driver";
	String gekco_driver_path = projectLocation + "/lib/geckodriver/geckodriver.exe";
	String chrome_driver = "webdriver.chrome.driver";
	String chrome_driver_path = projectLocation + "/lib/chromedriver/chromedriver.exe";

	@BeforeTest

	public void setup() {

		System.setProperty(gecko_driver, gekco_driver_path);

		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(testUrl);

	}

	@Test(priority = 1)
	public void blankSearch() {

		LieferendoHomePage objBlankSearch = new LieferendoHomePage(driver);
		objBlankSearch.clickSearchSubmitButton();

		String placeholder = objBlankSearch.searchFieldPlaceholder();
		Assert.assertTrue(placeholder.contains("Adresse, z.B. Kirchstraße 1"));

		String errorMessage = objBlankSearch.blankSearchError();
		Assert.assertTrue(
				errorMessage.contains("Die eingegebene Adresse ist leider inkorrekt. Probiere es noch einmal."));
	}

	@Test(priority = 2)
	public void invalidSearchByZipcode() throws Exception {

		LieferendoHomePage objSearchByInvalidZip = new LieferendoHomePage(driver);
		objSearchByInvalidZip.clickHomeLogo();
		String invalidZipcodes[] = { "88888", "505050505050" };

		for (int i = 0; i < invalidZipcodes.length; i++) {
			objSearchByInvalidZip.enterTextInSearchField(invalidZipcodes[i]);
			objSearchByInvalidZip.pressEnterInSearch();

			String suggestionMessage = objSearchByInvalidZip.suggestionsText();
			Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));

		}

	}

	@Test(priority = 3)
	public void validSearchByZipcode() throws Exception {

		LieferendoHomePage objSearchByZip = new LieferendoHomePage(driver);
		objSearchByZip.clickHomeLogo();
		objSearchByZip.enterTextInSearchField("12207");

		String suggestionMessage = objSearchByZip.suggestionsText();
		Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));

		objSearchByZip.pressEnterInSearch();

		String counterMessage = objSearchByZip.counterText();
		Assert.assertTrue(counterMessage.contains("Restaurants"));
		
		String searchFieldText = objSearchByZip.searchFieldTextInResultPage();
		Assert.assertTrue(searchFieldText.contains("Berlin, 12207"));

	}

	@Test(priority = 4)
	public void invalidsearchByAddress() throws Exception {

		LieferendoHomePage objSearchByInvlidAddress = new LieferendoHomePage(driver);
		objSearchByInvlidAddress.clickHomeLogo();
		objSearchByInvlidAddress.enterTextInSearchField("abccd 123");
		objSearchByInvlidAddress.pressEnterInSearch();

		String suggestionMessage = objSearchByInvlidAddress.suggestionsText();
		Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));
	}

	@Test(priority = 5)
	public void validsearchByAddress() throws Exception {

		LieferendoHomePage objSearchByAddress = new LieferendoHomePage(driver);
		objSearchByAddress.clickHomeLogo();
		objSearchByAddress.enterTextInSearchField("Goerzallee 108");
		
		String autoContentText = objSearchByAddress.autoCompleteContentText();
		Assert.assertTrue(autoContentText.contains("Goerzallee 108, Berlin"));
		
		objSearchByAddress.clickAutoCompleteDropdownContent();

		String counterMessage = objSearchByAddress.counterText();
		Assert.assertTrue(counterMessage.contains("Restaurants"));
		
		String searchFieldText = objSearchByAddress.searchFieldTextInResultPage();
		Assert.assertTrue(searchFieldText.contains("Goerzallee 108, Berlin"));

	}

	@Test(priority = 6)
	public void invalidSearchByRandomAndSpecialCharacters() throws Exception {

		LieferendoHomePage objinvalidSearchByRandomAndSpecialCharacters = new LieferendoHomePage(driver);
		objinvalidSearchByRandomAndSpecialCharacters.clickHomeLogo();
		String invalidInputs[] = { "s463457dbshdhf7567eugerogbv", "§$%&&//§%§$%$/$&" };

		for (int i = 0; i < invalidInputs.length; i++) {
			objinvalidSearchByRandomAndSpecialCharacters.enterTextInSearchField(invalidInputs[i]);
			objinvalidSearchByRandomAndSpecialCharacters.pressEnterInSearch();

			String suggestionMessage = objinvalidSearchByRandomAndSpecialCharacters.suggestionsText();
			Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));

		}

	}

	@Test(priority = 7)
	public void invalidSearchBySqlQuery() throws Exception {

		LieferendoHomePage objinvalidSearchBySqlQuery = new LieferendoHomePage(driver);
		objinvalidSearchBySqlQuery.clickHomeLogo();

		objinvalidSearchBySqlQuery.enterTextInSearchField("Select * from Customer");
		objinvalidSearchBySqlQuery.pressEnterInSearch();

		String suggestionMessage = objinvalidSearchBySqlQuery.suggestionsText();
		Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));

	}

	@Test(priority = 8)
	public void searchInputLimit() throws Exception {

		LieferendoHomePage objsearchInputLimit = new LieferendoHomePage(driver);
		objsearchInputLimit.clickHomeLogo();
		//Inserting more than 100 character
		objsearchInputLimit.enterTextInSearchField(
				"1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		objsearchInputLimit.pressEnterInSearch();

		String suggestionMessage = objsearchInputLimit.suggestionsText();
		Assert.assertTrue(suggestionMessage.contains("Bitte gib Deine Straße und Hausnummer ein"));
		//Verifying that it takes 100 character maximum
		String inputText = objsearchInputLimit.searchFieldInputText();
		Assert.assertTrue(inputText.equals(
				"1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"));

	}
	
	@AfterTest
	public void closeDriver() {
		
		driver.quit();
	
	}
}
