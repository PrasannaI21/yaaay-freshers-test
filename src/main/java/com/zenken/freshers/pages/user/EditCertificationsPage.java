package com.zenken.freshers.pages.user;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditCertificationsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditCertificationsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='black']")
	WebElement headline;
	
	@FindBy(id="name")
	WebElement certNameTb;
	
	@FindBy(id="description")
	WebElement certDetailsTb;
	
	@FindBy(css="[type=file]")
	WebElement fileInput;
	
	@FindBy(css="[class=uk-progress]")
	WebElement progressBar;
	
	@FindBy(id="fileList")
	WebElement files;
	
	@FindBy(id="descriptionCharacterCount")
	WebElement charCount;
	
	@FindBy(css=".delete-button")
	WebElement fileDelete;
	
	@FindBy(css="[class=u-w-100p]")
	WebElement fileBox;
	
	@FindBy(xpath="//form[contains(.,'Delete Cert')]")
	WebElement deleteCert;
	
	@FindBy(css="[role=dialog]")
	WebElement deletePopUp;
	
	@FindBy(css="[class*='small -p']")
	WebElement delete;
	
	@FindBy(xpath="(//div)[9]")
	WebElement certNameBox;
	
	@FindBy(css="[class=u-mt-30]")
	WebElement certDetailsBox;
	
	@FindBy(css="[class*='remove']")
	WebElement error;
	
	public String getCertHeadlineText()
	{
		return headline.getText();
	}
	
	public List<String> getCertPlaceholders()
	{
		List<String> attributes = new ArrayList<String>();
		attributes.add(certNameTb.getDomProperty("placeholder"));
		attributes.add(certDetailsTb.getDomProperty("placeholder"));
		return attributes;
	}
	
	public void enterCertName(String text)
	{
		certNameTb.clear();
		certNameTb.sendKeys(text);
	}
	
	public void enterCertDetails(String text)
	{
		certDetailsTb.clear();
		if(text.length() > 5000)
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value = arguments[1];", certDetailsTb, text);
		}else {
			certDetailsTb.sendKeys(text);
		}
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
		return wait.until(driver -> progressBar.getDomProperty("value").equals("100"));
	}
	
	public String getFileNameText()
	{
		waitUntilElementAppears(files);
		return files.getText();
	}
	
	public boolean isSelectFileActive()
	{
		return fileInput.isEnabled();
	}
	
	public String getCertNameText()
	{
		return certNameTb.getDomProperty("value");
	}
	
	public void clearProjectDetails()
	{
		certDetailsTb.clear();
	}
	
	public void enterCertDetails2(String text)
	{
		certDetailsTb.sendKeys(text);
	}
	
	public String getCertCharCount()
	{
		return charCount.getText();
	}
	
	public void clickCertFileDelete()
	{
		clickByJavaScript(fileDelete);
	}
	
	public boolean isCertFileDeleted(String fileName)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		boolean isDeleted = wait.until((ExpectedCondition<Boolean>) driver -> 
		{return !fileBox.getText().contains(fileName);});
		return isDeleted;
	}
	
	public void clickCertDeleteLink()
	{
		clickByJavaScript(deleteCert);
	}
	
	public String getCertPopUpText()
	{
		waitUntilElementAppears(deletePopUp);
		return deletePopUp.getText();
	}
	
	public void clickDeleteCert()
	{
		clickByJavaScript(delete);
	}
	
	public String getCertNameErrorText()
	{
		return certNameBox.getText();
	}
	
	public String getCertDetailsErrorText()
	{
		return certDetailsBox.getText();
	}
	
	public String getCertFileErrorText()
	{
		waitUntilElementAppears(error);
		return fileBox.getText();
	}
	
	public void deleteCert()
	{
		clickCertDeleteLink();
		clickDeleteCert();
	}
}
