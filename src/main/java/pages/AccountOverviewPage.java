
package pages;

import com.microsoft.playwright.Page;

public class AccountOverviewPage {
    private final Page page;

    public AccountOverviewPage(Page page) {
        this.page = page;
    }

    public boolean verifyAccountDetails(String accountNumber) {
        page.click("text=Accounts Overview");
        return page.isVisible("text=" + accountNumber);
    }
}
