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
        val transferAmount = 5000;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) + transferAmount;
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) - transferAmount;
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCardId())
                .transferFrom(transferAmount, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCards() {
        val transferAmount = 5000;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) - transferAmount;
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) + transferAmount;
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getSecondCardId())
                .transferFrom(transferAmount, getFirsCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);

    }

    @Test
    void shouldTransferOverBalanceMoney() {
        val transferAmount = 50000;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerification(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) + transferAmount;
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) - transferAmount;
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCardId())
                .transferFrom(transferAmount, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldLoginWithInvalidUserData() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo());
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

}
