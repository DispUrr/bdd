package ru.netology.data;


import lombok.AllArgsConstructor;
import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    @AllArgsConstructor
    public static class TransmissionInfo {
        String card;
    }
    // Номер первой карты
    public static TransmissionInfo getFirstCardNumber() {
        return new TransmissionInfo("5559000000000001");
    }
    // Номер второй карты
    public static TransmissionInfo getSecondCardNumber() {
        return new TransmissionInfo("5559000000000002");
    }
    // Номер карты отсутствует
    public static TransmissionInfo getEmptyCardNumber() {
        return new TransmissionInfo("");
    }
    // Некрректный номер карты
    public static TransmissionInfo getNotCorrectCardNumber() {
        return new TransmissionInfo("5559000000000003");
    }
    // Баланс при пополнении
    public static int getExpectedBalanceIfBalanceUp(int balance, int amount) {
        return balance + amount;
    }
    // Баланс при переводе
    public static int getExpectedBalanceIfBalanceDown(int balance, int amount) {
        return balance - amount;
    }
}