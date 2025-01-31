package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class CompanyDetailsPage extends WebDriverUtils{

	WebDriver driver;
	
	public CompanyDetailsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tbody tr:nth-child(1) td:nth-child(4)")
	WebElement password;
	
	public String getComUserPassword()
	{
		return password.getText();
	}
}
