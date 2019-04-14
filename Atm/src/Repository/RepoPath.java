package Repository;

import Model.Path;

/**
 * Repository specialy made for Paths
 */
public class RepoPath extends AbstractRepo<Path> {
    @Override
    void readObject(String[] attributes) {
        Path path=new Path(
                attributes[0],
                attributes[1],
                Integer.valueOf(attributes[2])
        );
        super.elements.add(path);
    }
}
