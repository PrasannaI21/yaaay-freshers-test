package com.zenken.freshers.user;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditSkillsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Skills extends BaseTest{

	EditSkillsPage editSkills;
	ProfilePreviewPage profilePreview;
	Properties properties;
	String url;
	
	@BeforeMethod
	public void setupTest() throws IOException
	{
		editSkills = new EditSkillsPage(driver);
		profilePreview = new ProfilePreviewPage(driver);
		navigateTo("/");
		profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
	    url = profilePreview.clickEdit(profilePreview.skillsEdit);
		properties = getProperties();
	}
	
	@Test(priority=1, description="This test verifies that 'Required to apply' text is "
			+ "displayed on Skills edit page and preview page")
	public void verifySkillRequiredToApply()
	{
		log("Step 1: Delete existing skills that are added");
		List<WebElement> deleteIcons = editSkills.getDeleteIcons();
		while(!deleteIcons.isEmpty())
			{
				editSkills.deleteSkill(0);
				deleteIcons = editSkills.getDeleteIcons();
			}
		log("Step 2: Click on 'Save' button");
		editSkills.clickSave();
		log("Step 3: Verify that 'Required to apply' text is displayed on preview page");
		Assert.assertTrue(profilePreview.getTitleValue(profilePreview.skillsTitle).contains(properties.getProperty("error16")));
		log("Step 4: Click on Skills edit icon");
		profilePreview.clickEdit(profilePreview.skillsEdit);
		log("Step 5: Verify that 'Required to apply' text is displayed on Skills edit page");
		Assert.assertTrue(editSkills.getLabelText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=6, description="This test verifies that user is redirected to 'Skills' edit page")
	public void verifySkillsUrl()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(url, properties.getProperty("url9"));
		Assert.assertEquals(editSkills.getHeadlineText(), "Skills");
	}
	
	@Test(priority=7, description="This test verifies that Skills page title is correct")
	public void verifyEditSkillsPageTitle()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(editSkills.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=8, description="This test verifies that expected placeholder is displayed")
	public void verifySkillsPlaceholder()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Verify that placeholder displayed is correct");
		Assert.assertEquals(editSkills.getSkillsPlaceholder(), properties.getProperty("skillsPH"));
	}
	
	@Test(priority=9, description="This test verifies that skills are populated based "
			+ "on the character input provided in the text box")
	public void verifySkillOptionsPopulation()
	{
		log("Step 1: Click on Skills edit icon");
		String[] letters = {"c", "ca", "pyth", "python"};
		log("Step 2: Enter single and multiple characters in the text box");
		log("Step 3: Verify that skill options are displayed in the dropdown and start "
				+ "with characters input");
		for(String letter : letters)
		{
			editSkills.enterSkills(letter);
			List<WebElement> options = editSkills.getOptionsDisplayed();
			Assert.assertFalse(options.isEmpty(), "No options displayed after "+letter);
			for(WebElement option : options)
			{
				String optionText = option.getText().toLowerCase();
				Assert.assertTrue(optionText.startsWith(letter), "Options do not start with "+letter);
			}
		}
	}
	
	@Test(priority=10, description="This test verifies that a skill option can be selected")
	public void verifySkillSelection()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Enter skill \"Oracle\" in the text box");
		editSkills.enterSkills("Oracle");
		log("Step 3: Click on the skill option \"Oracle Database\" in the dropdown");
		editSkills.getOptionsDisplayed().get(1).click();
		log("Step 4: Verify that the skill \"Oracle Database\" is selected");
		Assert.assertEquals(editSkills.getSkillLabels().get(0).getText(), "Oracle Database");
	}
	
	@Test(priority=11, description="This test verifies that all skill level options can be selected")
	public void verifySkillLevelSelection()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Add skill \"mySQL\" from the dropdown");
		editSkills.enterSkills("my");
		editSkills.getOptionsDisplayed().get(0).click();
		log("Step 3: Verify that all skill levels can be selected");
		for(int i=0; i<4; i++)
		{
			editSkills.selectSkillLevel(i);
		}
		
	}
	
	@Test(priority=3, description="This test verifies that the skill added can be saved successfully")
	public void verifySkillAddition()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Add skill \"POSTMAN\" from the dropdown");
		log("Step 3: Select skill level as \"I know the theory\"");
		editSkills.addSkill("p", 1, 1);
		log("Step 4: Click on 'Save' button");
		editSkills.clickSave();
		log("Step 5: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Skills");
		List<String> skills = profilePreview.getSkillsValues();
		log("Step 6: Verify that expected skill name and skill level are displayed on preview page");
		Assert.assertTrue(skills.contains("POSTMAN"));
		Assert.assertTrue(profilePreview.getSkillLevelValues().contains(properties.getProperty("skillLevel1")));
		log("Step 7: Verify that the text 'Required to apply' is not displayed");
		Assert.assertFalse(profilePreview.getTitleValue(profilePreview.skillsTitle).contains(properties.getProperty("error16")));
	}
	
	@Test(priority=4, dependsOnMethods="verifySkillAddition", description="This test "
			+ "verifies that the skill can be deleted on the edit page")
	public void verifySkillDeleteOnEditPage()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Add skill \"Git\" from the dropdown");
		editSkills.addSkill("g", 4, 2);
		WebElement skill = editSkills.getSkillLabels().get(0);
		log("Step 3: Click on delete icon for the skill 'Git'");
		editSkills.deleteSkill(0);
		log("Step 4: Verify that the skill 'Git' is no longer displayed");
		Assert.assertFalse(skill.getText().equalsIgnoreCase("Git"));
	}
	
	@Test(priority=5, description="This test verifies that the skill can be deleted "
			+ "from the preview page")
	public void verifySkillDeleteOnPreviewPage()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Add skill \"Git\" from the dropdown");
		editSkills.addSkill("g", 4, 2);
		log("Step 3: Click on 'Save' button");
		editSkills.clickSave();
		List<String> skills = profilePreview.getSkillsValues();
		log("Step 4: Verify that given skill name and skill level are displayed on preview page");
		Assert.assertTrue(skills.contains("Git"));
		Assert.assertTrue(profilePreview.getSkillLevelValues().contains(properties.getProperty("skillLevel2")));
		log("Step 5: Click on Skills edit icon");
		profilePreview.clickEdit(profilePreview.skillsEdit);
		log("Step 6: Click on delete icon for the skill 'Git'");
		editSkills.deleteSkill(1);
		log("Step 7: Click on 'Save' button");
		editSkills.clickSave();
		log("Step 8: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Skills");
		log("Step 9: Verify that the skill 'Git' is no longer displayed on preview page");
		Assert.assertFalse(profilePreview.getSkillsValues().contains("Git"));
	}
	
	@Test(priority=12, description="This test verifies that user is redirected to "
			+ "preview page after clicking on 'Cancel'")
	public void verifySkillsCancel()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Click on 'Cancel' button");
		editSkills.clickCancel();
		log("Step 3: Verify: Section display, Tab selection");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#Skills"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.skillsSection);
		Assert.assertTrue(state, "Skills section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.skillsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(priority=13, description="This test verifies that validation error occurs when "
			+ "skill level is not selected")
	public void verifySkillLevelRequired()
	{
		log("Step 1: Click on Skills edit icon");
		log("Step 2: Add skill from the dropdown");
		editSkills.addSkill("r", 2, 0);
		log("Step 3: Click on 'Save' button");
		editSkills.clickSave();
		log("Step 4: Verify that validation error occurs for skill level dropdown field");
		Assert.assertEquals(editSkills.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(editSkills.getSkillBoxText().contains(properties.getProperty("error18")), "Skill level should be mandatory");
	}
	
	@Test(priority=2, description="This test verifies that maximum one hundred skills can be added")
	public void verify100SkillsAddition()
	{
		log("Step 1: Click on Skills edit icon");
		int addedSkillsCount = 0;
		char letter = 'a';
		log("Step 2: Add one hundred skills");
		log("Step 3: Verify that the text box to enter the skills turns inactive and "
				+ "expected message is displayed");
		while(addedSkillsCount < 100)
		{
			editSkills.enterSkills(String.valueOf(letter));
			if(!editSkills.getOptionsDisplayed().isEmpty())
			{
				editSkills.getOptionsDisplayed().get(0).click();
				addedSkillsCount++;
			}
			letter = (char)(letter == 'z' ? 'a' : letter + 1);
			if(addedSkillsCount == 100)
			{
				Assert.assertFalse(editSkills.isSkillTextBoxActive(), "Skill text box should be inactive after 100 skills");
				Assert.assertTrue(editSkills.getSkillBoxErrorText().contains(properties.getProperty("error19")));
				Assert.assertTrue(editSkills.getSkillBoxErrorText().contains(properties.getProperty("error20")));
			}
		}
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.skillsSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.skillsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
