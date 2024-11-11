package com.zenken.frehsers.abstractcomponents;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.freshers.pages.user.LoginPage;

public class WebDriverUtils {

	WebDriver driver;
	
	public WebDriverUtils(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//header//img[1]")
	WebElement headerImage;
	
	@FindBy(linkText="Terms")
	WebElement terms;
	
	@FindBy(linkText="Privacy Policy")
	WebElement policy;
	
	@FindBy(css="[class*='u-mb-30']")
	WebElement copyright;
	
	@FindBy(css="[alt='Account icon']")
	WebElement icon;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	@FindBy(xpath="//a[contains(.,'Profile')]")
	WebElement profile;
	
	@FindBy(xpath="//a[contains(.,'Log')]")
	WebElement logout;
	
	@FindBy(css="span [class=u-c-red]")
	List<WebElement> requiredToApplyMarks;
	
	@FindBy(css="[type=submit]")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	public void waitUntilElementAppears(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public WebElement getHeaderImage()
	{
		return headerImage;
	}
	
	public String getImageAttribute()
	{
		String imgSrc = headerImage.getAttribute("src");
		return imgSrc;
	}
	
	public String getAltAttribute()
	{
		String imageAlt = headerImage.getAttribute("alt");
		return imageAlt;
	}
	
	public void clickByJavaScript(WebElement ele)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", ele);
	}
	
	public void openInNewTab(String link)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.open(arguments[0])", link);
	}
	
	public void clickTerms()
	{
		clickByJavaScript(terms);
	}
	
	public void clickPolicy()
	{
		clickByJavaScript(policy);
	}
	
	public WebElement getCopyrightMark()
	{
		return copyright;
	}
	
	public void loginUser(String emailId, String pass)
	{
		LoginPage loginPage = new LoginPage(driver);
		WebElement id = loginPage.email;
		id.sendKeys(emailId);
		WebElement password = loginPage.password;
		password.sendKeys(pass);
		WebElement login = loginPage.logIn;
		login.click();
	}
	
	public void hoverOver(WebElement ele)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).perform();
	}
	
	public WebElement getIcon()
	{
		return icon;
	}
	
	public String getAlert()
	{
		waitUntilElementAppears(alert);
		return alert.getText();
	}
	
	public List<WebElement> getDropdownOptions()
	{
		List<WebElement> elements = new ArrayList<>();
		elements.add(profile);
		elements.add(logout);
		return elements;
	}
	
	public void clickProfile()
	{
		profile.click();
	}
	
	public void clickLogOut()
	{
		logout.click();
	}
	
	public void selectDropdown(WebElement ele, String text)
	{
		Select select = new Select(ele);
		select.selectByVisibleText(text);
	}
	
	public void selectDropdownByIndex(WebElement ele, int index)
	{
		Select select = new Select(ele);
		select.selectByIndex(index);
	}
	
	public String getDropdownText(WebElement ele)
	{
		Select select = new Select(ele);
		String selectedText = select.getFirstSelectedOption().getText();
		return selectedText;
	}
	
	public ArrayList<String> switchTabs(int i)
	{
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(i));
		return tabs;
	}
	
	public String extractLinkFromMail(String url, String emailBody)
	{
		String urlStart = url;
		int startIndex = emailBody.indexOf(urlStart);
		int endIndex = emailBody.indexOf("\n", startIndex);
		String link = emailBody.substring(startIndex, endIndex);
		return link;
	}
	
	public String getPageTitle()
	{
		String title = driver.getTitle();
		return title;
	}
	
	public boolean getState(WebElement ele, WebElement ele2)
	{
		ele.click();
		boolean state = ele2.isEnabled();
		return state;
	}
	
	public Map<String, Boolean> getDropdownTextBoxStates(WebElement dropdown, WebElement textBox)
	{
		Map<String, Boolean> optionsStateMap = new LinkedHashMap<>();
		Select select = new Select(dropdown);
		int numberOfOptions = select.getOptions().size();
		for(int i=0; i<numberOfOptions; i++)
		{
			select.selectByIndex(i);
			String optionText = select.getOptions().get(i).getText();
			boolean isTextBoxActive = textBox.isEnabled();
			optionsStateMap.put(optionText, isTextBoxActive);
		}
		return optionsStateMap;
	}
	
	public String monitorDownloadLink(int maxRetries, List<WebElement> links) throws InterruptedException
	{
		int linkCount = links.size();
		int retryCount = 0;
		while(retryCount < maxRetries)
		{
			driver.navigate().refresh();
			int currentLinkCount = links.size();
			if(currentLinkCount > linkCount)
			{	
				return links.get(currentLinkCount -1).getText();
			}else {
				retryCount++;
				Thread.sleep(3000);
			}
		}
		throw new RuntimeException("New download link did not appear after "+maxRetries+" attempts.");
	}
	
	public void clearText(WebElement ele)
	{
		ele.clear();
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public boolean isSectionComplete()
	{
		return !requiredToApplyMarks.isEmpty() && requiredToApplyMarks.get(0).isDisplayed();
	}
	
}
