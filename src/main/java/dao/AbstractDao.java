package dao;

import model.Admin;
import model.Artifact;
import model.Mentor;
import model.Student;

import java.sql.SQLException;

public abstract class AbstractDao <T> {
//    private static final String[] TABLES_NAMES= { "Admins", "Mentors", "Students"};


    public void removeObject(T object) {

        String sql = String.format("DELETE FROM %s WHERE id = %s", getTableName(object), object.getId());
    }

    private String getTableName(T object) {
        String tableName = "";

        if (object instanceof Admin) {
            tableName = "Admins";

        } else if (object instanceof Mentor) {
            tableName = "Mentors";

        } else if (object instanceof Student) {
            tableName = "Students";

        } else if (object instanceof Artifact) {
            tableName = "Artifacts";
        }

        return tableName;
    }
}