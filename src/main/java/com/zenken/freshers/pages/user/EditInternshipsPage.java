package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditInternshipsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditInternshipsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="(//div[contains(.,'Intern')])[3]")
	WebElement headline;
	
	@FindBy(css="[name*=StartMonth]")
	WebElement startMonthDd;
	
	@FindBy(css="[name*=StartYear]")
	WebElement startYearDd;
	
	@FindBy(css="[name*=EndMonth]")
	WebElement endMonthDd;
	
	@FindBy(css="[name*=EndYear]")
	WebElement endYearDd;
	
	@FindBy(id="description")
	WebElement intDetailsTb;
	
	@FindBy(id="isCurrentlyWorking")
	WebElement endDateCheckbox;
	
	@FindBy(id="descriptionCharacterCount")
	WebElement charCount;
	
	@FindBy(xpath="//div[(@class='uk-form-control')]")
	WebElement endDateBox;
	
	@FindBy(xpath="//div[(@class='u-mt-30')]")
	WebElement intDetailsBox;
	
	@FindBy(xpath="//form[contains(.,'Delete Int')]")
	WebElement deleteInt;
	
	@FindBy(css="[role=dialog]")
	WebElement deleteIntPopUp;
	
	@FindBy(xpath="//button[contains(.,'Delete')]")
	WebElement delete;
	
	public String getInternshipHeadline()
	{
		return headline.getText();
	}
	
	public void selectStartMonth(String month)
	{
//		selectDropdownByIndex(startMonthDd, index);
		selectDropdown(startMonthDd, month);
	}
	
	public void selectStartYear(String year)
	{
//		selectDropdownByIndex(startYearDd, index);
		selectDropdown(startYearDd, year);
	}
	
	public void selectEndMonth(String month)
	{
//		selectDropdownByIndex(endMonthDd, index);
		selectDropdown(endMonthDd, month);
	}
	
	public void selectEndYear(String year)
	{
//		selectDropdownByIndex(endYearDd, index);
		selectDropdown(endYearDd, year);
	}
	
	public String getIntPlaceholder()
	{
		return intDetailsTb.getAttribute("placeholder");
	}
	
	public void clickEndDateCheckbox()
	{
		endDateCheckbox.click();
	}
	
	public boolean isEndMonthDdActive()
	{
		return endMonthDd.isEnabled();
	}
	
	public boolean isEndYearDdActive()
	{
		return endYearDd.isEnabled();
	}
	
	public void enterIntDetails(String details)
	{
		intDetailsTb.clear();
		intDetailsTb.sendKeys(details);
	}
	
	public void clearIntDetails()
	{
		intDetailsTb.clear();
	}
	
	public void enterIntDetails2(String details)
	{
		intDetailsTb.sendKeys(details);
	}
	
	public String getStartMonthOption()
	{
		Select select = new Select(startMonthDd);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getStartYearOption()
	{
		Select select = new Select(startYearDd);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getEndMonthOption()
	{
		Select select = new Select(endMonthDd);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getEndYearOption()
	{
		Select select = new Select(endYearDd);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getCharCount()
	{
		return charCount.getText();
	}
	
	public String getEndDateBoxText()
	{
		return endDateBox.getText();
	}
	
	public boolean isIntCheckBoxSelected()
	{
		return endDateCheckbox.isSelected();
	}
	
	public void addInternship(String startMonth, String startYear, String endMonth, String endYear)
	{
		selectStartMonth(startMonth);
		selectStartYear(startYear);
		selectEndMonth(endMonth);
		selectEndYear(endYear);
		clickSave();
	}
	
	public void clickDeleteIntLink()
	{
		clickByJavaScript(deleteInt);
	}
	
	public String getPopUpText()
	{
		waitUntilElementAppears(deleteIntPopUp);
		return deleteIntPopUp.getText();
	}
	
	public void clickDeleteInt()
	{
		clickByJavaScript(delete);
	}
	
	public String getIntDetailsBoxText()
	{
		return intDetailsBox.getText();
	}
	
	public void deleteInternship()
	{
		clickDeleteIntLink();
		clickDeleteInt();
	}
}
