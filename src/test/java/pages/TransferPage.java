package pages;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    public SelenideElement transferAmount = $("[data-test-id=amount] input");
    public SelenideElement from = $("[data-test-id=from] input");
    public SelenideElement transferButton = $("[data-test-id=action-transfer]");
    public SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public DashboardPage transferFrom(int amount, DataHelper.Cards cards) {
        $(transferAmount).setValue(Integer.toString(amount));
        $(from).setValue(cards.getNumber());
        $(transferButton).click();
        return new DashboardPage();
    }
}
