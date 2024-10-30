package com.zenken.freshers.user;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditSettingsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Settings extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditSettingsPage settings;
	Properties properties;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		settings = new EditSettingsPage(driver);
		properties = getProperties();
		navigateTo("/");
		profilePreview.login("prasanna.inamdar@zenken.co.jp", "Password_1");
	}
	
	@Test(description="This test verifies that user is redirected to 'Settings' edit page")
	public void verifySettingsUrl()
	{
		log("Step 1: Click on Settings edit icon");
		profilePreview.clickSettingsEdit();
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url17"));
		Assert.assertEquals(settings.getSettingsHeadline(), "Settings");
	}
	
	@Test(description="This test verifies that Settings page title is correct")
	public void verifySettingsTitle()
	{
		log("Step 1: Click on Settings edit icon");
		profilePreview.clickSettingsEdit();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(description="This test verifies that the texts describing 'Yaaay Midcareer' and 'Zenken Newsletter' are correct")
	public void verifySetDesTexts()
	{
		log("Step 1: Click on Settings edit icon");
		profilePreview.clickSettingsEdit();
		log("Step 2: Verify that the description  for 'Yaaay Midcareer' displayed is correct");
		Assert.assertEquals(settings.getSetting1Text(), properties.getProperty("setting1"));
		log("Step 3: Verify that the description  for 'Zenken Newsletter' displayed is correct");
		Assert.assertEquals(settings.getSetting2Text(), properties.getProperty("setting2"));
	}
	
	@Test(description="This test verifies that appropriate texts are displayed on preview page when checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter' are selected")
	public void verifySetSelected()
	{
		log("Step 1: Click on Settings edit icon");
		profilePreview.clickSettingsEdit();
		log("Step 2: Select checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter'");
		settings.selectSetCb1();
		settings.selectSetCb2();
		log("Step 3: Click on 'Save' button");
		settings.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Settings");
		log("Step 5: Verify that the value for 'Yaaay Midcareer' is \"Connected\"");
		Assert.assertEquals(profilePreview.getSettingsValues().get(0), "Connected");
		log("Step 6: Verify that the value for 'Zenken Newsletter' is \"Subscribed\"");
		Assert.assertEquals(profilePreview.getSettingsValues().get(1), "Subscribed"); 
	}
	
	@Test(description="This test verifies that appropriate texts are displayed on preview page when checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter' are NOT selected")
	public void verifySetNotSelected()
	{
		log("Step 1: Click on Settings edit icon");
		profilePreview.clickSettingsEdit();
		log("Step 2: De-select checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter'");
		settings.deSelectSetCb1();
		settings.deSelectSetCb2();
		log("Step 3: Click on 'Save' button");
		settings.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Settings");
		log("Step 5: Verify that the value for 'Yaaay Midcareer' is \"Not Connected\"");
		Assert.assertEquals(profilePreview.getSettingsValues().get(0), "Not Connected");
		log("Step 6: Verify that the value for 'Zenken Newsletter' is \"Unsubscribed\"");
		Assert.assertEquals(profilePreview.getSettingsValues().get(1), "Unsubscribed"); 
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.settingsSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.settingsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
