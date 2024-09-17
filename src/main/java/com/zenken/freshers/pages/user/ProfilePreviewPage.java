package com.zenken.freshers.pages.user;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	
	@FindBy(xpath="//a[contains(.,'Basic')]")
	public WebElement basicInformationAnchor;
	
	@FindBy(xpath="//a[contains(.,'Job')]")
	public WebElement jobPrefAnchor;
	
	@FindBy(xpath="//a[contains(.,'Field')]")
	public WebElement fieldOfStudyAnchor;
	
	@FindBy(xpath="//a[contains(.,'Skills')]")
	public WebElement skillsAnchor;
	
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
	
	//Method to check if the required mark is present and displayed
	public boolean isProfileComplete()
	{
		return !requiredMarks.isEmpty() && requiredMarks.get(0).isDisplayed();
//		try
//		{
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//			List<WebElement> mark = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class=u-c-red]")));
//			return !mark.isEmpty() && mark.get(0).isDisplayed();	
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=u-c-red]")));
//			return true;
//		}
//		catch(TimeoutException e) {
//			return false;
//		}
	}
}
