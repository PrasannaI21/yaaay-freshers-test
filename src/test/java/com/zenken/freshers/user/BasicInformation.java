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
		url = profilePreview.clickEdit();
		state = editBasicInfo.isSectionComplete();
		properties = getProperties();
	}
	
//	@Test  //Test for preview page
//	public void verifyHeadlineText()
//	{
//		String headline = profilePreview.getHeadlineText();
//		Assert.assertTrue(headline.equals(properties.getProperty("text7")), "Profile page's headline is incorrect");
//	}
	
	@Test
	public void verifyEditUrl()
	{
		Assert.assertTrue(url.equals(properties.getProperty("url6")), "Did not redirect to edit page");
	}
	
	@Test
	public void verifyTitleName()
	{
		String title = profilePreview.getPageTitle();
		Assert.assertTrue(title.equals(properties.getProperty("title4")), "Page title is incorrect");
	}
	
	@Test(dependsOnMethods="verifyProfileUpdate")
	public void verifySavedData()//Need to login with user+2
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
	
	@Test
	public void verifyPlaceholders()
	{
		List<String> pTexts = editBasicInfo.getPlaceholders();
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholder"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test
	public void verifyTextBoxInactiveLastName()
	{
		boolean state = editBasicInfo.getLastNameTextBoxState();
		Assert.assertFalse(state, "Text box remained active");
	}
	
	@Test
	public void verifyNotSureStates()
	{
		Map<String, Boolean> states = editBasicInfo.getNotSureTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifyOtherBachelorStates()
	{
		Map<String, Boolean> states = editBasicInfo.getOtherBachelorTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifyOtherMasterStates()
	{
		Map<String, Boolean> states = editBasicInfo.getOtherMasterTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifyProceedOtherStates()
	{
		Map<String, Boolean> states = editBasicInfo.getProceedOtherTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
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
	
	@Test
	public void verifyProfileUpdate()//Need to login with user+2
	{
		editBasicInfo.enterData();
		editBasicInfo.clickSave();
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyRedirectionForCancel()
	{
		editBasicInfo.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#BasicInformation"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay();
		Assert.assertTrue(state, "Basic Information section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute();
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test
	public void verifyFirstNameValue()
	{
		String text = "Khal";
		editBasicInfo.enterFirstName(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getFirstNameValue();
		Assert.assertTrue(name.equals(text), "First name displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyMiddleNameValue()
	{
		String text = "Viserys";
		editBasicInfo.enterMiddleName(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getMiddleNameValue();
		Assert.assertTrue(name.equals(text), "Middle name displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyLastNameValue()
	{
		String text = "Drogo";
		editBasicInfo.enterLastName(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getLastNameValue();
		Assert.assertTrue(name.equals(text), "Last name displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyEmail1Value()
	{
		String text = "heythere@example.com";
		editBasicInfo.enterEmail1(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getEmail1Value();
		Assert.assertTrue(name.equals(text), "Email address 1 displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyEmail2Value()
	{
		String text = "heythere2@example.com";
		editBasicInfo.enterEmail2(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getEmail2Value();
		Assert.assertTrue(name.equals(text), "Email address 2 displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyPhoneValue()
	{
		String text = "1234-567-890";
		editBasicInfo.enterPhone(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getPhoneValue();
		Assert.assertTrue(name.equals(text), "Phone number displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyAddressValue()
	{
		String text = "15 Yemen Road, Yemen!";
		editBasicInfo.enterAddress(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getAddressValue();
		Assert.assertTrue(name.equals(text), "Address displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyCityValue()
	{
		String text = "Guadalajara, Mexico";
		editBasicInfo.enterCity(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getCityValue();
		Assert.assertTrue(name.equals(text), "City displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyStateValue()
	{
		String text = "Texas";
		editBasicInfo.enterState(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getStateValue();
		Assert.assertTrue(name.equals(text), "State displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyCountryValue()
	{
		String text = "British Virgin Islands";
		editBasicInfo.selectCountry(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getCountryValue();
		Assert.assertTrue(name.equals(text), "Country displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyPinCodeValue()
	{
		String text = "36976";
		editBasicInfo.enterPinCode(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getPinCodeValue();
		Assert.assertTrue(name.equals(text), "Pin code displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyDobValue()
	{
		String day = "31";
		String month = "August";
		String year = "2014";
		editBasicInfo.selectDateOfBirth(day, month, year);
		editBasicInfo.clickSave();
		String name = profilePreview.getDobValue();
		Assert.assertTrue(name.equals(month+" "+day+","+" "+year), "Date of birth displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifySexValue()
	{
		editBasicInfo.selectSex();
		editBasicInfo.clickSave();
		String name = profilePreview.getSexValue();
		Assert.assertTrue(name.equals("Female"), "Sex displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyNationalityValue()
	{
		String text = "Dominican (Dominican Republic)";
		editBasicInfo.selectNationality(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getNationalityValue();
		Assert.assertTrue(name.equals(text), "Nationality displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyTimeInJapanValue()
	{
		String text = "10+ years";
		editBasicInfo.selectTimeInJapan(text);
		editBasicInfo.clickSave();
		String name = profilePreview.getTimeInJapanValue();
		Assert.assertTrue(name.equals(text), "Time period in Japn displayed is not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyTimeNotSureValue()
	{
		String text = properties.getProperty("keep500");
		editBasicInfo.enterTimeNotSure(text);
		editBasicInfo.clickSave();
		String value = profilePreview.getTimeNotSureValue();
		Assert.assertTrue(value.equals(text), "Not sure description displayed in not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyHobbyValue()
	{
		String text = properties.getProperty("keep500");
		editBasicInfo.enterHobby(text);
		editBasicInfo.clickSave();
		String value = profilePreview.getHobbyValue();
		Assert.assertTrue(value.equals(text), "Hobby displayed in not correct");
		redirectionAssertions("#BasicInformation", state);
	}
	
	@Test
	public void verifyFirstNameRequired()
	{
		String text = editBasicInfo.getFirstNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyLastNameRequired()
	{
		String text = editBasicInfo.getLastNameRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyEmailRequired()
	{
		String text = editBasicInfo.getEmailRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyCollegeRequired()
	{
		String text = editBasicInfo.getCollegeRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyUsnRequired()
	{
		String text = editBasicInfo.getUsnRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyYearRequired()
	{
		String text = editBasicInfo.getYearRequiredText();
		String text2 = editBasicInfo.getAlert();
		Assert.assertTrue(text2.equals(properties.getProperty("alert3")), "Alert text is not correct");
		Assert.assertTrue(text.contains(properties.getProperty("error16")), "Required to Apply text is not displayed");
		Assert.assertTrue(text.contains(properties.getProperty("error1")), "Required text is not displayed");
	}
	
	@Test
	public void verifyRequiredToApplyTexts()//Need to login with user+2 having incomplete profile
	{
		List<String> texts = editBasicInfo.getRequiredToApplyTexts();
		for(int i=0;i<texts.size();i++)
		{
			Assert.assertTrue(texts.get(i).contains(properties.getProperty("error16")),"'Required to Apply' text is not displayed for the field: "+texts.get(i));
		}
	}
	
	private void assertTextBoxStates(Map<String, Boolean> states)
	{
		int numberOfOptions = states.size();
		int currentIndex = 1;
		for(Map.Entry<String, Boolean> entry : states.entrySet())
		{
			if(currentIndex < numberOfOptions)
			{
				Assert.assertFalse(entry.getValue(), "TextBox should be inactive for option: " + entry.getKey());
			}
			else
			{
				Assert.assertTrue(entry.getValue(), "TextBox should be active for option: " + entry.getKey());
			}
			currentIndex++;
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
			Assert.assertTrue(profilePreview.getTitleValue().contains(properties.getProperty("error16")));
		}
		else{
			Assert.assertFalse(profilePreview.getTitleValue().contains(properties.getProperty("error16")));
		}
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains(parameter), "The URL does not contain the expected anchor");
		boolean state2 = profilePreview.getSectionDisplay();
		Assert.assertTrue(state2, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute();
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
