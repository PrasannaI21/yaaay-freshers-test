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
		if(testName.equals("verifyBasicInfoSavedData") || testName.equals("verifyBasicInfoProfileUpdate") || testName.equals("verifyRequiredToApplyTexts"))
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
	
	@Test(description="This test verifies that user is redirected to Basic Information edit page")
	public void verifyBasicInfoEditUrl()
	{
		Assert.assertEquals(editBasicInfo.getBIHeadline(), "Basic Information");;
		Assert.assertTrue(url.equals(properties.getProperty("url6")), "Did not redirect to edit page");
	}
	
	@Test(description="This test verifies that the Basic Information page title is correct")
	public void verifyBasicInfoTitleName()
	{
		String title = profilePreview.getPageTitle();
		Assert.assertTrue(title.equals(properties.getProperty("title4")), "Page title is incorrect");
	}
	
	@Test(description="This test verifies that the saved data displayed is correct", 
			dependsOnMethods="verifyBasicInfoProfileUpdate")
	public void verifyBasicInfoSavedData()//Need to login with user+2
	{
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
	public void verifyBasicInfoPlaceholders()
	{
		List<String> pTexts = editBasicInfo.getPlaceholders();
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholder"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test(description="This test verifies that the text box for 'Last name' turns inactive after selecting the checkbox")
	public void verifyTextBoxInactiveLastName()
	{
		boolean state = editBasicInfo.getLastNameTextBoxState();
		Assert.assertFalse(state, "Text box remained active");
	}
	
	@Test(description="This test verifies the activation of 'If you chose \"Not sure\", "
			+ "please describe here' textbox based on the input given for corresponding field")
	public void verifyBasicInfoNotSureStates()
	{
		Map<String, Boolean> states = editBasicInfo.getNotSureTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'Other (Bachelors)' textbox "
			+ "based on the input given for corresponding field")
	public void verifyOtherBachelorStates()
	{
		Map<String, Boolean> states = editBasicInfo.getOtherBachelorTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'Other (Masters)' textbox "
			+ "based on the input given for corresponding field")
	public void verifyOtherMasterStates()
	{
		Map<String, Boolean> states = editBasicInfo.getOtherMasterTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'If you chose \"Other\", "
			+ "please describe here' textbox based on the input given for corresponding field")
	public void verifyProceedOtherStates()
	{
		Map<String, Boolean> states = editBasicInfo.getProceedOtherTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation of 'How many active backlogs "
			+ "do you have?' dropdown based on the input given for corresponding field")
	public void verifyBacklogCountStates()
	{
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
	public void verifyBasicInfoProfileUpdate()//Need to login with user+2
	{
		editBasicInfo.enterData();
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test(description="This test verifies that the user is redirected to preview page upon clicking on 'Cancel' button")
	public void verifyBasicInfoCancel()
	{
		editBasicInfo.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#BasicInformation"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.basicInformationSection);
		Assert.assertTrue(state, "Basic Information section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.basicInformationAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that the value entered for 'First Name' field is displayed accordingly on preview page")
	public void verifyBasicInfoFirstNameValue()
	{
		String text = "Walter";
		editBasicInfo.enterFirstName(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getFirstNameValue();
		Assert.assertTrue(name.equals(text), "First name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Middle Name' field is displayed accordingly on preview page")
	public void verifyBasicInfoMiddleNameValue()
	{
		String text = "Hartwell";
		editBasicInfo.enterMiddleName(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getMiddleNameValue();
		Assert.assertTrue(name.equals(text), "Middle name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Last Name' field is displayed accordingly on preview page")
	public void verifyBasicInfoLastNameValue()
	{
		String text = "White";
		editBasicInfo.enterLastName(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getLastNameValue();
		Assert.assertTrue(name.equals(text), "Last name displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'E-mail Address 1 "
			+ "(Private E-mail)' field is displayed accordingly on preview page")
	public void verifyBasicInfoEmail1Value()
	{
		String text = "heythere@example.com";
		editBasicInfo.enterEmail1(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getEmail1Value();
		Assert.assertTrue(name.equals(text), "Email address 1 displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'E-mail Address 2 "
			+ "(College E-mail)' field is displayed accordingly on preview page")
	public void verifyBasicInfoEmail2Value()
	{
		String text = "heythere2@example.com";
		editBasicInfo.enterEmail2(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getEmail2Value();
		Assert.assertTrue(name.equals(text), "Email address 2 displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Phone No. (Mobile)' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoPhoneValue()
	{
		String text = "1234-567-890";
		editBasicInfo.enterPhone(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getPhoneValue();
		Assert.assertTrue(name.equals(text), "Phone number displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Address' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoAddressValue()
	{
		String text = "15 Yemen Road, Yemen!";
		editBasicInfo.enterAddress(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getAddressValue();
		Assert.assertTrue(name.equals(text), "Address displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'City/District' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoCityValue()
	{
		String text = "Guadalajara, Mexico";
		editBasicInfo.enterCity(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getCityValue();
		Assert.assertTrue(name.equals(text), "City displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'State' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoStateValue()
	{
		String text = "Texas";
		editBasicInfo.enterState(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getStateValue();
		Assert.assertTrue(name.equals(text), "State displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Country' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoCountryValue()
	{
		String text = "British Virgin Islands";
		editBasicInfo.selectCountry(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getCountryValue();
		Assert.assertTrue(name.equals(text), "Country displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Pin Code' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoPinCodeValue()
	{
		String text = "36976";
		editBasicInfo.enterPinCode(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getPinCodeValue();
		Assert.assertTrue(name.equals(text), "Pin code displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Date of Birth' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoDobValue()
	{
		String day = "31";
		String month = "August";
		String year = "2014";
		editBasicInfo.selectDateOfBirth(day, month, year);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getDobValue();
		Assert.assertTrue(name.equals(month+" "+day+","+" "+year), "Date of birth displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Sex' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoSexValue()
	{
		editBasicInfo.selectSex();
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getSexValue();
		Assert.assertTrue(name.equals("Female"), "Sex displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'Nationality' "
			+ "field is displayed accordingly on preview page")
	public void verifyBasicInfoNationalityValue()
	{
		String text = "Dominican (Dominican Republic)";
		editBasicInfo.selectNationality(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getNationalityValue();
		Assert.assertTrue(name.equals(text), "Nationality displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'How long would "
			+ "you like to work in Japan?' field is displayed accordingly on preview page")
	public void verifyTimeInJapanValue()
	{
		String text = "10+ years";
		editBasicInfo.selectTimeInJapan(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String name = profilePreview.getTimeInJapanValue();
		Assert.assertTrue(name.equals(text), "Time period in Japn displayed is not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'If you chose "
			+ "\"Not sure\", please describe here' field is displayed accordingly on preview page")
	public void verifyTimeNotSureValue()
	{
		String text = properties.getProperty("exceed200");
		editBasicInfo.enterTimeNotSure(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String value = profilePreview.getTimeNotSureValue();
		Assert.assertTrue(value.equals(text), "Not sure description displayed in not correct");
	}
	
	@Test(description="This test verifies that the value entered for 'What is your "
			+ "hobby?' field is displayed accordingly on preview page")
	public void verifyHobbyValue()
	{
		String text = properties.getProperty("exceed200");
		editBasicInfo.enterHobby(text);
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
		String value = profilePreview.getHobbyValue();
		Assert.assertTrue(value.equals(text), "Hobby displayed in not correct");
	}
	
	@Test(description="This test verifies that validation error occurs for 'First Name' field")
	public void verifyFirstNameRequired()
	{
		String text = editBasicInfo.getFirstNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'Last Name' field")
	public void verifyLastNameRequired()
	{
		String text = editBasicInfo.getLastNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'E-mail Address 1 (Private E-mail)' field")
	public void verifyBasicInfoEmailRequired()
	{
		String text = editBasicInfo.getEmailRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'College' field")
	public void verifyBasicInfoCollegeRequired()
	{
		String text = editBasicInfo.getCollegeRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'USN / Registration Number' field")
	public void verifyBasicInfoUsnRequired()
	{
		String text = editBasicInfo.getUsnRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that validation error occurs for 'Expected Graduation Year' field")
	public void verifyBasicInfoYearRequired()
	{
		String text = editBasicInfo.getYearRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test(description="This test verifies that 応募に必須項目 show 'required to apply' text")
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
