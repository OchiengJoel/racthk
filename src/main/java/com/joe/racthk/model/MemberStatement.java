package com.joe.racthk.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class MemberStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Column(name = "expectedContribution" )
    private BigDecimal expectedContribution;

    @Column(name = "amountContributed")
    private BigDecimal amountContributed;

    @Column(name = "balance")
    private BigDecimal balance;




    public MemberStatement() {
    }

    public MemberStatement(Long id, Member member, BigDecimal expectedContribution, BigDecimal amountContributed) {
        this.id = id;
        this.member = member;
        this.expectedContribution = expectedContribution;
        this.amountContributed = amountContributed;
    }


    @Override
    public String toString() {
        return "MemberStatement{" +
                "id=" + id +
                ", member=" + member +
                ", expectedContribution=" + expectedContribution +
                ", amountContributed=" + amountContributed +
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

    public BigDecimal getBalance() {
        return expectedContribution.subtract(amountContributed);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
