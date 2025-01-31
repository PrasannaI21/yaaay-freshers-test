package com.zenken.freshers.pages.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class CForgotPasswordPage extends WebDriverUtils{

	WebDriver driver;
	
	public CForgotPasswordPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h2[contains(.,'忘れ')]")
	WebElement headline;
	
	@FindBy(id="email")
	WebElement emailTb;
	
	@FindBy(css="[class*='m-c']")
	WebElement emailBox;
	
	@FindBy(css="[role=alert]")
	WebElement alert;
	
	public String getComHeadline()
	{
		return headline.getText();
	}
	
	public void enterEmail(String email)
	{
		emailTb.sendKeys(email);
	}
	
	public String getComEmailBoxText()
	{
		return emailBox.getText();
	}
	
	public String getComAlertText()
	{
		return alert.getText();
	}
}
