package com.zenken.freshers.pages.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditPhotoPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditPhotoPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[.='Photo']")
	WebElement headline;
	
	@FindBy(css="[class*='u-w-90']")
	WebElement photoModal;
	
	@FindBy(css="[role=dialog]")
	WebElement popup;
	
	@FindBy(css="[class*=cancel]")
	WebElement cancelBt;
	
	@FindBy(css="[type=file]")
	WebElement fileInput;
	
	@FindBy(css="[class*='crop-box']")
	WebElement cropBox;
	
	@FindBy(css="[class*='line-e']")
	WebElement cropLine;
	
	@FindBy(id="profilePhotoSubmit")
	WebElement uploadBt;
	
	@FindBy(css="[alt*='Photo']")
	WebElement photoAlt;
	
	@FindBy(css=".delete-button")
	WebElement deleteBt;
	
	@FindBy(css="[class*='d-def']")
	WebElement card;
	
	@FindBy(css="[class=u-c-red]")
	WebElement reqError;
	
	@FindBy(css="[class*='arrow']")
	WebElement back;
	
	@FindBy(css="[class*='top-50']")
	WebElement icon;
	
	public String getPhotoHeadline()
	{
		return headline.getText();
	}
	
	public void clickUploadPhoto()
	{
		photoModal.click();
	}
	
	public boolean isPopUpDisplayed()
	{
		return popup.isDisplayed();
	}
	
	public void clickModalCancel()
	{
		cancelBt.click();
	}
	
	public void uploadPhoto(String filePath)
	{
		fileInput.sendKeys(filePath);
	}
	
	public String getCropBoxStyle()
	{
		return cropBox.getAttribute("style");
	}
	
	public void resizePhoto()
	{
		Actions actions = new Actions(driver);
		actions.dragAndDropBy(cropLine, 30, 0).perform();
	}
	
	public void dragPhoto()
	{
		Actions actions = new Actions(driver);
		actions.dragAndDropBy(cropBox, 50, 50).perform();
	}
	
	public void clickModalUpload()
	{
		uploadBt.click();
	}
	
	public String getImageSrc()
	{
		return photoAlt.getAttribute("src");
	}
	
	public void clickDelete()
	{
		deleteBt.click();
	}
	
	public String getCardText()
	{
		return card.getText();
	}
	
	public String getImageSrcAfterDel()
	{
		waitUntilElementAppears(reqError);
		return photoAlt.getAttribute("src");
	}
	
	public void clickBack()
	{
		clickByJavaScript(back);
	}
	
	public String getPopUpText()
	{
		return popup.getText();
	}
	
	public String getIconText()
	{
		return icon.getText();
	}
	
	public boolean isUploadBtActive()
	{
		return uploadBt.isEnabled();
	}

}
