package com.joe.racthk.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class MemberStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Column(name = "expectedContribution" )
    private BigDecimal expectedContribution = BigDecimal.ZERO; // Set a default value

    @Column(name = "amountContributed")
    private BigDecimal amountContributed = BigDecimal.ZERO; // Set a default value

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "recordingDate")
    private LocalDate recordingDate;

    @Column(name = "bankingDate")
    private LocalDate bankingDate;

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

    public MemberStatement(Long id, Member member, BigDecimal expectedContribution, BigDecimal amountContributed, BigDecimal balance, LocalDate recordingDate, LocalDate bankingDate, String paymentReference, String modeOfPayment, BigDecimal amountPaid, String transactionType) {
        this.id = id;
        this.member = member;
        this.expectedContribution = expectedContribution;
        this.amountContributed = amountContributed;
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
                ", amountContributed=" + amountContributed +
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

    public BigDecimal getAmountContributed() {
        return amountContributed;
    }

    public void setAmountContributed(BigDecimal amountContributed) {
        this.amountContributed = amountContributed;
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

    public LocalDate getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(LocalDate recordingDate) {
        this.recordingDate = recordingDate;
    }

    public LocalDate getBankingDate() {
        return bankingDate;
    }

    public void setBankingDate(LocalDate bankingDate) {
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
