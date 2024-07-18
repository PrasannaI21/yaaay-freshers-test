package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ResetPasswordCompletePage extends WebDriverUtils{

	WebDriver driver;
	
	public ResetPasswordCompletePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h1[contains(.,'been')]")
	WebElement headline;
	
	@FindBy(css="a[class*='button']")
	WebElement logIn;
	
	public String getHeadlineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public String clickLogIn()
	{
		logIn.click();
		String url = driver.getCurrentUrl();
		return url;
	}
}
