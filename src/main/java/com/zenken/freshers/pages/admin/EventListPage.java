package com.zenken.freshers.pages.admin;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EventListPage extends WebDriverUtils{

	WebDriver driver;
	
	public EventListPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="span a")
	List<WebElement> links;
	
	public void clickFirstEvent()
	{
		links.get(0).click();
	}
}
