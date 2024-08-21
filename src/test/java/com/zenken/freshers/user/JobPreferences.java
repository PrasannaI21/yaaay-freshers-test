package com.zenken.freshers.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditJobPreferencesPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class JobPreferences extends BaseTest{

	EditJobPreferencesPage editJobPref;
	ProfilePreviewPage profilePreview;
	Properties properties;
	String url;
	
	@BeforeMethod
	public void testSetUp() throws IOException
	{
		navigateTo("/");
		editJobPref = new EditJobPreferencesPage(driver);
		properties = getProperties();
		profilePreview = new ProfilePreviewPage(driver);
		profilePreview.login("prasanna.inamdar@zenken.co.jp", "Password_1");
		url = profilePreview.clickEdit(profilePreview.preferenceEdit);
	}
	
	@Test
	public void verifyJobPreferenceUrl()
	{
		Assert.assertTrue(url.equals(properties.getProperty("url7")), "Did not redirect to Job Preference edit page");
	}
	
	@Test
	public void verifyPlaceholders()
	{
		List<String> texts = editJobPref.getPlaceholderTexts();
		for(int i=0;i<texts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeHolder"+(i+1));
			Assert.assertEquals(texts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test
	public void verifyFirstPreferenceStates()
	{
		Map<String, Boolean> states = editJobPref.getFirstPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifySecondPreferenceStates()
	{
		Map<String, Boolean> states = editJobPref.getSecondPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifyThirdPreferenceStates()
	{
		Map<String, Boolean> states = editJobPref.getThirdPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test
	public void verifyPreferencesValues()
	{
		editJobPref.selectPreferences(3, 11, 19);
		editJobPref.clickSave();
		Assert.assertTrue(profilePreview.getFirstPrefValue().equals("Server Engineer"), "First Preference value is incorrect");
		Assert.assertTrue(profilePreview.getSecondPrefValue().equals("Embedded Software Engineer"), "Second Preference value is incorrect");
		Assert.assertTrue(profilePreview.getThirdPrefValue().equals("Unity/XR engineer"), "Third Preference value is incorrect");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.jobPrefTitle).contains(properties.getProperty("error16")));
		redirectionAssertions("#JobPreferences");
	}
	
	@Test
	public void verifyPreferencesOtherValue()
	{
		String text1 = "Paranormal investigator";
		String text2 = "Animal rights activist";
		String text3 = "Master chef";
		editJobPref.selectPreferencesOther();
		editJobPref.enterFirstPrefOther(text1);
		editJobPref.enterSecondPrefOther(text2);
		editJobPref.enterThirdPrefOther(text3);
		editJobPref.clickSave();
		List<String> texts = profilePreview.getOtherPrefValues();
		Assert.assertTrue(texts.get(0).equals(text1), "Other first Preference value is incorrect");
		Assert.assertTrue(texts.get(1).equals(text2), "Other second Preference value is incorrect");
		Assert.assertTrue(texts.get(2).equals(text3), "Other third Preference value is incorrect");
		redirectionAssertions("#JobPreferences");
	}
	
	@Test
	public void verifyRequiredToApplyTexts()
	{
		editJobPref.deSelectPref();
		Assert.assertTrue(profilePreview.getTitleValue(profilePreview.jobPrefTitle).contains(properties.getProperty("error16")));
		profilePreview.clickEdit(profilePreview.preferenceEdit);
		List<String> texts = editJobPref.getRequiredToApplyTexts();
		for(int i=0;i<texts.size();i++)
		{
			Assert.assertTrue(texts.get(i).contains(properties.getProperty("error16")),"'Required to Apply' text is not displayed for the field: "+texts.get(i));
		}
	}
	
	@Test
	public void verifyCancelRedirection()
	{
		editJobPref.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#JobPreferences"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.jobPrefSection);
		Assert.assertTrue(state, "Job Preferences section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.jobPrefAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test
	public void verifyCharExceedFirstPref()
	{
		editJobPref.selectPreferencesOther();
		editJobPref.enterFirstPrefOther(properties.getProperty("exceed200"));
		editJobPref.clickSave();
		Assert.assertTrue(editJobPref.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editJobPref.getFirstPrefErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test
	public void verifyCharExceedSecondPref()
	{
		editJobPref.selectPreferencesOther();
		editJobPref.enterSecondPrefOther(properties.getProperty("exceed200"));
		editJobPref.clickSave();
		Assert.assertTrue(editJobPref.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editJobPref.getSecondPrefErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test
	public void verifyCharExceedThirdPref()
	{
		editJobPref.selectPreferencesOther();
		editJobPref.enterThirdPrefOther(properties.getProperty("exceed200"));
		editJobPref.clickSave();
		Assert.assertTrue(editJobPref.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editJobPref.getThirdPrefErrorText().contains(properties.getProperty("error17")));
	}
	
	private void redirectionAssertions(String parameter)
	{
		String alert = profilePreview.getAlertText();
		if(profilePreview.isProfileComplete())
		{
			Assert.assertTrue(alert.equals(properties.getProperty("alert4")), "Alert text is not correct");
		}
		else{
			Assert.assertTrue(alert.equals(properties.getProperty("alert5")), "Alert text is not correct");
		}
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains(parameter), "The URL does not contain the expected anchor");
		boolean state2 = profilePreview.getSectionDisplay(profilePreview.jobPrefSection);
		Assert.assertTrue(state2, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.jobPrefAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
