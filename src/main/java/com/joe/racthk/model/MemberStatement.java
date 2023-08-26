package com.joe.racthk.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class MemberStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Column(name = "expectedContribution" )
    private BigDecimal expectedContribution = BigDecimal.ZERO; // Set a default value

    @Column(name = "period")
    private String period;

    @Column(name = "amountContributed")
    private BigDecimal amountContributed = BigDecimal.ZERO; // Set a default value

    @Column(name = "paymentPeriods")
    private String paymentPeriods;

    @Column(name = "balance")
    private BigDecimal balance;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "recordingDate")
    private Date recordingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "bankingDate")
    private Date bankingDate;

    @Column(name = "paymentReference")
    private String paymentReference;

    @Column(name = "modeOfPayment")
    private String modeOfPayment;

    @Column(name = "amountPaid")
    private BigDecimal amountPaid;

    @Column(name = "transaction_type")
    private String transactionType;


    public MemberStatement() {
    }

    public MemberStatement(Long id, Member member, BigDecimal expectedContribution, String period, BigDecimal amountContributed, String paymentPeriods, BigDecimal balance, Date recordingDate, Date bankingDate, String paymentReference, String modeOfPayment, BigDecimal amountPaid, String transactionType) {
        this.id = id;
        this.member = member;
        this.expectedContribution = expectedContribution;
        this.period = period;
        this.amountContributed = amountContributed;
        this.paymentPeriods = paymentPeriods;
        this.balance = balance;
        this.recordingDate = recordingDate;
        this.bankingDate = bankingDate;
        this.paymentReference = paymentReference;
        this.modeOfPayment = modeOfPayment;
        this.amountPaid = amountPaid;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "MemberStatement{" +
                "id=" + id +
                ", member=" + member +
                ", expectedContribution=" + expectedContribution +
                ", period=" + period +
                ", amountContributed=" + amountContributed +
                ", paymentPeriods=" + paymentPeriods +
                ", balance=" + balance +
                ", recordingDate=" + recordingDate +
                ", bankingDate=" + bankingDate +
                ", paymentReference='" + paymentReference + '\'' +
                ", modeOfPayment='" + modeOfPayment + '\'' +
                ", amountPaid=" + amountPaid +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getExpectedContribution() {
        return expectedContribution;
    }

    public void setExpectedContribution(BigDecimal expectedContribution) {
        this.expectedContribution = expectedContribution;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getAmountContributed() {
        return amountContributed;
    }

    public void setAmountContributed(BigDecimal amountContributed) {
        this.amountContributed = amountContributed;
    }

    public String getPaymentPeriods() {
        return paymentPeriods;
    }

    public void setPaymentPeriods(String paymentPeriods) {
        this.paymentPeriods = paymentPeriods;
    }

    /*  public BigDecimal getBalance() {
            return expectedContribution.subtract(amountContributed);
        }
    */
    public BigDecimal getBalance() {
        // Ensure expectedContribution and amountContributed are never null
        if (expectedContribution == null) {
            expectedContribution = BigDecimal.ZERO;
        }
        if (amountContributed == null) {
            amountContributed = BigDecimal.ZERO;
        }

        return expectedContribution.subtract(amountContributed);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(Date recordingDate) {
        this.recordingDate = recordingDate;
    }

    public Date getBankingDate() {
        return bankingDate;
    }

    public void setBankingDate(Date bankingDate) {
        this.bankingDate = bankingDate;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
