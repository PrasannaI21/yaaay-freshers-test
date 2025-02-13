package com.zenken.freshers.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditProjectsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Projects extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditProjectsPage projects;
	Properties properties;
	private boolean proDelete = false;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		projects = new EditProjectsPage(driver);
		navigateTo("/");
		profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
		properties = getProperties();
		if(!proDelete)
		{
			int addedPro = profilePreview.getAddedProjectCount();
			for(int i=0; i<addedPro; i++)
			{
				profilePreview.clickProjectEdit(i*0);
				projects.deleteProject();
			}
			proDelete = true;
		}
	}
	
	@Test(priority=1, description="This test verifies that the expected 'Project New' URL and headline are displayed")
	public void verifyProjectsNewUrl()
	{
		profilePreview.clickAddProject();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url12"));
		Assert.assertEquals(projects.getProjectHeadline(), "Projects");
	}
	
	@Test(priority=2, description="This test verifies that the Project page title is correct")
	public void verifyProjectPageTitle()
	{
		profilePreview.clickAddProject();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that expected placeholders are displayed")
	public void verifyProjectPlaceholders()
	{
		profilePreview.clickAddProject();
		List<String> attributes = projects.getProjectPlaceholders();
		for(int i=0; i<attributes.size(); i++)
		{
			String placeholder = properties.getProperty("proPH"+i);
			Assert.assertEquals(attributes.get(i), placeholder);
		}
	}
	
	@Test(priority=4, description="This test verifies that End Date dropdowns turn inactive")
	public void verifyProjectEndDateDdDisable()
	{
		profilePreview.clickAddProject();
		projects.clickEndDateCheckBox();
		Assert.assertFalse(projects.isEndMonthDdActive(), "End Month dropdown should be inactive");
		Assert.assertFalse(projects.isEndYearDdActive(), "End Year dropdown should be inactive");
	}
	
	@Test(priority=5, description="This test verifies that the project is added with only required to save fields")
	public void verifyProjectAddition()
	{
		String[] data = {"AI-Powered Customer Support Chatbot Development", "December", "2023", "March", "2024"};
		profilePreview.clickAddProject();
		projects.enterProjectTitle(data[0]);
		projects.selectStartMonth(data[1]);
		projects.selectStartYear(data[2]);
		projects.selectEndMonth(data[3]);
		projects.selectEndYear(data[4]);
		projects.clickSave();
		redirectionAssertions("#Projects");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+data[3]+" / "+data[4]);
	}
	
	@Test(priority=6, description="This test verifies that a project is added with required to save fields + Project Overview")
	public void verifyProjectAddition2()
	{
		String[] data = {"Cloud-Based Inventory Management System", "April", "2022"};
		profilePreview.clickAddProject();
		projects.enterProjectTitle(data[0]);
		projects.selectStartMonth(data[1]);
		projects.selectStartYear(data[2]);
		projects.clickEndDateCheckBox();
		projects.enterProjectOverview(properties.getProperty("proDetails"));
		projects.clickSave();
		redirectionAssertions("#Projects");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+"TBD");
		Assert.assertEquals(profilePreview.getProjectDetailsValue(), properties.getProperty("proDetails"));
	}
	
	@Test(priority=7, description="This test verifies that a project is added with required to save fields, Project Overview and Link")
	public void verifyProjectAddition3()
	{
		String[] data = {"Blockchain-Based Voting System", "July", "2007", "November", "https://www.zenken.co.jp/en/"};
		profilePreview.clickAddProject();
		projects.enterProjectTitle(data[0]);
		projects.selectStartMonth(data[1]);
		projects.selectStartYear(data[2]);
		projects.selectEndMonth(data[3]);
		projects.selectEndYear(data[2]);
		projects.enterProjectOverview(properties.getProperty("proDetails"));
		projects.enterProjectLink(data[4]);
		projects.clickSave();
		redirectionAssertions("#Projects");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+data[3]+" / "+data[2]);
		Assert.assertEquals(profilePreview.getProjectDetailsValue(), properties.getProperty("proDetails"));
		Assert.assertEquals(profilePreview.getProjectLinkValue(), data[4]);
	}
	
	@Test(priority=8, description="This test verifies that the project link can be accessed from preview page")
	public void verifyProjectLink()
	{
		profilePreview.clickProjectLink();
		ArrayList<String> tabs = projects.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "New tab did not open");
		Assert.assertEquals(profilePreview.getPageUrl(), "https://www.zenken.co.jp/en/");
	}
	
	@Test(priority=9, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class,
			description="This test verifies that a project file can be uploaded")
	public void verifyProjectFileUpload()
	{
		profilePreview.clickAddProject();
		projects.uploadFile(System.getProperty("user.dir")+"/test-data/Sample_Project.pdf");
		Assert.assertTrue(projects.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(projects.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(projects.getFileNameText().contains("Sample_Project.pdf"));
		Assert.assertTrue(projects.getFileNameText().contains("(file check in progress)"));
	}
	
	@Test(priority=10, description="This test verifies that default project name is correct")
	public void verifyDefaultProjectName()
	{
		Assert.assertEquals(profilePreview.getProjectTitleValue(), "Untitled Project");
	}
	
	@Test(priority=11, description="This test verifies that the project is added with file")
	public void verifyProjectAddition4() throws InterruptedException
	{
		String[] data = {"IoT-Enabled Smart Home Automation", "June", "1999"};
		profilePreview.clickAddProject();
		projects.enterProjectTitle(data[0]);
		projects.selectStartMonth(data[1]);
		projects.selectStartYear(data[2]);
		projects.clickEndDateCheckBox();
		projects.uploadFile(System.getProperty("user.dir")+"/test-data/Sample_Project.pdf");
		projects.getFileNameText();
		projects.clickSave();
		redirectionAssertions("#Projects");
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("Sample_Project.pdf"));
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("(file check in progress)"));
		Assert.assertEquals(profilePreview.getProjectDlLink(10), "Sample_Project.pdf");
		int initialCount = profilePreview.getFileCount();
		profilePreview.clickProjectFile();
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Sample_Project.pdf");
	}
	
	@Test(priority=12, description="This test verifies that expected 'Project Edit' URL is displayed")
	public void verifyProjectEditUrl()
	{
		profilePreview.clickProjectEdit(0);
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url13")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=13, description="This test verifies that the information saved for first project is correct")
	public void verifyProjectSavedData()
	{
		profilePreview.clickProjectEdit(0);
		Assert.assertEquals(projects.getProjectTitleText(), "AI-Powered Customer Support Chatbot Development");
		Assert.assertEquals(projects.getStartMonthOption(), "December");
		Assert.assertEquals(projects.getStartYearOption(), "2023");
		Assert.assertEquals(projects.getEndMonthOption(), "March");
		Assert.assertEquals(projects.getEndYearOption(), "2024");
	}
	
	@Test(priority=14, description="This test verifies that the character count for Project Overview is dynamically updated")
	public void verifyProjectCharCount()
	{
		profilePreview.clickProjectEdit(0);
		projects.clearProjectDetails();
		String descr = properties.getProperty("proDetails");
		for(int i=0; i<descr.length(); i++)
		{
			char currnetChar = descr.charAt(i);
			projects.enterProjectOverview2(Character.toString(currnetChar));
			String displayedCount = projects.getProjectCharCount();
			Assert.assertEquals(displayedCount, String.valueOf(i + 1), "Character count mismatch at index "+i);
		}
		Assert.assertEquals(Integer.parseInt(projects.getProjectCharCount()), 496);
	}
	
	@Test(priority=15, description="This test verifies that multiple files with different formats are uploaded")//6 projects added so far
	public void verifyProjectFilesUpload()
	{
		profilePreview.clickAddProject();
		String[] paths = {System.getProperty("user.dir")+"/test-data/file-sample-doc.doc", 
				System.getProperty("user.dir")+"/test-data/file-sample-docx.docx", 
				System.getProperty("user.dir")+"/test-data/file-sample-rtf.rtf"};
		String firstPath = paths[0];
		for(String path : paths)
		{
			projects.uploadFile(path);
			File file = new File(path);
			String fileName = file.getName();
			Assert.assertTrue(projects.isProgressBarDisplayed(), "Progress bar is not displayed for file "+fileName);
			Assert.assertTrue(projects.isUploadComplete(), "File upload did not complete successfully for "+fileName);
			Assert.assertTrue(projects.getProjectFileText(firstPath, fileName, path).contains(fileName));
			Assert.assertTrue(projects.getProjectFileText(firstPath, fileName, path).contains("(file check in progress)"));	
		}
	}
	
	@Test(priority=16, description="This test verifies that the project file is deleted")
	public void verifyProjectFileDelete()
	{
		profilePreview.clickProjectEdit(3);//send index '3' during execution
		projects.clickProjectFileDelete();
		Assert.assertTrue(projects.isProjectFileDeleted("Sample_Project.pdf"), "Project file should have been deleted");
	}
	
	@Test(priority=17, description="This test verifies that 'Add project' button turns inactive once ten projects are added")
	public void verifyProjectCountExceed()
	{
		int totalProjectsCount = 10;
		String[][] data = {{"Project1", "January", "1981"}, {"Project2", "February", "1982"}, {"Project3", "March", "1983"},
				{"Project4", "April", "1984"}, {"Project5", "May", "1985"}, {"Project6", "June", "1986"},
				{"Project7", "July", "1987"}, {"Project8", "August", "1986"}, {"Project9", "September", "1989"}, {"Project10", "October", "1990"}};
		int addedProjects = profilePreview.getAddedProjectCount();
		int remainingProjects = totalProjectsCount - addedProjects;
		for(int i=0; i<remainingProjects; i++)
		{
			profilePreview.clickAddProject();
			projects.addProject(data[i][0], data[i][1], data[i][2]);
		}
		Assert.assertTrue(profilePreview.isAddProjectActive(), "'Add Project' button should be inactive");
		Assert.assertTrue(profilePreview.getProjectSectionText().contains(properties.getProperty("error24")), "Validation error for maximum projects did not occur.");
	}
	
	@Test(priority=18, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class,
			description="This test verifies that a confirmation popup to delete the project appears")
	public void verifyProjectDeletePopUp()
	{
		profilePreview.clickProjectEdit(0);
		projects.clickProjectDeleteLink();
		projects.waitForGodDamnPopUp();
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2")), "Confirmation popup could not be displayed");
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=19, description="This test verifies that the project is deleted successfully")
	public void verifyProjectDelete()
	{
		profilePreview.clickProjectEdit(0);
		projects.clickProjectDeleteLink();
		projects.clickDeleteProject();
		redirectionAssertions("#Projects");
		Assert.assertFalse(profilePreview.getProjectTitleValue(0).equals("AI-Powered Customer Support Chatbot Development"));//send "AI-Powered Customer Support Chatbot Development" during execution
		Assert.assertTrue(profilePreview.isAddProjectButtonActive(), "Add Project button should be active");
	}
	
	@Test(priority=20, description="This test verifies that validation error for maximum characters occurs: Project Title")
	public void verifyProjectTitleCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectTitle(properties.getProperty("exceed200"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getTitleErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=21, description="This test verifies that validation error occurs when end date selected is before the start date")
	public void verifyBeforeEndDateError()
	{
		profilePreview.clickProjectEdit(0);
		projects.selectStartMonth("January");
		projects.selectStartYear("2023");
		if(projects.isCheckBoxSelected())
		{
			projects.clickEndDateCheckBox();
		}
		projects.selectEndMonth("December");
		projects.selectEndYear("2022");
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));	
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error21")));
	}
	
	@Test(priority=22, description="This test verifies that validation error for maximum characters occurs: Project Overview")
	public void verifyProjectDetailsCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectOverview(properties.getProperty("exceed5k"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectDetailsErrorText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=23, description="This test verifies that validation error for invalid project link occurs")
	public void verifyInvalidProjectLinkError()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectLink("www.google.com");
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error25")));
	}
	
	@Test(priority=24, description="The test verifies that validation error for maximum characters occurs: Project Link")
	public void verifyProjectLinkCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectLink(properties.getProperty("mail200"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=25, description="This test verifies that validation error for required to save field occurs: Project Title")
	public void verifyProjectTitleRequired()
	{
		profilePreview.clickAddProject();
		projects.selectStartMonth("May");
		projects.selectStartYear("2022");
		projects.clickEndDateCheckBox();
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getTitleErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=26, description="This test verifies that validation error for required to save field occurs: Start Month")
	public void verifyStartMonthRequired()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartYear("2022");
		projects.clickEndDateCheckBox();
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getStartDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=27, description="This test verifies that validation error for required to save field occurs: Start Year")
	public void verifyStartYearRequired()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartMonth("May");
		projects.clickEndDateCheckBox();
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getStartDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=28, description="This test verifies that validation error for required to save field occurs: End Month")
	public void verifyEndMonthRequired()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartMonth("May");
		projects.selectStartYear("2022");
		projects.selectEndYear("2022");
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=29, description="This test verifies that validation error for required to save field occurs: End Year")
	public void verifyEndYearRequired()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartMonth("May");
		projects.selectStartYear("2022");
		projects.selectEndMonth("April");
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=30, description="This test verifies that validation error for invalid file format occurs")
	public void verifyProInvalidFileFormat()
	{
		profilePreview.clickAddProject();
		projects.uploadFile(System.getProperty("user.dir")+"/test-data/file-sample-txt.txt");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error26")));
	}
	
	@Test(priority=31, description="This test verifies that validation error for invalid size for project file occurs")
	public void verifyProInvalidFileSize()
	{
		profilePreview.clickAddProject();
		projects.uploadFile(System.getProperty("user.dir")+"/test-data/samplepdf-above2mb.pdf");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=32, description="This test verifies that 'Cancel' from New Project redirectes user to preview page ")
	public void verifyProjectCancelNewRed()
	{
		profilePreview.clickAddProject();
		projects.clickCancel();
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#Projects"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.projectsSection);
		Assert.assertTrue(state, "Projects section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.projectsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(priority=33, description="This test verifies that 'Select From Files' button turns inactive once five files are uploaded")
	public void verifyMaxFilesUpload()
	{
		profilePreview.clickAddProject();
		String[] paths = {System.getProperty("user.dir")+"/test-data/Sample_Project.pdf", System.getProperty("user.dir")+"/test-data/file-sample-doc.doc",
				System.getProperty("user.dir")+"/test-data/file-sample-docx.docx", System.getProperty("user.dir")+"/test-data/file-sample-rtf.rtf",
				System.getProperty("user.dir")+"/test-data/Sample_Project-pdf.pdf"};
		String firstPath = paths[0];
		for(String path : paths)
		{
			projects.uploadFile(path);
			File file = new File(path);
			String fileName = file.getName();
			projects.getProjectFileText(firstPath, fileName, path);
		}
		Assert.assertFalse(projects.isSelectFileActive(), "Select From Files button should be inactive");
	}
	
	@Test(priority=34, description="This test verifies that 'Cancel' from Edit Project redirectes user to preview page")
	public void verifyProjectCancelEditRed()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Click on 'Cancel' button");
		projects.clickCancel();
		log("Step 3: Verify that user is redirected to 'Project' section of profile page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#Projects"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.projectsSection);
		Assert.assertTrue(state, "Projects section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.projectsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.projectsSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.projectsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
