package com.zenken.freshers.company;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.admin.AdminNewJobsPage;
import com.zenken.freshers.pages.admin.EventDetailsPage;
import com.zenken.freshers.pages.admin.EventListPage;
import com.zenken.freshers.pages.admin.JobsDetailsPage;
import com.zenken.freshers.pages.admin.NewEvents;
import com.zenken.freshers.pages.company.AppliationsListPage;
import com.zenken.freshers.pages.company.ApplicantDetailsPage;
import com.zenken.freshers.pages.company.CLoginPage;
import com.zenken.freshers.pages.company.EventsPage;
import com.zenken.freshers.pages.company.JobSelectionPage;
import com.zenken.freshers.pages.company.PriorityListPage;
import com.zenken.freshers.pages.user.JobsPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class ScreeeningProcess extends BaseTest{

	NewEvents newEvents;
	EventsPage events;
	AppliationsListPage applications;
	AdminLoginPage adminLogin;
	EventListPage eventList;
	JobsDetailsPage jobDetails;
	JobsPage jobs;
	CLoginPage cLogin;
	ApplicantDetailsPage appDetails;
	private boolean isEventCreated = false;
	Properties properties;
	String eventName;
	String url;
	String jobUrl1;
	String jobUrl2;
	String comUrl;
	String date;
	
	@BeforeMethod
	public void setupScreeeningProcess() throws IOException
	{
		newEvents = new NewEvents(driver);
		events = new EventsPage(driver);
		AdminNewJobsPage newJobs = new AdminNewJobsPage(driver);
		adminLogin = new AdminLoginPage(driver);
		EventDetailsPage eventDetails = new EventDetailsPage(driver);
		jobDetails = new JobsDetailsPage(driver);
		cLogin = new CLoginPage(driver);	
		applications = new AppliationsListPage(driver);
		eventList = new EventListPage(driver);
		appDetails = new ApplicantDetailsPage(driver);
		jobs = new JobsPage(driver);
		properties = getProperties();
		if(!isEventCreated) {
			navigateTo("/admin/");
			adminLogin.loginAdmin();
			navigateTo("/admin/events/new/");
			newEvents.enterEventName(eventName);
			newEvents.selectGradYear(4);
			newEvents.selectCompany("Prasanna Co.");
			newEvents.enterAppStartDate("02/01/2025\t12:002");
			newEvents.enterAppEndDate("02/01/2030\t12:002");
			newEvents.clickSave();
		    url = driver.getCurrentUrl();
			eventDetails.clickjobPostBt();
			newJobs.createNewJob("Big Data Engineer");
			jobDetails.changeJobStatus();
		    jobUrl1 = jobDetails.getJobUrl();
			jobDetails.clickBack();
			eventDetails.clickjobPostBt();
			newJobs.createNewJob("Learning and Development Manager");
			jobDetails.changeJobStatus();
		    jobUrl2 = jobDetails.getJobUrl();
			isEventCreated = true;
		}
		navigateTo("/company/");
		cLogin.loginCompany("prasanna.inamdar@zenken.co.jp", "Password_1");
	}
	
	@BeforeClass
	public void generateEventName()
	{
		String id = UUID.randomUUID().toString().substring(0, 8);
		eventName = "選考テストイベント" + id;
		System.out.println("イベント名："+eventName);
	}
	
	@Test(priority=1, description="This test verifies that the event record is added in イベント一覧 page and "
			+ "the values (イベント名, etc.) being displayed are correct")
	public void verifyEventRecord()
	{
		Assert.assertTrue(events.getLastEvent().contains(eventName), "Event name is not correct");
		Assert.assertTrue(events.getLastEvent().contains("27卒"), "Graduation year is not correct");
		Assert.assertTrue(events.getLastEvent().contains("募集中"), "Event status is not correct");
	}
	
	@Test(priority=2, description="This test verifies that event details such as イベント名、企業名、応募者 and "
			+ "選考の手順 are correct on 応募一覧 page")
	public void verifyEventDetails()
	{
		events.clickEvent();
		comUrl = driver.getCurrentUrl();
		System.out.println(comUrl);
		Assert.assertEquals(applications.getEventName(), eventName);
		Assert.assertTrue(applications.getEventDetails().contains("Prasanna Corporation"), "企業名 is not correct");
		Assert.assertTrue(applications.getEventDetails().contains("0名"), "応募者数 is not correct");
		Assert.assertEquals(applications.getScreeningSteps(), properties.getProperty("cominstructions"));
	}
	
	@Test(priority=3, description="This test verifies that 求人票 names are displayed as expected")
	public void verifyJobNames()
	{
		events.clickEvent();
		Assert.assertEquals(applications.getComJobTitles().get(0), "Big Data Engineer");
		Assert.assertEquals(applications.getComJobTitles().get(1), "Learning and Development Manager");
	}
	
	@Test(priority=4, description="This test verifies that expected text is displayed when no candidate has"
			+ "applied yet")
	public void verifyNoApplicantText()
	{
		events.clickEvent();
		Assert.assertEquals(applications.getNoAppText(), "応募者が見つかりませんでした。");
	}
	
	@Test(priority=5, description="This test verifies that application deadline is correct and that "
			+ "選考終了 button is inactive")
	public void verifyScreeningEndBtState()
	{
		events.clickEvent();
		Assert.assertTrue(applications.getApplicationDeadline().contains("2030/2/1 12:00 PM"), "Application deadline is incorrect");
		Assert.assertFalse(applications.isScrEndBtActive(), "選考終了 button should be inactive");
	}
	
	@Test(priority=6, description="This test verifies that the event record is updated in イベント一覧 page after"
			+ " 実施期間 has started and candidates have applied")
	public void verifyEventRecord2()
	{
		navigateTo("/");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
		date = LocalDate.now().format(formatter);
		adminLogin.loginUser("prasanna.inamdar+user4@zenken.co.jp", "Freshers123#");
		driver.get(jobUrl1);jobs.clickApply();jobs.clickPopupButton();
		driver.get(jobUrl2);jobs.clickApply();jobs.clickPopupButton();
		adminLogin.hoverOver(adminLogin.getIcon());adminLogin.clickLogOut();
		adminLogin.loginUser("prasanna.inamdar+user5@zenken.co.jp", "Freshers123#");
		driver.get(jobUrl2);jobs.clickApply();jobs.clickPopupButton();
		navigateTo("/admin/");
		adminLogin.loginAdmin();
		driver.get(url+"edit/");
		newEvents.enterAppEndDate("02/20/2025\t12:002");
		newEvents.enterEventStartDate("02/25/2025\t12:002");
		newEvents.enterEventEndDate("02/01/2030\t12:002");
		newEvents.selectEventType(1);
		newEvents.clickSave();
		navigateTo("/company/");
		String eventRow = driver.findElement(By.xpath("//tr[contains(.,'"+eventName+"')]")).getText();
		String oubosha = driver.findElement(By.xpath("(//tr[contains(.,'"+eventName+"')]/td)[4]")).getText();
		Assert.assertTrue(eventRow.contains("2025/02/25 〜 2030/02/01"), "イベント実施日 is not correct");
		Assert.assertEquals(oubosha, "2");
		Assert.assertTrue(eventRow.contains("選考中"), "Event status is not correct");
	}
	
	@Test(priority=7, description="This test verifies that event details such as 応募者 and 採用イベント実施日 are correct on 応募一覧 page")
	public void verifyEventDetails2()
	{
		driver.get(comUrl);
		Assert.assertTrue(applications.getEventDetails().contains("2名"), "応募者数 is not correct");
		Assert.assertTrue(applications.getEventDetails().contains("2025/2/25~2030/2/1"), "採用イベント実施日 is incorrect");
	}
	
	@Test(priority=8, description="This test verifies that expected text is displayed at bottom and that "
			+ "選考終了 button turns active when event status is \"選考中\"")
	public void verifyScreeningEndBtState2()
	{
		driver.get(comUrl);
		Assert.assertEquals(applications.getBottomText(), properties.getProperty("confirmScreening"));
		Assert.assertTrue(applications.isScrEndBtActive(), "選考終了 button should be active");
	}
	
	@Test(priority=9, description="This test verifies that candidate record has expected values on 応募一覧 page")
	public void verifyCandDetails()
	{
		driver.get(comUrl);
		Assert.assertTrue(applications.getCandRow1().contains("Prasanna"), "Candidate name is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("CIT"), "College name is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("コンピューター応用学士"), "学科 is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("28卒"), "Graduation year is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("Big Data Engineer"), "求人票 is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("+1"), "+1 should be displayed for multiple applications");
		Assert.assertTrue(applications.getCandRow1().contains("7"), "CGPA is incorrect");
		Assert.assertTrue(applications.getCandRow1().contains("なし"), "Backlog value is incorrect");
	}
	
	@Test(priority=10, description="This test verifies that company user is able to reject (不合格) all candidates at once")
	public void verifyRejectAll()
	{
		driver.get(comUrl);
		applications.clickAllCheckCb();
		for(WebElement checkbox : applications.getCheckboxes()) {
			Assert.assertTrue(checkbox.isSelected(), "Checkbox should be selected");
		}
		Assert.assertTrue(applications.getRejectBts().get(0).isEnabled(), "不合格 button should be active");
		Assert.assertTrue(applications.getRejectBts().get(1).isEnabled(), "不合格 button should be active");
		applications.clickByJavaScript(applications.getRejectBts().get(0));
		Assert.assertTrue(applications.getRejectPopup().contains(properties.getProperty("rejectPopup")), "Popup text is not correct");
		applications.clickRejectBt();
		Assert.assertEquals(applications.getAlert(), properties.getProperty("alert14"));
		for(String alt : applications.getRejectAlt()) {
			Assert.assertEquals(alt, "不合格");
		}
	}
	
	@Test(priority=11, description="This test verifies that company user is redirected to 応募詳細 page"
			+ "after clicking on the link of the name")
	public void verifyCandDetailsLink()
	{
		driver.get(comUrl);
		applications.clickNameLink();
		Assert.assertEquals(appDetails.getTopText(), properties.getProperty("topText"));	
	}
	
	@Test(priority=12, description="This test verifies that application date displayed on 応募詳細 page is correct")
	public void verifyApplicationDate()
	{
		driver.get(comUrl);
		applications.clickNameLink();
		Assert.assertEquals(appDetails.getAppDates().get(0), "応募日: "+date);
		Assert.assertEquals(appDetails.getAppDates().get(1), "応募日: "+date);	
	}
	
	@Test(priority=13, description="This test verifies that expected popup is displayed when company user tries to complete"
			+ "the screening when no candidate is 合格 or 補欠")
	public void verifyScreeningPopup()
	{
		driver.get(comUrl);
		applications.clickScrEndBt();
		Assert.assertTrue(applications.getScreenPopup().contains(properties.getProperty("screenPopup1.1")), "Popup text is not correct");
		Assert.assertTrue(applications.getScreenPopup().contains(properties.getProperty("screenPopup1.2")), "Popup text is not correct");
	}
	
	@Test(priority=14, description="This test verifies that company user is successfully able to complete the "
			+ "screening (選考終了) of the candidates")
	public void verifyScreeningEnd()
	{
		driver.get(comUrl);
		applications.clickMaru();
		applications.clickSankaku();
		applications.clickScrEndBt();
		Assert.assertTrue(driver.getCurrentUrl().contains("applications/job-selection/"), "Did not redirect to 求人票選択 page");
		JobSelectionPage jobSelection = new JobSelectionPage(driver);
		Assert.assertEquals(jobSelection.getCandNumbers().get(0), "1名");
		Assert.assertEquals(jobSelection.getCandNumbers().get(1), "1名");
		Assert.assertEquals(jobSelection.getResults().get(0), "合格");
		Assert.assertEquals(jobSelection.getResults().get(1), "補欠");
		jobSelection.clickSave();
		Assert.assertEquals(jobSelection.getAlert(), properties.getProperty("alert15"));
		jobSelection.selectJob();jobSelection.clickSave();
		Assert.assertTrue(driver.getCurrentUrl().contains("applications/priority-list/"), "Did not redirect to 優先順位記入 page");
		PriorityListPage priorityList = new PriorityListPage(driver);
		priorityList.clickSave();
		Assert.assertEquals(jobSelection.getAlert(), properties.getProperty("alert16"));
		priorityList.enterPriority("123a", "123a");priorityList.clickSave();
		Assert.assertEquals(jobSelection.getAlert(), properties.getProperty("alert17"));
		priorityList.enterPriority("1", "2");priorityList.clickSave();		
		Assert.assertTrue(priorityList.getScreenPopup().contains(properties.getProperty("screenPopup1.1")), "Popup text is not correct");
		Assert.assertTrue(priorityList.getScreenPopup().contains(properties.getProperty("screenPopup2")), "Popup text is not correct");
		priorityList.clickScrEndBt();
		Assert.assertEquals(driver.getCurrentUrl(), comUrl);
		Assert.assertEquals(applications.getAlert(), properties.getProperty("alert18"));
	}
	
	@Test(priority=15, description="This test verifies that 応募一覧 page is displayed as expected after 選考終了")
	public void verifyPostScreeningDisplay()
	{
		driver.get(comUrl);
		Assert.assertEquals(applications.getScreeningSteps(), properties.getProperty("cominstructions2"));
		Assert.assertTrue(applications.getCandRow1().contains("合格"), "書類選考 should be 合格");
		Assert.assertTrue(applications.getCandRow2().contains("補欠"), "書類選考 should be 補欠");
		Assert.assertFalse(applications.isScrnBtDisplayed(), "選考終了 button should not be displayed");
	}
}
