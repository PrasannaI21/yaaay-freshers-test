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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertTrue(url.equals(properties.getProperty("url8")), "Did not redirect to Field of Study page");
		String headline = editField.getHeadlineText();
		Assert.assertEquals(headline, "Field of Study", "Headline is incorrect");
	}
	
	@Test(description="This test verifies that expected options are displayed for 'Core' field")
	public void verifyCoreOptions()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(1);
		String coreOptions = editField.getCoreOptionsTexts();
		log("Step 3: Verify that core options are displayed");
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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(1);
		String placeholder = editField.getCoreOtherPlaceholder();
		log("Step 3: Verify that placeholder equals "+ "\""+"Describe Your Core Field"+"\"");
		Assert.assertEquals(placeholder, properties.getProperty("corePlaceholder"), "Core placeholder is incorrect");
	}
	
	@Test(description="This test verifies that expected options are displayed for 'IT' field")
	public void verifyITOptions()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(2);
		String coreOptions = editField.getITOptionsTexts();
		log("Step 3: Verify that IT options are displayed");
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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(2);
		String placeholder = editField.getITOtherPlaceholder();
		log("Step 3: Verify that placeholder equals "+ "\""+"Describe Your IT Field"+"\"");
		Assert.assertTrue(placeholder.equals(properties.getProperty("itPlaceholder")), "IT placeholder is incorrect");
	}
	
	@Test(description="This test verifies that the text box for 'Other (core field)' turns active/inactive based on the options selected")
	public void verifyCoreOtherTextBoxStates()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(1);
		log("Step 3: Click on each radio button for core options");
		List<Boolean> states = editField.getCoreRadioTextBoxStates();
		int currentIndex = 1;
		log("Step 4: Verify that 'Other (core field)' text box turns active only when 'Other' option is clicked");
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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(2);
		log("Step 3: Click on each radio button for IT options");
		List<Boolean> states = editField.getITRadioTextBoxStates();
		int currentIndex = 1;
		log("Step 4: Verify that 'Other (IT field)' text box turns active only when 'Other' option is clicked");
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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(1);
		log("Step 3: Click on radio button for \"Instrumentation engineer\"");
		editField.selectCoreOption(3);
		log("Step 4: Click on 'Save' button");
		editField.clickSave();
		log("Step 5: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		log("Step 6: Verify that the value for 'What is your main area of research and study?' is \"Core\" on preview page");
		Assert.assertEquals(value, "Core", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getCoreLabel(), properties.getProperty("coreLabel"));
		log("Step 7: Verify that the value for 'Which core field is your area of study/focus?' is \"Instrumentation engineer\" on preview page");
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("core4"));
		log("Step 8: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that an IT option can be saved and is displayed accordingly on preview page")
	public void verifyITOptionSelection() throws InterruptedException
	{
//		Thread.sleep(2000);
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(2);
		log("Step 3: Click on radio button for \"Data Science\"");
		editField.selectITOption(6);
		log("Step 4: Click on 'Save' button");
//		Thread.sleep(5000);
		editField.clickSave();
		log("Step 5: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		log("Step 6: Verify that the value for 'What is your main area of research and study?' is \"IT\" on preview page");
		Assert.assertEquals(value, "IT", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getITLabel(), properties.getProperty("itLabel"));
		log("Step 7: Verify that the value for 'Which IT field is your area of study/focus?' is \"Data Science\" on preview page");
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("it7"));
		log("Step 8: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that the user is redirected to preview page upon clicking on 'Cancel' button on Field of Study edit page")
	public void verifyCancelRedirection()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Click on 'Cancel' button");
		editField.clickCancel();
		log("Step 3: Verify: Section display, Tab selection on preview page");
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
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		String coreOther = "I don't know my core field";
		log("Step 3: Click on 'Other' radio button");
		log("Step 4: Enter text: "+"\""+coreOther+"\""+" in the text box");
		editField.enterCoreOther(coreOther);
		log("Step 5: Click on 'Save' button");
		editField.clickSave();
		log("Step 6: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		log("Step 7: Verify that the value for 'What is your main area of research and study?' is \"Core\" on preview page");
		Assert.assertEquals(value, "Core", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getCoreLabel(), properties.getProperty("coreLabel"));
		log("Step 8: Verify that the value for 'Which core field is your area of study/focus?' is \"Other\" on preview page");
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("core8"));
		Assert.assertEquals(profilePreview.getCoreOtherLabel(), properties.getProperty("coreOtherLabel"));
		log("Step 9: Verify that the value for 'Other (core field)' is "+"\""+coreOther+"\""+" on preview page");
		Assert.assertEquals(profilePreview.getCoreOtherValue(), coreOther);
		log("Step 10: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(priority=1, description="This test verifies that an IT other option can be saved and is displayed accordingly on preview page")
	public void verifyITOtherField()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		String itOther = "I don't know my IT field";
		log("Step 3: Click on 'Other' radio button");
		log("Step 4: Enter text: "+"\""+itOther+"\""+" in the text box");
		editField.enterITOther(itOther);
		log("Step 5: Click on 'Save' button");
		editField.clickSave();
		log("Step 6: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#FieldOfStudy");
		String value = profilePreview.getAreaOfResearchValue();
		log("Step 7: Verify that the value for 'What is your main area of research and study?' is \"IT\" on preview page");
		Assert.assertEquals(value, "IT", "Area of research is incorrect");
		Assert.assertEquals(profilePreview.getITLabel(), properties.getProperty("itLabel"));
		log("Step 8: Verify that the value for 'Which IT field is your area of study/focus?' is \"Other\" on preview page");
		Assert.assertEquals(profilePreview.getFieldValue(), properties.getProperty("it13"));
		Assert.assertEquals(profilePreview.getITOtherLabel(), properties.getProperty("itOtherLabel"));
		log("Step 9: Verify that the value for 'Other (IT field)' is "+"\""+itOther+"\""+" on preview page");
		Assert.assertEquals(profilePreview.getITOtherValue(), itOther);
		log("Step 10: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.fieldOfStduyTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(description="This test verifies that validation error occurs when area of research is not selected")
	public void verifyResearchRequired()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Select' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(0);
		log("Step 3: Click on 'Save' button");
		editField.clickSave();
		log("Step 4: Verify that validation error occurs for required to save field");
		String box = editField.getResearchBoxText();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 5: Verify that 'Required to apply' text is displayed");
		Assert.assertTrue(box.contains(properties.getProperty("error16")), "Required to apply text is not displayed");
		Assert.assertTrue(box.contains(properties.getProperty("error1")), "Field should be required to save");
		
	}
	
	@Test(priority=2, description="This test verifies that validation error occurs when core area of study is not selected")
	public void verifyCoreFieldRequired()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(1);
		log("Step 3: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 4: Verify that validation error occurs for required to save field");
		Assert.assertTrue(editField.getCoreOptionsTexts().contains(properties.getProperty("error1")), "Core field should be required to save");
	}
	
	@Test(priority=4, description="This test verifies that validation error occurs when IT area of study is not selected")
	public void verifyITFieldRequired()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		editField.selectFieldDropdown(2);
		log("Step 3: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 4: Verify that validation error occurs for required to save field");
		Assert.assertTrue(editField.getITOptionsTexts().contains(properties.getProperty("error1")), "IT field should be required to save");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Other (core field)' is blank")
	public void verifyCoreOtherFieldRequired()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		log("Step 3: Click on 'Other' radio button");
		editField.enterCoreOther("");
		log("Step 4: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 5: Verify that validation error occurs for 'Other (core field)'");
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error1")), "Other core field should be required to save");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Other (IT field)' is blank")
	public void verifyITOtherFieldRequired()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		log("Step 3: Click on 'Other' radio button");
		editField.enterITOther("");
		log("Step 4: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 5: Verify that validation error occurs for 'Other (IT field)'");
		Assert.assertTrue(editField.getITOtherBoxText().contains(properties.getProperty("error1")), "Other IT field should be required to save");
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Other (core field)")
	public void verifyCoreOtherCharExceedError()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'Core' option from 'What is your main area of research and study?' dropdown");
		log("Step 3: Click on 'Other' radio button");
		log("Step 4: Enter 201 characters in 'Other (core field)' text box");
		editField.enterCoreOther(properties.getProperty("exceed200"));
		log("Step 5: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 6: Verify that validation error for maximum characters occurs for 'Other (core field)'");
		Assert.assertTrue(editField.getCoreOtherBoxText().contains(properties.getProperty("error17")));
	}
	
	@Test(description="The test verifies that validation error for maximum characters occurs: Other (IT field)")
	public void verifyITOtherCharExceedError()
	{
		log("Step 1: Click on Field of Study edit icon");
		log("Step 2: Select 'IT' option from 'What is your main area of research and study?' dropdown");
		log("Step 3: Click on 'Other' radio button");
		log("Step 4: Enter 201 characters in 'Other (IT field)' text box");
		editField.enterITOther(properties.getProperty("exceed200"));
		log("Step 5: Click on 'Save' button");
		editField.clickSave();
		Assert.assertEquals(editField.getAlert(), properties.getProperty("alert3"));
		log("Step 6: Verify that validation error for maximum characters occurs for 'Other (IT field)'");
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
