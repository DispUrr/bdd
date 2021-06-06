package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class TransmissionTest {

    @BeforeEach
    void setUp() {
        val loginPage = new LoginPage();
        open("http://localhost:9999");
        val verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        val verificationCode = DataHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
    }
    // Перевод с первой карты на вторую
    @Test
    void shouldTransferFromFirstToSecond() {
        val dashBoardPage = new DashBoardPage();
        val amount = 1000;
        val currentBalanceOfFirstCard = dashBoardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashBoardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashBoardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);
        val balanceOfFirstCardAfterTransmission = dashBoardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmission = dashBoardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDown(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceUp(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmission);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmission);
    }
    // Перевод со второй карты на первую
    @Test
    void shouldTransferFromSecondToFirst() {
        val dashBoardPage = new DashBoardPage();
        val amount = 1000;
        val currentBalanceOfFirstCard = dashBoardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashBoardPage.getCurrentBalanceOfSecondCard();
        val transmissionRage = dashBoardPage.transmissionToFirstCard();
        transmissionRage.transmission(getSecondCardNumber(), amount);
        val balanceOfFirstCardAfterTransmission = dashBoardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmission = dashBoardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDown(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceUp(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmission);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmission);
    }
    // Перевод нулевой суммы
    @Test
    void shouldTransferZeroAmount() {
        val dashBoardPage = new DashBoardPage();
        val amount = 0;
        val currentBalanceOfFirstCard = dashBoardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashBoardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashBoardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);
        val balanceOfFirstCardAfterTransmit = dashBoardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashBoardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDown(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceUp(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }
    // Перевод суммы, равной текущему балансу
    @Test
    void shouldBeTransferAmountEqualsCurrentBalance(){
        val dashboardPage = new DashBoardPage();
        val amount = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);
        val balanceOfFirstCardAfterTransmit = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashboardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDown(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceUp(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }
    // Перевод, если сумма перевода не укзаана
    @Test
    void shouldTransferIfAmountIsEmpty() {
        val dashboardPage = new DashBoardPage();
        String amount = "";
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), Integer.parseInt(amount));
        transmissionPage.errorTransmission();
    }
    // Перевод, если перевод осуществляется с пустой карты (не указан номер карты)
    @Test
    void shouldBeErrorIfFromInputCardIsEmpty() {
        val dashboardPage = new DashBoardPage();
        val amount = 1000;
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getEmptyCardNumber(), amount);
        transmissionPage.errorTransmission();
    }
    // Перевод с некорректного номера карты
    @Test
    void shouldBeErrorIfIncorrectCard() {
        val dashboardPage = new DashBoardPage();
        val amount = 1000;
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getNotCorrectCardNumber(), amount);
        transmissionPage.errorTransmission();
    }
    // Перевод суммы большей текущего баланса
    @Test
    void shouldBeErrorIfAmountMoreThanCurrentBalance() {
        val dashboardPage = new DashBoardPage();
        val amount = dashboardPage.getCurrentBalanceOfSecondCard() * 2;
        val transmissionPage = dashboardPage.transmissionToFirstCard();
        transmissionPage.transmission(getSecondCardNumber(), amount);
        transmissionPage.errorTransmission();
    }
}
