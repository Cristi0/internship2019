package Repository;

import Model.Card;
import Model.CardType;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Repository specialy made for Cards
 */
public class RepoCard extends AbstractRepo<Card> {
    @Override
    void readObject(String[] attributes) {
        String[] data= attributes[3].split("\\.");

        Card card=new Card(
                CardType.valueOf(attributes[0]),
                Double.parseDouble(attributes[1].substring(0,attributes[1].length()-1)),
                new BigDecimal(attributes[2]),
                LocalDateTime.parse(data[2]+"-"+data[1]+"-"+data[0]+"T00:00"),
                new BigDecimal(attributes[4])
        );
        super.elements.add(card);
    }
}
