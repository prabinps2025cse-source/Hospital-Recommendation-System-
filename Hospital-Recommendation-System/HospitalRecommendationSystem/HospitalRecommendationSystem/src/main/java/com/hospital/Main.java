/*package com.hospital;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hospital Recommendation System");
        System.out.println("Project started successfully!");
    }
}*/

/*package com.hospital;

import com.hospital.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (
            Connection con = DatabaseConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT hospital_name FROM hospitals"
            )
        ) {
            System.out.println("MySQL connected successfully!");

            while (rs.next()) {
                System.out.println(rs.getString("hospital_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/

/*package com.hospital;

import com.hospital.service.SymptomRecommendationService;

public class Main {
    public static void main(String[] args) {
        SymptomRecommendationService service =
                new SymptomRecommendationService();

        service.recommendDepartments("Skin rash");
    }
}*/

/*package com.hospital;

import com.hospital.service.HospitalRecommendationService;

public class Main {
    public static void main(String[] args) {
        HospitalRecommendationService service =
                new HospitalRecommendationService();

        service.recommendHospitals("Dermatology");
    }
}*/

/*package com.hospital;

import com.hospital.service.DoctorRecommendationService;

public class Main {
    public static void main(String[] args) {
        DoctorRecommendationService service =
                new DoctorRecommendationService();

        service.recommendDoctors(
                "Apollo Sample Hospital",
                "Cardiology"
        );
    }
}*/

/*package com.hospital;

import com.hospital.service.RecommendationService;

public class Main {
    public static void main(String[] args) {

        RecommendationService service = new RecommendationService();

        service.recommend(
                "Skin rash",
                "Apollo Sample Hospital",
                "Dermatology"
        );
    }
}*/

package com.hospital;

import com.hospital.ui.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}





