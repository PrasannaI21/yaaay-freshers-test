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
//		if(!proDelete)
//		{
//			int addedPro = profilePreview.getAddedProjectCount();
//			for(int i=0; i<addedPro; i++)
//			{
//				profilePreview.clickProjectEdit(i*0);
//				projects.deleteProject();
//			}
//			proDelete = true;
//		}
	}
	
	@Test(priority=1, description="This test verifies that the expected 'Project New' URL and headline are displayed")
	public void verifyProjectsNewUrl()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Verify that URL and headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url12"));
		Assert.assertEquals(projects.getProjectHeadline(), "Projects");
	}
	
	@Test(priority=2, description="This test verifies that the Project page title is correct")
	public void verifyProjectPageTitle()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that expected placeholders are displayed")
	public void verifyProjectPlaceholders()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Verify that placeholders displayed for 'Project-Title, Overview, Link' are correct");
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
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 3: Verify that the dropdowns for month and year turn inactive");
		Assert.assertFalse(projects.isEndMonthDdActive(), "End Month dropdown should be inactive");
		Assert.assertFalse(projects.isEndYearDdActive(), "End Year dropdown should be inactive");
	}
	
	@Test(priority=5, description="This test verifies that the project is added with only required to save fields")
	public void verifyProjectAddition()
	{
		String[] data = {"AI-Powered Customer Support Chatbot Development", "December", "2023", "March", "2024"};
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: "+data[0]);
		projects.enterProjectTitle(data[0]);
		log("Step 3: Select start month: "+data[1]);
		projects.selectStartMonth(data[1]);
		log("Step 4: Select start year: "+data[2]);
		projects.selectStartYear(data[2]);
		log("Step 5: Select end month: "+data[3]);
		projects.selectEndMonth(data[3]);
		log("Step 6: Select end year: "+data[4]);
		projects.selectEndYear(data[4]);
		log("Step 7: Click on 'Save' button");
		projects.clickSave();
		redirectionAssertions("#Projects");
		log("Step 8: Verify that expected project title and dates are displayed on profile preview page");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+data[3]+" / "+data[4]);
	}
	
	@Test(priority=6, description="This test verifies that a project is added with required to save fields + Project Overview")
	public void verifyProjectAdditionWithDetails()
	{
		String[] data = {"Cloud-Based Inventory Management System", "April", "2022"};
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: "+data[0]);
		projects.enterProjectTitle(data[0]);
		log("Step 3: Select start month: "+data[1]);
		projects.selectStartMonth(data[1]);
		log("Step 4: Select start year: "+data[2]);
		projects.selectStartYear(data[2]);
		log("Step 5: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 6: Enter Project Overview");
		projects.enterProjectOverview(properties.getProperty("proDetails"));
		log("Step 7: Click on 'Save' button");
		projects.clickSave();
		redirectionAssertions("#Projects");
		log("Step 8: Verify that expected project title, dates, and details are displayed");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+"TBD");
		Assert.assertEquals(profilePreview.getProjectDetailsValue(), properties.getProperty("proDetails"));
	}
	
	@Test(priority=7, description="This test verifies that a project is added with required to save fields, Project Overview and Link")
	public void verifyProjectAdditionWithLink()
	{
		String[] data = {"Blockchain-Based Voting System", "July", "2007", "November", "https://www.zenken.co.jp/en/"};
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: "+data[0]);
		projects.enterProjectTitle(data[0]);
		log("Step 3: Select start month: "+data[1]);
		projects.selectStartMonth(data[1]);
		log("Step 4: Select start year: "+data[2]);
		projects.selectStartYear(data[2]);
		log("Step 5: Select end month: "+data[3]);
		projects.selectEndMonth(data[3]);
		log("Step 6: Select end year: "+data[2]);
		projects.selectEndYear(data[2]);
		log("Step 7: Enter Project Overview");
		projects.enterProjectOverview(properties.getProperty("proDetails"));
		log("Step 8: Enter Project Link: "+data[4]);
		projects.enterProjectLink(data[4]);
		log("Step 9: Click on 'Save' button");
		projects.clickSave();
		redirectionAssertions("#Projects");
		log("Step 10: Verify that expected project title, dates, details, and link are displayed");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), data[0]);
		Assert.assertEquals(profilePreview.getProjectDateValue(), data[1]+" / "+data[2]+" - "+data[3]+" / "+data[2]);
		Assert.assertEquals(profilePreview.getProjectDetailsValue(), properties.getProperty("proDetails"));
		Assert.assertEquals(profilePreview.getProjectLinkValue(), data[4]);
	}
	
	@Test(priority=8, description="This test verifies that the project link can be accessed from preview page")
	public void verifyProjectLink()
	{
		log("Step 1: Click on the project link that is added");
		profilePreview.clickProjectLink();
		ArrayList<String> tabs = projects.switchTabs(1);
		log("Step 2: Verify that the expected website is opened in a separate tab");
		Assert.assertEquals(tabs.size(), 2, "New tab did not open");
		Assert.assertEquals(profilePreview.getPageUrl(), "https://www.zenken.co.jp/en/");
	}
	
	@Test(priority=9, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class,
			description="This test verifies that a project file can be uploaded")
	public void verifyProjectFileUpload()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Upload a sample PDF file");
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\Sample_Project.pdf");
		log("Step 3: Verify that the file is uploaded successfully");
		Assert.assertTrue(projects.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(projects.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(projects.getFileNameText().contains("Sample_Project.pdf"));
		log("Step 4: Verify that (file check in progress) is displayed");
		Assert.assertTrue(projects.getFileNameText().contains("(file check in progress)"));
	}
	
	@Test(priority=10, description="This test verifies that default project name is correct")
	public void verifyDefaultProjectName()
	{
		log("Step 1: Verify that the Project Title is 'Untitled Project'");
		Assert.assertEquals(profilePreview.getProjectTitleValue(), "Untitled Project");
	}
	
	@Test(priority=11, description="This test verifies that the project is added with file")
	public void verifyProjectAdditionWithFile() throws InterruptedException
	{
		String[] data = {"IoT-Enabled Smart Home Automation", "June", "1999"};
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: "+data[0]);
		projects.enterProjectTitle(data[0]);
		log("Step 3: Select start month: "+data[1]);
		projects.selectStartMonth(data[1]);
		log("Step 4: Select start year: "+data[2]);
		projects.selectStartYear(data[2]);
		log("Step 5: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 6: Upload a sample PDF file");
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\Sample_Project.pdf");
		projects.getFileNameText();
		log("Step 7: Click on 'Save' button");
		projects.clickSave();
		redirectionAssertions("#Projects");
		log("Step 8: Verify that expected file name with file check text are displayed on preview page");
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("Sample_Project.pdf"));
		Assert.assertTrue(profilePreview.getProjectFileValue().contains("(file check in progress)"));
		log("Step 9: Verify that expected file name with file check text are displayed on preview page");
		Assert.assertEquals(profilePreview.monitorDownloadLink(10), "Sample_Project.pdf");
		int initialCount = profilePreview.getFileCount();
		profilePreview.clickProjectFile();
		log("Step 10: Verify that file can be downloaded from preview page after refreshing");
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		Thread.sleep(2000);//wait time to process the download
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Sample_Project.pdf");
	}
	
	@Test(priority=12, description="This test verifies that expected 'Project Edit' URL is displayed")
	public void verifyProjectEditUrl()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Verify that user is redirected to project edit URL");
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url13")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=13, description="This test verifies that the information saved for first project is correct")
	public void verifyProjectSavedData()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Verify that Project Title is 'AI-Powered Customer Support Chatbot Development'");
		Assert.assertEquals(projects.getProjectTitleText(), "AI-Powered Customer Support Chatbot Development");
		log("Step 3: Verify that start month is 'December'");
		Assert.assertEquals(projects.getStartMonthOption(), "December");
		log("Step 4: Verify that start year is '2023'");
		Assert.assertEquals(projects.getStartYearOption(), "2023");
		log("Step 5: Verify that end month is 'March'");
		Assert.assertEquals(projects.getEndMonthOption(), "March");
		log("Step 6: Verify that end year is '2024'");
		Assert.assertEquals(projects.getEndYearOption(), "2024");
	}
	
	@Test(priority=14, description="This test verifies that the character count for Project Overview is dynamically updated")
	public void verifyProjectCharCount()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		projects.clearProjectDetails();
		String descr = properties.getProperty("proDetails");
		log("Step 2: Enter individual characters in Project Overview field");
		for(int i=0; i<descr.length(); i++)
		{
			char currnetChar = descr.charAt(i);
			projects.enterProjectOverview2(Character.toString(currnetChar));
			String displayedCount = projects.getProjectCharCount();
			Assert.assertEquals(displayedCount, String.valueOf(i + 1), "Character count mismatch at index "+i);
		}
		log("Step 3: Verify that character count is updated and the number displayed is correct");
		Assert.assertEquals(Integer.parseInt(projects.getProjectCharCount()), 496);
	}
	
	@Test(priority=15, description="This test verifies that multiple files with different formats are uploaded")//6 projects added so far
	public void verifyProjectFilesUpload()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		String[] paths = {"C:\\Users\\prasa\\Downloads\\file-sample-doc.doc", 
				"C:\\Users\\prasa\\Downloads\\file-sample-docx.docx", "C:\\Users\\prasa\\Downloads\\file-sample-rtf.rtf"};
		log("Step 2: Upload sample files with doc, docx, and rtf formats");
		log("Step 3: Verify that the files are uploaded");
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
		log("Step 1: Click on pen icon of fourth project");
		profilePreview.clickProjectEdit(3);//send index '3' during execution
		log("Step 2: Click on 'Delete' button for added file");
		projects.clickProjectFileDelete();
		log("Step 3: Verify that project file is deleted");
		Assert.assertTrue(projects.isProjectFileDeleted("Sample_Project.pdf"), "Project file should have been deleted");
	}
	
	@Test(priority=17, description="This test verifies that 'Add project' button turns inactive once ten projects are added")
	public void verifyProjectCountExceed()
	{
		log("Step 1: Add ten projects");
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
		log("Step 2: Verify that 'Add Project' button turns inactive");
		Assert.assertTrue(profilePreview.isAddProjectActive(), "'Add Project' button should be inactive");
		log("Step 3: Verify that Validation error for maximum projects appears");
		Assert.assertTrue(profilePreview.getProjectSectionText().contains(properties.getProperty("error24")), "Validation error for maximum projects did not occur.");
	}
	
	@Test(priority=18, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class,
			description="This test verifies that a confirmation popup to delete the project appears")
	public void verifyProjectDeletePopUp()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Click on 'Delete Project' link");
		projects.clickProjectDeleteLink();
		projects.waitForGodDamnPopUp();
		log("Step 3: Verify that popup is displayed and the text is correct");
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2")), "Confirmation popup could not be displayed");
		Assert.assertTrue(projects.getProjectPopUpText().contains(properties.getProperty("popup2.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=19, description="This test verifies that the project is deleted successfully")
	public void verifyProjectDelete()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Click on 'Delete Project' link");
		projects.clickProjectDeleteLink();
		log("Step 3: Click on 'Delete' on the confirmation popup");
		projects.clickDeleteProject();
		redirectionAssertions("#Projects");
		log("Step 4: Verify that deleted project's title is not displayed on preview page");
		Assert.assertFalse(profilePreview.getProjectTitleValue(0).equals("AI-Powered Customer Support Chatbot Development"));//send "AI-Powered Customer Support Chatbot Development" during execution
		Assert.assertTrue(profilePreview.isAddProjectButtonActive(), "Add Project button should be active");
	}
	
	@Test(priority=20, description="This test verifies that validation error for maximum characters occurs: Project Title")
	public void verifyProjectTitleCharExceed()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Enter 201 characters in Project Title field");
		projects.enterProjectTitle(properties.getProperty("exceed200"));
		log("Step 3: Click on 'Save' button");
		projects.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding the 200 character limit");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getTitleErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=21, description="This test verifies that validation error occurs when end date selected is before the start date")
	public void verifyBeforeEndDateError()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Select start month: January");
		projects.selectStartMonth("January");
		log("Step 3: Select start year: 2023");
		projects.selectStartYear("2023");
		if(projects.isCheckBoxSelected())
		{
			projects.clickEndDateCheckBox();
		}
		log("Step 4: Select end month: December");
		projects.selectEndMonth("December");
		log("Step 5: Select end year: 2022");
		projects.selectEndYear("2022");
		log("Step 6: Click on 'Save' button");
		projects.clickSave();
		log("Step 7: Verify that validation error corresponds to end date being selected before start date");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));	
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error21")));
	}
	
	@Test(priority=22, description="This test verifies that validation error for maximum characters occurs: Project Overview")
	public void verifyProjectDetailsCharExceed()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Enter 5001 characters in Project Overview field");
		projects.enterProjectOverview(properties.getProperty("exceed5k"));
		log("Step 3: Click on 'Save' button");
		projects.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding the 5000 character limit");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectDetailsErrorText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=23, description="This test verifies that validation error for invalid project link occurs")
	public void verifyInvalidProjectLinkError()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Enter Project Link: www.google.com");
		projects.enterProjectLink("www.google.com");
		log("Step 3: Click on 'Save' button");
		projects.clickSave();
		log("Step 4: Verify that validation error corresponds to invalid project link");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error25")));
	}
	
	@Test(priority=24, description="The test verifies that validation error for maximum characters occurs: Project Link")
	public void verifyProjectLinkCharExceed()
	{
		log("Step 1: Click on pen icon for first project");
		profilePreview.clickProjectEdit(0);
		log("Step 2: Enter valid link containing 201 characters in Project Link field");
		projects.enterProjectLink(properties.getProperty("mail200"));
		log("Step 3: Click on 'Save' button");
		projects.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding the 200 character limit");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getProjectLinkErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=25, description="This test verifies that validation error for required to save field occurs: Project Title")
	public void verifyProjectTitleRequired()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Select start month: May");
		projects.selectStartMonth("May");
		log("Step 3: Select start year: 2022");
		projects.selectStartYear("2022");
		log("Step 4: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 5: Click on 'Save' button");
		projects.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for Project Title field");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getTitleErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=26, description="This test verifies that validation error for required to save field occurs: Start Month")
	public void verifyStartMonthRequired()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: Project");
		projects.enterProjectTitle("Project");
		log("Step 3: Select start year: 2022");
		projects.selectStartYear("2022");
		log("Step 4: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 5: Click on 'Save' button");
		projects.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for start month field");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getStartDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=27, description="This test verifies that validation error for required to save field occurs: Start Year")
	public void verifyStartYearRequired()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: Project");
		projects.enterProjectTitle("Project");
		log("Step 3: Select start month: May");
		projects.selectStartMonth("May");
		log("Step 4: Select checkbox for End Date");
		projects.clickEndDateCheckBox();
		log("Step 5: Click on 'Save' button");
		projects.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for start year field");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getStartDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=28, description="This test verifies that validation error for required to save field occurs: End Month")
	public void verifyEndMonthRequired()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: Project");
		projects.enterProjectTitle("Project");
		log("Step 3: Select start month: May");
		projects.selectStartMonth("May");
		log("Step 4: Select start year: 2022");
		projects.selectStartYear("2022");
		log("Step 5: Select end year: 2022");
		projects.selectEndYear("2022");
		log("Step 6: Click on 'Save' button");
		projects.clickSave();
		log("Step 7: Verify that validation error corresponds to 'Required to save' for end month field");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=29, description="This test verifies that validation error for required to save field occurs: End Year")
	public void verifyEndYearRequired()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Enter Project Title: Project");
		projects.enterProjectTitle("Project");
		log("Step 3: Select start month: May");
		projects.selectStartMonth("May");
		log("Step 4: Select start year: 2022");
		projects.selectStartYear("2022");
		log("Step 5: Select end month: April");
		projects.selectEndMonth("April");
		log("Step 6: Click on 'Save' button");
		projects.clickSave();
		log("Step 7: Verify that validation error corresponds to 'Required to save' for end year field");
		Assert.assertEquals(projects.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(projects.getEndDateErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=30, description="This test verifies that validation error for invalid file format occurs")
	public void verifyInvalidFileFormat()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Upload a file in txt format");
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\file-sample-txt.txt");
		log("Step 3: Verify that validation error corresponds to file type: doc, docx, rtf, pdf");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error26")));
	}
	
	@Test(priority=31, description="This test verifies that validation error for invalid size for project file occurs")
	public void verifyInvalidFileSize()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		log("Step 2: Upload a file having size more than 2mb");
		projects.uploadFile("C:\\Users\\prasa\\Downloads\\samplepdf-above2mb.pdf");
		log("Step 3: Verify that validation error corresponds to file size less than 2048 kilobytes");
		Assert.assertTrue(projects.getProjectFileErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=32, description="This test verifies that 'Cancel' from New Project redirectes user to preview page ")
	public void verifyProjectCancelNewRed()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
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
	
	@Test(priority=33, description="This test verifies that 'Select From Files' button turns inactive once five files are uploaded")
	public void verifyMaxFilesUpload()
	{
		log("Step 1: Click on 'Add Project' button");
		profilePreview.clickAddProject();
		String[] paths = {"C:\\Users\\prasa\\Downloads\\Sample_Project.pdf", "C:\\Users\\prasa\\Downloads\\file-sample-doc.doc",
				"C:\\Users\\prasa\\Downloads\\file-sample-docx.docx", "C:\\Users\\prasa\\Downloads\\file-sample-rtf.rtf",
				"C:\\Users\\prasa\\Downloads\\Sample_Project-pdf.pdf"};
		String firstPath = paths[0];
		log("Step 2: Upload five sample project files");
		for(String path : paths)
		{
			projects.uploadFile(path);
			File file = new File(path);
			String fileName = file.getName();
			projects.getProjectFileText(firstPath, fileName, path);
		}
		log("Step 3: Verify that 'Select From Files' button turns inactive");
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
