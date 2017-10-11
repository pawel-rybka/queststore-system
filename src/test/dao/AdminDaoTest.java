package dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdminDaoTest {
    AdminDao dao;

    @BeforeEach
    void initializee(){
    dao = new AdminDao();

    }

    @Test
    @DisplayName("Testujemy get Admins")
    void testGetAdmins() throws SQLException {
        assertEquals("a", dao.getAdmins());
    }

}