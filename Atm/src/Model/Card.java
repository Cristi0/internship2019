package Model;

import Except.CardException;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Currency;

public class Card implements Model {

    private CardType type;
    private Double fee;
    private BigDecimal windrowLimit;
    private LocalDateTime expirationDate;
    private BigDecimal avaiable;
    /**
     * Constructor
     * @param type CardType
     * @param fee double
     * @param windrowLimit Currency
     * @param expirationDate Date
     * @param avaiable Currency
     */
    public Card(CardType type, Double fee, BigDecimal windrowLimit, LocalDateTime expirationDate, BigDecimal avaiable) {
        this.type = type;
        this.fee = fee;
        this.windrowLimit = windrowLimit;
        this.expirationDate = expirationDate;
        this.avaiable = avaiable;
    }

    /**
     * Get the type of Card : GOLD, SILVER, PLATINUM
     * @return
     */
    public CardType getType() {
        return type;
    }

    /**
     * Get the procentual tax
     * @return
     */
    public Double getFee() {
        return fee;
    }

    /**
     * Get the maximum limit a person can retrive a day
     * @return
     */
    public BigDecimal getWindrowLimit() {
        return windrowLimit;
    }

    /**
     * Get the expiration date
     * @return
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Get the amount of money it has
     * @return BigDecimal
     */
    public BigDecimal getAvaiable() {
        return avaiable;
    }

    /**
     * Once a day this method should be called to reinitalize the amount of money a persone can withdrow
     */
    public void setAvaiable() {
        switch (type) {
            case SILVER:
                windrowLimit=BigDecimal.valueOf(4500);
                break;
            case GOLD:
                windrowLimit=BigDecimal.valueOf(3000);
                break;
            case PLATINUM:
                windrowLimit=BigDecimal.valueOf(4000);
                break;
        }

    }

    /**
     * Withdrow some money from the card
     * @param amount BigDecimal
     * @throws CardException if limit of the day reached
     */
    public void withdrow(BigDecimal amount, LocalDateTime today) throws CardException {
        if(today.isAfter(expirationDate)){
            throw new CardException("Card expired!");
        }
        if(amount.compareTo(windrowLimit)>0){
            throw new CardException("Withdrow limit reached!");
        }

        windrowLimit=windrowLimit.subtract(amount);

        amount=amount.add(amount.multiply(BigDecimal.valueOf(fee/100)));
        avaiable=avaiable.subtract(amount);
    }

    /**
     * Get the maximum amount we can withdrow at the moment
     * @return BigDecimal
     */
    public BigDecimal maxWithdrow(){
        if(windrowLimit.compareTo(avaiable)<0)
            return windrowLimit;
        else
            return avaiable;
    }

    /**
     * Show the entire entity wwith all atributes
     * @return String
     */
    @Override
    public String toString() {
        return "Card{" +
                "type=" + type +
                ", fee=" + fee +
                ", windrowLimit=" + windrowLimit +
                ", expirationDate=" + expirationDate +
                ", avaiable=" + avaiable +
                '}';
    }
}
