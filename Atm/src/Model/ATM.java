package Model;

import java.math.BigDecimal;
import java.sql.Time;

public class ATM implements Model{
    private String name;
    private Time opening, closing;
    private BigDecimal amount;

    /**
     * Constructor
     * @param name string
     * @param opening time
     * @param closing time
     */
    public ATM(String name, Time opening, Time closing) {
        this.name = name;
        this.opening = opening;
        this.closing = closing;
        amount=BigDecimal.valueOf(5000);
    }

    /**
     * Get the name of ATM
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of ATM
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the opening time
     * @return  time
     */
    public Time getOpening() {
        return opening;
    }

    /**
     * Set the opening time
     * @param opening   time
     */
    public void setOpening(Time opening) {
        this.opening = opening;
    }
    /**
     * Get the closing time
     * @return  time
     */
    public Time getClosing() {
        return closing;
    }
    /**
     * Set the closing time
     * @param closing   time
     */
    public void setClosing(Time closing) {
        this.closing = closing;
    }

    /**
     * Return the amount of money that we have
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Subtract the amount of money that we want to obtain fizicaly, from the card
     * @param amount BigDecimal
     */
    public void withrowAmount(BigDecimal amount) {
        this.amount=this.amount.subtract(amount);
    }

    /**
     * How much can we withdrow in a moment of time
     * @return BigDecimal
     */
    public BigDecimal maxWithrowAmount(){
        return amount;
    }

    /**
     * Show the name only
     * @return String
     */
    @Override
    public String toString() {
        return name;
    }
}
