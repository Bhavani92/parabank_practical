
package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Random;

public class RegistrationPage {
    private final Page page;

    public RegistrationPage(Page page) {
        this.page = page;
    }

    public String registerRandomUser() {
        String username = "user" + new Random().nextInt(10000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Register")).click();
        page.fill("[id='customer.firstName']", "John");
        page.fill("[id='customer.lastName']", "Doe");
        page.fill("[id='customer.address.street']", "123 Elm Street");
        page.fill("[id='customer.address.city']", "Springfield");
        page.fill("[id='customer.address.state']", "IL");
        page.fill("[id='customer.address.zipCode']", "62701");
        page.fill("[id='customer.phoneNumber']", "1234567890");
        page.fill("[id='customer.ssn']", "123-45-6789");
        page.fill("[id='customer.username']", username);
        page.fill("[id='customer.password']", "Password");
        page.fill("#repeatedPassword", "Password");
        page.click("input[value='Register']");
        return username;
    }

}
