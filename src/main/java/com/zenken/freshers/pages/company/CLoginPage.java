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
	
	
}
