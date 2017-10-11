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
//    private Connection c = null;

    public void removeObject(T object) throws SQLException {
        Connection c = DBConnection.getC();
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName(object));
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, object.getId());
        pstmt.executeUpdate();

    }

    public abstract void addObject(T object) throws SQLException;
    public abstract void updateData(T object) throws SQLException;

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