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
	
	@Test(description="This test verifies that user is redirected to Job Preferences edit page")
	public void verifyJobPreferenceUrl()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertTrue(url.equals(properties.getProperty("url7")), "Did not redirect to Job Preference edit page");
		Assert.assertEquals(editJobPref.getJPHeadlineText(), "Job Preferences");
	}
	
	@Test(description="This test verifies that the placeholders displayed are correct for all text boxes on Job Preferences edit page")
	public void verifyPlaceholders()
	{
		log("Step 1: Click on Job Preferences edit icon");
		List<String> texts = editJobPref.getPlaceholderTexts();
		log("Step 2: Verify that placeholders for all text boxes are correct");
		for(int i=0;i<texts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeHolder"+(i+1));
			Assert.assertEquals(texts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test(description="This test verifies the activation and deactivation of First Preference text box based on the option selected from the dropdown")
	public void verifyFirstPreferenceStates()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Verify that upon selecting 'Other' option from 'First Preference' "
				+ "field, the corresponding text box turns active");
		Map<String, Boolean> states = editJobPref.getFirstPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation and deactivation of Second Preference text box based on the option selected from the dropdown")
	public void verifySecondPreferenceStates()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Verify that upon selecting 'Other' option from 'Second Preference' "
				+ "field, the corresponding text box turns active");
		Map<String, Boolean> states = editJobPref.getSecondPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies the activation and deactivation of Third Preference text box based on the option selected from the dropdown")
	public void verifyThirdPreferenceStates()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Verify that upon selecting 'Other' option from 'Third Preference' "
				+ "field, the corresponding text box turns active");
		Map<String, Boolean> states = editJobPref.getThirdPreferenceTextBoxStates();
		assertTextBoxStates(states);
	}
	
	@Test(description="This test verifies that jon preferences can be saved and their values are displayed accordingly on preview page")
	public void verifyPreferencesValues()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Select \"Server Engineer\" for First Preference");
		log("Step 3: Select \"Embedded Software Engineer\" for Second Preference");
		log("Step 4: Select \"Unity/XR engineer\" for Third Preference");
		editJobPref.selectPreferences(3, 11, 19);
		log("Step 5: Click on 'Save' button");
		editJobPref.clickSave();
		log("Step 6: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#JobPreferences");
		log("Step 7: Verify that the correct values are displayed for preferences on preview page");
		Assert.assertTrue(profilePreview.getFirstPrefValue().equals("Server Engineer"), "First Preference value is incorrect");
		Assert.assertTrue(profilePreview.getSecondPrefValue().equals("Embedded Software Engineer"), "Second Preference value is incorrect");
		Assert.assertTrue(profilePreview.getThirdPrefValue().equals("Unity/XR engineer"), "Third Preference value is incorrect");
		log("Step 8: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.jobPrefTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that 'Other' values entered are displayed accordingly on preview page")
	public void verifyPreferencesOtherValue()
	{
		log("Step 1: Click on Job Preferences edit icon");
		String text1 = "Paranormal Investigator";
		String text2 = "Animal Rights Activist";
		String text3 = "Master Chef";
		log("Step 2: Select 'Other' options for all job preferences");
		editJobPref.selectPreferencesOther();
		log("Step 3: Enter "+text1+" in First Preference text box");
		editJobPref.enterFirstPrefOther(text1);
		log("Step 4: Enter "+text2+" in Second Preference text box");
		editJobPref.enterSecondPrefOther(text2);
		log("Step 5: Enter "+text3+" in Third Preference text box");
		editJobPref.enterThirdPrefOther(text3);
		log("Step 6: Click on 'Save' button");
		editJobPref.clickSave();
		log("Step 7: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#JobPreferences");
		List<String> texts = profilePreview.getOtherPrefValues();
		log("Step 8: Verify that the correct values for are displayed for 'Other' preferences on preview page");
		Assert.assertTrue(texts.get(0).equals(text1), "Other first Preference value is incorrect");
		Assert.assertTrue(texts.get(1).equals(text2), "Other second Preference value is incorrect");
		Assert.assertTrue(texts.get(2).equals(text3), "Other third Preference value is incorrect");
	}
	
	@Test(description="This test verifies that 'Required to apply' text is displayed")
	public void verifyRequiredToApplyTexts()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: De-select all job preferences and click on 'Save' button");
		editJobPref.deSelectPref();
		log("Step 3: Verify that 'Required to apply' text is displayed on preview page");
		Assert.assertTrue(profilePreview.getTitleValue(profilePreview.jobPrefTitle).contains(properties.getProperty("error16")));
		log("Step 4: Click on Job Preferences edit icon");
		profilePreview.clickEdit(profilePreview.preferenceEdit);
		List<String> texts = editJobPref.getRequiredToApplyTexts();
		log("Step 5: Verify that 'Required to apply' text is displayed for all job preferences");
		for(int i=0;i<texts.size();i++)
		{
			Assert.assertTrue(texts.get(i).contains(properties.getProperty("error16")),"'Required to Apply' text is not displayed for the field: "+texts.get(i));
		}
	}
	
	@Test(description="This test verifies that the user is redirected to preview page upon clicking on 'Cancel' button on Job Preference edit page")
	public void verifyCancelRedirection()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 1: Click on 'Cancel' button");
		editJobPref.clickCancel();
		log("Step 3: Verify: Section display, Tab selection on preview page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#JobPreferences"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.jobPrefSection);
		Assert.assertTrue(state, "Job Preferences section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.jobPrefAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: First Preference")
	public void verifyCharExceedFirstPref()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Select 'Other' options for all job preferences");
		editJobPref.selectPreferencesOther();
		log("Step 3: Enter 201 characters in First Preference text box");
		editJobPref.enterFirstPrefOther(properties.getProperty("exceed200"));
		log("Step 4: Click on 'Cancel' button");
		editJobPref.clickSave();
		log("Step 5: Verify that validation error corresponds to exceeding the 200 character limit");
		Assert.assertTrue(editJobPref.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editJobPref.getFirstPrefErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Second Preference")
	public void verifyCharExceedSecondPref()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Select 'Other' options for all job preferences");
		editJobPref.selectPreferencesOther();
		log("Step 3: Enter 201 characters in Second Preference text box");
		editJobPref.enterSecondPrefOther(properties.getProperty("exceed200"));
		log("Step 4: Click on 'Cancel' button");
		editJobPref.clickSave();
		log("Step 5: Verify that validation error corresponds to exceeding the 200 character limit");
		Assert.assertTrue(editJobPref.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editJobPref.getSecondPrefErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Third Preference")
	public void verifyCharExceedThirdPref()
	{
		log("Step 1: Click on Job Preferences edit icon");
		log("Step 2: Select 'Other' options for all job preferences");
		editJobPref.selectPreferencesOther();
		log("Step 3: Enter 201 characters in Third Preference text box");
		editJobPref.enterThirdPrefOther(properties.getProperty("exceed200"));
		log("Step 4: Click on 'Cancel' button");
		editJobPref.clickSave();
		log("Step 5: Verify that validation error corresponds to exceeding the 200 character limit");
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
