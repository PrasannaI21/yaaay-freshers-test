package com.zenken.freshers.pages.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class CLoginPage extends WebDriverUtils{

	WebDriver driver;
	
	public CLoginPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="email")
	WebElement emailTb;
	
	@FindBy(id="password")
	WebElement passwordTb;
	
	@FindBy(css="[class*='500']")
	WebElement headline;
	
	@FindBy(linkText="パスワード再設定")
	WebElement resetLink;
	
	@FindBy(xpath="(//div)[8]")
	WebElement mailBox;
	
	@FindBy(css="[class*='m-c']")
	WebElement passwordBox;
	
	public String getComHeadline()
	{
		return headline.getText();
	}
	
	public void enterComEmail(String email)
	{
		emailTb.sendKeys(email);
	}
	
	public void enterComPassword(String password)
	{
		passwordTb.sendKeys(password);
	}
	
	public void clickResetLink()
	{
		resetLink.click();
	}
	
	public String getMailBoxText()
	{
		return mailBox.getText();
	}
	
	public String getPasswordBoxText()
	{
		return passwordBox.getText();
	}
	
	public void loginCompany(String email, String password)
	{
		enterComEmail(email);
		enterComPassword(password);
		clickSave();
	}
}
