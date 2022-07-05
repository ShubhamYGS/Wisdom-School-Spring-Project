package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        String sql = "INSERT INTO CONTACT_MSG (NAME,EMAIL,SUBJECT,MESSAGE,STATUS," +
                "CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,contact.getName(),contact.getEmail(),contact.getSubject(),
                contact.getMessage(),contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
    }

    public List<Contact> findMsgWithStatus(String status) {
        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,status);
            }
        },new ContactRowMapper());
    }

    public int updateMsgStatus(int id, String updatedBy, String status) {
        String sql = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_AT = ?, UPDATED_BY = ? WHERE CONTACT_ID = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(3, updatedBy);
                ps.setInt(4, id);
            }
        });
    }
}
