package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import model.Admin;
import model.Artifact;
import model.GetIdable;
import model.Mentor;
import model.Student;


public abstract class AbstractDao <T extends GetIdable> {

    public void removeObject(T object) throws SQLException {
        Connection c = DBConnection.getC();
        String sql = String.format("DELETE FROM %s WHERE id = ?;", getTableName(object));
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, object.getId());
        pstmt.executeUpdate();

    }

    public abstract void addObject(T object) throws SQLException;
    public abstract void updateData(T object) throws SQLException;

    private String getTableName(T object) {
        String tableName = object.getClass().getSimpleName() + "s";

        return tableName;
    }
}