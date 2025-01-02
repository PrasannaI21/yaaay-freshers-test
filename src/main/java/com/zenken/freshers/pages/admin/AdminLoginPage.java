package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class AdminLoginPage extends WebDriverUtils{

	WebDriver driver;
	
	public AdminLoginPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="email")
	WebElement emailTb;
	
	@FindBy(id="password")
	WebElement passwordTb;
	
	public void loginAdmin()
	{
		emailTb.sendKeys("admin@example.com");
		passwordTb.sendKeys("Password_1");
		clickSave();
	}
	
	public void enterPassword()
	{
		
	}
	
	public void clickLogin()
	{
		
	}
}
