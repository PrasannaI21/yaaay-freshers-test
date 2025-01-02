package com.zenken.freshers.pages.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditBasicInformationPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditBasicInformationPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[.='Basic Information']")
	WebElement headlineBI;
	
	@FindBy(id="firstName")
	WebElement firstNameTb;
	
	@FindBy(id="middleName")
	WebElement middleNameTb;
	
	@FindBy(id="lastName")
	WebElement lastNameTb;
	
	@FindBy(id="notHasLastName")
	WebElement checkbox;
	
	@FindBy(id="privateEmail")
	WebElement emailTb;
	
	@FindBy(id="collegeEmail")
	WebElement email2Tb;
	
	@FindBy(id="phoneNumber")
	WebElement phoneTb;
	
	@FindBy(id="address")
	WebElement addressTb;
	
	@FindBy(id="currentLocationCity")
	WebElement cityTb;
	
	@FindBy(id="state")
	WebElement stateTb;
	
	@FindBy(id="currentLocation")
	WebElement countryDd;
	
	@FindBy(id="pinCode")
	WebElement pinCodeTb;
	
	@FindBy(css="[name*=Day]")
	WebElement dayDd;
	
	@FindBy(css="[name*=Month]")
	WebElement monthDd;
	
	@FindBy(css="[name*=hYear]")
	WebElement birthYearDd;
	
	@FindBy(id="sex1")
	WebElement sex1;
	
	@FindBy(id="sex2")
	WebElement sex2;
	
	@FindBy(id="sex4")
	WebElement sex3;
	
	@FindBy(id="sex5")
	WebElement sex4;
	
	@FindBy(id="nationality")
	WebElement nationalityDd;
	
	@FindBy(id="lengthOfTimeWorkInJapan")
	WebElement lenghtOfTimeDd;
	
	@FindBy(css="textarea[id*='Not']")
	WebElement timeNotSureTb;
	
	@FindBy(id="hobby")
	WebElement hobbyTb;
	
	@FindBy(id="college")
	WebElement collegeDd;
	
	@FindBy(id="usn")
	WebElement usnTb;
	
	@FindBy(id="expectedGraduationYear")
	WebElement yearDd;
	
	@FindBy(id="beBTechBranch")
	WebElement beBTechBranchDd;
	
	@FindBy(id="otherBachelor")
	WebElement otherBachelorTb;
	
	@FindBy(id="beBTechCgpa")
	WebElement beCgpaTb;
	
	@FindBy(id="mcaMTechBranch")
	WebElement mcaMTechBranchDd;
	
	@FindBy(id="otherMaster")
	WebElement otherMasterTb;
	
	@FindBy(id="mcaMTechCgpa")
	WebElement mcaCgpaTb;
	
	@FindBy(id="planToProceed")
	WebElement planToProceedDd;
	
	@FindBy(id="proceedOther")
	WebElement proceedOtherTb;
	
	@FindBy(id="hasActiveBacklog1")
	WebElement radioBacklogYes;
	
	@FindBy(id="hasActiveBacklog0")
	WebElement radioBacklogNo;
	
	@FindBy(id="activeBacklogCount")
	WebElement activeBacklogCountDd;
	
	@FindBy(id="preUniversity")
	WebElement preUniversityTb;
	
	@FindBy(id="highSchool")
	WebElement highSchoolTb;
	
	@FindBy(css="[id*='e-1']")
	WebElement interest1;
	
	@FindBy(css="[id*='e-2']")
	WebElement interest2;
	
	@FindBy(css="[id*='e-3']")
	WebElement interest3;
	
	@FindBy(id="languageOtherDetail")
	WebElement languageOtherTb;
	
	@FindBy(xpath="(//div)[10]")
	WebElement firstNameBox;
	
	@FindBy(xpath="(//div)[14]")
	WebElement lastNameBox;
	
	@FindBy(xpath="(//div)[48]")
	WebElement collegeBox;
	
	@FindBy(xpath="(//div)[16]")
	WebElement emailBox;
	
	@FindBy(xpath="(//div)[50]")
	WebElement usnBox;
	
	@FindBy(xpath="(//div)[52]")
	WebElement yearBox;
	
	@FindBy(id="phoneNumberLabel")
	WebElement phone;
	
	@FindBy(xpath="(//div[contains(.,'Birth')])[6]")
	WebElement dob;
	
	@FindBy(xpath="(//div[contains(.,'Sex')])[6]")
	WebElement sex;
	
	@FindBy(id="nationalityLabel")
	WebElement nationality;
	
	@FindBy(id="lengthOfTimeWorkInJapanLabel")
	WebElement lengthOfTime;
	
	@FindBy(id="beBTechBranchLabel")
	WebElement beBTech;
	
	@FindBy(id="beBTechCgpaLabel")
	WebElement beBTechCgpa;
	
	@FindBy(id="planForContinuedEducationLabel")
	WebElement planForEducation;
	
	@FindBy(id="hasActiveBacklogLabel")
	WebElement hasBacklog;
	
	@FindBy(id="englishLevelLabel")
	WebElement english;
	
	@FindBy(id="japaneseLevelLabel")
	WebElement japanese;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	@FindBy(css="[type='submit']")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	@FindBy(css="[class*=card] [class=u-c-red]")
	List<WebElement> requiredToApplyMarks;
	
	public String getBIHeadline()
	{
		return headlineBI.getText();
	}
	
	public List<Object> getSavedData()
	{
		List<Object> obj = new ArrayList<>();
		obj.add(firstNameTb.getDomProperty("value"));
		obj.add(lastNameTb.getDomProperty("value"));
		obj.add(emailTb.getDomProperty("value"));
		obj.add(getDropdownText(collegeDd));
		obj.add(usnTb.getDomProperty("value"));
		obj.add(getDropdownText(yearDd));
		obj.add(interest3.isSelected());
		return obj;
	}
	
	public List<String> getPlaceholders()
	{
		List<WebElement> textBoxes = Arrays.asList(firstNameTb, middleNameTb, lastNameTb, emailTb, email2Tb, phoneTb, addressTb,
				cityTb, stateTb, pinCodeTb, timeNotSureTb, hobbyTb, usnTb, otherBachelorTb, beCgpaTb, otherMasterTb, mcaCgpaTb, proceedOtherTb,
				preUniversityTb, highSchoolTb, languageOtherTb);
		List<String> attributes = new ArrayList<>();
		for(WebElement element: textBoxes)
		{
			String text = element.getDomProperty("placeholder");
			attributes.add(text);
		}
		return attributes;
	}
	
	public boolean getLastNameTextBoxState()
	{
		boolean state = getState(checkbox, lastNameTb);
		return state;
	}
	
	public Map<String, Boolean> getNotSureTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(lenghtOfTimeDd, timeNotSureTb);
		return states;
	}
	
	public Map<String, Boolean> getOtherBachelorTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(beBTechBranchDd, otherBachelorTb);
		return states;
	}
	
	public Map<String, Boolean> getOtherMasterTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(mcaMTechBranchDd, otherMasterTb);
		return states;
	}
	
	public Map<String, Boolean> getProceedOtherTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(planToProceedDd, proceedOtherTb);
		return states;
	}
	
	public List<Boolean> getBacklogCountStates()
	{
		List<Boolean> states = new ArrayList<>();
		boolean state = radioBacklogYes.isSelected();
		boolean state2 = radioBacklogNo.isSelected();
		boolean state3 = activeBacklogCountDd.isEnabled();
		states.add(state);
		states.add(state2);
		states.add(state3);
		return states;
	}
	
	public String getFirstNameRequiredText()
	{
		clearText(firstNameTb);
		clickByJavaScript(save);
		return firstNameBox.getText();
	}
	
	public String getLastNameRequiredText()
	{
		clearText(lastNameTb);
		clickByJavaScript(save);
		return lastNameBox.getText();
	}
	
	public String getEmailRequiredText()
	{
		clearText(emailTb);
		clickByJavaScript(save);
		return emailBox.getText();
	}
	
	public String getCollegeRequiredText()
	{
		selectDropdownByIndex(collegeDd, 0);
		clickByJavaScript(save);
		return collegeBox.getText();
	}
	
	public String getUsnRequiredText()
	{
		clearText(usnTb);
		clickByJavaScript(save);
		return usnBox.getText();
	}
	
	public String getYearRequiredText()
	{
		selectDropdownByIndex(yearDd, 0);
		clickByJavaScript(save);
		return yearBox.getText();
	}
	
	public List<String> getRequiredToApplyTexts()
	{
		List<String> texts = new ArrayList<>();
		texts.add(phone.getText());
		texts.add(dob.getText());
		texts.add(sex.getText());
		texts.add(nationality.getText());
		texts.add(lengthOfTime.getText());
		texts.add(beBTech.getText());
		texts.add(beBTechCgpa.getText());
		texts.add(planForEducation.getText());
		texts.add(hasBacklog.getText());
		texts.add(english.getText());
		texts.add(japanese.getText());
		return texts;
	}
	
	public String getAlert()
	{
		waitUntilElementAppears(alert);
		String text = alert.getText();
		return text;
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
		
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public void enterFirstName(String text)
	{
		firstNameTb.clear();
		firstNameTb.sendKeys(text);
	}
	
	public void enterMiddleName(String text)
	{
		middleNameTb.clear();
		middleNameTb.sendKeys(text);
	}
	
	public void enterLastName(String text)
	{
		lastNameTb.clear();
		lastNameTb.sendKeys(text);
	}
	
	public void enterEmail1(String text)
	{
		emailTb.clear();
		emailTb.sendKeys(text);
	}
	
	public void enterEmail2(String text)
	{
		email2Tb.clear();
		email2Tb.sendKeys(text);
	}
	
	public void enterPhone(String text)
	{
		phoneTb.clear();
		phoneTb.sendKeys(text);
	}
	
	public void enterAddress(String text)
	{
		addressTb.clear();
		addressTb.sendKeys(text);
	}
	
	public void enterCity(String text)
	{
		cityTb.clear();
		cityTb.sendKeys(text);
	}
	
	public void enterState(String text)
	{
		stateTb.clear();
		stateTb.sendKeys(text);
	}
	
	public void selectCountry(String text)
	{
		selectDropdown(countryDd, text);
	}
	
	public void enterPinCode(String text)
	{
		pinCodeTb.clear();
		pinCodeTb.sendKeys(text);
	}
	
	public void selectDateOfBirth(String text1, String text2, String text3)
	{
		selectDropdown(dayDd, text1);
		selectDropdown(monthDd, text2);
		selectDropdown(birthYearDd, text3);
	}
	
	public void selectSex()
	{
		clickByJavaScript(sex2);
	}
	
	public void selectNationality(String text)
	{
		selectDropdown(nationalityDd, text);
	}
	
	public void selectTimeInJapan(String text)
	{
		selectDropdown(lenghtOfTimeDd, text);
	}
	
	public void enterTimeNotSure(String text)
	{
		selectDropdown(lenghtOfTimeDd, "Not sure");
		timeNotSureTb.clear();
		timeNotSureTb.sendKeys(text);
	}
	
	public void enterHobby(String text)
	{
		hobbyTb.clear();
		hobbyTb.sendKeys(text);
	}
	
	public void enterData()
	{
		firstNameTb.clear();
		firstNameTb.sendKeys("Jesse");
		lastNameTb.clear();
		lastNameTb.sendKeys("Pinkman");
		emailTb.clear();
		emailTb.sendKeys("jesse@example.com");
		selectDropdown(collegeDd, "IITB (Indian Institute of Technology Bombay)");
		usnTb.clear();
		usnTb.sendKeys("123456789");
		selectDropdown(yearDd, "2024");
		clickByJavaScript(interest3);
	}
	
//	public boolean isSectionComplete()
//	{
//		return !requiredToApplyMarks.isEmpty() && requiredToApplyMarks.get(0).isDisplayed();
//		try {
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//			List<WebElement> marks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class*=card] [class=u-c-red]")));
//			return !marks.isEmpty() && marks.get(0).isDisplayed();
//		}
//		catch(TimeoutException e) {
//			return false;
//		}
//	}
	
}
