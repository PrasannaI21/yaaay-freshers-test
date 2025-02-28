package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class JobsDetailsPage extends WebDriverUtils{

	WebDriver driver;
	
	public JobsDetailsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[contains(.,'公開')]")
	WebElement jobStatusBt;
	
	@FindBy(css="[role=dialog]")
	WebElement jobStatusPopup;
	
	@FindBy(css="[class*='arrow-back']")
	WebElement backBt;
	
	@FindBy(css="[class*='link u-fw']")
	WebElement jobUrl;
	
	public void changeJobStatus()
	{
		jobStatusBt.click();
		waitUntilElementAppears(jobStatusPopup);
		clickSave();
	}
	
	public void clickBack()
	{
		backBt.click();
	}
	
	public String getJobUrl()
	{
		return jobUrl.getText();	
	}
}
