package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ForgotPasswordPage extends WebDriverUtils{

	WebDriver driver;
	
	public ForgotPasswordPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1[class*='28']")
	WebElement headline;
	
	@FindBy(css="[class='uk-text-center']")
	WebElement mainText;
	
	@FindBy(id="email")
	WebElement emailBox;
	
	@FindBy(css="[type='submit']")
	WebElement send;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	@FindBy(css="[class*='red']")
	WebElement errors;
	
	public String getTitleName()
	{
		String title = driver.getTitle();
		return title;
	}
	
	public String getHeadLineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public String getMainText()
	{
		String text = mainText.getText();
		return text;
	}
	
	public void enterEmail(String email)
	{
		emailBox.sendKeys(email);
	}
	
	public String getAlertText()
	{
		waitUntilElementAppears(alert);
		String text = alert.getText();
		return text;
	}
	
	public void clickSend()
	{
		send.click();
	}
	
	public String extractPasswordResetLink(String emailBody)
	{
		String link = extractLinkFromMail("https://freshers.dspf-dev.com/reset-password/", emailBody);
		return link;
	}
	
	public String getErrorText()
	{
		String text = errors.getText();
		return text;
	}
	
}
