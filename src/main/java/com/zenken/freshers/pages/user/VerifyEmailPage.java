package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class VerifyEmailPage extends WebDriverUtils{

	WebDriver driver;
	
	public VerifyEmailPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='28']")
	WebElement title;
	
	@FindBy(css="[class*='320']")
	WebElement mainText;
	
	@FindBy(css="[class='link']")
	WebElement resend;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	public String getTitle()
	{
		String text = title.getText();
		return text;
	}
	
	public String getMainText()
	{
		String text = mainText.getText();
		return text;
	}
	
	public void clickResend()
	{
		resend.click();
	}
	
	public String getAlert()
	{
		waitUntilElementAppears(alert);
		String text = alert.getText();
		return text;
	}
	
}
