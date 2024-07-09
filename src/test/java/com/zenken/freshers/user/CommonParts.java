package com.zenken.freshers.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;
import com.zenken.freshers.testcomponents.BaseTest;

public class CommonParts extends BaseTest{
	
	WebDriverUtils utils = new WebDriverUtils(driver);

	@Test
	public void verifyHeaderImage()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		WebElement headerImage = utils.getHeaderImage();
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		//リンクの日付が動的に変わるので、固定な部分を取得
		String expectedSrcPart = "/build/assets/yaaay-freshers-logo-zenken-pc-BYTdvblZ.svg";
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test
	public void verifyAltText()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = "Yaaay Freshers by Zenken";
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test
	public void verifyTermsPDF()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.clickTerms();
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		driver.switchTo().window(tabs.get(1));
		String expectedPdfUrl = "https://cdn-freshers.dspf-dev.com/docs/user/Yaaay%20Freshers%20General%20Terms%20and%20Conditions%20of%20Use_20240522.pdf";
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyPolicy()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.clickPolicy();
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		driver.switchTo().window(tabs.get(1));
		String expectedPdfUrl = "https://www.zenken.co.jp/en/privacypolicy/";
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyCopyrightMark()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		WebElement copyright = utils.getCopyrightMark();
		Assert.assertEquals(copyright.getText(), "© Zenken Corporation");
	}
	
	@Test
	public void verifyDropdown()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
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
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement icon = utils.getIcon();
		utils.hoverOver(icon);
		utils.clickProfile();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = "https://freshers.dspf-dev.com/mypage/profile/";
		Assert.assertEquals(currentUrl, expectedUrl, "Profile page did not load");
	}
	
	@Test
	public void verifyLogOut()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement icon = utils.getIcon();
		utils.hoverOver(icon);
		utils.clickLogOut();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = "https://freshers.dspf-dev.com/login/";
		Assert.assertEquals(currentUrl, expectedUrl, "Could not log out");
	}
	
	@Test
	public void verifyHeaderImagePostLogIn()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		WebElement headerImage = utils.getHeaderImage();
		Assert.assertTrue(headerImage.isDisplayed(), "Logo is not displayed");
		String imgSrc = utils.getImageAttribute();
		String expectedSrcPart = "/build/assets/yaaay-freshers-logo-zenken-pc-BYTdvblZ.svg";
		Assert.assertTrue(imgSrc.contains(expectedSrcPart), "Image source does not contain the expected path");
	}
	
	@Test
	public void verifyAltTextPostLogIn()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		String imgAlt = utils.getAltAttribute();
		String expectedAlt = "Yaaay Freshers by Zenken";
		Assert.assertEquals(imgAlt, expectedAlt, "Image alt is not as expected");
	}
	
	@Test
	public void verifyTermsPDFPostLogIn()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		utils.clickTerms();
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		driver.switchTo().window(tabs.get(1));
		String expectedPdfUrl = "https://cdn-freshers.dspf-dev.com/docs/user/Yaaay%20Freshers%20General%20Terms%20and%20Conditions%20of%20Use_20240522.pdf";
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyPolicyPostLogIn()
	{
		utils = new WebDriverUtils(driver);
		utils.goToUser();
		utils.loginUser("prasanna.inamdar@zenken.co.jp", "Freshers123#");
		utils.clickPolicy();
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		driver.switchTo().window(tabs.get(1));
		String expectedPdfUrl = "https://www.zenken.co.jp/en/privacypolicy/";
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, expectedPdfUrl, "The opened URL is not the expected PDF URL.");
	}

}
