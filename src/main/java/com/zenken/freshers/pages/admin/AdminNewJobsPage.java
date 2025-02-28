package com.zenken.freshers.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class AdminNewJobsPage extends WebDriverUtils{

	WebDriver driver;
	
	public AdminNewJobsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[name=jobTitle]")
	WebElement jobTitleDd;
	
	@FindBy(id="vacancies")
	WebElement vacanciesTb;
	
	@FindBy(id="jobDescription")
	WebElement jobDesTb;
	
	@FindBy(id="detailsOfJob")
	WebElement jobDetailsTb;
	
	@FindBy(id="requiredSkillsDetails")
	WebElement skillDetailsTb;
	
	@FindBy(id="softSkills")
	WebElement softSkillsTb;
	
	@FindBy(css="[name*='emp']")
	WebElement empTypeDd;
	
	@FindBy(css="[name*='jobC']")
	WebElement jobContractPeriodDd;
	
	@FindBy(id="locations1")
	WebElement hokkaidoCb;
	
	@FindBy(id="hasMeasuresAgainstPassiveSmoking1")
	WebElement smoking1Rb;
	
	@FindBy(id="hasRemoteWorkAfterJoining1")
	WebElement remoteWork1Rb;
	
	@FindBy(id="monthlySalary")
	WebElement monthlySalaryTb;
	
	@FindBy(id="annualSalary")
	WebElement annualSalaryTb;
	
	@FindBy(id="hasBonus1")
	WebElement bonus1Rb;
	
	@FindBy(id="hasRaise1")
	WebElement raise1Rb;
	
	@FindBy(id="payroll")
	WebElement payrollTb;
	
	@FindBy(id="workingDays")
	WebElement workingDaysTb;
	
	@FindBy(id="workHours")
	WebElement workHoursTb;
	
	@FindBy(id="breakTime")
	WebElement breakTimeTb;
	
	@FindBy(id="hasJapaneseEducation1")
	WebElement JapEdu1Rb;
	
	public void createNewJob(String jobTitle)
	{
		selectDropdown(jobTitleDd, jobTitle);
		vacanciesTb.sendKeys("10");
		jobDesTb.sendKeys("This is job description for this position.");
		jobDetailsTb.sendKeys("These are job details for this position.");
		skillDetailsTb.sendKeys("These are skill details for this position.");
		softSkillsTb.sendKeys("These are soft skills for this position.");
		selectDropdownByIndex(empTypeDd, 1);
		selectDropdownByIndex(jobContractPeriodDd, 1);
		clickByJavaScript(hokkaidoCb);
		clickByJavaScript(smoking1Rb);
		clickByJavaScript(remoteWork1Rb);
		monthlySalaryTb.sendKeys("500000");
		annualSalaryTb.sendKeys("70000000");
		clickByJavaScript(bonus1Rb);
		clickByJavaScript(raise1Rb);
		payrollTb.sendKeys("You will get paid!");
		workingDaysTb.sendKeys("Monday to Friday");
		workHoursTb.sendKeys("8 hours");
		breakTimeTb.sendKeys("1 hour for lunch.");
		clickByJavaScript(JapEdu1Rb);
		clickSave();
	}
}
