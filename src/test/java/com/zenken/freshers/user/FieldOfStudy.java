package com.zenken.freshers.user;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditFieldOfStudyPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class FieldOfStudy extends BaseTest{

	EditFieldOfStudyPage editField;
	ProfilePreviewPage profilePreview;
	Properties properties;
	String url;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		navigateTo("/");
		editField = new EditFieldOfStudyPage(driver);
		profilePreview = new ProfilePreviewPage(driver);
		profilePreview.login("prasanna.inamdar@zenken.co.jp", "Password_1");
		url = profilePreview.clickEdit(profilePreview.fieldOfStudyEdit);
		properties = getProperties();
	}
	
	@Test
	public void verifyFieldOfStudyUrl()
	{
		Assert.assertTrue(url.equals(properties.getProperty("url8")), "Did not redirect to Field of Study page");
		String headline = editField.getHeadlineText();
		Assert.assertEquals(headline, "Field of Study", "Headline is incorrect");
	}
	
	@Test
	public void verifyCoreOptions()
	{
		editField.selectFieldDropdown(1);
		String coreOptions = editField.getCoreOptionsTexts();
		for(int i=1; i<=8; i++)
		{
			String key = "core" + i;
			String option = properties.getProperty(key);
			Assert.assertTrue(coreOptions.contains(option), "Core option not found: " + option);
		}
	}
	
	@Test
	public void verifyCoreOtherPlaceholder()
	{
		editField.selectFieldDropdown(1);
		String placeholder = editField.getCoreOtherPlaceholder();
		Assert.assertTrue(placeholder.equals(properties.getProperty("corePlaceholder")), "Core placeholder is incorrect");
	}
	
	@Test
	public void verifyITOptions()
	{
		editField.selectFieldDropdown(2);
		String coreOptions = editField.getITOptionsTexts();
		for(int i=1; i<=13; i++)
		{
			String key = "it" + i;
			String option = properties.getProperty(key);
			Assert.assertTrue(coreOptions.contains(option), "IT option not found: " + option);
		}
	}
	
	@Test
	public void verifyITOtherPlaceholder()
	{
		editField.selectFieldDropdown(2);
		String placeholder = editField.getITOtherPlaceholder();
		Assert.assertTrue(placeholder.equals(properties.getProperty("itPlaceholder")), "IT placeholder is incorrect");
	}
	
	@Test
	public void verifyCoreOtherTextBoxStates()
	{
		editField.selectFieldDropdown(1);
		List<Boolean> states = editField.getCoreRadioTextBoxStates();
		int currentIndex = 1;
		for(boolean state: states)
		{
			if(currentIndex < states.size())
			{
				Assert.assertFalse(state, "Text box should be inactive");
			}
			else
			{
				Assert.assertTrue(state, "Text box should be active");
			}
			currentIndex++;
		}
	}
	
	@Test
	public void verifyITOtherTextBoxStates()
	{
		editField.selectFieldDropdown(2);
		List<Boolean> states = editField.getITRadioTextBoxStates();
		int currentIndex = 1;
		for(boolean state: states)
		{
			if(currentIndex < states.size())
			{
				Assert.assertFalse(state, "Text box should be inactive");
			}
			else
			{
				Assert.assertTrue(state, "Text box should be active");
			}
			currentIndex++;
		}
	}
	
	@Test(priority=3)
	public void verifyCoreOptionSelection() throws IOException
	{
		editField.selectFieldDropdown(1);
		editField.selectCoreOption(3);
		editField.clickSave();
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertTrue(value.equals("Core"), "Area of research is incorrect");
		Assert.assertTrue(profilePreview.getCoreLabel().equals(properties.getProperty("coreLabel")));
		Assert.assertTrue(profilePreview.getFieldValue().equals(properties.getProperty("core4")));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
		redirectionAssertions("#FieldOfStudy");
//		takeScreenshot(driver, "example");
	}
	
	@Test
	public void verifyITOptionSelection()
	{
		editField.selectFieldDropdown(2);
		editField.selectITOption(6);
		editField.clickSave();
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertTrue(value.equals("IT"), "Area of research is incorrect");
		Assert.assertTrue(profilePreview.getITLabel().equals(properties.getProperty("itLabel")));
		Assert.assertTrue(profilePreview.getFieldValue().equals(properties.getProperty("it7")));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
		redirectionAssertions("#FieldOfStudy");
	}
	
	@Test
	public void verifyCancelRedirection()
	{
		editField.selectFieldDropdown(2);
		editField.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#FieldOfStudy"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.fieldOfStudySection);
		Assert.assertTrue(state, "Field of Study section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.fieldOfStudyAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test
	public void verifyCoreOtherField()
	{
		String coreOther = "I don't know my core field";
		editField.enterCoreOther(coreOther);
		editField.clickSave();
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertTrue(value.equals("Core"), "Area of research is incorrect");
		Assert.assertTrue(profilePreview.getCoreLabel().equals(properties.getProperty("coreLabel")));
		Assert.assertTrue(profilePreview.getFieldValue().equals(properties.getProperty("core8")));
		Assert.assertTrue(profilePreview.getCoreOtherLabel().equals(properties.getProperty("coreOtherLabel")));
		Assert.assertTrue(profilePreview.getCoreOtherValue().equals(coreOther));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
		redirectionAssertions("#FieldOfStudy");
	}
	
	@Test(priority=1)
	public void verifyITOtherField()
	{
		String itOther = "I don't know my IT field";
		editField.enterITOther(itOther);
		editField.clickSave();
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertTrue(value.equals("IT"), "Area of research is incorrect");
		Assert.assertTrue(profilePreview.getITLabel().equals(properties.getProperty("itLabel")));
		Assert.assertTrue(profilePreview.getFieldValue().equals(properties.getProperty("it13")));
		Assert.assertTrue(profilePreview.getITOtherLabel().equals(properties.getProperty("itOtherLabel")));
		Assert.assertTrue(profilePreview.getITOtherValue().equals(itOther));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
		redirectionAssertions("#FieldOfStudy");
	}
	
	@Test
	public void verifyResearchRequired()
	{
		editField.selectFieldDropdown(0);
		editField.clickSave();
		String box = editField.getResearchBoxText();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(box.contains(properties.getProperty("error16")), "Required to apply text is not displayed");
		Assert.assertTrue(box.contains(properties.getProperty("error1")), "Field should be required to save");
		
	}
	
	@Test(priority=2, dependsOnMethods="verifyITOtherField")
	public void verifyCoreFieldRequired()
	{
		editField.selectFieldDropdown(1);
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getCoreOptionsTexts().contains(properties.getProperty("error1")), "Core field should be required to save");
	}
	
	@Test(priority=4, dependsOnMethods="verifyCoreOptionSelection")
	public void verifyITFieldRequired()
	{
		editField.selectFieldDropdown(2);
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getITOptionsTexts().contains(properties.getProperty("error1")), "IT field should be required to save");
	}
	
	@Test
	public void verifyCoreOtherFieldRequired()
	{
		editField.enterCoreOther("");
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error1")), "Other core field should be required to save");
	}
	
	@Test
	public void verifyITOtherFieldRequired()
	{
		editField.enterITOther("");
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getITOtherBoxText().contains(properties.getProperty("error1")), "Other IT field should be required to save");
	}
	
	@Test
	public void verifyCoreOtherCharExceedError()
	{
		editField.enterCoreOther(properties.getProperty("exceed200"));
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error17")));
	}
	
	@Test
	public void verifyITOtherCharExceedError()
	{
		editField.enterITOther(properties.getProperty("exceed200"));
		editField.clickSave();
		Assert.assertTrue(editField.getAlert().equals(properties.getProperty("alert3")));
		Assert.assertTrue(editField.getITOtherBoxText().contains(properties.getProperty("error17")));
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.fieldOfStudySection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.fieldOfStudyAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
