package com.zenken.freshers.pages.user;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditProjectsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditProjectsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="(//div[contains(.,'Project')])[3]")
	WebElement headline;
	
	@FindBy(id="title")
	WebElement titleTb;
	
	@FindBy(id="startMonth")
	WebElement startMonthDd;
	
	@FindBy(id="startYear")
	WebElement startYearDd;
	
	@FindBy(id="isCurrentlyWorking")
	WebElement checkbox;
	
	@FindBy(id="endMonth")
	WebElement endMonthDd;
	
	@FindBy(id="endYear")
	WebElement endYearDd;
	
	@FindBy(id="description")
	WebElement detailsTb;
	
	@FindBy(id="descriptionCharacterCount")
	WebElement charCount;
	
	@FindBy(id="link")
	WebElement linkTb;
	
	@FindBy(css="[type=file]")
	WebElement fileInput;
	
	@FindBy(css="[class=uk-progress]")
	WebElement progressBar;
	
	@FindBy(id="fileList")
	WebElement files;
	
	@FindBy(css="#fileList [class=u-mt-10]:last-child")
	WebElement file;
	
	@FindBy(css="[class=delete-button]")
	WebElement fileDelete;
	
	@FindBy(css="[class=u-w-100p]")
	WebElement fileBox;
	
	@FindBy(xpath="//form[contains(.,'Delete Pro')]")
	WebElement deleteProject;
	
	@FindBy(css="[role=dialog]")
	WebElement deletePopUp;
	
	@FindBy(css="[class*='small -p']")
	WebElement delete;
	
	@FindBy(xpath="(//div)[10]")
	WebElement titleBox;
	
	@FindBy(css=".u-w-50p-pc.u-mt-30:nth-of-type(2)")
	WebElement startDateBox;
	
	@FindBy(css="[class=uk-form-control]")
	WebElement endDateBox;
	
	@FindBy(css="[class=u-mt-30]")
	WebElement detailsBox;
	
	@FindBy(css="[class=u-mt-20]")
	WebElement linkBox;
	
	@FindBy(css="[class=u-fw-b]")
	WebElement selectFiles;
	
	@FindBy(css="[class*='remove']")
	WebElement error;
	
	public String getProjectHeadline()
	{
		return headline.getText();
	}
	
	public List<String> getProjectPlaceholders()
	{
		List<WebElement> textBoxes = Arrays.asList(titleTb, detailsTb, linkTb);		
		List<String> attributes = new ArrayList<String>();
		for(WebElement element :  textBoxes)
		{
			String text = element.getDomProperty("placeholder");
			attributes.add(text);
		}
		return attributes;
	}
	
	public void clickEndDateCheckBox()
	{
		checkbox.click();
	}
	
	public boolean isEndMonthDdActive()
	{
		return endMonthDd.isEnabled();
	}
	
	public boolean isEndYearDdActive()
	{
		return endYearDd.isEnabled();
	}
	
	public void enterProjectTitle(String text)
	{
		titleTb.clear();
		titleTb.sendKeys(text);
	}
	
	public void selectStartMonth(String month)
	{
		selectDropdown(startMonthDd, month);
	}
	
	public void selectStartYear(String year)
	{
		selectDropdown(startYearDd, year);
	}
	
	public void selectEndMonth(String month)
	{
		selectDropdown(endMonthDd, month);
	}
	
	public void selectEndYear(String year)
	{
		selectDropdown(endYearDd, year);
	}
	
	public void enterProjectOverview(String text)
	{
		detailsTb.clear();
		if(text.length() > 5000)
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value = arguments[1];", detailsTb, text);
		}else {
			detailsTb.sendKeys(text);
		}
	}
	
	public void enterProjectLink(String text)
	{
		linkTb.clear();
		linkTb.sendKeys(text);
	}
	
	public void uploadFile(String filePath)
	{
		fileInput.sendKeys(filePath);
	}
	
	public boolean isProgressBarDisplayed()
	{
		waitUntilElementAppears(progressBar);
		return progressBar.isDisplayed();
	}
	
	public boolean isUploadComplete()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		return wait.until(driver -> progressBar.getDomProperty("value").equals("100"));
	}
	
	public String getFileNameText()
	{
		waitUntilElementAppears(files);
		return files.getText();
	}
	
	public String getProjectTitleText()
	{
		return titleTb.getDomProperty("value");
	}
	
	public String getStartMonthOption()
	{
		return getDropdownText(startMonthDd);
	}
	
	public String getStartYearOption()
	{
		return getDropdownText(startYearDd);
	}
	
	public String getEndMonthOption()
	{
		return getDropdownText(endMonthDd);
	}
	
	public String getEndYearOption()
	{
		return getDropdownText(endYearDd);
	}
	
	public String getProjectCharCount()
	{
		return charCount.getText();
	}
	
	public void clearProjectDetails()
	{
		detailsTb.clear();
	}
	
	public void enterProjectOverview2(String text)
	{
		detailsTb.sendKeys(text);
	}
	
	public String getProjectFileText(String firstFile, String fileName, String path)
	{
		if(path == firstFile)
		{
			waitUntilElementAppears(file);
		}else {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until((ExpectedCondition<Boolean>) driver -> 
			{return file.getText().contains(fileName);});
		}
		return file.getText();
	}
	
	public void clickProjectFileDelete()
	{
		clickByJavaScript(fileDelete);
	}
	
	public boolean isProjectFileDeleted(String fileName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		boolean isDeleted = wait.until((ExpectedCondition<Boolean>) driver -> 
		{return !fileBox.getText().contains(fileName);});
		return isDeleted;
	}
	
	public void addProject(String title, String startMonth, String startYear)
	{
		enterProjectTitle(title);
		selectStartMonth(startMonth);
		selectStartYear(startYear);
		clickEndDateCheckBox();
		clickSave();
	}
	
	public void clickProjectDeleteLink()
	{
		clickByJavaScript(deleteProject);
	}
	
	public void waitForGodDamnPopUp()
	{
		waitUntilElementAppears(deletePopUp);
	}
	
	public String getProjectPopUpText()
	{
		return deletePopUp.getText();
	}
	
	public void clickDeleteProject()
	{
		clickByJavaScript(delete);
	}
	
	public String getTitleErrorText()
	{
		return titleBox.getText();
	}
	
	public String getEndDateErrorText()
	{
		return endDateBox.getText();
	}
	
	public boolean isCheckBoxSelected()
	{
		return checkbox.isSelected();
	}
	
	public String getProjectDetailsErrorText()
	{
		return detailsBox.getText();
	}
	
	public String getProjectLinkErrorText()
	{
		return linkBox.getText();
	}
	
	public boolean isSelectFileActive()
	{
		return fileInput.isEnabled();
	}
	
	public String getStartDateErrorText()
	{
		return startDateBox.getText();
	}
	
	public String getProjectFileErrorText()
	{
		waitUntilElementAppears(error);
		return fileBox.getText();
	}
	
	public void deleteProject()
	{
		clickProjectDeleteLink();
		clickDeleteProject();
	}
}
