package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class FinalResultEditPage extends WebDriverUtils{

	WebDriver driver;
	
	public FinalResultEditPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[for=Passed]")
	WebElement passedBt;
	
	@FindBy(css="[for=Substitute]")
	WebElement substituteBt;
	
	public void clickPassed()
	{
		passedBt.click();
	}
	
	public void clickSubstitute()
	{
		substituteBt.click();
	}
	
	
}
