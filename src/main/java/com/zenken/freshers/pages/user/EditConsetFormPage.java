package com.zenken.freshers.pages.user;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditConsetFormPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditConsetFormPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='black']")
	WebElement headline;
	
	@FindBy(id="doesAgree")
	WebElement agreeCheckbox;
	
	@FindBy(css="[class='link u-ml-5']")
	WebElement consentForm;
	
	@FindBy(css="[type=file]")
	WebElement fileInput;
	
	@FindBy(css="[class=uk-progress]")
	WebElement progressBar;
	
	@FindBy(id="fileList")
	WebElement fileBox;
	
	@FindBy(css=".delete-button")
	WebElement fileDelete;
	
	@FindBy(id="uploadComponent")
	WebElement cFBox;
	
	@FindBy(id="japanesePlacementConsentFormFile")
	WebElement reqText;
	
	@FindBy(css="[class*='remove']")
	WebElement error;
	
	public void selectAgreeCheckbox()
	{
		if(!agreeCheckbox.isSelected()) {
			agreeCheckbox.click();
		}
	}
	
	public String getFormHeadlineText()
	{
		return headline.getText();
	}
	
	public void clickConsentFormLink()
	{
		consentForm.click();
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(driver -> progressBar.getAttribute("value").equals("100"));
	}
	
	public String getFileNameText()
	{
		waitUntilElementAppears(fileBox);
		return fileBox.getText();
	}
	
	public boolean isSelectFileActive()
	{
		return fileInput.isEnabled();
	}
	
	public void clickCFDelete()
	{
		clickByJavaScript(fileDelete);
	}
	
	public boolean isCFDeleted(String fileName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		boolean isDeleted = wait.until((ExpectedCondition<Boolean>) driver -> 
		{return !cFBox.getText().contains(fileName);});
		return isDeleted;
	}
	
	public String getCFReqText()
	{
		return reqText.getText();
	}
	
	public void deSelectAgreeCheckbox()
	{
		if(agreeCheckbox.isSelected()) {
			agreeCheckbox.click();
		}
	}
	
	public String getCFErrorText()
	{
		waitUntilElementAppears(error);
		return cFBox.getText();
	}
	
	public String getReqToSaveText()
	{
		return error.getText();
	}
	
	public void switchToPdf()
	{
		switchTabs(1);
	}
	
}
