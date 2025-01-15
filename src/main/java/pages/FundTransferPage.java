package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FundTransferPage {
    private final Page page;

    public FundTransferPage(Page page) {
        this.page = page;
    }

    public void transferFund(String accNumber) {
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Transfer Funds")).click();
        page.waitForTimeout(2000);
        page.locator("#amount").click();
        page.locator("#amount").fill("50");
        page.waitForTimeout(2000);
        page.locator("#fromAccountId").selectOption(accNumber);
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Transfer")).click();
        page.waitForTimeout(2000);
        assertThat(page.locator("#showResult")).containsText("Transfer Complete!");
    }

}
