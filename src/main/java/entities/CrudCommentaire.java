package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudCommentaire <Comment> {
    public void ajouter(Comment c);
    public void modifier(Comment c);
    public void supprimer(int id) throws SQLException;
    public List<Commentaire> Show();

    public Forum getById(int id) throws SQLException;
}
