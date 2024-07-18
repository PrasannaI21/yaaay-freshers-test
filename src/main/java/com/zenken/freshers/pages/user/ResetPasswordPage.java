package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ResetPasswordPage extends WebDriverUtils{

	WebDriver driver;
	
	public ResetPasswordPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1[class*='28']")
	WebElement headline;
	
	@FindBy(id="password")
	WebElement passwordBox;
	
	@FindBy(css="[class*='list']")
	WebElement conditions;
	
	@FindBy(css="[type='submit']")
	WebElement change;
	
	@FindBy(css="[class*='red']")
	WebElement errors;
	
	@FindBy(css="div[class*='red']")
	WebElement token;
	
	public String getHeadlineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public void enterPassword(String password)
	{
		passwordBox.sendKeys(password);
	}
	
	public String clickChangePassword()
	{
		change.click();
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public String getErrorText()
	{
		String text = errors.getText();
		return text;
	}
	
	public String getinvalidTokenText()
	{
		String text = token.getText();
		return text;
	}
}
