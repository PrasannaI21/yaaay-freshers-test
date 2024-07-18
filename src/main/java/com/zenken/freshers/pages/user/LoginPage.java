package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class LoginPage extends WebDriverUtils{

	WebDriver driver;
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="email")
	public WebElement email;
	
	@FindBy(id="password")
	public WebElement password;
	
	@FindBy(css="[type='submit']")
	public WebElement logIn;
	
	@FindBy(css="[class*='fz-28']")
	public WebElement headline;
	
	@FindBy(xpath="//a[contains(.,'Forgot')]")
	public WebElement link1;
	
	@FindBy(xpath="//a[contains(.,'an')]")
	public WebElement link2;
	
	@FindBy(css="[class*='ib']")
	public WebElement invalidEmail;
	
	@FindBy(xpath="//span[contains(.,'This')]")
	public WebElement required;
	
	@FindBy(css="[class*='red']")
	public WebElement notMatch;
	
	public String getTitleName()
	{
		String title = driver.getTitle();
		return title;
	}
	
	public void enterEmailId(String text)
	{
		email.sendKeys(text);
	}
	
	public void enterPassword(String text)
	{
		password.sendKeys(text);
	}
	
	public String clickLogIn()
	{
		logIn.click();
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public String getHeadLineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public String clickForgotPasswordLink()
	{
		link1.click();
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public String clickCreateAccountLink()
	{
		link2.click();
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public String getInvalidEmailText()
	{
		String text = invalidEmail.getText();
		return text;
	}
	
	public String getRequiredText()
	{
		String text = required.getText();
		return text;
	}
	
	public String getNotMatchText()
	{
		String text = notMatch.getText();
		return text;
	}
	
}
