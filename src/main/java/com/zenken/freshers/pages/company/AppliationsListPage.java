package com.zenken.freshers.pages.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class AppliationsListPage extends WebDriverUtils{

	WebDriver driver;
	
	public AppliationsListPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="csvDownloadLink")
	WebElement csvDlLink;
	
	@FindBy(xpath="(//div[@role='dialog'])[4]")
	WebElement csvpopup;

	@FindBy(id="buttonCsvDownload")
	WebElement csvpopupBt;
	
	@FindBy(id="messageArea")
	WebElement csvAlert;
	
	@FindBy(css="[for*='searchJobs']")
	List<WebElement> jobTitles;
	
	public void clickCsvDlLink()
	{
		csvDlLink.click();
	}
	
	public String getCsvLinkText()
	{
		return csvDlLink.getText();
	}
	
	public String getCsvPopup()
	{
		waitUntilElementAppears(csvpopup);
		return csvpopup.getText();
	}
	
	public void clickCsvDlBt()
	{
		csvpopupBt.click();
	}
	
	public String getCsvDlAlertText()
	{
		waitUntilElementAppears(csvAlert);
		return csvAlert.getText();
	}
	
	public List<String> getComJobTitlesWoSpace()
	{
		List<String> jobs = new ArrayList<>();
		for(WebElement jobTitle : jobTitles) {
			String job = jobTitle.getText().replaceAll("\\s", "");
			jobs.add(job);
		}
		Collections.sort(jobs);
		return jobs;
	}
	
	public List<String> getComJobTitles()
	{
		List<String> jobs = new ArrayList<>();
		for(WebElement jobTitle : jobTitles) {
			jobs.add(jobTitle.getText());
		}
		return jobs;
	}
	
	public int getComJobs()
	{
		return jobTitles.size();
	}

}
