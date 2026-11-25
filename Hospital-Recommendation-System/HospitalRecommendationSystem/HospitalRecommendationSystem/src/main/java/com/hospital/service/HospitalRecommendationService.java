
package com.hospital.service;

import com.hospital.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalRecommendationService {

    public void recommendHospitals(String departmentName) {

        String sql = """
            SELECT h.hospital_name,
                   l.area_name,
                   h.rating,
                   MIN(d.consultation_fee) AS lowest_fee
            FROM hospitals h
            JOIN locations l
                ON h.location_id = l.location_id
            JOIN hospital_departments hd
                ON h.hospital_id = hd.hospital_id
            JOIN departments dep
                ON hd.department_id = dep.department_id
            LEFT JOIN doctors d
                ON d.hospital_id = h.hospital_id
                AND d.department_id = dep.department_id
            WHERE dep.department_name = ?
            GROUP BY h.hospital_id, h.hospital_name,
                     l.area_name, h.rating
            ORDER BY h.rating DESC,
                     lowest_fee ASC
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                        rs.getString("hospital_name")
                        + " | Area: " + rs.getString("area_name")
                        + " | Rating: " + rs.getDouble("rating")
                        + " | Lowest fee: ₹" + rs.getBigDecimal("lowest_fee")
                    );
                }

                if (!found) {
                    System.out.println(
                        "No hospitals found for this department."
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

