
package tests;

import base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class PlaywrightTestFramework extends BaseTest {
    public static String username;
    public static String accountNumber;
    RegistrationPage registrationPage;
    HomePage homePage;
    LoginPage loginPage;
    FundTransferPage fundTransfer;
    BillPage billPage;

    @BeforeClass
    public static void setupTest() {
        setup();
    }

    @AfterClass
    static void teardownTest() {
        teardown();
    }

    @Test(priority = 1)
    void registration() {
        page.navigate("https://parabank.parasoft.com/");
        registrationPage = new RegistrationPage(page);

        username = registrationPage.registerRandomUser();

    }

    @Test(priority = 2)
    void login() {
        page.navigate("https://parabank.parasoft.com/");
        loginPage = new LoginPage(page);
        loginPage.logout();
        loginPage.login(username, "Password");
    }

    @Test(priority = 3)
    void createSavingAccount() {
        homePage = new HomePage(page);
        accountNumber = homePage.openNewAccount();
    }

    @Test(priority = 4)
    void fundTransfer() {
        fundTransfer = new FundTransferPage(page);
        fundTransfer.transferFund(accountNumber);
    }

    @Test(priority = 5)
    void billPay() {
        billPage = new BillPage(page);
        billPage.billPay(accountNumber);
    }

    @Test(priority = 6)
    void validateTransactionApi() {
        billPage.findTransaction();
    }
}
