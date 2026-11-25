
package com.hospital.service;

import com.hospital.exception.SymptomNotFoundException;
import java.util.List;

public abstract class AbstractRecommendationService implements RecommendationOperations {

    public abstract List<String> getDepartments(String symptom)
            throws SymptomNotFoundException;

    public abstract List<String> getHospitals(String departmentName);

    public abstract List<String> getDoctors(
            String hospitalName,
            String departmentName
    );
    public abstract List<String> getDoctors(String hospitalName);
}
