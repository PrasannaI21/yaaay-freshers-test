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
	
	@Test(description="This test verifies that user is redirected to Field of Study edit page")
	public void verifyFieldOfStudyUrl()
	{
		Assert.assertTrue(url.equals(properties.getProperty("url8")), "Did not redirect to Field of Study page");
		String headline = editField.getHeadlineText();
		Assert.assertEquals(headline, "Field of Study", "Headline is incorrect");
	}
	
	@Test(description="This test verifies that expected options are displayed for 'Core' field")
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
	
	@Test(description="This test verifies that placeholder for 'Other (core field)' text box is correct")
	public void verifyCoreOtherPlaceholder()
	{
		editField.selectFieldDropdown(1);
		String placeholder = editField.getCoreOtherPlaceholder();
		Assert.assertEquals(placeholder, properties.getProperty("corePlaceholder"), "Core placeholder is incorrect");
	}
	
	@Test(description="This test verifies that expected options are displayed for 'IT' field")
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
	
	@Test(description="This test verifies that placeholder for 'Other (IT field)' text box is correct")
	public void verifyITOtherPlaceholder()
	{
		editField.selectFieldDropdown(2);
		String placeholder = editField.getITOtherPlaceholder();
		Assert.assertTrue(placeholder.equals(properties.getProperty("itPlaceholder")), "IT placeholder is incorrect");
	}
	
	@Test(description="This test verifies that the text box for 'Other (core field)' turns active/inactive based on the options selected")
	public void verifyCoreOtherTextBoxStates()
	{
		editField.selectFieldDropdown(1);
		List<Boolean> states = editField.getCoreRadioTextBoxStates();
		int currentIndex = 1;
		for(boolean state: states)
		{
			if(currentIndex < states.size()){
				Assert.assertFalse(state, "Text box should be inactive");
			}
			else{
				Assert.assertTrue(state, "Text box should be active");
			}
			currentIndex++;
		}
	}
	
	@Test(description="This test verifies that the text box for 'Other (IT field)' turns active/inactive based on the options selected")
	public void verifyITOtherTextBoxStates()
	{
		editField.selectFieldDropdown(2);
		List<Boolean> states = editField.getITRadioTextBoxStates();
		int currentIndex = 1;
		for(boolean state: states)
		{
			if(currentIndex < states.size()){
				Assert.assertFalse(state, "Text box should be inactive");
			}
			else{
				Assert.assertTrue(state, "Text box should be active");
			}
			currentIndex++;
		}
	}
	
	@Test(priority=3, description="This test verifies that a core option can be saved and is displayed accordingly on preview page")
	public void verifyCoreOptionSelection() throws IOException
	{
		editField.selectFieldDropdown(1);
		editField.selectCoreOption(3);
		editField.clickSave();
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertEquals(value, "Core", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getCoreLabel(), properties.getProperty("coreLabel"));
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("core4"));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that an IT option can be saved and is displayed accordingly on preview page")
	public void verifyITOptionSelection() throws InterruptedException
	{
		editField.selectFieldDropdown(2);
		editField.selectITOption(6);
		editField.clickSave();
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertEquals(value, "IT", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getITLabel(), properties.getProperty("itLabel"));
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("it7"));
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that the user is redirected to preview page upon clicking on 'Cancel' button on Field of Study edit page")
	public void verifyFieldOfStudyCancelRed()
	{
		editField.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#FieldOfStudy"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.fieldOfStudySection);
		Assert.assertTrue(state, "Field of Study section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.fieldOfStudyAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that a core other option can be saved and is displayed accordingly on preview page")
	public void verifyCoreOtherField()
	{
		String coreOther = "I don't know my core field";
		editField.enterCoreOther(coreOther);
		editField.clickSave();
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertEquals(value, "Core", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getCoreLabel(), properties.getProperty("coreLabel"));
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("core8"));
		Assert.assertEquals(profilePreview.getCoreOtherLabel(), properties.getProperty("coreOtherLabel"));
		Assert.assertEquals(profilePreview.getCoreOtherValue(), coreOther);
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(priority=1, description="This test verifies that an IT other option can be saved and is displayed accordingly on preview page")
	public void verifyITOtherField()
	{
		String itOther = "I don't know my IT field";
		editField.enterITOther(itOther);
		editField.clickSave();
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		Assert.assertEquals(value, "IT", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getITLabel(), properties.getProperty("itLabel"));
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("it13"));
		Assert.assertEquals(profilePreview.getITOtherLabel(), properties.getProperty("itOtherLabel"));
		Assert.assertEquals(profilePreview.getITOtherValue(), itOther);
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that validation error occurs when area of research is not selected")
	public void verifyResearchRequired()
	{
		editField.selectFieldDropdown(0);
		editField.clickSave();
		String box = editField.getResearchBoxText();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(box.contains(properties.getProperty("error16")), "Required to apply text is not displayed");
		Assert.assertTrue(box.contains(properties.getProperty("error1")), "Field should be required to save");
		
	}
	
	@Test(priority=2, description="This test verifies that validation error occurs when core area of study is not selected")
	public void verifyCoreFieldRequired()
	{
		editField.selectFieldDropdown(1);
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editField.getCoreOptionsTexts().contains(properties.getProperty("error1")), "Core field should be required to save");
	}
	
	@Test(priority=4, description="This test verifies that validation error occurs when IT area of study is not selected")
	public void verifyITFieldRequired()
	{
		editField.selectFieldDropdown(2);
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editField.getITOptionsTexts().contains(properties.getProperty("error1")), "IT field should be required to save");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Other (core field)' is blank")
	public void verifyCoreOtherFieldRequired()
	{
		editField.enterCoreOther("");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error1")), "Other core field should be required to save");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Other (IT field)' is blank")
	public void verifyITOtherFieldRequired()
	{
		editField.enterITOther("");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editField.getITOtherBoxText().contains(properties.getProperty("error1")), "Other IT field should be required to save");
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Other (core field)")
	public void verifyCoreOtherCharExceedError()
	{
		editField.enterCoreOther(properties.getProperty("exceed200"));
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error17")));
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Other (IT field)")
	public void verifyITOtherCharExceedError()
	{
		editField.enterITOther(properties.getProperty("exceed200"));
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
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
