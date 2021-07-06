package pages;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement code = $("[data-test-id=code] input");
    public SelenideElement verificationButton = $("[data-test-id=action-verify]");

    public DashboardPage validVerification(DataHelper.VerificationCode verificationCode) {
        code.setValue(verificationCode.getCode());
        verificationButton.click();
        return new DashboardPage();
    }
}
