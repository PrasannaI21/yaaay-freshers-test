package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class NewEvents extends WebDriverUtils{

	WebDriver driver;
	
	public NewEvents(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[name=name]")
	WebElement eventNameTb;
	
	@FindBy(id="graduationYear")
	WebElement gradYearDd;
	
	@FindBy(id="companyId")
	WebElement companyDd;
	
	@FindBy(id="applicationStartAt")
	WebElement appStartTb;
	
	@FindBy(id="applicationEndAt")
	WebElement appEndTb;
	
	@FindBy(id="eventStartDate")
	WebElement eventStartTb;
	
	@FindBy(id="eventEndDate")
	WebElement eventEndTb;
	
	@FindBy(id="type")
	WebElement eventTypeDd;
	
	public void enterEventName(String eventName)
	{
		eventNameTb.sendKeys(eventName);
	}
	
	public void selectGradYear(int year)
	{
		selectDropdownByIndex(gradYearDd, year);
	}
	
	public void selectCompany(String companyName)
	{
		selectDropdown(companyDd, companyName);
	}
	
	public void enterAppStartDate(String date)
	{
		appStartTb.sendKeys(date);
	}
	
	public void enterAppEndDate(String date)
	{
		appEndTb.sendKeys(date);
	}
	
	public void enterEventStartDate(String date)
	{
		eventStartTb.sendKeys(date);
	}
	
	public void enterEventEndDate(String date)
	{
		eventEndTb.sendKeys(date);
	}
	
	public void selectEventType(int index)
	{
		selectDropdownByIndex(eventTypeDd, index);
	}
}
