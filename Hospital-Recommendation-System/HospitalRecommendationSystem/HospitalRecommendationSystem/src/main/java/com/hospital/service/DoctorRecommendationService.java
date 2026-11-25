
package com.hospital.service;

import com.hospital.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorRecommendationService {

    public void recommendDoctors(String hospitalName, String departmentName) {

        String sql = """
            SELECT d.doctor_name,
                   d.qualification,
                   d.experience_years,
                   d.consultation_fee,
                   d.rating
            FROM doctors d
            JOIN hospitals h
                ON d.hospital_id = h.hospital_id
            JOIN departments dep
                ON d.department_id = dep.department_id
            WHERE h.hospital_name = ?
              AND dep.department_name = ?
            ORDER BY d.rating DESC,
                     d.experience_years DESC,
                     d.consultation_fee ASC
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hospitalName);
            ps.setString(2, departmentName);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                        rs.getString("doctor_name")
                        + " | " + rs.getString("qualification")
                        + " | Experience: " + rs.getInt("experience_years")
                        + " years | Fee: ₹" + rs.getBigDecimal("consultation_fee")
                        + " | Rating: " + rs.getDouble("rating")
                    );
                }

                if (!found) {
                    System.out.println("No matching doctors found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

