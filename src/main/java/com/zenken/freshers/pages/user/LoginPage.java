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
	
	
	
}
