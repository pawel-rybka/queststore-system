package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Admin;
import model.Artifact;
import model.GetIdable;
import model.Mentor;
import model.Student;


public abstract class AbstractDao <T extends GetIdable> {
    private Connection conn = null;

    public void removeObject(T object) throws SQLException {

        String sql = "DELETE FROM ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getTableName(object));
            pstmt.setInt(2, object.getId());
            pstmt.executeUpdate();
        }
    }

    public T addObject(T object) throws SQLException {

//        String sql = "INSERT INTO ? (";


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