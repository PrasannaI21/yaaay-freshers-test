package com.zenken.freshers.pages.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class RegisterPage extends WebDriverUtils{

	public WebDriver driver;
	
	public RegisterPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='u-fz-28']")
	WebElement text;
	
	@FindBy(xpath="//p[contains(.,'off')]")
	WebElement text2;
	
	@FindBy(id="firstName")
	WebElement firstName;
	
	@FindBy(id="middleName")
	WebElement middleName;
	
	@FindBy(id="lastName")
	WebElement lastName;
	
	@FindBy(id="usn")
	WebElement usn;
	
	@FindBy(id="email")
	WebElement email;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(id="notHasLastName")
	WebElement checkbox1;
	
	@FindBy(id="college")
	WebElement college;
	
	@FindBy(css="[id*='Year']")
	WebElement year;
	
	@FindBy(id="Yes")
	WebElement radio1;
	
	@FindBy(id="No")
	WebElement radio2;
	
	@FindBy(id="Maybe")
	WebElement radio3;
	
	@FindBy(linkText="Terms")
	WebElement terms;
	
	@FindBy(linkText="Privacy Policy")
	WebElement policy;
	
	@FindBy(id="terms")
	WebElement checkbox2;
	
	@FindBy(css="[id*='Mid']")
	WebElement checkbox3;
	
	@FindBy(css="[id*='Sub']")
	WebElement checkbox4;
	
	@FindBy(linkText="Log in")
	WebElement logIn;
	
	@FindBy(css="[type='submit']")
	WebElement submit;
	
	@FindBy(xpath="//span[contains(.,'This')]")
	WebElement required;
	
	@FindBy(xpath="//span[contains(.,'read')]")
	WebElement requiredTerms;
	
	@FindBy(xpath="//span[contains(.,'30')]")
	WebElement exceed30;
	
	@FindBy(xpath="//span[contains(.,'invalid')]")
	WebElement invalid;
	
	@FindBy(xpath="//span[contains(.,'email')]")
	WebElement invalidEmail;
	
	@FindBy(xpath="//span[contains(.,'taken')]")
	WebElement takenEmail;
	
	@FindBy(xpath="//span[contains(.,'8')]")
	WebElement invalidPass1;
	
	@FindBy(xpath="//span[contains(.,'number')]")
	WebElement invalidPass2;
	
	@FindBy(xpath="//span[contains(.,'case')]")
	WebElement invalidPass3;
	
	@FindBy(xpath="//span[contains(.,'symbol')]")
	WebElement invalidPass4;
	
	public String getTitle()
	{
		String title = driver.getTitle();
		return title;
	}
	
	public List<String> getTexts()
	{
		List<String> texts = new ArrayList<>();
		texts.add(text.getText());
		texts.add(text2.getText());
		return texts;
	}
	
	public List<String> getPlaceholders()
	{
		List<WebElement> textBoxes = Arrays.asList(firstName, middleName, lastName, usn, email);
		List<String> attributes = new ArrayList<>();
		for(WebElement element: textBoxes)
		{
			String text = element.getDomProperty("placeholder");
			attributes.add(text);
		}
		return attributes;
	}
	
	public boolean getTextBoxState()
	{
		checkbox1.click();
		boolean state = lastName.isEnabled();
		return state;
	}
	
	public String selectCollege(String text)
	{
		selectDropdown(college, text);
		String selectedCollege = getDropdownText(college);
		return selectedCollege;
	}
	
	public String selectYear(String text)
	{
		selectDropdown(year, text);
		String selectedYear = getDropdownText(year);
		return selectedYear;
	}
	
	public boolean getRadioState()
	{
		clickByJavaScript(radio1);
		boolean state = radio1.isSelected();
		return state;
	}
	
	public void clickTerms()
	{
		clickByJavaScript(terms);
	}
	
	public void clickTermsCheckbox()
	{
		clickByJavaScript(checkbox2);
	}
	
	public void clickPolicy()
	{
		clickByJavaScript(policy);
	}
	
	public ArrayList<String> switchTab(int i)
	{
		ArrayList<String> tabs = switchTabs(i);
		return tabs;
	}
	
	public boolean getCheckBox3State()
	{
		boolean state = checkbox3.isSelected();
		return state;
	}
	
	public boolean getCheckBox4State()
	{
		boolean state = checkbox4.isSelected();
		return state;
	}
	
	public String getLogInUrl()
	{
		clickByJavaScript(logIn);
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public void enterName(String text)
	{
		firstName.sendKeys(text);
	}
	
	public void enterMiddleName(String text)
	{
		middleName.sendKeys(text);
	}
	
	public void enterLastName(String text)
	{
		lastName.sendKeys(text);
	}
	
	public void enterUsn(String text)
	{
		usn.sendKeys(text);
	}
	
	public void selectInterest()
	{
		clickByJavaScript(radio3);
	}
	
	public void enterEmail(String text)
	{
		email.sendKeys(text);
	}
	
	public void enterPassword(String text)
	{
		password.sendKeys(text);
	}
	
	public void clickSubmit()
	{
		clickByJavaScript(submit);
	}
	
	public String extractVerificationLink(String emailBody)
	{
		String urlStart = "https://freshers.dspf-dev.com/verify-email/";
		int startIndex = emailBody.indexOf(urlStart);
		int endIndex = emailBody.indexOf("\n", startIndex);
		String verificationLink = emailBody.substring(startIndex, endIndex);
		return verificationLink;
	}
	
	public void openLinkInNewTab(String link)
	{
		openInNewTab(link);
	}
	
	public String getRequiredText()
	{
		String text = required.getText();
		return text;
	}
	
	public String getTermsRequiredText()
	{
		String text = requiredTerms.getText();
		return text;
	}
	
	public String getCharExceedText()
	{
		String text = exceed30.getText();
		return text;
	}
	
	public String getInvalidFormatText()
	{
		String text = invalid.getText();
		return text;
	}
	
	public String getInvalidEmailText()
	{
		String text = invalidEmail.getText();
		return text;
	}
	
	public String getUsedEmailText()
	{
		String text = takenEmail.getText();
		return text;
	}
	
	public String getInvalidPass1Text()
	{
		String text = invalidPass1.getText();
		return text;
	}
	
	public String getInvalidPass2Text()
	{
		String text = invalidPass2.getText();
		return text;
	}
	
	public String getInvalidPass3Text()
	{
		String text = invalidPass3.getText();
		return text;
	}
	
	public String getInvalidPass4Text()
	{
		String text = invalidPass4.getText();
		return text;
	}
	
	public void registerUser(String email)
	{
		enterName("ApplyTest");
		enterLastName("User");
		selectCollege("CMRIT (CMR Institute of Technology)");
		enterUsn("68492471");
		selectYear("2028");
		clickByJavaScript(radio2);
		enterEmail(email);
		enterPassword("Password_1");
		clickTermsCheckbox();
		clickSubmit();
	}
}
