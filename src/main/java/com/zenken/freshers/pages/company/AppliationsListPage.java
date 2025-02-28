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
	
	@FindBy(css="[class*='32 u']")
	WebElement eventName;
	
	@FindBy(css="[class*='0 u-flex']:first-child")
	WebElement eventDetails;
	
	@FindBy(css="[class*='12 u']")
	WebElement screeningStepsText;
	
	@FindBy(css="[class*='20 u-b-']")
	WebElement noApplicantText;
	
	@FindBy(css="[class*='lh']")
	WebElement deadline;
	
	@FindBy(xpath="//button[contains(.,'選考終了')]")
	WebElement screeningEndBt;
	
	@FindBy(id="checkAllTrigger")
	WebElement allCheckCb;
	
	@FindBy(css="tr [type=checkbox]")
	List<WebElement> checkboxes;
	
	@FindBy(css="[class*='bulk-reject']")
	List<WebElement> rejectAllBt;
	
	@FindBy(xpath="(//div[@role='dialog'])[5]")
	WebElement rejectPopup;
	
	@FindBy(xpath="(//div[@role='dialog'])[6]")
	WebElement screenPopup;
	
	@FindBy(css="[form*='bulk']")
	WebElement rejectPopupBt;
	
	@FindBy(css="[alt*='合格']")
	List<WebElement> goukaku;
	
	@FindBy(css="[alt*='補欠']")
	List<WebElement> hoketsu;
	
	@FindBy(css="[alt*='不合格']")
	List<WebElement> fugoukaku;
	
	@FindBy(css="[role=alert]")
	WebElement alert;
	
	@FindBy(css="[class*='50 u-f']")
	WebElement bottomText;
	
	@FindBy(xpath="(//tr)[2]")
	WebElement candRow1;
	
	@FindBy(xpath="(//tr)[3]")
	WebElement candRow2;
	
	@FindBy(css="td a")
	WebElement nameLink;
	
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
	
	public String getEventName()
	{
		return eventName.getText();
	}
	
	public String getEventDetails()
	{
		return eventDetails.getText();
	}
	
	public String getScreeningSteps()
	{
		return screeningStepsText.getText();
	}
	
	public String getNoAppText()
	{
		return noApplicantText.getText();
	}
	
	public String getApplicationDeadline()
	{
		return deadline.getText();
	}
	
	public boolean isScrEndBtActive()
	{
		return screeningEndBt.isEnabled();
	}
	
	public void clickAllCheckCb()
	{
		clickByJavaScript(allCheckCb);
	}
	
	public List<WebElement> getCheckboxes()
	{
		return checkboxes;
	}
	
	public List<WebElement> getRejectBts()
	{
		return rejectAllBt;
	}
	
	public String getRejectPopup()
	{
		waitUntilElementAppears(rejectPopup);
		return rejectPopup.getText();
	}
	
	public void clickRejectBt()
	{
		clickByJavaScript(rejectPopupBt);
	}
	
	public List<String> getRejectAlt()
	{
		List<String> alts = new ArrayList<>();
		for(WebElement ele : fugoukaku) {
			alts.add(ele.getDomAttribute("alt"));
		}
		return alts;
	}
	
	public String getBottomText()
	{
		return bottomText.getText();
	}
	
	public String getCandRow1()
	{
		return candRow1.getText();
	}
	
	public String getCandRow2()
	{
		return candRow2.getText();
	}
	
	public void clickNameLink()
	{
		clickByJavaScript(nameLink);
	}
	
	public void clickScrEndBt()
	{
		clickByJavaScript(screeningEndBt);
	}
	
	public String getScreenPopup()
	{
		waitUntilElementAppears(screenPopup);
		return screenPopup.getText();
	}
	
	public void clickMaru()
	{
		clickByJavaScript(goukaku.get(0));
	}
	
	public void clickSankaku()
	{
		clickByJavaScript(hoketsu.get(1));
	}
	
	public boolean isScrnBtDisplayed()
	{
		return isElementPresent(screeningEndBt);
	}

}
