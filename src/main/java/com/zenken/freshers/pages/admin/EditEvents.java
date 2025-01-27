package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditEvents extends WebDriverUtils{

	WebDriver driver;
	
	public EditEvents(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="applicationEndAt")
	WebElement appEnd;
	
	public void setApplicationDeadline(String dateTime)
	{
		appEnd.sendKeys(dateTime);
	}
}
