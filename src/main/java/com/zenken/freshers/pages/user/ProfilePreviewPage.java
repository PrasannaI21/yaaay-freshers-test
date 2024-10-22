package com.zenken.freshers.pages.user;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ProfilePreviewPage extends WebDriverUtils{

	WebDriver driver;
	
	public ProfilePreviewPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1[class*='40']")
	WebElement headline;
	
	@FindBy(xpath="(//img[@alt='Edit icon'])[2]")
	public WebElement informationEdit;
	
	@FindBy(xpath="(//img[@alt='Edit icon'])[3]")
	public WebElement preferenceEdit;
	
	@FindBy(xpath="(//img[@alt='Edit icon'])[4]")
	public WebElement fieldOfStudyEdit;
	
	@FindBy(xpath="(//img[@alt='Edit icon'])[5]")
	public WebElement skillsEdit;
	
	@FindBy(xpath="//a[contains(.,'Add Internship')]")
	WebElement addIntership;
	
	@FindBy(xpath="//a[contains(.,'Add Project')]")
	WebElement addProject;
	
	@FindBy(xpath="//a[contains(.,'Add Cert')]")
	WebElement addCertification;
	
	@FindBy(xpath="//span[contains(.,'Add Internship')]")
	WebElement addIntDisabled;
	
	@FindBy(xpath="//span[contains(.,'Add Project')]")
	WebElement addProjectDisabled;
	
	@FindBy(xpath="//span[contains(.,'Add Cert')]")
	WebElement addCertDisabled;
	
	@FindBy(xpath="//section[contains(.,'Internship')]/descendant::img[@alt='Edit icon']")
	List<WebElement> intershipEdit;
	
	@FindBy(xpath="//section[contains(.,'Projects')]/descendant::img[@alt='Edit icon']")
	List<WebElement> projectEdit;
	
	@FindBy(xpath="//section[contains(.,'Cert')]/descendant::img[@alt='Edit icon']")
	List<WebElement> certEdit;
	
	@FindBy(css="[class*='not'] [role='alert']")
	WebElement profileAlert;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(2)")
	public WebElement basicInformationSection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(3)")
	public WebElement jobPrefSection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(4)")
	public WebElement fieldOfStudySection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(5)")
	public WebElement skillsSection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(6)")
	public WebElement internshipsSection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(7)")
	public WebElement projectsSection;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(8)")
	public WebElement certificationsSection;
	
	@FindBy(xpath="//a[contains(.,'Basic')]")
	public WebElement basicInformationAnchor;
	
	@FindBy(xpath="//a[contains(.,'Job')]")
	public WebElement jobPrefAnchor;
	
	@FindBy(xpath="//a[contains(.,'Field')]")
	public WebElement fieldOfStudyAnchor;
	
	@FindBy(xpath="//a[contains(.,'Skills')]")
	public WebElement skillsAnchor;
	
	@FindBy(xpath="//a[contains(.,'Internships')]")
	public WebElement internshipsAnchor;
	
	@FindBy(xpath="//a[contains(.,'Project')]")
	public WebElement projectsAnchor;
	
	@FindBy(xpath="//a[contains(.,'Certifications')]")
	public WebElement certificationsAnchor;
	
	@FindBy(xpath="(//div[contains(.,'Basic')])[7]")
	public WebElement basicInformationTitle;
	
	@FindBy(xpath="(//div[contains(.,'Job')])[7]")
	public WebElement jobPrefTitle;
	
	@FindBy(xpath="(//div[contains(.,'Field')])[7]")
	public WebElement fieldOfStduyTitle;
	
	@FindBy(xpath="(//div[contains(.,'Skills')])[7]")
	public WebElement skillsTitle;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[1]")
	WebElement firstName;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[2]")
	WebElement middleName;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[3]")
	WebElement lastName;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[4]")
	WebElement email1;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[5]")
	WebElement email2;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[6]")
	WebElement phone;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[7]")
	WebElement address;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[8]")
	WebElement city;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[9]")
	WebElement state;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[10]")
	WebElement country;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[11]")
	WebElement pinCode;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[12]")
	WebElement dob;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[13]")
	WebElement sex;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[14]")
	WebElement nationality;
	
	@FindBy(xpath="(//div[@class='u-pt-10'])[15]")
	WebElement timeInJapan;
	
	@FindBy(xpath="(//div[@class='u-pt-10 uk-text-truncate'])[1]")
	WebElement timeNotSure;
	
	@FindBy(xpath="(//div[@class='u-pt-10 uk-text-truncate'])[2]")
	WebElement hobby;
	
	@FindBy(xpath="//h3[contains(.,'First P')]/following-sibling::div[@class='u-pt-10']")
	WebElement firstPref;
	
	@FindBy(xpath="//h3[contains(.,'Second')]/following-sibling::div[@class='u-pt-10']")
	WebElement secondPref;
	
	@FindBy(xpath="//h3[contains(.,'Third')]/following-sibling::div[@class='u-pt-10']")
	WebElement thirdPref;
	
	@FindBy(xpath="//h3[text()='Other']/following-sibling::div[@class='u-pt-10']")
	List<WebElement> otherPref;
	
	@FindBy(xpath="//h3[contains(.,'research')]/following-sibling::div[@class='u-pt-10']")
	WebElement areaOfResearch;
	
	@FindBy(xpath="//h3[contains(.,'focus')]/following-sibling::div[@class='u-pt-10']")
	WebElement field;
	
	@FindBy(xpath="//h3[contains(.,'Which core')]")
	WebElement coreLabel;
	
	@FindBy(xpath="//h3[contains(.,'Which IT')]")
	WebElement itLabel;
	
	@FindBy(xpath="//h3[contains(.,'Other (core')]")
	WebElement coreOtherLabel;
	
	@FindBy(xpath="//h3[contains(.,'Other (IT')]")
	WebElement itOtherLabel;
	
	@FindBy(xpath="//h3[contains(.,'Other (core')]/following-sibling::div[@class='u-pt-10']")
	WebElement otherCoreField;
	
	@FindBy(xpath="//h3[contains(.,'Other (IT')]/following-sibling::div[@class='u-pt-10']")
	WebElement otherITField;
	
	@FindBy(css="[class*=blue]")
	List<WebElement> skills;
	
	@FindBy(css="dd[class*='100p']")
	List<WebElement> skillLevels;
	
	@FindBy(xpath="//section[contains(.,'Internship')]/descendant::div[@class='uk-flex uk-flex-middle u-mt-10']")
	List<WebElement> internships;
	
	@FindBy(xpath="//section[contains(.,'Internship')]/descendant::div[@class='uk-text-truncate u-fz-14']")
	List<WebElement> intDetails;
	
	@FindBy(xpath="//section[contains(.,'Project')]/descendant::div[@class='u-d-f u-mt-20']")
	List<WebElement> projectTitles;
	
	@FindBy(xpath="//section[contains(.,'Project')]/descendant::div[@class='uk-flex uk-flex-middle u-mt-10']")
	List<WebElement> projectDates;
	
	@FindBy(xpath="//section[contains(.,'Project')]/descendant::div[@class='uk-text-truncate']")
	List<WebElement> projectDetails;
	
	@FindBy(css="[class*='10'] [target=_blank]")
	List<WebElement> projectLinks;
	
	@FindBy(xpath="//ul[contains(@class, 'muted')]")
	List<WebElement> projectFiles;
	
	@FindBy(xpath="//section[contains(.,'Project')]/ul/li/a")
	List<WebElement> projectDlLinks;
	
	@FindBy(xpath="//section[contains(.,'Cert')]/descendant::span[@class='u-fz-16 u-fw-b']")
	List<WebElement> certNames;
	
	@FindBy(xpath="//section[contains(.,'Cert')]/descendant::div[@class='uk-text-truncate u-fz-14']")
	List<WebElement> certDetails;
	
	@FindBy(xpath="//section[contains(.,'Cert')]/descendant::div[@class='u-mt-10']")
	List<WebElement> certFiles;
	
	@FindBy(xpath="//section[contains(.,'Cert')]/descendant::a[@class='link']")
	List<WebElement> certDlLinks;
	
	@FindBy(css="[class=u-c-red]")
	List<WebElement> requiredMarks;
	
	public void login(String email, String password)
	{
		loginUser(email, password);
	}
	
	public String getHeadlineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public String clickEdit(WebElement element)
	{
		clickByJavaScript(element);
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public String getAlertText()
	{
		waitUntilElementAppears(profileAlert);
		String text = profileAlert.getText();
		return text;
	}
	
	public String getPageUrl()
	{
		return driver.getCurrentUrl();
	}
	
	public boolean getSectionDisplay(WebElement ele)
	{
		return ele.isDisplayed();
	}
	
	public String getAnchorLinkAttribute(WebElement ele)
	{
		String attribute = ele.getAttribute("class");
		return attribute;
	}
	
	public String getTitleValue(WebElement ele)
	{
		return ele.getText();
	}
	
	public String getFirstNameValue()
	{
		return firstName.getText();
	}
	
	public String getMiddleNameValue()
	{
		return middleName.getText();
	}
	
	public String getLastNameValue()
	{
		return lastName.getText();
	}
	
	public String getEmail1Value()
	{
		return email1.getText();
	}
	
	public String getEmail2Value()
	{
		return email2.getText();
	}
	
	public String getPhoneValue()
	{
		return phone.getText();
	}
	
	public String getAddressValue()
	{
		return address.getText();
	}
	
	public String getCityValue()
	{
		return city.getText();
	}
	
	public String getStateValue()
	{
		return state.getText();
	}
	
	public String getCountryValue()
	{
		return country.getText();
	}
	
	public String getPinCodeValue()
	{
		return pinCode.getText();
	}
	
	public String getDobValue()
	{
		return dob.getText();
	}
	
	public String getSexValue()
	{
		return sex.getText();
	}
	
	public String getNationalityValue()
	{
		return nationality.getText();
	}
	
	public String getTimeInJapanValue()
	{
		return timeInJapan.getText();
	}
	
	public String getTimeNotSureValue()
	{
		return timeNotSure.getText();
	}
	
	public String getHobbyValue()
	{
		return hobby.getText();
	}
	
	public String getFirstPrefValue()
	{
		return firstPref.getText();
	}
	
	public String getSecondPrefValue()
	{
		return secondPref.getText();
	}
	
	public String getThirdPrefValue()
	{
		return thirdPref.getText();
	}
	
	public String getAreaOfResearchValue()
	{
		return areaOfResearch.getText();
	}
	
	public String getCoreLabel()
	{
		return coreLabel.getText();
	}
	
	public String getITLabel()
	{
		return itLabel.getText();
	}
	
	public String getFieldValue()
	{
		return field.getText();
	}
	
	public String getCoreOtherLabel()
	{
		return coreOtherLabel.getText();
	}
	
	public String getITOtherLabel()
	{
		return itOtherLabel.getText();
	}
	
	public String getCoreOtherValue()
	{
		return otherCoreField.getText();
	}
	
	public String getITOtherValue()
	{
		return otherITField.getText();
	}
	
	public List<String> getOtherPrefValues()
	{
		List<String> texts = new ArrayList<>();
		for(int i=0;i<otherPref.size();i++)
		{
			String text = otherPref.get(i).getText();
			texts.add(text);
		}
		return texts;
	}
	
	public List<String> getSkillsValues()
	{
		List<String> texts = new ArrayList<>();
		for(int i=0;i<skills.size();i++)
		{
			String text = skills.get(i).getText();
			texts.add(text);
		}
		return texts;
	}
	
	public List<String> getSkillLevelValues()
	{
		List<String> texts = new ArrayList<>();
		for(int i=0;i<skillLevels.size();i++)
		{
			String text = skillLevels.get(i).getText();
			texts.add(text);
		}
		return texts;
	}
	
	public void clickAddInternship()
	{
		clickByJavaScript(addIntership);
	}
	
	public String getIntDateValue()
	{
		if(!internships.isEmpty())
		{
			return internships.get(internships.size() - 1).getText();
		}else {
			return "No internship records found.";
		}
	}
	
	public String getIntDetailsValue()
	{
		if(!intDetails.isEmpty())
		{
			return intDetails.get(intDetails.size() - 1).getText();
		}else {
			return "No internship records found.";
		}
	}
	
	public String getIntDateValue(int index)
	{
		return internships.get(index).getText();
	}
	
	public void clickIntEdit(int index)
	{
		clickByJavaScript(intershipEdit.get(index));
	}
	
	public int getAddedIntCount()
	{
		return internships.size();
	}
	
	public boolean isAddIntActive()
	{
		return addIntDisabled.isDisplayed();
	}
	
	public boolean isAddIntButtonActive()
	{
		return addIntership.isEnabled();
	}
	
	public String getIntSectionText()
	{
		return internshipsSection.getText();
	}
	
	public void clickAddProject()
	{
		clickByJavaScript(addProject);
	}
	
	public String getProjectTitleValue()
	{
		if(!projectTitles.isEmpty())
		{
			return projectTitles.get(projectTitles.size() - 1).getText();
		}else {
			return "No project records found.";
		}
	}
	
	public String getProjectDateValue()
	{
		if(!projectDates.isEmpty())
		{
			return projectDates.get(projectDates.size() - 1).getText();
		}else {
			return "No project records found.";
		}
	}
	
	public String getProjectDetailsValue()
	{
		if(!projectDetails.isEmpty())
		{
			return projectDetails.get(projectDetails.size() - 1).getText();
		}else {
			return "No project records found.";
		}
	}
	
	public String getProjectLinkValue()
	{
		if(!projectLinks.isEmpty())
		{
			return projectLinks.get(projectLinks.size() - 1).getText();
		}else {
			return "No project records found.";
		}
	}
	
	public void clickProjectLink()
	{
		clickByJavaScript(projectLinks.get(projectLinks.size()-1));
	}
	
	public String getProjectFileValue()
	{
		if(!projectFiles.isEmpty())
		{
			return projectFiles.get(projectFiles.size() - 1).getText();
		}else {
			return "No project records found.";
		}
	}
	
	public String getProjectDlLinkText()
	{
		return projectDlLinks.get(projectDlLinks.size() -1).getText();
	}
	
	public void clickProjectFile()
	{
		clickByJavaScript(projectDlLinks.get(projectDlLinks.size() - 1));
	}
	
	public String getProjectDlLink(int maxRetries) throws InterruptedException
	{
		return monitorDownloadLink(maxRetries, projectDlLinks);
	}
	
	public int getFileCount()
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		int initialFileCount = downloadFolder.listFiles().length;
		return initialFileCount;
	}
	
	public boolean isFileDownloaded(int initialFileCount)
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean isDownloaded = wait.until((ExpectedCondition<Boolean>) driver -> 
		{int currentFileCount = downloadFolder.listFiles().length;
		String fileName = downloadFolder.listFiles()[0].getName();
		return currentFileCount > initialFileCount && fileName.endsWith(".pdf");}); 
		return isDownloaded;
	}
	
	public String getDownloadedFileName()
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		File[] files = downloadFolder.listFiles();
		if(files != null && files.length > 0)
		{
			Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
			return files[0].getName();
		}else {
			return null;
		}
	}
	
	public void clickProjectEdit(int index)
	{
		clickByJavaScript(projectEdit.get(index));
	}
	
	public int getAddedProjectCount()
	{
		return projectTitles.size();
	}
	
	public boolean isAddProjectActive()
	{
		return addProjectDisabled.isDisplayed();
	}
	
	public String getProjectSectionText()
	{
		return projectsSection.getText();
	}
	
	public String getProjectTitleValue(int index)
	{
		return projectTitles.get(index).getText();
	}
	
	public boolean isAddProjectButtonActive()
	{
		return addProject.isEnabled();
	}
	
	public void clickAddCertification()
	{
		clickByJavaScript(addCertification);
	}
	
	public String getCertNameValue()
	{
		if(!certNames.isEmpty())
		{
			return certNames.get(certNames.size() - 1).getText();
		}else {
			return "No certifications records found.";
		}
	}
	
	public String getCertDetailValue()
	{
		if(!certDetails.isEmpty())
		{
			return certDetails.get(certDetails.size() - 1).getText();
		}else {
			return "No certifications records found.";
		}
	}
	
	public String getCertFileValue()
	{
		if(!certFiles.isEmpty())
		{
			return certFiles.get(certFiles.size() - 1).getText();
		}else {
			return "No certifications records found.";
		}
	}
	public String getCertDlLink(int maxRetries) throws InterruptedException
	{
		return monitorDownloadLink(maxRetries, certDlLinks);
	}
	
	public void clickCertFile()
	{
		clickByJavaScript(certDlLinks.get(certDlLinks.size() - 1));
	}
	
	public void clickCertEdit(int index)
	{
		clickByJavaScript(certEdit.get(index));
	}
	
	public int getAddedCertCount()
	{
		return certNames.size();
	}
	
	public boolean isAddCertActive()
	{
		return addCertDisabled.isDisplayed();
	}
	
	public String getCertSectionText()
	{
		return certificationsSection.getText();
	}
	
	public boolean isAddCertButtonActive()
	{
		return addCertification.isEnabled();
	}
	
	//Method to check if the required mark is present and displayed
	public boolean isProfileComplete()
	{
		return !requiredMarks.isEmpty() && requiredMarks.get(0).isDisplayed();
	}
}
