package com.zenken.frehsers.abstractcomponents;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.freshers.pages.user.LoginPage;

public class WebDriverUtils {

	WebDriver driver;
	
	public String username = "yaaayfreshersuser";
	public String password = "Kajibyw6.";
	public String domain = "freshers.dspf-dev.com";
	public String url;
	String subDomain;
	
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
	
	@FindBy(xpath="//a[contains(.,'Profile')]")
	WebElement profile;
	
	@FindBy(xpath="//a[contains(.,'Log')]")
	WebElement logout;
	
	public String formUrl(String subDomain)
	{
		url = "https://" + username + ":" + password + "@" + domain + subDomain;
		return url;
	}
	
	public void goToUser()
	{
		String url = formUrl("/");
		driver.get(url);
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
	
}
