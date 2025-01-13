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
	}
	
	public static String getDescription(String methodName)
	{
		return map.getOrDefault(methodName, "No detailed description provided");
	}
}
