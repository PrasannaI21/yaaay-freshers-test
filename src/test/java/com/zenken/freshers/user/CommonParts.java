package com.zenken.freshers.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;
import com.zenken.freshers.testcomponents.BaseTest;

public class CommonParts extends BaseTest{
	
	WebDriverUtils utils;
	Properties properties;
	String email;
	String password;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		navigateTo("/");
		utils = new WebDriverUtils(driver);
		properties = getProperties();
		email = properties.getProperty("email");
		password = properties.getProperty("password");
	}

	@Test(description="This test verifies that Freshers logo is displyaed on header")
	public void verifyHeaderImage()
	{
		log("Step 1: Verify that header image is displayed");
		WebElement headerImage = utils.getHeaderImage();
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		//リンクの日付が動的に変わるので、固定な部分を取得
		String expectedSrcPart = properties.getProperty("imagePath");
		log("Step 2: Verify that image source contains path for Freshers logo");
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test(description="This test verifies that Alt text is displyaed on header")
	public void verifyAltText()
	{
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = properties.getProperty("alt");
		log("Step 1: Verify that image alt equals \"Yaaay Freshers by Zenken\"");
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test(description="This test verifies that Terms PDF is opened in a separate tab")
	public void verifyTermsPDF()
	{
		log("Step 1: Click on 'Terms' link on the footer");
		utils.clickTerms();
		ArrayList<String> tabs = utils.switchTabs(1);
		log("Step 2: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("terms");
		String currentUrl = driver.getCurrentUrl();
		log("Step 3: Verify that PDF url is correct");
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test(description="This test verifies that the link for Privacy Policy is opened "
			+ "in a separate tab")
	public void verifyPolicy()
	{
		log("Step 1: Click on 'Privacy Policy' link on the footer");
		utils.clickPolicy();
		ArrayList<String> tabs = utils.switchTabs(1);
		log("Step 2: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("policy");
		String currentUrl = driver.getCurrentUrl();
		log("Step 3: Verify that Privacy Policy url is correct");
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected one.");
	}
	
	@Test(description="This test verifies that Zenken's copyright mark is displayed on footer")
	public void verifyCopyrightMark()
	{
		WebElement copyright = utils.getCopyrightMark();
		log("Step 2: Verify that expected copyright mark is displayed");
		Assert.assertEquals(copyright.getText(), properties.getProperty("copyright"));
	}
	
	@Test(description="This test verifies that the expected dropdown options are displayed")
	public void verifyDropdown()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		WebElement icon = utils.getIcon();
		log("Step 2: Hover the cursor over the profile icon");
		utils.hoverOver(icon);
		List<WebElement> elements = utils.getDropdownOptions();
		log("Step 3: Verify that 'Profile' and 'Log Out' options are displayed in the dropdown");
		Assert.assertTrue(elements.get(0).isDisplayed(), "Profile is not displayed");
		Assert.assertTrue(elements.get(1).isDisplayed(), "Log Out is not displayed");
	}
	
	@Test(description="This test verifies that user is redirected to profile preview "
			+ "page after clicking on 'Profile'")
	public void verifyProfileClick()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		WebElement icon = utils.getIcon();
		log("Step 2: Hover the cursor over the profile icon");
		utils.hoverOver(icon);
		log("Step 3: Click on 'Profile' option");
		utils.clickProfile();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = properties.getProperty("url2");
		log("Step 4: Verify that URL loaded is for the profile preview page");
		Assert.assertEquals(currentUrl, expectedUrl, "Profile page did not load");
	}
	
	@Test(description="This test verifies that user is logged out from the account "
			+ "after clicking on 'Log Out'")
	public void verifyLogOut()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		WebElement icon = utils.getIcon();
		log("Step 2: Hover the cursor over the profile icon");
		utils.hoverOver(icon);
		log("Step 3: Click on 'Log Out' option");
		utils.clickLogOut();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = properties.getProperty("url");
		log("Step 4: Verify that user is logged out and is redirected to Login page");
		Assert.assertEquals(currentUrl, expectedUrl, "Could not log out");
	}
	
	@Test(description="This test verifies that Freshers logo is displyaed on the header "
			+ "of the profile preview page")
	public void verifyHeaderImagePostLogIn()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		WebElement headerImage = utils.getHeaderImage();
		log("Step 2: Verify that header image is displayed");
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		String expectedSrcPart = properties.getProperty("imagePath");
		log("Step 3: Verify that image source contains path for Freshers logo");
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test(description="This test verifies that Alt text is displyaed on the header "
			+ "of the profile preview page")
	public void verifyAltTextPostLogIn()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = properties.getProperty("alt");
		log("Step 2: Verify that image alt equals \"Yaaay Freshers by Zenken\"");
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test(description="This test verifies that Terms PDF is opened in a separate tab "
			+ "from the footer of profile preview page")
	public void verifyTermsPDFPostLogIn()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		log("Step 2: Click on 'Terms' link on the footer");
		utils.clickTerms();
		ArrayList<String> tabs = utils.switchTabs(1);
		log("Step 3: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("terms");
		String currentUrl = driver.getCurrentUrl();
		log("Step 4: Verify that PDF url is correct");
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test(description="This test verifies that the link for Privacy Policy is opened "
			+ "in a separate tab from the footer of profile preview page")
	public void verifyPolicyPostLogIn()
	{
		log("Step 1: Login with 人材 account");
		utils.loginUser(email, password);
		log("Step 2: Click on 'Privacy Policy' link on the footer");
		utils.clickPolicy();
		ArrayList<String> tabs = utils.switchTabs(1);
		log("Step 3: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("policy");
		String currentUrl = driver.getCurrentUrl();
		log("Step 4: Verify that Privacy Policy url is correct");
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}

}
