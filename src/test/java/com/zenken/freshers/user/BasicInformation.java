package com.zenken.freshers.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditBasicInformationPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class BasicInformation extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditBasicInformationPage editBasicInfo;
	Properties properties;
	String url;
	boolean state;
	
	@BeforeMethod
	public void setUpTest(ITestResult result) throws IOException
	{
		String testName = result.getMethod().getMethodName();
		navigateTo("/");
		profilePreview = new ProfilePreviewPage(driver);
		editBasicInfo = new EditBasicInformationPage(driver);
		if(testName.equals("verifySavedData") || testName.equals("verifyProfileUpdate") || testName.equals("verifyRequiredToApplyTexts"))
		{
			profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
		}
		else {
			profilePreview.login("prasanna.inamdar@zenken.co.jp", "Password_1");
		}
		url = profilePreview.clickEdit(profilePreview.informationEdit);
		state = editBasicInfo.isSectionComplete();
		properties = getProperties();
	}
	
//	@Test  //Test for preview page
//	public void verifyHeadlineText()
//	{
//		String headline = profilePreview.getHeadlineText();
//		Assert.assertTrue(headline.equals(properties.getProperty("text7")), "Profile page's headline is incorrect");
//	}
	
	@Test(description="This test verifies that user is redirected to Basic Information edit page")
	public void verifyEditUrl()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(editBasicInfo.getBIHeadline(), "Basic Information");;
		Assert.assertTrue(url.equals(properties.getProperty("url6")), "Did not redirect to edit page");
	}
	
	@Test(description="This test verifies that the Basic Information page title is correct")
	public void verifyTitleName()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that title name is correct");
		String title = profilePreview.getPageTitle();
		Assert.assertTrue(title.equals(properties.getProperty("title4")), "Page title is incorrect");
	}
	
	@Test(description="This test verifies that the saved data displayed is correct", 
			dependsOnMethods="verifyProfileUpdate")
	public void verifySavedData()//Need to login with user+2
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that the saved data for 'required to save' fields is displayed accordingly");
		List<Object> obj = editBasicInfo.getSavedData();
		Assert.assertTrue(obj.get(0).equals("Jesse"), "First name is incorrect");
		Assert.assertTrue(obj.get(1).equals("Pinkman"), "Last name is incorrect");
		Assert.assertTrue(obj.get(2).equals("jesse@example.com"), "Email is incorrect");
		Assert.assertTrue(obj.get(3).equals("IITB (Indian Institute of Technology Bombay)"), "College is incorrect");
		Assert.assertTrue(obj.get(4).equals("123456789"), "USN is incorrect");
		Assert.assertTrue(obj.get(5).equals("2024"), "Year is incorrect");
		Assert.assertTrue((boolean) obj.get(6), "Interest 'Maybe' is not selected");
	}
	
	@Test(description="This test verifies that the placeholders displayed are correct for all text boxes on Basic Information edit page")
	public void verifyPlaceholders()
	{
		log("Step 1: Click on Basic Information edit icon");
		List<String> pTexts = editBasicInfo.getPlaceholders();
		log("Step 2: Verify that placeholders for all text boxes are correct");
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholder"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test(description="This test verifies that the text box for 'Last name' turns inactive after selecting the checkbox")
	public void verifyTextBoxInactiveLastName()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Click on checkbox for 'Last Name' field");
		boolean state = editBasicInfo.getLastNameTextBoxState();
		log("Step 3: Verify that text box turns inactive");
		Assert.assertFalse(state, "Text box remained active");
	}
	
	@Test(description="This test verifies the activation of 'If you chose \"Not sure\", "
			+ "please describe here' textbox based on the input given for corresponding field")
	public void verifyNotSureStates()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that upon selecting 'Not sure' option from 'How long "
				+ "would you like to work in Japan?' field, the corresponding text box "
				+ "turns active");
		Map<String, Boolean> states = editBasicInfo.getNotSureTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'Other (Bachelors)' textbox "
			+ "based on the input given for corresponding field")
	public void verifyOtherBachelorStates()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that upon selecting 'Others' option from 'BE/B.TECH BRANCH' "
				+ "field, the corresponding text box turns active");
		Map<String, Boolean> states = editBasicInfo.getOtherBachelorTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'Other (Masters)' textbox "
			+ "based on the input given for corresponding field")
	public void verifyOtherMasterStates()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that upon selecting 'Others' option from 'MCA/M.TECH BRANCH' "
				+ "field, the corresponding text box turns active");
		Map<String, Boolean> states = editBasicInfo.getOtherMasterTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'If you chose \"Other\", "
			+ "please describe here' textbox based on the input given for corresponding field")
	public void verifyProceedOtherStates()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that upon selecting 'Other' option from 'When do you plan "
				+ "to proceed?' field, the corresponding text box turns active");
		Map<String, Boolean> states = editBasicInfo.getProceedOtherTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'How many active backlogs "
			+ "do you have?' dropdown based on the input given for corresponding field")
	public void verifyBacklogCountStates()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Verify that if 'Yes' is selected for 'Do you have any active "
				+ "backlogs?' field, the corresponding dropdown turns active");
		List<Boolean> states = editBasicInfo.getBacklogCountStates();
		if(states.get(0) == true)
		{
			Assert.assertTrue(states.get(2));
		}
		else if(states.get(1) == true)
		{
			Assert.assertFalse(states.get(2));
		}
	}
	
	@Test(description="This test verifies that the user can save mandatory fields for the Basic information section")
	public void verifyProfileUpdate()//Need to login with user+2
	{
		log("Step 1: Click on Basic Information edit icon");
		editBasicInfo.enterData();
		log("Step 2: Fill in information for the 'required to save' fields");
		editBasicInfo.clickSave();
		log("Step 3: Click on 'Save' button");
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test(description="This test verifies that the user is redirected to preview page upon clicking on 'Cancel' button")
	public void verifyRedirectionForCancel()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Click on 'Cancel' button");
		editBasicInfo.clickCancel();
		log("Step 3: Verify: Section display, Tab selection on preview page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#BasicInformation"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.basicInformationSection);
		Assert.assertTrue(state, "Basic Information section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.basicInformationAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that the value entered for 'First Name' field is displayed accordingly on preview page")
	public void verifyFirstNameValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "Walter";
		log("Step 2: Enter "+text+" in 'First Name' text box");
		editBasicInfo.enterFirstName(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getFirstNameValue();
		log("Step 5: Verify that the value for 'First Name' displayed is correct");
		Assert.assertTrue(name.equals(text), "First name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Middle Name' field is displayed accordingly on preview page")
	public void verifyMiddleNameValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "Hartwell";
		log("Step 2: Enter "+text+" in 'Middle Name' text box");
		log("Step 3: Click on 'Save' button");
		editBasicInfo.enterMiddleName(text);
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getMiddleNameValue();
		log("Step 5: Verify that the value for 'Middle Name' displayed is correct");
		Assert.assertTrue(name.equals(text), "Middle name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Last Name' field is displayed accordingly on preview page")
	public void verifyLastNameValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "White";
		log("Step 2: Enter "+text+" in 'Last Name' text box");
		editBasicInfo.enterLastName(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getLastNameValue();
		log("Step 5: Verify that the value for 'Last Name' displayed is correct");
		Assert.assertTrue(name.equals(text), "Last name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'E-mail Address 1 "
			+ "(Private E-mail)' field is displayed accordingly on preview page")
	public void verifyEmail1Value()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "heythere@example.com";
		log("Step 2: Enter "+text+" in 'E-mail Address 1 (Private E-mail)' text box");
		editBasicInfo.enterEmail1(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getEmail1Value();
		log("Step 5: Verify that the value for Email address 1 displayed is correct");
		Assert.assertTrue(name.equals(text), "Email address 1 displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'E-mail Address 2 "
			+ "(College E-mail)' field is displayed accordingly on preview page")
	public void verifyEmail2Value()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "heythere2@example.com";
		log("Step 2: Enter "+text+" in 'E-mail Address 2 (College E-mail)' text box");
		editBasicInfo.enterEmail2(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getEmail2Value();
		log("Step 5: Verify that the value for Email address 2 displayed is correct");
		Assert.assertTrue(name.equals(text), "Email address 2 displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Phone No. (Mobile)' "
			+ "field is displayed accordingly on preview page")
	public void verifyPhoneValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "1234-567-890";
		log("Step 2: Enter "+text+" in 'Phone No. (Mobile)' text box");
		editBasicInfo.enterPhone(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getPhoneValue();
		log("Step 5: Verify that the value for 'Phone No. (Mobile)' displayed is correct");
		Assert.assertTrue(name.equals(text), "Phone number displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Address' "
			+ "field is displayed accordingly on preview page")
	public void verifyAddressValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "15 Yemen Road, Yemen!";
		log("Step 2: Enter "+text+" in 'Address' text box");
		editBasicInfo.enterAddress(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getAddressValue();
		log("Step 5: Verify that the value for 'Address' displayed is correct");
		Assert.assertTrue(name.equals(text), "Address displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'City/District' "
			+ "field is displayed accordingly on preview page")
	public void verifyCityValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "Guadalajara, Mexico";
		log("Step 2: Enter "+text+" in 'City/District' text box");
		editBasicInfo.enterCity(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getCityValue();
		log("Step 5: Verify that the value for 'City/District' displayed is correct");
		Assert.assertTrue(name.equals(text), "City displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'State' "
			+ "field is displayed accordingly on preview page")
	public void verifyStateValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "Texas";
		log("Step 2: Enter "+text+" in 'State' text box");
		editBasicInfo.enterState(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getStateValue();
		log("Step 5: Verify that the value for 'State' displayed is correct");
		Assert.assertTrue(name.equals(text), "State displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Country' "
			+ "field is displayed accordingly on preview page")
	public void verifyCountryValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "British Virgin Islands";
		log("Step 2: Select "+text+" from 'Country' dropdown");
		editBasicInfo.selectCountry(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getCountryValue();
		log("Step 5: Verify that the value for 'Country' displayed is correct");
		Assert.assertTrue(name.equals(text), "Country displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Pin Code' "
			+ "field is displayed accordingly on preview page")
	public void verifyPinCodeValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "36976";
		log("Step 2: Enter "+text+" in 'Pin Code' text box");
		editBasicInfo.enterPinCode(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getPinCodeValue();
		log("Step 5: Verify that the value for 'Pin code' displayed is correct");
		Assert.assertTrue(name.equals(text), "Pin code displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Date of Birth' "
			+ "field is displayed accordingly on preview page")
	public void verifyDobValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String day = "31";
		String month = "August";
		String year = "2014";
		log("Step 2: Select "+day+" "+month+" "+year+" for 'Date of Birth' dropdowns");
		editBasicInfo.selectDateOfBirth(day, month, year);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getDobValue();
		log("Step 5: Verify that the value for 'Date of Birth' displayed is correct");
		Assert.assertTrue(name.equals(month+" "+day+","+" "+year), "Date of birth displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Sex' "
			+ "field is displayed accordingly on preview page")
	public void verifySexValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Select 'Female' option for 'Sex' field");
		editBasicInfo.selectSex();
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getSexValue();
		log("Step 5: Verify that the value for 'Sex' displayed is correct");
		Assert.assertTrue(name.equals("Female"), "Sex displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Nationality' "
			+ "field is displayed accordingly on preview page")
	public void verifyNationalityValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "Dominican (Dominican Republic)";
		log("Step 2: Select "+text+" for 'Nationality' dropdown");
		editBasicInfo.selectNationality(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getNationalityValue();
		log("Step 5: Verify that the value for 'Nationality' displayed is correct");
		Assert.assertTrue(name.equals(text), "Nationality displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'How long would "
			+ "you like to work in Japan?' field is displayed accordingly on preview page")
	public void verifyTimeInJapanValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = "10+ years";
		log("Step 2: Select "+text+" for the applicable dropdown");
		editBasicInfo.selectTimeInJapan(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getTimeInJapanValue();
		log("Step 5: Verify that the value for the applicable field displayed is correct");
		Assert.assertTrue(name.equals(text), "Time period in Japn displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'If you chose "
			+ "\"Not sure\", please describe here' field is displayed accordingly on preview page")
	public void verifyTimeNotSureValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = properties.getProperty("exceed200");
		log("Step 2: Enter description in the applicable textbox");
		editBasicInfo.enterTimeNotSure(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String value = profilePreview.getTimeNotSureValue();
		log("Step 5: Verify that the value for the applicable field displayed is correct");
		Assert.assertTrue(value.equals(text), "Not sure description displayed in not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'What is your "
			+ "hobby?' field is displayed accordingly on preview page")
	public void verifyHobbyValue()
	{
		log("Step 1: Click on Basic Information edit icon");
		String text = properties.getProperty("exceed200");
		log("Step 2: Enter description for hobby in the applicable textbox");
		editBasicInfo.enterHobby(text);
		log("Step 3: Click on 'Save' button");
		editBasicInfo.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#BasicInformation", state);
		String value = profilePreview.getHobbyValue();
		log("Step 5: Verify that the value for 'What is your hobby?' displayed is correct");
		Assert.assertTrue(value.equals(text), "Hobby displayed in not correct");
	}
	
	@Test(description="This test verifies that validation error occurs for 'First Name' field")
	public void verifyFirstNameRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'First Name' and click on 'Save' button");
		String text = editBasicInfo.getFirstNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for first name field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'Last Name' field")
	public void verifyLastNameRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'Last Name' and click on 'Save' button");
		String text = editBasicInfo.getLastNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for last name field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'E-mail Address 1 (Private E-mail)' field")
	public void verifyEmailRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'E-mail Address 1 (Private E-mail)' and click on 'Save' button");
		String text = editBasicInfo.getEmailRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for email address 1 field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'College' field")
	public void verifyCollegeRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'College' and click on 'Save' button");
		String text = editBasicInfo.getCollegeRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for college field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'USN / Registration Number' field")
	public void verifyUsnRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'USN / Registration Number' and click on 'Save' button");
		String text = editBasicInfo.getUsnRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for usn field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'Expected Graduation Year' field")
	public void verifyYearRequired()
	{
		log("Step 1: Click on Basic Information edit icon");
		log("Step 2: Clear existing text from the text box for 'Expected Graduation Year' and click on 'Save' button");
		String text = editBasicInfo.getYearRequiredText();
		String text2 = editBasicInfo.getAlert();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for graduation year field");
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed for the field");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyRequiredToApplyTexts()//Need to login with user+2 having incomplete profile
	{
		log("Step 1: Click on Basic Information edit icon");
		List<String> texts = editBasicInfo.getRequiredToApplyTexts();
		log("Step 2: Verify that 'Required to apply' text is displayed for the fields which are not 'Required to save'");
		for(int i=0;i<texts.size();i++)
		{
			Assert.assertTrue(texts.get(i).contains(properties.getProperty("error16")),
					"'Required to Apply' text is not displayed for the field: "+texts.get(i));
		}
	}
	
	private void redirectionAssertions(String parameter, Boolean state)
	{
		String alert = profilePreview.getAlertText();
		if(profilePreview.isProfileComplete())
		{
			Assert.assertTrue(alert.equals(properties.getProperty("alert4")), "Alert text is not correct");
		}
		else{
			Assert.assertTrue(alert.equals(properties.getProperty("alert5")), "Alert text is not correct");
		}
		if(state){
			Assert.assertTrue(profilePreview.getTitleValue(profilePreview.basicInformationTitle).contains(properties.getProperty("error16")));
		}
		else{
			Assert.assertFalse(profilePreview.getTitleValue(profilePreview.basicInformationTitle).contains(properties.getProperty("error16")));
		}
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains(parameter), "The URL does not contain the expected anchor");
		boolean state2 = profilePreview.getSectionDisplay(profilePreview.informationEdit);
		Assert.assertTrue(state2, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.basicInformationAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
