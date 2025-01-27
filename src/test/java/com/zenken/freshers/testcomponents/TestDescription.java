package com.zenken.freshers.testcomponents;

import java.util.HashMap;
import java.util.Map;

public class TestDescription {

	private static Map<String, String> map = new HashMap<>();
	
	static {
		map.put("verifyJobsPageTitle", "Step 1: Verify that Jobs page title name is correct");
		map.put("verifyJobsUrl", "Step 1: Verify that the URL loaded is correct");
		map.put("verifyJobDetailAnchor", "Step 1: Click on \"Job Detail\" anchor link\nStep 2: Verify that the URL contains \"#JobDetail\" parameter\nStep 3: Verify that selected anchor's class attribute contains \"current\"");
		map.put("verifyCondtionsAnchor", "Step 1: Click on \"Condtions\" anchor link\nStep 2: Verify that the URL contains \"#Condtions\" parameter\nStep 3: Verify that selected anchor's class attribute contains \"current\"");
		map.put("verifyCompanyOverviewAnchor", "Step 1: Click on \"Company Overview\" anchor link\nStep 2: Verify that the URL contains \"#CompanyOverview\" parameter\nStep 3: Verify that selected anchor's class attribute contains \"current\"");
		map.put("verifyJobsWarningText", "Step 1: Verify that the text below the page is correct");
		map.put("verifyJobsValues", "Step 1: Login into Admin account\nStep 2: Go to Jobs edit page (event: 116, job: 295)\nStep3: Update fields with the values from current dataset and click 'Save'\nStep 4: Verify that all updated field values are displayed in expected format on Jobs page");
		map.put("verifyJobsCompaniesUrl", "Step 1: Click the link for the company\nStep 2: Verify that the URL loaded corresponds to Companies page");
		map.put("verifyComPageTitle", "Step 1: Verify that Companies page title name is correct");
		map.put("verifyComUrl", "Step 1: Verify that the Companies URL loaded is correct");
		map.put("verifyComValues", "Step 1: Login into Admin account\nStep 2: Go to Companies manuscript edit page (Company ID: 57)\nStep3: Update fields with the values from current dataset and click 'Save'\nStep 4: Verify that all updated field values are displayed in expected format on Companies page");
		map.put("verifyCompWebSite", "Step 1: Click on the company's website link\nStep 2: Verify that expected link is opened in a separate tab");
		map.put("verifyComImages", "Step 1: Login into Admin account\nStep 2: Go to Companies manuscript image page (Company ID: 57)\nStep3: Upload company logo and header images from current dataset and click 'Save'\nStep 4: Verify that company logo and header images are displayed as expected on Companies page");
		map.put("verifyComAnchorLink", "Step 1: Click on all anchor links\nStep 2: Verify that each URL contains respective anchor link's href value");
		map.put("verifyApplyAreaDisplay", "Step 1: Access 求人詳細 page with hash ID: 9df004f4\nStep 2: Verify that Apply frame is not displayed");
		map.put("verifyApplyAreaText", "Step 1: Login with the 人材 account\nStep 2: Access 求人詳細 page with hash ID: 9df004f4\nStep 3: Verify that the text \"This position is not currently accepting applications\" is displayed on the apply frame");
		map.put("verifyApplicationDeadline", "Step 1: Login into Admin account\nStep 2: Go to イベント編集 page with event ID: 108\nStep 3: Edit end date for 募集期間 and click 'Save'\nStep 4: Verify that the application deadline displayed is in correct format on 求人詳細 page");
		map.put("verifyApplyNotAllowed1", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Verify that user is redirected to 求人詳細 page");
		map.put("verifyProfileCompletePopup", "Step 1: Access 求人詳細 page with hash ID: b5dbad9e\nStep 2: Click on 'Apply' button\nStep 3: Verify that expected popup is displayed");
		map.put("verifyProfilePageURL", "Step 1: Access 求人詳細 page with hash ID: b5dbad9e\nStep 2: Click on 'Apply' button\nStep 3: Click on 'Edit Profile' button on the popup\nStep 4: Verify that user is redirected to profile preview page");
		map.put("verifyFinalConfPopup1", "Step 1: Complete the profile by providing appropriate inputs for 'required to apply' fields\nStep 2: Access 求人詳細 page with hash ID: b5dbad9e\nStep 3: 'Click on 'Apply' button\nStep 4: Verify that expected popup is displayed");
		map.put("verifyJobApplication1", "Step 1: Access 求人詳細 page with hash ID: b5dbad9e\nStep 2: Click on 'Apply' button\nStep 3: Click on 'Apply' button on the popup\nStep 4: Verify that user is redirected to 求人詳細 page, expected snackbar is displayed, and the text \"Applied\" is displayed on the apply frame");
		map.put("verifyApplyMailReception1", "Step 1: Verify that user receives an email for successful job application\nStep 2: Verify that the company name, job title written in the mail are correct, and that the link navigates user to 求人詳細 page");
		map.put("verifyApplyNotAllowed2", "Step 1: Access 応募フォーム page with hash ID: fc777807\nStep 2: Verify that user is redirected to 求人詳細 page");
		map.put("verifyApplyFormURL", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Verify that the URL loaded is correct and the headline \"Application Form\" is displayed");
		map.put("verifyApplyFormText", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Verify that 定型文 is displayed as expected\nStep 3: Verify that 自由翻訳 is displayed as set in CS");
		map.put("verifyApplyFormCancel", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Click on 'Cancel' button\nStep 3: Verify that user is redirected to 求人詳細 page");
		map.put("verifyApplyFormMandatoryFields", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Provide valid inputs for all questions except question 1\nStep 3: Verify that \"This field is required\" text is displayed for the question\nStep 4: Repeat steps 2 and 3 for the rest of the questions");
		map.put("verifyApplyFormCharExceed", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Enter 5001 characters in question 1's text box\nStep 3: Verify that character limit exceed text is displayed for the question\nStep 4: Repeat steps 2 and 3 for question 4");
		map.put("verifyFinalConfPopup2", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Provide valid inputs for all questions\nStep 3: 'Click on 'Apply' button\nStep 4: Verify that final confirmation popup is displayed");
		map.put("verifyJobApplication2", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Provide valid inputs for all questions\nStep 3: Click on 'Apply' button on the popup\nStep 4: Verify that user is redirected to 求人詳細 page, expected snackbar is displayed, and the text \"Applied\" is displayed on the apply frame");
		map.put("verifyApplyMailReception2", "Step 1: Verify that user receives an email for successful job application\nStep 2: Verify that the company name, job title written in the mail are correct, and that the link navigates user to 求人詳細 page");
		map.put("verifyApplyNotAllowed3", "Step 1: Access 応募フォーム page with hash ID: 2a2c6d4e\nStep 2: Verify that user is redirected to 求人詳細 page");
	}
	
	public static String getDescription(String methodName)
	{
		return map.getOrDefault(methodName, "No detailed description provided");
	}
}
