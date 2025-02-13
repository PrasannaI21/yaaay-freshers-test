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
	public void setupSettings() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		settings = new EditSettingsPage(driver);
		properties = getProperties();
		navigateTo("/");
		profilePreview.login("prasanna.inamdar@zenken.co.jp", "Password_1");
	}
	
	@Test(description="This test verifies that user is redirected to 'Settings' edit page")
	public void verifySettingsUrl() throws InterruptedException
	{
		profilePreview.clickSettingsEdit();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url17"));
		Assert.assertEquals(settings.getSettingsHeadline(), "Settings");
	}
	
	@Test(description="This test verifies that Settings page title is correct")
	public void verifySettingsTitle()
	{
		profilePreview.clickSettingsEdit();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(description="This test verifies that the texts describing 'Yaaay Midcareer' and 'Zenken Newsletter' are correct")
	public void verifySetDesTexts()
	{
		profilePreview.clickSettingsEdit();
		Assert.assertEquals(settings.getSetting1Text(), properties.getProperty("setting1"));
		Assert.assertEquals(settings.getSetting2Text(), properties.getProperty("setting2"));
	}
	
	@Test(description="This test verifies that appropriate texts are displayed on preview page when checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter' are selected")
	public void verifySetSelected()
	{
		profilePreview.clickSettingsEdit();
		settings.selectSetCb1();
		settings.selectSetCb2();
		settings.clickSave();
		redirectionAssertions("#Settings");
		Assert.assertEquals(profilePreview.getSettingsValues().get(0), "Connected");
		Assert.assertEquals(profilePreview.getSettingsValues().get(1), "Subscribed"); 
	}
	
	@Test(description="This test verifies that appropriate texts are displayed on preview page when checkboxes for 'Yaaay Midcareer' and 'Zenken Newsletter' are NOT selected")
	public void verifySetNotSelected()
	{
		profilePreview.clickSettingsEdit();
		settings.deSelectSetCb1();
		settings.deSelectSetCb2();
		settings.clickSave();
		redirectionAssertions("#Settings");
		Assert.assertEquals(profilePreview.getSettingsValues().get(0), "Not Connected");
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
