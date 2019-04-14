package Repository;

import Model.Model;

import java.util.Vector;

/**
 * The interface for the repository
 * @param <T>
 */
public interface Repo<T extends Model> {

    public Vector<T> getAll();

    /**
     * Read the data from the file
     * @param filename the filename with/without the path
     */
    public void read(String filename);

    /**
     * Write the data to a file
     * @param filename the path\name.extention
     */
    public void write(String filename);

}
