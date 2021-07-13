package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    public SelenideElement transferAmount = $("[data-test-id=amount] input");
    public SelenideElement from = $("[data-test-id=from] input");
    public SelenideElement transferButton = $("[data-test-id=action-transfer]");
    public SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    public SelenideElement wrongAmount = $("[data-test-id=error-notification]");


    public DashboardPage transferFrom(int amount, DataHelper.CardInfo cardInfo) {
        $(transferAmount).setValue(Integer.toString(amount));
        $(from).setValue(cardInfo.getNumber());
        $(transferButton).click();
        return new DashboardPage();
    }

    public void notificationError() {
        wrongAmount.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Превышен лимит доступных средств"));
    }


}
