package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class SettingsNew extends WebDriverUtils{

	WebDriver driver;
	
	public SettingsNew(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[name=name]")
	WebElement companyTb;
	
	@FindBy(id="email")
	WebElement emailTb;
	
	public void enterComName(String name)
	{
		companyTb.sendKeys(name);
	}
	
	public void enterComEmail(String email)
	{
		emailTb.sendKeys(email);
	}
}
