
package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username, String password) {
        page.fill("input[name=\"username\"]", username);
        page.fill("input[name=\"password\"]", password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In")).click();
        page.waitForTimeout(2000);
        assertThat(page.locator("#showOverview")).containsText("Accounts Overview");
    }

    public void logout() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
    }
}
