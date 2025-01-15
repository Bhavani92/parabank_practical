package pages;


import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.options.RequestOptions;
import com.microsoft.playwright.APIResponse;

import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BillPage {
    private final Page page;
    private final APIRequestContext apiRequestContext;

    public BillPage(Page page) {
        this.page = page;
        this.apiRequestContext = page.context().request();
    }

    public void billPay(String accNum) {
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bill Pay")).click();

        page.locator("input[name=\"payee\\.name\"]").fill("Bhavani");

        page.locator("input[name=\"payee\\.address\\.street\"]").fill("Address");

        page.locator("input[name=\"payee\\.address\\.city\"]").fill("Ciy");

        page.locator("input[name=\"payee\\.address\\.state\"]").fill("State");

        page.locator("input[name=\"payee\\.address\\.zipCode\"]").fill("21382");

        page.locator("input[name=\"payee\\.phoneNumber\"]").fill("10293843");

        page.locator("input[name=\"payee\\.accountNumber\"]").fill("1502");

        page.locator("input[name=\"payee\\.accountNumber\"]").fill(accNum);
        page.waitForTimeout(2000);
        page.locator("input[name=\"verifyAccount\"]").fill(accNum);
        page.waitForTimeout(2000);
        page.locator("input[name=\"amount\"]").fill("75");
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send Payment")).click();
        page.waitForTimeout(2000);
        assertThat(page.locator("#billpayResult")).containsText("Bill Payment Complete");
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Accounts Overview")).click();
        page.waitForTimeout(2000);
        assertThat(page.locator("tbody")).containsText("$440.50");
    }

    public void findTransaction() {
        // Define the headers for the API call
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
//        headers.put("Cookie", "JSESSIONID=51CCDAA1DC22FBFAC36A75EB8499E3A9"); // Update session ID dynamically if needed
        headers.put("Referer", "https://parabank.parasoft.com/parabank/findtrans.htm");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");

        // Define the API endpoint
        String url = "https://parabank.parasoft.com/parabank/services_proxy/bank/accounts/14565/transactions/amount/75?timeout=30000";

        // Create RequestOptions object
        RequestOptions requestOptions = RequestOptions.create();

        // Iterate over headers map and set each header
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestOptions.setHeader(entry.getKey(), entry.getValue());
        }

        // Perform the GET request
        APIResponse response = apiRequestContext.get(url, requestOptions);

        // Validate the response
        int statusCode = response.status();
        String responseBody = response.text();

        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Check if the response body is empty (this could indicate no matching transactions)
        if (responseBody.trim().isEmpty() || responseBody.equals("[]")) {
            System.out.println("No transactions found. Response body is empty or contains an empty array.");
            return;  // Early exit if no transactions found
        }

        // Add assertions for the response
        if (statusCode == 200) {
            // Look for the transaction and validate the amount numerically
            if (responseBody.contains("\"amount\": 75.00")) {
                System.out.println("API response validated successfully.");
            } else {
                // If you want to check the exact amount as a float:
                if (responseBody.contains("\"amount\":") && responseBody.contains("75.00")) {
                    System.out.println("API response validated successfully.");
                } else {
                    throw new AssertionError("Amount validation failed! Expected amount not found in response.");
                }
            }
        } else {
            throw new AssertionError("API request failed with status code: " + statusCode);
        }
    }
}
