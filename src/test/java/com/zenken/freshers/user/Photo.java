package com.zenken.freshers.user;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditPhotoPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Photo extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditPhotoPage photo;
	Properties properties;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		photo = new EditPhotoPage(driver);
		navigateTo("/");
		profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
		properties = getProperties();
	}
	
	@Test(priority=1, description="This test verifies that user is redirected to Photo edit page")
	public void verifyPhotoUrl()
	{
		profilePreview.clickphotoEdit();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url18"));
		Assert.assertEquals(photo.getPhotoHeadline(), "Photo");
	}
	
	@Test(priority=2, description="This test verifies that the Photo page title is correct")
	public void verifyPhotoTitle()
	{
		profilePreview.clickphotoEdit();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that upload modal appears when upload icon is clicked")
	public void verifyUploadModalDisplay()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		Assert.assertTrue(photo.isPopUpDisplayed(), "Upload modal should have been displayed");
		photo.clickModalCancel();
	}
	
	@Test(priority=4, description="This test verifies that the uploaded image can be resized")
	public void verifyImageResize()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		String initialStyle = photo.getCropBoxStyle();
		photo.resizePhoto();
		String newStyle = photo.getCropBoxStyle();
		Assert.assertNotEquals(newStyle, initialStyle, "The crop-box style should have changed after resizing");
		Assert.assertTrue(newStyle.contains("width: 478px; height: 478px"));
	}
	
	@Test(priority=5, description="This test verifies that the uploaded image can be dragged")
	public void verifyImageDrag()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		String initialStyle = photo.getCropBoxStyle();
		photo.dragPhoto();
		String newStyle = photo.getCropBoxStyle();
		Assert.assertNotEquals(newStyle, initialStyle, "The crop-box style should have moved after dragging");
		Assert.assertTrue(newStyle.contains("translateX(106px) translateY(135px)"));
	}
	
	@Test(priority=6, description="This test verifies that the image can be uploaded successfully")
	public void verifyPhotoUpload()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		photo.clickModalUpload();
		redirectionAssertions("#Photo");
		String text = properties.getProperty("photo");
		Assert.assertEquals(profilePreview.getPhotoText(), text);
		profilePreview.clickphotoEdit();
		Assert.assertEquals(profilePreview.getPhotoText(), text);
	}
	
	@Test(priority=7, description="This test verifies that the uploaded image is same and 'Required to apply' text is not displayed on preview and Photo edit page")
	public void verifyPhotoImage() throws InterruptedException
	{
		String src = profilePreview.monitorPhoto();
		Assert.assertFalse(profilePreview.photoTitle.getText().contains(properties.getProperty("error16")));
		String subSrc = src.substring(0, src.indexOf("webp"));
		profilePreview.clickphotoEdit();
		String src2 = photo.getImageSrc();
		Assert.assertEquals(subSrc, src2.substring(0, src2.indexOf("webp")));
		Assert.assertFalse(photo.getCardText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=8, description="This test verifies that the uploaded image can be changed")
	public void verifyPhotoChange()
	{
		profilePreview.clickphotoEdit();
		Assert.assertTrue(photo.getIconText().contains("Change"));
		Assert.assertTrue(photo.getIconText().contains("Photo"));
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		Assert.assertTrue(photo.isUploadBtActive(), "Upload button should be active");
	}
	
	@Test(priority=9, description="This test verifies that the image can be deleted and 'Required to apply' text is displayed on Photo edit and preview page")
	public void verifyPhotoDelete()
	{
		profilePreview.clickphotoEdit();
		photo.clickDelete();
		String src = photo.getImageSrcAfterDel();
		Assert.assertTrue(src.contains(properties.getProperty("src")), "src value is not correct");
		Assert.assertTrue(photo.getCardText().contains(properties.getProperty("error16")));
		photo.clickBack();
		Assert.assertTrue(profilePreview.photoTitle.getText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=10, description="This test verifies that validation error occurs for invalid file format")
	public void verifyPhotoInvalidFile()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample_Project.pdf");
		photo.clickModalUpload();
		Assert.assertEquals(photo.getAlert(), properties.getProperty("alert6"));
	}
	
	@Test(priority=11, description="This test verifies that validation error occurs when image size is more than 5MB")
	public void verifyPhotoInvalidSize()
	{
		profilePreview.clickphotoEdit();
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample-png-image-5mb.png");
		photo.clickModalUpload();
		Assert.assertTrue(photo.getPopUpText().contains(properties.getProperty("error30")));
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.photoSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.photoAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
