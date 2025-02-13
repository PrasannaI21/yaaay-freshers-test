package com.zenken.freshers.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.data.DataReader;
import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.admin.FinalResultEditPage;
import com.zenken.freshers.pages.company.AppliationsListPage;
import com.zenken.freshers.pages.company.CLoginPage;
import com.zenken.freshers.pages.user.EditBasicInformationPage;
import com.zenken.freshers.pages.user.EditCertificationsPage;
import com.zenken.freshers.pages.user.EditInternshipsPage;
import com.zenken.freshers.pages.user.EditProjectsPage;
import com.zenken.freshers.pages.user.EditSkillsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class ApplicantListCsv extends BaseTest{

	AppliationsListPage applications;
	Properties properties;
	private static boolean isCsvInfoUpdated = false;
	private static boolean toggle;
	
	@BeforeMethod
	public void setupApplicantListCsv(ITestResult result) throws IOException
	{
		applications = new AppliationsListPage(driver);
		CLoginPage cLogin = new CLoginPage(driver);	
		properties = getProperties();		
		if(!isCsvInfoUpdated) {
			toggle = Boolean.parseBoolean(properties.getProperty("csvToggle"));
			navigateTo("/admin/");
			AdminLoginPage adminLogin = new AdminLoginPage(driver);
			adminLogin.loginAdmin();
			navigateTo("/admin/events/10/screening-status/edit/2813/");
			FinalResultEditPage finalResultEdit = new FinalResultEditPage(driver);
			if(toggle == true) {
				System.out.println("toggle value is true!");
				finalResultEdit.clickPassed();
				finalResultEdit.clickSave();
				updateData1();
				isCsvInfoUpdated = true;
			}else {
				System.out.println("toggle value is false!");
				finalResultEdit.clickSubstitute();
				finalResultEdit.clickSave();
				updateData2();
				isCsvInfoUpdated = true;
			}
		}
		navigateTo("/company/");
		String test = result.getMethod().getMethodName();
		if(test.equals("verifyCsvHeader2") || test.equals("verifyCsvValues")) {
			cLogin.loginCompany("screening_completed_exists_applicant_job_company@example.com", "Password_1");
			navigateTo("/company/events/878fca2e/applications/");
		}else {
			cLogin.loginCompany("event_list_test@example.com", "Password_1");
			navigateTo("/company/events/bbcfc68b/applications/");
		}
	}
	
	@Test(description="This test verifies that the text of the link to download the CSV"
			+ " file is correct")
	public void verifyCsvDlLink()
	{
		Assert.assertEquals(applications.getCsvLinkText(), "応募者一覧 CSVダウンロード");	
	}
	
	@Test(description="This test verifies that an expected popup is displayed after clicking"
			+ " on the CSV download link")
	public void verifyCsvDlPopup()
	{
		applications.clickCsvDlLink();
		Assert.assertTrue(applications.getCsvPopup().contains(properties.getProperty("csvpopup"))
				, "CSV popup text is not correct");
	}
	
	@Test(priority=1, description="This test verifies that the CSV file is downloaded in zip format "
			+ "and that the zip file name and CSV file names are correct")
	public void verifyCsvDl() throws FileNotFoundException, IOException
	{
		int initialCount = applications.getFileCount();
		applications.clickCsvDlLink();
		applications.clickCsvDlBt();
		Assert.assertEquals(applications.getCsvDlAlertText(), properties.get("alert13"));
		Assert.assertTrue(applications.isFileDownloaded(initialCount, ".zip"), "zip file did not download");
		Assert.assertEquals(applications.getDownloadedFileName(), "applications_data.zip");
		File zipFile = new File(System.getProperty("user.dir")+"/downloads/applications_data.zip");
		applications.unzip(zipFile);
		File folder = new File(System.getProperty("user.dir")+"/downloads/extracted");
		File[] csvFiles = folder.listFiles();
		Assert.assertEquals(csvFiles.length, applications.getComJobs()+3);
		List<String> jobTitles = applications.getComJobTitlesWoSpace();
		int i = 0;
		for(File file : csvFiles) {
			if(file.getName().startsWith("candidates_")) {
				Assert.assertEquals(file.getName(), "candidates_"+jobTitles.get(i)+".csv");
			}
			i++;
			if(i == applications.getComJobs()) {
				List<String> files = Arrays.asList("certifications.csv", "internships.csv", "projects.csv");
				for(String rFile : files) {
					File eFile = new File(folder, rFile);
					Assert.assertTrue(eFile.exists(), "Missing file: "+eFile);
				}
				break;
			}
		}
	}
	
	@Test(priority=2, description="This test verifies that the header (field names) of all extracted"
			+ "CSV files are correct (応募者なし)")
	public void verifyCsvHeader1() throws FileNotFoundException, IOException
	{
		File folder = new File(System.getProperty("user.dir")+"/downloads/extracted");
		File[] csvFiles = folder.listFiles();
		int csvNumber = 1;
		for(File csvFile : csvFiles) {
			if(csvFile.getName().startsWith("candidates_")) {
				Assert.assertEquals(applications.getCsvHeader(String.valueOf(csvFile)), properties.get("candidatecsv"));
			}else {
				Assert.assertEquals(applications.getCsvHeader(String.valueOf(csvFile)), properties.get("csv" + csvNumber));
				csvNumber++;
			}
		}
	}
	
	@Test(priority=3, description="This test verifies that the CSV files do not have any candidate records")
	public void verifyCsvNoApplicant() throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		File folder = new File(System.getProperty("user.dir")+"/downloads/extracted");
		File[] csvFiles = folder.listFiles();
		for(File csvFile : csvFiles) {
			String nextLine = applications.getCsvFirstLine(String.valueOf(csvFile));
			Assert.assertNull(nextLine, "Expected no data after header, but found: "+nextLine);
		}	
	}
	
	@Test(priority=4, description="This test verifies that the header (field names) of all extracted "
			+ "CSV files are correct (応募者あり)")
	public void verifyCsvHeader2() throws FileNotFoundException, IOException
	{
		int initialCount = applications.getFileCount();
		applications.clickCsvDlLink();
		applications.clickCsvDlBt();
		applications.isFileDownloaded(initialCount, ".zip");
		File zipFile = new File(System.getProperty("user.dir")+"/downloads/applications_data (1).zip");
		applications.unzip(zipFile);
		File folder = new File(System.getProperty("user.dir")+"/downloads/extracted");
		File[] csvFiles = folder.listFiles();
		int csvNumber = 1;
		for(File csvFile : csvFiles) {
			if(csvFile.getName().startsWith("candidates.")) {
				Assert.assertEquals(applications.getCsvHeader(String.valueOf(csvFile)), properties.get("candidatecsv1"));
			}else if(csvFile.getName().startsWith("cert") || csvFile.getName().startsWith("int")
					|| csvFile.getName().startsWith("pro")){
				Assert.assertEquals(applications.getCsvHeader(String.valueOf(csvFile)), properties.get("csv" + csvNumber));
				csvNumber++;
			}
		}
	}
	
	@Test(priority=5, description="This test verifies that the values are updated in all CSV files "
			+ "after profile is updated (Company ID: 878fca2e, 応募者ID: 8)")
	public void verifyCsvValues() throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		String jobTitle = applications.getComJobTitles().get(0);
		File folder = new File(System.getProperty("user.dir")+"/downloads/extracted");
		File[] csvFiles = folder.listFiles();
		for(File csvFile : csvFiles) {
			switch(csvFile.getName()) {
			case "candidates.csv":
				String[] row = applications.getCsvRowById(csvFile, "8");
				String[] actualRow = applications.santizeArray(row);
				String[] values = toggle ? properties.getProperty("candidatevalues").split(",")
						: properties.getProperty("candidatevalues2").split(",");
				String[] expectedRow = new String[values.length+1];
				expectedRow[0] = jobTitle;
				System.arraycopy(values, 0, expectedRow, 1, values.length);
				Assert.assertEquals(actualRow, expectedRow);
				break;
			case "certifications.csv":
				String[] row1 = applications.getCsvRowById(csvFile, "8");
				String[] actualRow1 = applications.santizeArray(row1);
				String[] values1 = toggle ? properties.getProperty("certvalues").split(",")
						: properties.getProperty("certvalues2").split(",");
				String[] expectedRow1 = new String[values1.length+1];
				expectedRow1[5] = toggle ? properties.getProperty("certDetails")
						: "This is certification details";
				System.arraycopy(values1, 0, expectedRow1, 0, values1.length);
				Assert.assertEquals(actualRow1, expectedRow1);
				break;
			case "internships.csv":
				String[] row2 = applications.getCsvRowById(csvFile, "8");
				String[] actualRow2 = applications.santizeArray(row2);
				String[] values2 = toggle ? properties.getProperty("intvalues").split(",")
						: properties.getProperty("intvalues2").split(",");
				String[] expextedRow2 = new String[values2.length+1];
				expextedRow2[6] = toggle ? properties.getProperty("intDetails") : "This is internship details";
				System.arraycopy(values2, 0, expextedRow2, 0, values2.length);
				Assert.assertEquals(actualRow2, expextedRow2);
				break;
			case "projects.csv":
				String[] row3 = applications.getCsvRowById(csvFile, "8");
				String[] actualRow3 = applications.santizeArray(row3);
				String[] values3 = toggle ? properties.getProperty("provalues").split(",") : properties.getProperty("provalues2").split(",");
				String[] expextedRow3 = new String[values3.length+2];
				expextedRow3[7] = toggle ? properties.getProperty("proDetails") : "This is project details";
				expextedRow3[8] = toggle ? "https://www.google.com" : "https://www.facebook.com";
				System.arraycopy(values3, 0, expextedRow3, 0, values3.length);
				Assert.assertEquals(actualRow3, expextedRow3);
				break;
			}			
		}
		DataReader.saveProperty("csvToggle", String.valueOf(!toggle));
	}
	
	private void updateData1()
	{
		navigateTo("/");
		applications.loginUser("applied_user_13@example.com", "Password_1");
		ProfilePreviewPage profilePreview = new ProfilePreviewPage(driver);
		EditBasicInformationPage editInfo = new EditBasicInformationPage(driver);
		profilePreview.clickEdit(profilePreview.informationEdit);
		editInfo.enterInfoForCsv1();
		EditSkillsPage skills = new EditSkillsPage(driver);
		profilePreview.clickEdit(profilePreview.skillsEdit);
		List<WebElement> deleteIcons = skills.getDeleteIcons();
		while(!deleteIcons.isEmpty())
			{
			    skills.deleteSkill(0);
				deleteIcons = skills.getDeleteIcons();
			}
		skills.addSkill("d", 1, 1);
		skills.addSkill("r", 2, 2);
		skills.addSkill("se", 2, 3);
		skills.clickSave();
		EditInternshipsPage internship = new EditInternshipsPage(driver);
		profilePreview.clickIntEdit(0);
		internship.enterIntDetails(properties.getProperty("intDetails"));
		if(internship.isIntCheckBoxSelected()){
			internship.clickEndDateCheckbox();
		}
		internship.addInternship("May", "1999", "August", "2017");
		EditProjectsPage projects = new EditProjectsPage(driver);
		profilePreview.clickProjectEdit(0);
		projects.enterProjectTitle("ProjectTitle1");
		if(projects.isCheckBoxSelected()){
			projects.clickEndDateCheckBox();
		}
		projects.selectStartMonth("December");
		projects.selectStartYear("2022");
		projects.selectEndMonth("July");
		projects.selectEndYear("2023");
		projects.enterProjectOverview(properties.getProperty("proDetails"));
		projects.enterProjectLink("https://www.google.com");
		projects.clickSave();
		EditCertificationsPage cert = new EditCertificationsPage(driver);
		profilePreview.clickCertEdit(0);
		cert.enterCertName("Certification1");
		cert.enterCertDetails(properties.getProperty("certDetails"));
		projects.clickSave();
	}
	
	private void updateData2()
	{
		navigateTo("/");
		applications.loginUser("applied_user_13@example.com", "Password_1");
		ProfilePreviewPage profilePreview = new ProfilePreviewPage(driver);
		EditBasicInformationPage editInfo = new EditBasicInformationPage(driver);
		profilePreview.clickEdit(profilePreview.informationEdit);
		editInfo.enterInfoForCsv2();
		EditSkillsPage skills = new EditSkillsPage(driver);
		profilePreview.clickEdit(profilePreview.skillsEdit);
		List<WebElement> deleteIcons = skills.getDeleteIcons();
		while(!deleteIcons.isEmpty())
			{
			    skills.deleteSkill(0);
				deleteIcons = skills.getDeleteIcons();
			}
		skills.addSkill("g", 4, 1);
		skills.addSkill("p", 1, 2);
		skills.addSkill("r", 3, 3);
		skills.clickSave();
		EditInternshipsPage internship = new EditInternshipsPage(driver);
		profilePreview.clickIntEdit(0);
		internship.enterIntDetails("This is internship details");
		if(internship.isIntCheckBoxSelected()){
			internship.clickEndDateCheckbox();
		}
		internship.addInternship("January", "2024", "November", "2025");
		EditProjectsPage projects = new EditProjectsPage(driver);
		profilePreview.clickProjectEdit(0);
		projects.enterProjectTitle("ProjectTitle2");
		if(projects.isCheckBoxSelected()){
			projects.clickEndDateCheckBox();
		}
		projects.selectStartMonth("March");
		projects.selectStartYear("2018");
		projects.selectEndMonth("June");
		projects.selectEndYear("2020");
		projects.enterProjectOverview("This is project details");
		projects.enterProjectLink("https://www.facebook.com");
		projects.clickSave();
		EditCertificationsPage cert = new EditCertificationsPage(driver);
		profilePreview.clickCertEdit(0);
		cert.enterCertName("Certification2");
		cert.enterCertDetails("This is certification details");
		projects.clickSave();
	}
	
}
