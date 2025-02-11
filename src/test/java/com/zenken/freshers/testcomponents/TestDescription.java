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
		map.put("verifyComTitle", "Step 1: Verify that Company's Login page title name is correct\nStep 2: Verify that 見出し text is \"企業ログイン\"");
		map.put("verifyComPasswordSet", "Step 1: Login into CS account\nStep 2: Go to 企業アカウント追加 page (Company ID: 98)\nStep 3: Create a company user\nStep 4: Login with the company user with the email and initial password provided by CS\nStep 5: Verify that the URL loaded is for 企業初期パスワード page\nStep 6: Verify that the headline text is \"新しいパスワードを設定\"");
		map.put("verifyComBlankPassword", "Step 1: Leave password field blank\nStep 2: Click on 'パスワードを変更' button\nStep 3: Verify that expected validation error message is shown");
		map.put("verifyComInvalidPasswords", "Step 1: Enter invalid passwords (8文字なし・数字なし・大文字なし・記号なし)\nStep 2: Click on 'パスワードを変更' button after entering each password\nStep 3: Verify that expected validation error messages are shown");
		map.put("verifyComPasswordChange", "Step 1: Enter password \"Password_1\"\nStep 2: Click on 'パスワードを変更' button\nStep 3: Verify that headline text is \"パスワードの変更が完了しました。\"\nStep 4: Click on \"ログイン\" button\nStep 5: Verify that user is redirected to Company Login page");
		map.put("verifyAdminPassTextChange", "Step 1: Login into CS account\nStep 2: Go to 企業の詳細 page (Company ID: 98)\nStep 3: Verify that password text has been changed");
		map.put("verifyComLogin", "Step 1: Login into 企業 account with the updated password\nStep 2: Click on 'ログイン' button\nStep 3: Verify that user is redirected to イベント一覧 page\nStep 4: Verify that headline text is \"イベント一覧\"");
		map.put("verifyComLogout", "Step 1: Click on \"ログアウト\" button\nStep 2: Verify that user is redirected to 企業ログイン page");
		map.put("verifyComForgotPasswordLink", "Step 1: Click on 'パスワード再設定' link\nStep 2: Verify that user is redirected to Forgot Password page\nStep 3: Verify that headline text is correct");
		map.put("verifyComInvalidEmail2", "Step 1: Click on 'パスワード再設定' link\nStep 2: Enter an invalid email \"prasanna.inamdar+zenken.co.jp\"\nStep 3: Click on 'メールを送信' button\nStep 4: Verify that expected validation error message is shown");
		map.put("verifyComBlankEmail", "Step 1: Click on 'パスワード再設定' link\nStep 2: Leave 'メールアドレス' field blank\nStep 3: Click on 'メールを送信' button\nStep 4: Verify that expected validation error message is shown");
		map.put("verifyComInvalidEmail3", "Step 1: Click on 'パスワード再設定' link\nStep 2: Enter an email \"sample123@orkut.com\"\nStep 3: Click on 'メールを送信' button\nStep 4: Verify that expected alert message is shown");
		map.put("verifyComMailSend", "Step 1: Click on 'パスワード再設定' link\nStep 2: Enter email ID provided by CS\nStep 3: Click on 'メールを送信' button\nStep 4: Verify that expected alert message is shown");
		map.put("verifyComMailSendAgain", "Step 1: Click on 'パスワード再設定' link\nStep 2: Enter email ID provided by CS\nStep 3: Click on 'メールを送信' button within one minute\nStep 4: Verify that expected alert message is shown");
		map.put("verifyComInvalidEmail", "Step 1: Enter email \"prasanna.inamdar@zenken.co.\"\nStep 2: Enter password \"Password_1\"\nStep 3: Click on 'ログイン' button\nStep 4: Verify that validation error message for an invalid email is displayed");
		map.put("verifyComBlankEmailAndPass", "Step 1: Leave 'メールアドレス' field blank\nStep 2: Enter password \"Password_1\"\nStep 3: Click on 'ログイン' button\nStep 4: Verify that expected validation error message is shown\nStep 5: Enter email ID provided by CS\nStep 6: Leave 'パスワード' field blank\nStep 7: Click on 'ログイン' button\nStep 8: Verify that expected validation error message is shown");
		map.put("verifyComIncorrectPass", "Step 1: Enter email ID provided by CS\nStep 2: Enter incorrect password \"password_1#\"\nStep 3: Click on 'ログイン' button\nStep 4: Verify that expected validation error message is displayed");
		map.put("verifyCsvDlLink", "Step 1: Verify that the text of the link to download the CSV file is \"応募者一覧 CSVダウンロード\"");
		map.put("verifyCsvDlPopup", "Step 1: Click CSV download link\nStep 2: Verify that the title on the popup is correct");
		map.put("verifyCsvDl", "Step 1: Login into CS account\nStep 2: Update 最終結果 of the candidate (ID: 15, event ID: 10) from 〇 to △ or from △ to 〇\nStep 3: Login into 人材 account with the user \"applied_user_13@example.com\"\nStep 4: Update profile information (Basic info, skills, internships, certifications, projects)\nStep 5: Login into 企業 account (Company ID: 94)\nStep 6: Download CSV file (ID: bbcfc68b)\nStep 7: Verify that expected alert is displayed and the file is downloaded in zip format\nStep 8: Extract the files\nStep 9: Verify that file names (candidates_求人名.csv) are correct");
		map.put("verifyCsvHeader1", "Step 1: Open CSV files\nStep 2: Verify that the field names in CSV files are correct");
		map.put("verifyCsvNoApplicant", "Step 1: Verify that CSV files have no data for 応募者");
		map.put("verifyCsvHeader2", "Step 1: Login into 企業 account (Company ID: 11)\nStep 2: Download the CSV file (event ID: 878fca2e)\nStep 3: Verify that the field names in CSV files are correct (最終結果 is included)");
		map.put("verifyCsvValues", "Step 1: Open CSV files\nStep 2: Verify that the values are updated in all CSV files as expected (応募者ID: 8)");
		map.put("verifyBasicInfoEditUrl", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that URL and page headline are correct");
		map.put("verifyBasicInfoTitleName", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that title name is correct");
		map.put("verifyBasicInfoSavedData", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that the saved data for 'required to save' fields is displayed accordingly");
		map.put("verifyBasicInfoPlaceholders", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that placeholders for all text boxes are correct");
		map.put("verifyTextBoxInactiveLastName", "Step 1: Click on Basic Information edit icon\nStep 2: Click on checkbox for 'Last Name' field\nStep 3: Verify that text box turns inactive");
		map.put("verifyBasicInfoNotSureStates", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that upon selecting 'Not sure' option from 'How long would you like to work in Japan?' field, the corresponding text box turns active");
		map.put("verifyOtherBachelorStates", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that upon selecting 'Others' option from 'BE/B.TECH BRANCH' field, the corresponding text box turns active");
		map.put("verifyOtherMasterStates", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that upon selecting 'Others' option from 'MCA/M.TECH BRANCH' field, the corresponding text box turns active");
		map.put("verifyProceedOtherStates", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that upon selecting 'Other' option from 'When do you plan to proceed?' field, the corresponding text box turns active");
		map.put("verifyBacklogCountStates", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that if 'Yes' is selected for 'Do you have any active backlogs?' field, the corresponding dropdown turns active");
		map.put("verifyBasicInfoProfileUpdate", "Step 1: Click on Basic Information edit icon\nStep 2: Fill in information for the 'required to save' fields\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection");
		map.put("verifyBasicInfoCancel", "Step 1: Click on Basic Information edit icon\nStep 2: Click on 'Cancel' button\nStep 3: Verify: Section display, Tab selection on preview page");
		map.put("verifyBasicInfoFirstNameValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"Walter\" in 'First Name' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'First Name' displayed is correct");
		map.put("verifyBasicInfoMiddleNameValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"Hartwell\" in 'Middle Name' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Middle Name' displayed is correct");
		map.put("verifyBasicInfoLastNameValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"White\" in 'Middle Name' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Last Name' displayed is correct");
		map.put("verifyBasicInfoEmail1Value", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"heythere@example.com\" in 'E-mail Address 1 (Private E-mail)' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Email address 1' displayed is correct");
		map.put("verifyBasicInfoEmail2Value", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"heythere2@example.com\" in 'E-mail Address 2 (College E-mail)' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Email address 2' displayed is correct");
		map.put("verifyBasicInfoPhoneValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"1234-567-890\" in 'Phone No. (Mobile)' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Phone No. (Mobile)' displayed is correct");
		map.put("verifyBasicInfoAddressValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"15 Yemen Road, Yemen!\" in 'Address' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Address' displayed is correct");
		map.put("verifyBasicInfoCityValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"Guadalajara, Mexico\" in 'City/District' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'City/District' displayed is correct");
		map.put("verifyBasicInfoStateValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"Texas\" in 'State' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'State' displayed is correct");
		map.put("verifyBasicInfoCountryValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"British Virgin Islands\" in 'Country' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Country' displayed is correct");
		map.put("verifyBasicInfoPinCodeValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter \"36976\" in 'Pin Code' text box\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Pin Code' displayed is correct");
		map.put("verifyBasicInfoDobValue", "Step 1: Click on Basic Information edit icon\nStep 2: Select \"31 August, 2014\" for 'Date of Birth' dropdowns\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Date of Birth' displayed is correct");
		map.put("verifyBasicInfoSexValue", "Step 1: Click on Basic Information edit icon\nStep 2: Select 'Female' option for 'Sex' field\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Sex' displayed is correct");
		map.put("verifyBasicInfoNationalityValue", "Step 1: Click on Basic Information edit icon\nStep 2: Select 'Dominican (Dominican Republic)' option for 'Nationality' field\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'Nationality' displayed is correct");
		map.put("verifyTimeInJapanValue", "Step 1: Click on Basic Information edit icon\nStep 2: Select \"10+ years\" for the applicable dropdown\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for the applicable field displayed is correct");
		map.put("verifyTimeNotSureValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter description in the applicable textbox\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for the applicable field displayed is correct");
		map.put("verifyHobbyValue", "Step 1: Click on Basic Information edit icon\nStep 2: Enter description for hobby in the applicable textbox\nStep 3: Click on 'Save' button\nStep 4: Verify: Snackbar, Section display, Tab selection\nStep 5: Verify that the value for 'What is your hobby?' displayed is correct");
		map.put("verifyFirstNameRequired", "Step 1: Click on Basic Information edit icon\nStep 2: Clear existing text from the text box for 'First Name' and click on 'Save' button\nStep 3: Verify that validation error corresponds to 'Required to save' for first name field\nStep 4: Verify that 'Required to apply' text is displayed for the field");
		map.put("verifyLastNameRequired", "Step 1: Click on Basic Information edit icon\nStep 2: Clear existing text from the text box for 'Last Name' and click on 'Save' button\nStep 3: Verify that validation error corresponds to 'Required to save' for last name field\nStep 4: Verify that 'Required to apply' text is displayed for the field");
		map.put("verifyBasicInfoEmailRequired", "Step 1: Click on Basic Information edit icon\nStep 2: Clear existing text from the text box for 'E-mail Address 1 (Private E-mail)' and click on 'Save' button\nStep 3: Verify that validation error corresponds to 'Required to save' for email address 1 field\nStep 4: Verify that 'Required to apply' text is displayed for the field");
		map.put("verifyBasicInfoCollegeRequired", "Step 1: Click on Basic Information edit icon\nStep 2: Clear existing text from the text box for 'College' and click on 'Save' button\nStep 3: Verify that validation error corresponds to 'Required to save' for College field\nStep 4: Verify that 'Required to apply' text is displayed for the field");
		map.put("verifyBasicInfoUsnRequired", "Step 1: Click on Basic Information edit icon\nStep 2: Clear existing text from the text box for 'USN / Registration Number' and click on 'Save' button\nStep 3: Verify that validation error corresponds to 'Required to save' for USN / Registration Number field\nStep 4: Verify that 'Required to apply' text is displayed for the field");
		map.put("verifyRequiredToApplyTexts", "Step 1: Click on Basic Information edit icon\nStep 2: Verify that 'Required to apply' text is displayed for the fields which are not 'Required to save'");
	}
	
	public static String getDescription(String methodName)
	{
		return map.getOrDefault(methodName, "No detailed description provided");
	}
}
