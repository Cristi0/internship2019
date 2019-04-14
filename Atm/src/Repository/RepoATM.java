package Repository;

import Model.ATM;

import java.sql.Time;

/**
 * Repository specialy made for ATMs
 */
public class RepoATM extends AbstractRepo<ATM> {
    @Override
    void readObject(String[] attributes) {
        ATM atm=new ATM(
                attributes[0],
                Time.valueOf(attributes[1]+":00"),
                Time.valueOf(attributes[2]+":00")
        );
        super.elements.add(atm);
    }
}
