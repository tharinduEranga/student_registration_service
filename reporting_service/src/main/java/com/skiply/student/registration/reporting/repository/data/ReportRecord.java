package com.skiply.student.registration.reporting.repository.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "report")
public class ReportRecord {

    @Id
    @Column(name = "id", length = 200)
    private String id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "student_registration_id", length = 200, nullable = false)
    private String studentRegistrationId;

    @Column(name = "student_name", length = 200)
    private String studentName;

    @Column(name = "payment_id", length = 255)
    private String paymentId;

    @Column(name = "datetime", nullable = false)
    private OffsetDateTime datetime;

    @Column(name = "masked_card_no", length = 255)
    private String maskedCardNo;

    @Column(name = "card_type", length = 25)
    private String cardType;

    public ReportRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStudentRegistrationId() {
        return studentRegistrationId;
    }

    public void setStudentRegistrationId(String studentRegistrationId) {
        this.studentRegistrationId = studentRegistrationId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public OffsetDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(OffsetDateTime datetime) {
        this.datetime = datetime;
    }

    public String getMaskedCardNo() {
        return maskedCardNo;
    }

    public void setMaskedCardNo(String maskedCardNo) {
        this.maskedCardNo = maskedCardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ReportRecord reportRecord = new ReportRecord();

        public Builder id(String id) {
            reportRecord.id = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            reportRecord.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            reportRecord.currency = currency;
            return this;
        }

        public Builder studentRegistrationId(String studentRegistrationId) {
            reportRecord.studentRegistrationId = studentRegistrationId;
            return this;
        }

        public Builder studentName(String studentName) {
            reportRecord.studentName = studentName;
            return this;
        }

        public Builder paymentId(String paymentId) {
            reportRecord.paymentId = paymentId;
            return this;
        }

        public Builder datetime(OffsetDateTime datetime) {
            reportRecord.datetime = datetime;
            return this;
        }

        public Builder maskedCardNo(String maskedCardNo) {
            reportRecord.maskedCardNo = maskedCardNo;
            return this;
        }

        public Builder cardType(String cardType) {
            reportRecord.cardType = cardType;
            return this;
        }

        public ReportRecord build() {
            return reportRecord;
        }
    }
}
