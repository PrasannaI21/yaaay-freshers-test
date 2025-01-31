package com.zenken.freshers.pages.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EventsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EventsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1")
	WebElement headline;
	
	@FindBy(css="[class='u-d-f u-ai-c']")
	WebElement logoutBt;
	
	public String getComEventsHeadline()
	{
		return headline.getText();
	}
	
	public void clickLogout()
	{
		logoutBt.click();
	}
}
