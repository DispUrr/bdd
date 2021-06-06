package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransmissionPage {
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement transmissionButton = $("[data-test-id=action-transfer]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public TransmissionPage() {
        SelenideElement heading = $(byText("Пополнение карты"));
        heading.shouldBe(Condition.visible);
    }

    public void transmission(DataHelper.TransmissionInfo TransmissionInfo, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromInput.setValue(TransmissionInfo.getCard());
        transmissionButton.click();
        new DashBoardPage();
    }

    public void errorTransmission() {
        error.shouldBe(Condition.visible);
    }
}
