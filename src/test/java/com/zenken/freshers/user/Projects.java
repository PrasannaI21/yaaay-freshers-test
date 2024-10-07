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
	
	@Test(priority=1)
	public void verifyProjectsNewUrl()
	{
		profilePreview.clickAddProject();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url12"));
		Assert.assertEquals(projects.getProjectHeadline(), "Projects");
	}
	
	@Test(priority=2)
	public void verifyProjectPageTitle()
	{
		profilePreview.clickAddProject();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3)
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
	
	@Test(priority=4)
	public void verifyProjectEndDateDdDisable()
	{
		profilePreview.clickAddProject();
		projects.clickEndDateCheckBox();
		Assert.assertFalse(projects.isEndMonthDdActive(), "End Month dropdown should be inactive");
		Assert.assertFalse(projects.isEndYearDdActive(), "End Year dropdown should be inactive");
	}
	
	@Test(priority=5)
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
	
	@Test(priority=6)
	public void verifyProjectAdditionWithDetails()
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
	
	@Test(priority=7)
	public void verifyProjectAdditionWithLink()
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
	
	@Test(priority=8)
	public void verifyProjectLink()
	{
		profilePreview.clickProjectLink();
		ArrayList<String> tabs = projects.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "New tab did not open");
		Assert.assertEquals(profilePreview.getPageUrl(), "https://www.zenken.co.jp/en/");
	}
	
	@Test(priority=9, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class)
	public void verifyProjectFileUpload()
	{
		profilePreview.clickAddProject();
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\Sample_Project.pdf");
		Assert.assertTrue(projects.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(projects.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(projects.getFileNameText().contains("Sample_Project.pdf"));
		Assert.assertTrue(projects.getFileNameText().contains("(file check in progress)"));
	}
	
	@Test(priority=10)
	public void verifyDefaultProjectName()
	{
		Assert.assertEquals(profilePreview.getProjectTitleValue(), "Untitled Project");
	}
	
	@Test(priority=11)
	public void verifyProjectAdditionWithFile() throws InterruptedException
	{
		String[] data = {"IoT-Enabled Smart Home Automation", "June", "1999"};
		profilePreview.clickAddProject();
		projects.enterProjectTitle(data[0]);
		projects.selectStartMonth(data[1]);
		projects.selectStartYear(data[2]);
		projects.clickEndDateCheckBox();
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\Sample_Project.pdf");
		projects.getFileNameText();
		projects.clickSave();
		redirectionAssertions("#Projects");
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("Sample_Project.pdf"));
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("(file check in progress)"));
		Assert.assertEquals(profilePreview.monitorDownloadLink(10), "Sample_Project.pdf");
		int initialCount = profilePreview.getFileCount();
		profilePreview.clickProjectFile();
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		Thread.sleep(1500);//wait time to process the download
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Sample_Project.pdf");
	}
	
	@Test(priority=12)
	public void verifyProjectEditUrl()
	{
		profilePreview.clickProjectEdit(0);
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url13")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=13)
	public void verifyProjectSavedData()
	{
		profilePreview.clickProjectEdit(0);
		Assert.assertEquals(projects.getProjectTitleText(), "AI-Powered Customer Support Chatbot Development");
		Assert.assertEquals(projects.getStartMonthOption(), "December");
		Assert.assertEquals(projects.getStartYearOption(), "2023");
		Assert.assertEquals(projects.getEndMonthOption(), "March");
		Assert.assertEquals(projects.getEndYearOption(), "2024");
	}
	
	@Test(priority=14)
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
	
	@Test(priority=15)//6 files uploaded so far
	public void verifyProjectFilesUpload()
	{
		profilePreview.clickAddProject();
		String[] paths = {"C:\\Users\\prasa\\Downloads\\file-sample-doc.doc", 
				"C:\\Users\\prasa\\Downloads\\file-sample-docx.docx", "C:\\Users\\prasa\\Downloads\\file-sample-rtf.rtf"};
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
	
	@Test(priority=16)
	public void verifyProjectFileDelete()
	{
		profilePreview.clickProjectEdit(3);//send index '3' during execution
		projects.clickProjectFileDelete();
		Assert.assertTrue(projects.isProjectFileDeleted("Sample_Project.pdf"), "Project file should have been deleted");
	}
	
	@Test(priority=17)
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
	
	@Test(priority=18, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class)
	public void verifyProjectDeletePopUp()
	{
		profilePreview.clickProjectEdit(0);
		projects.clickProjectDeleteLink();
		projects.waitForGodDamnPopUp();
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2")), "Confirmation popup could not be displayed");
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=19)
	public void verifyProjectDelete()
	{
		profilePreview.clickProjectEdit(0);
		projects.clickProjectDeleteLink();
		projects.clickDeleteProject();
		redirectionAssertions("#Projects");	
		Assert.assertFalse(profilePreview.getProjectTitleValue(0).equals("AI-Powered Customer Support Chatbot Development"));//send "AI-Powered Customer Support Chatbot Development" during execution
		Assert.assertTrue(profilePreview.isAddProjectButtonActive(), "Add Project button should be active");
	}
	
	@Test(priority=20)
	public void verifyProjectTitleCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectTitle(properties.getProperty("exceed200"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getTitleErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=21)
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
	
	@Test(priority=22)
	public void verifyProjectDetailsCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectOverview(properties.getProperty("exceed5k"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectDetailsErrorText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=23)
	public void verifyInvalidProjectLinkError()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectLink("www.google.com");
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error25")));
	}
	
	@Test(priority=24)
	public void verifyProjectLinkCharExceed()
	{
		profilePreview.clickProjectEdit(0);
		projects.enterProjectLink(properties.getProperty("mail200"));
		projects.clickSave();
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=25)
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
	
	@Test(priority=26)
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
	
	@Test(priority=27)
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
	
	@Test(priority=28)
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
	
	@Test(priority=29)
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
	
	@Test(priority=30)
	public void verifyInvalidFileFormat()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartMonth("May");
		projects.selectStartYear("2022");
		projects.clickEndDateCheckBox();
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\file-sample-txt.txt");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error26")));
	}
	
	@Test(priority=31)
	public void verifyInvalidFileSize()
	{
		profilePreview.clickAddProject();
		projects.enterProjectTitle("Project");
		projects.selectStartMonth("May");
		projects.selectStartYear("2022");
		projects.clickEndDateCheckBox();
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\samplepdf-above2mb.pdf");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=32)
	public void verifyMaxFilesUpload()
	{
		profilePreview.clickAddProject();
		String[] paths = {"C:\\Users\\prasa\\Downloads\\Sample_Project.pdf", "C:\\Users\\prasa\\Downloads\\file-sample-doc.doc",
				"C:\\Users\\prasa\\Downloads\\file-sample-docx.docx", "C:\\Users\\prasa\\Downloads\\file-sample-rtf.rtf",
				"C:\\Users\\prasa\\Downloads\\Sample_Project-pdf.pdf"};
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
