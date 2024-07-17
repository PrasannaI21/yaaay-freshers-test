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
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		navigateTo("/");
		utils = new WebDriverUtils(driver);
		properties = getProperties();
	}

	@Test
	public void verifyHeaderImage()
	{
		WebElement headerImage = utils.getHeaderImage();
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		//リンクの日付が動的に変わるので、固定な部分を取得
		String expectedSrcPart = properties.getProperty("imagePath");
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test
	public void verifyAltText()
	{
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = properties.getProperty("alt");
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test
	public void verifyTermsPDF()
	{
		utils.clickTerms();
		ArrayList<String> tabs = utils.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("terms");
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyPolicy()
	{
		utils.clickPolicy();
		ArrayList<String> tabs = utils.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("policy");
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyCopyrightMark()
	{
		WebElement copyright = utils.getCopyrightMark();
		Assert.assertEquals(copyright.getText(), properties.getProperty("copyright"));
	}
	
	@Test
	public void verifyDropdown()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement icon = utils.getIcon();
		utils.hoverOver(icon);
		List<WebElement> elements = utils.getDropdownOptions();
		Assert.assertTrue(elements.get(0).isDisplayed(), "Profile is not displayed");
		Assert.assertTrue(elements.get(1).isDisplayed(), "Log Out is not displayed");
	}
	
	@Test
	public void verifyProfileClick()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement icon = utils.getIcon();
		utils.hoverOver(icon);
		utils.clickProfile();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = properties.getProperty("url2");
		Assert.assertEquals(currentUrl, expectedUrl, "Profile page did not load");
	}
	
	@Test
	public void verifyLogOut()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement icon = utils.getIcon();
		utils.hoverOver(icon);
		utils.clickLogOut();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = properties.getProperty("url");
		Assert.assertEquals(currentUrl, expectedUrl, "Could not log out");
	}
	
	@Test
	public void verifyHeaderImagePostLogIn()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement headerImage = utils.getHeaderImage();
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		String expectedSrcPart = properties.getProperty("imagePath");
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test
	public void verifyAltTextPostLogIn()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = properties.getProperty("alt");
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test
	public void verifyTermsPDFPostLogIn()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		utils.clickTerms();
		ArrayList<String> tabs = utils.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("terms");
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyPolicyPostLogIn()
	{
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		utils.clickPolicy();
		ArrayList<String> tabs = utils.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPdfUrl = properties.getProperty("policy");
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}

}
