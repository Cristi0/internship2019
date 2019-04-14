package Service;

import Except.CardException;
import Model.ATM;
import Model.Card;
import Model.Path;
import Repository.AbstractRepo;
import Repository.RepoATM;
import Repository.RepoCard;
import Repository.RepoPath;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * The main brain of the program
 */
public class Controller {
    private AbstractRepo<ATM> repoATM;
    private AbstractRepo<Card> repoCard;                //the repositoryes
    private AbstractRepo<Path> repoPath;

    private LocalDateTime currentDate, stopDate;    //the time we need to get all of it
    private BigDecimal winthdrawing;                //the amount that we want to winthdrow

    private String startingPoint="User starting point"; //conventions for the starting user point

    /**
     * Constructor
     * @param stopDate  the maximum datetime we want to have all the money withdrow
     * @param winthdrawing  the sum we want
     */
    public Controller(LocalDateTime stopDate,  BigDecimal winthdrawing) {

        repoATM = new RepoATM();
        repoCard = new RepoCard();
        repoPath = new RepoPath();

        repoATM.read("src/Resurse/ATM.csv");
        repoCard.read("src/Resurse/Card.csv");
        repoPath.read("src/Resurse/Path.csv");

        //setting curent date time
        currentDate=LocalDateTime.parse("2019-03-19T11:30");

        this.stopDate=stopDate;
        this.winthdrawing= winthdrawing;
    }


    private Vector<Card> cards;//sorting card with smallest flee
    private TreeMap<String,Vector<Path>> graph ; //give the graph with the name of the Atms as keys
    private TreeMap<String,ATM> atms ;
    /**
     * A greedy method that calculates the atms we go to and that we can accomplish our
     * mission: to withdrow that amount of money in that given time
     * It goes to the closer ATM and winthdrow from the card with the lowest fee
     *
     * in the problem given i suppose that we could use multiple cards to the same atm
     * so to get the lowest fee possible, we could use 1,2..n cards on the same track
     * @return
     */
    public List<ATM> getAtmsRoute(){
        cards= getCardsWithSmallestFee();//sorting card with smallest flee,  that are not expired
        graph = getPonderedGraph(); //give the graph with the name of the Atms as keys
        atms = getAtmsByName();

        Vector<Path> paths=new Vector<>();
        //settings variables
        String currentAtm=startingPoint;
        //o metoda de tip greedy
        while(winthdrawing.compareTo(BigDecimal.ZERO)!=0 && !currentDate.isAfter(stopDate)){
            Path p=getSmallestDuration(graph.get(currentAtm));  //todo de vazut
            currentDate=currentDate.plusMinutes(p.getDuration());
            paths.add(p);  //adougam atm la care mergem

            BigDecimal maxPosibleWithdrow = maxWithdrow(atms.get(p.getStop()),cards.elementAt(0));
            if(winthdrawing.compareTo(maxPosibleWithdrow)<0){
               doWithdrowing(p,winthdrawing);
            }
            else {
               doWithdrowing(p,maxPosibleWithdrow);
            }
            // daca am epuizat toata suma ce se putea lua intr-o zi de pe card sau nu mai sunt bani in card punem la socoteaza daca am fi folosit si urmatorul card in acelasi timp
            if(cards.elementAt(0).maxWithdrow().compareTo(BigDecimal.ZERO)==0){
                cards.remove(cards.elementAt(0));
                if(cards.size()!=0)
                redoWithdrowFor(paths);
            }
            currentAtm=p.getStop();
        }

        List<ATM> result=new ArrayList<>();
        for(Path a:paths){
            result.add(atms.get(a.getStop()));
        }
        return result;
    }

    /**
     * On the same path, when the amount of money on a card are none,
     * we get the money from the next card, as if we had both card from a single run
     * @param alreadyVizited the atms that we already vizited from the starting point
     */
    private void redoWithdrowFor(Vector<Path> alreadyVizited) {
        for(Path p:alreadyVizited) {

            BigDecimal maxPosibleWithdrow = maxWithdrow(atms.get(p.getStop()), cards.elementAt(0));
            if(maxPosibleWithdrow.compareTo(BigDecimal.ZERO)==0){
                break;
            }
            if (winthdrawing.compareTo(maxPosibleWithdrow) < 0) {
                doWithdrowing(p, winthdrawing);
            } else {
                doWithdrowing(p, maxPosibleWithdrow);
            }
        }
    }

    /**
     * Do a withdrow on the atm that we arrived and take "winth" money
     * @param p     the path from start to the atm
     * @param winth the amount of money we want to withdrow
     */
    public void doWithdrowing(Path p, BigDecimal winth){
        atms.get(p.getStop()).withrowAmount(winth);

        try {
            cards.elementAt(0).withdrow(winth,currentDate);    //nu mai avem bani pe card sau a expirat
            winthdrawing=winthdrawing.subtract(winth);
        } catch (CardException e) {
            // cards.remove(cards.elementAt(0));
        }
    }

    /**
     * Get the maximul amount of money that we can withdrow from the card and the atm
     * @param atm an atm
     * @param card  a card
     * @return  the maximum amount of money we can receive from that specific atm and that specific card
     */
    private BigDecimal maxWithdrow(ATM atm, Card card) {
        if(atm.maxWithrowAmount().compareTo(card.maxWithdrow())<0)
            return atm.maxWithrowAmount();
        else
            return card.maxWithdrow();
    }

    /**
     * Get the smallest path to go to and erase the atms that are already used
     * @param paths all the path that we didn`t go to yet
     * @return the path we eill go
     */
    private Path getSmallestDuration(Vector<Path> paths) {
        Path path=paths.elementAt(0);   //initializam cu primul
        for(Path p:paths){  //gasim pathul cu durata minima
            if(path.getDuration()>p.getDuration()){
                path=p;
            }
        }
        for(Path p:paths){  //eliminam atmul la care mergem
            if(path.getStop().compareTo(p.getStart())==0){
                paths.remove(p);
            }
        }
        return path;
    }

    /**
     * Get the atms in a map in order to get easy acces to them
     * @return map
     */
    private TreeMap<String, ATM> getAtmsByName() {
        TreeMap<String, ATM> map=new TreeMap<String, ATM>();
        for(ATM atm: repoATM.getAll()){
            map.put(atm.getName(),atm);
        }
        return map;
    }

    /**
     * Get the pondered graph in order to get easy acces to atms
     * @return map
     */
    private TreeMap<String, Vector<Path>> getPonderedGraph() {
        TreeMap<String, Vector<Path>> map=new TreeMap<String, Vector<Path>>();
        for(Path p: repoPath.getAll()){
            if(!map.containsKey(p.getStart())){
                Vector<Path> vec=new Vector<Path>();
                vec.add(p);
                map.put(p.getStart(),vec);
            }else{
                map.get(p.getStart()).add(p);
            }
        }
        return map;
    }

    /**
     * get a vector sorted with the smallest fee
     * @return
     */
    private Vector<Card> getCardsWithSmallestFee() {
        Vector<Card> vec=new Vector<>();
        for(Card c: repoCard.getAll()){
            if(c.getExpirationDate().isAfter(currentDate)){
                vec.add(c);
            }
        }
        vec.sort(Comparator.comparing(Card::getFee));
        return vec;
    }


}
