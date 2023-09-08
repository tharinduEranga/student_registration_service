package com.skiply.student.registration.common.model;

import java.util.Objects;

public record CardDetails(
        String maskedCardNo,
        CardType cardType
) {
    public CardDetails {
        Objects.requireNonNull(maskedCardNo, "maskedCardNo cannot be empty");
        Objects.requireNonNull(cardType, "cardType cannot be empty");
    }

    public static CardDetails of(String maskedCardNo, CardType cardType) {
        return new CardDetails(maskedCardNo, cardType);
    }

    public static CardDetails of(String maskedCardNo, String cardType) {
        return of(maskedCardNo, CardType.valueOf(cardType));
    }

    public enum CardType {
        VISA, MASTERCARD, AMEX
    }
}
