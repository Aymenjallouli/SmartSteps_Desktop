package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudForum <For> {
    public void ajouter(For f);
    public void modifier(For f);
    public void supprimer(int id) throws SQLException;
    public List<Forum> Show();

    public int getById(int id) throws SQLException;
}
