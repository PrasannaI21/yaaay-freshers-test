package com.zenken.freshers.pages.company;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ApplicantDetailsPage extends WebDriverUtils{

	WebDriver driver;
	
	public ApplicantDetailsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='20 u-pl']")
	WebElement topText;
	
	@FindBy(css="[class*='2 u']")
	List<WebElement> appDates;
	
	public String getTopText()
	{
		return topText.getText();
	}
	
	public List<String> getAppDates()
	{
		List<String> dates = new ArrayList<>();
		dates.add(appDates.get(0).getText());
		dates.add(appDates.get(1).getText());
		return dates;
	}
}
