
package com.hospital.service;

import com.hospital.exception.SymptomNotFoundException;
import com.hospital.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationService extends AbstractRecommendationService {

    @Override
    public List<String> getDepartments(String symptom) throws SymptomNotFoundException {

        List<String> departments = new ArrayList<>();

        String sql = """
            SELECT d.department_name
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

            ps.setString(1, symptom);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    departments.add(rs.getString("department_name"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not load departments: " + e.getMessage(), e
            );
        }

        if (departments.isEmpty()) {
            throw new SymptomNotFoundException(
                "No departments found for symptom: " + symptom
            );
        }

        return departments;
    }

    @Override
    public List<String> getHospitals(String departmentName) {

        List<String> hospitals = new ArrayList<>();

        String sql = """
            SELECT h.hospital_name
            FROM hospitals h
            JOIN hospital_departments hd
                ON h.hospital_id = hd.hospital_id
            JOIN departments d
                ON d.department_id = hd.department_id
            WHERE d.department_name = ?
            ORDER BY h.rating DESC, h.hospital_name ASC
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hospitals.add(rs.getString("hospital_name"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not load hospitals: " + e.getMessage(), e
            );
        }

        return hospitals;
    }

    @Override
    public List<String> getDoctors(
            String hospitalName,
            String departmentName) {

        List<String> doctors = new ArrayList<>();

        String sql = """
            SELECT d.doctor_name,
                   d.qualification,
                   d.experience_years,
                   d.consultation_fee,
                   d.rating
            FROM doctors d
            JOIN hospitals h
                ON h.hospital_id = d.hospital_id
            JOIN departments dep
                ON dep.department_id = d.department_id
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
                while (rs.next()) {
                    String doctor =
                        rs.getString("doctor_name")
                        + " | " + rs.getString("qualification")
                        + " | Experience: "
                        + rs.getInt("experience_years") + " years"
                        + " | Fee: Rs. "
                        + rs.getBigDecimal("consultation_fee")
                        + " | Rating: "
                        + rs.getBigDecimal("rating");

                    doctors.add(doctor);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not load doctors: " + e.getMessage(), e
            );
        }

        return doctors;

    }
    @Override
public List<String> getDoctors(String hospitalName) {

    List<String> doctors = new ArrayList<>();

    String sql = """
        SELECT d.doctor_name,
               d.qualification,
               d.experience_years,
               d.consultation_fee,
               d.rating
        FROM doctors d
        JOIN hospitals h
            ON h.hospital_id = d.hospital_id
        WHERE h.hospital_name = ?
        ORDER BY d.rating DESC,
                 d.experience_years DESC,
                 d.consultation_fee ASC
        """;

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, hospitalName);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String doctor =
                    rs.getString("doctor_name")
                    + " | " + rs.getString("qualification")
                    + " | Experience: "
                    + rs.getInt("experience_years") + " years"
                    + " | Fee: Rs. "
                    + rs.getBigDecimal("consultation_fee")
                    + " | Rating: "
                    + rs.getBigDecimal("rating");

                doctors.add(doctor);
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException(
            "Could not load doctors: " + e.getMessage(), e
        );
    }

    return doctors;
}
}
