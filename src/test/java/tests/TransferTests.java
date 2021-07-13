package tests;

import com.codeborne.selenide.Condition;
import data.DataHelper;
import groovyjarjarpicocli.CommandLine;
import lombok.Value;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.TransferPage;
import pages.VerificationPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTests {
    @BeforeEach
    void shouldOpenTransferPage() {
        open("http://localhost:9999/");

    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCards() {
        val transferAmount = 500;
        val transferPage = new TransferPage();
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCard()) + transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getFirstCard())) {
            transferPage.notificationError();
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCard()) - transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getSecondCard())) {
            transferPage.notificationError();
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCard());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCard());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCards() {
        val transferAmount = 500;
        val transferPage = new TransferPage();
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCard()) - transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getFirstCard())) {
            transferPage.notificationError();
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCard()) + transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getSecondCard())) {
            transferPage.notificationError();
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getSecondCard())
                .transferFrom(transferAmount, getFirstCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCard());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCard());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);

    }

    @Test
    void shouldNotTransferOverBalanceMoney() {
        val transferAmount = 50000;
        val transferPage = new TransferPage();
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCard()) + transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getFirstCard())) {
            transferPage.notificationError();
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCard()) - transferAmount;
        if (transferAmount > dashboardPageBeforeTransfer.getCardBalance(getSecondCard())) {
            transferPage.notificationError();
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCard());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCard());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldNotLoginWithInvalidUserData() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo());
    }

}
