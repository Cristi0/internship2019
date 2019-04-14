package Repository;

import Model.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


/**
 * A general abstract class that reads from a .csv format file
 * @param <T> T the tyipe of the Object
 */
public abstract class AbstractRepo<T extends Model> implements Repo<T> {

    protected Vector<T> elements;

    public AbstractRepo() {
        this.elements = new Vector<T>();
    }

    @Override
    public Vector<T> getAll() {
        return elements;
    }

    @Override
    public void read(String filename) {
        try {
            BufferedReader br =new BufferedReader(new FileReader(filename));
            String line="";
            while((line = br.readLine())!=null){
                String[] attributes = line.split(",");
                readObject(attributes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(String filename) {

    }

    /**
     * Introduce the exact object in the elements
     * @param attributes
     */
    abstract void readObject(String[] attributes);
}
