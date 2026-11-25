
package com.hospital.service;

import com.hospital.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SymptomRecommendationService {

    public void recommendDepartments(String symptomName) {

        String sql = """
            SELECT d.department_name,
                   sd.relevance_weight
            FROM symptoms s
            JOIN symptom_departments sd
                ON s.symptom_id = sd.symptom_id
            JOIN departments d
                ON sd.department_id = d.department_id
            WHERE s.symptom_name = ?
            ORDER BY sd.relevance_weight DESC
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, symptomName);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                        rs.getString("department_name")
                        + " | Relevance weight: "
                        + rs.getInt("relevance_weight")
                    );
                }

                if (!found) {
                    System.out.println("No matching symptom found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

