
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public String openNewAccount() {
        page.waitForTimeout(2000);
        page.click("text=Open New Account");
        page.waitForTimeout(2000);
        page.selectOption("#type", "SAVINGS");
        page.waitForTimeout(2000);
        Locator openNewAccountButton = page.locator("//input[@value='Open New Account']");
        openNewAccountButton.click();
        Locator accId = page.locator("#newAccountId");
        page.waitForTimeout(2000);
        assertThat(page.locator("#openAccountResult")).containsText("Congratulations, your account is now open.");
        return page.textContent("#newAccountId");
    }
}
