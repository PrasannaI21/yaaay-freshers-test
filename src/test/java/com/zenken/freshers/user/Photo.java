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
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url18"));
		Assert.assertEquals(photo.getPhotoHeadline(), "Photo");
	}
	
	@Test(priority=2, description="This test verifies that the Photo page title is correct")
	public void verifyPhotoTitle()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that upload modal appears when upload icon is clicked")
	public void verifyUploadModalDisplay()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Verify that Upload modal is displayed");
		Assert.assertTrue(photo.isPopUpDisplayed(), "Upload modal should have been displayed");
		log("Step 4: Verify that clicking on 'Cancel' closes the modal");
		photo.clickModalCancel();
	}
	
	@Test(priority=4, description="This test verifies that the uploaded image can be resized")
	public void verifyImageResize()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Upload an image");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		String initialStyle = photo.getCropBoxStyle();
		log("Step 4: Resize the image by click-holding right edge of the crop-box and moving horizontally by 30 offset");
		photo.resizePhoto();
		String newStyle = photo.getCropBoxStyle();
		log("Step 5: Verify that image is resized by given offset");
		Assert.assertNotEquals(newStyle, initialStyle, "The crop-box style should have changed after resizing");
		Assert.assertTrue(newStyle.contains("width: 478px; height: 478px"));
	}
	
	@Test(priority=5, description="This test verifies that the uploaded image can be dragged")
	public void verifyImageDrag()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Select an image");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		String initialStyle = photo.getCropBoxStyle();
		log("Step 4: Drag the image by click-holding it and moving vertically and horizontally by 50 offset");
		photo.dragPhoto();
		String newStyle = photo.getCropBoxStyle();
		log("Step 5: Verify that image is dragged by given offset");
		Assert.assertNotEquals(newStyle, initialStyle, "The crop-box style should have moved after dragging");
		Assert.assertTrue(newStyle.contains("translateX(106px) translateY(135px)"));
	}
	
	@Test(priority=6, description="This test verifies that the image can be uploaded successfully")
	public void verifyPhotoUpload()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Select an image");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		log("Step 4: Click on 'Upload' button");
		photo.clickModalUpload();
		log("Step 5: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Photo");
		String text = properties.getProperty("photo");
		log("Step 6: Verify that 'file checking' text is displayed on preview page");
		Assert.assertEquals(profilePreview.getPhotoText(), text);
		log("Step 7: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 8: Verify that 'file checking' text is displayed on Photo edit page");
		Assert.assertEquals(profilePreview.getPhotoText(), text);
	}
	
	@Test(priority=7, description="This test verifies that the uploaded image is same and 'Required to apply' text is not displayed on preview and Photo edit page")
	public void verifyPhotoImage() throws InterruptedException
	{
		log("Step 1: Refresh the page until the photo is displayed");
		String src = profilePreview.monitorPhoto();
		log("Step 2: Verify that 'Required to apply' text is not displayed on preview page");
		Assert.assertFalse(profilePreview.photoTitle.getText().contains(properties.getProperty("error16")));
		log("Step 3: Get src value of the image on preview page");
		String subSrc = src.substring(0, src.indexOf("webp"));
		log("Step 4: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 5: Verify that src value of the image on edit page matches to that of preview page");
		String src2 = photo.getImageSrc();
		Assert.assertEquals(subSrc, src2.substring(0, src2.indexOf("webp")));
		log("Step 6: Verify that 'Required to apply' text is not displayed on Photo edit page");
		Assert.assertFalse(photo.getCardText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=8, description="This test verifies that the uploaded image can be changed")
	public void verifyPhotoChange()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Verify that the photo icon contains text \"Change Photo\"");
		Assert.assertTrue(photo.getIconText().contains("Change"));
		Assert.assertTrue(photo.getIconText().contains("Photo"));
		log("Step 3: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 4: Select an image");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		log("Step 5: Verify that 'Upload' button is active");
		Assert.assertTrue(photo.isUploadBtActive(), "Upload button should be active");
	}
	
	@Test(priority=9, description="This test verifies that the image can be deleted and 'Required to apply' text is displayed on Photo edit and preview page")
	public void verifyPhotoDelete()
	{
    	log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on 'Delete' button next to the image");
		photo.clickDelete();
		log("Step 3: Verify that the image is deleted (gray icon is displayed)");
		String src = photo.getImageSrcAfterDel();
		Assert.assertTrue(src.contains(properties.getProperty("src")), "src value is not correct");
		log("Step 4: Verify that 'Required to apply' text is displayed on edit page");
		Assert.assertTrue(photo.getCardText().contains(properties.getProperty("error16")));
		log("Step 5: Click on arrow-back button");
		photo.clickBack();
		log("Step 5: Verify that 'Required to apply' text is displayed on preview page");
		Assert.assertTrue(profilePreview.photoTitle.getText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=10, description="This test verifies that validation error occurs for invalid file format")
	public void verifyPhotoInvalidFile()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Upload a PDF file");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample_Project.pdf");
		photo.clickModalUpload();
		log("Step 4: Verify that validation error corresponds to invalid file format");
		Assert.assertEquals(photo.getAlert(), properties.getProperty("alert6"));
	}
	
	@Test(priority=11, description="This test verifies that validation error occurs when image size is more than 5MB")
	public void verifyPhotoInvalidSize()
	{
		log("Step 1: Click on Photo edit button");
		profilePreview.clickphotoEdit();
		log("Step 2: Click on Upload Photo icon");
		photo.clickUploadPhoto();
		log("Step 3: Upload a sample image having size more than 5MB");
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample-png-image-5mb.png");
		photo.clickModalUpload();
		log("Step 4: Verify that validation error corresponds to file size more than 5MB");
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
