package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EventDetailsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EventDetailsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[contains(.,'求人票')]")
	WebElement jobPostBt;
	
	public void clickjobPostBt()
	{
		jobPostBt.click();
	}
}
