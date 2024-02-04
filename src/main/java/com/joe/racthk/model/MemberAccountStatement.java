package com.joe.racthk.model;

import java.math.BigDecimal;

public class MemberAccountStatement {

    private Member member;
    private BigDecimal totalExpectedContribution;
    private BigDecimal totalAmountContributed;
    private BigDecimal balance;


    public MemberAccountStatement() {
    }

    @Override
    public String toString() {
        return "MemberAccountStatement{" +
                "member=" + member +
                ", totalExpectedContribution=" + totalExpectedContribution +
                ", totalAmountContributed=" + totalAmountContributed +
                ", balance=" + balance +
                '}';
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getTotalExpectedContribution() {
        return totalExpectedContribution;
    }

    public void setTotalExpectedContribution(BigDecimal totalExpectedContribution) {
        this.totalExpectedContribution = totalExpectedContribution;
    }

    public BigDecimal getTotalAmountContributed() {
        return totalAmountContributed;
    }

    public void setTotalAmountContributed(BigDecimal totalAmountContributed) {
        this.totalAmountContributed = totalAmountContributed;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
