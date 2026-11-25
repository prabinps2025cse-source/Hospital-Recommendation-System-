
package com.hospital.service;

import com.hospital.exception.SymptomNotFoundException;
import java.util.List;

public interface RecommendationOperations {

    List<String> getDepartments(String symptom)
            throws SymptomNotFoundException;

    List<String> getHospitals(String departmentName);

    List<String> getDoctors(
            String hospitalName,
            String departmentName
    );

    List<String> getDoctors(String hospitalName);
}
