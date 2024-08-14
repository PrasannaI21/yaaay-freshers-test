package com.zenken.freshers.pages.user;

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
	WebElement informationEdit;
	
	@FindBy(css="[class*='not'] [role='alert']")
	WebElement profileAlert;
	
	@FindBy(css="[class*=ellipsis]:nth-of-type(2)")
	WebElement basicInformationSection;
	
	@FindBy(xpath="//a[contains(.,'Basic')]")
	WebElement basicInformationAnchor;
	
	@FindBy(xpath="(//div[contains(.,'Basic')])[7]")
	WebElement basicInformationTitle;
	
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
	
	public String clickEdit()
	{
		clickByJavaScript(informationEdit);
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
	
	public boolean getSectionDisplay()
	{
		return basicInformationSection.isDisplayed();
	}
	
	public String getAnchorLinkAttribute()
	{
		String attribute = basicInformationAnchor.getAttribute("class");
		return attribute;
	}
	
	public String getTitleValue()
	{
		return basicInformationTitle.getText();
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
