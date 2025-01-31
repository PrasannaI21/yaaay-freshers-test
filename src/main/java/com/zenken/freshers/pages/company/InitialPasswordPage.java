package com.zenken.freshers.pages.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class InitialPasswordPage extends WebDriverUtils{

	WebDriver driver;
	
	public InitialPasswordPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h2[contains(.,'新')]")
	WebElement headline;
	
	@FindBy(id="password")
	WebElement passwordTb;
	
	@FindBy(xpath="//h2[contains(.,'変更が完了')]")
	WebElement headline2;
	
	@FindBy(xpath="//a[.='ログイン']")
	WebElement loginBt;
	
	@FindBy(css="form [class=u-mt-20]")
	WebElement passwordBox;
	
	public String getComHeadline()
	{
		return headline.getText();
	}
	
	public void enterComPassword(String password)
	{
		passwordTb.sendKeys(password);
	}
	
	public String getComHeadline2()
	{
		return headline2.getText();
	}
	
	public void clickLogin()
	{
		loginBt.click();
	}
	
	public String getPasswordBoxText()
	{
		return passwordBox.getText();
	}
}
