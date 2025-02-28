package com.zenken.freshers.pages.company;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class JobSelectionPage extends WebDriverUtils{

	WebDriver driver;
	
	public JobSelectionPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class='u-ml-5']")
	List<WebElement> applicants;
	
	@FindBy(css="[class*='-screen']")
	List<WebElement> results;
	
	@FindBy(css="[id*='selectJobs']")
	WebElement jobDd;
	
	public List<String> getCandNumbers()
	{
		List<String> texts = new ArrayList<>();
		texts.add(applicants.get(0).getText());
		texts.add(applicants.get(1).getText());
		return texts;
	}
	
	public List<String> getResults()
	{
		List<String> texts = new ArrayList<>();
		texts.add(results.get(0).getText());
		texts.add(results.get(1).getText());
		return texts;
	}
	
	public void selectJob()
	{
		selectDropdown(jobDd, "Big Data Engineer");
	}
}
