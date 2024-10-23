package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditSettingsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditSettingsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='black']")
	WebElement headline;
	
	@FindBy(css="[for*='Mid']")
	WebElement setting1;
	
	@FindBy(css="[for*='News']")
	WebElement setting2;
	
	@FindBy(css="[id*='Con']")
	WebElement setCb1;
	
	@FindBy(css="[id*='Sub']")
	WebElement setCb2;
	
	public String getSettingsHeadline()
	{
		return headline.getText();
	}
	
	public String getSetting1Text()
	{
		return setting1.getText();
	}
	
	public String getSetting2Text()
	{
		return setting2.getText();
	}
	
	public void selectSetCb1()
	{
		if(!setCb1.isSelected()) {
			setCb1.click();
		}
	}
	
	public void selectSetCb2()
	{
		if(!setCb2.isSelected()) {
			setCb2.click();
		}
	}
	
	public void deSelectSetCb1()
	{
		if(setCb1.isSelected()) {
			setCb1.click();
		}
	}
	
	public void deSelectSetCb2()
	{
		if(setCb2.isSelected()) {
			setCb2.click();
		}
	}
}
