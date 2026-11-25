
package com.hospital.ui;
import com.hospital.service.AbstractRecommendationService;
import com.hospital.exception.SymptomNotFoundException;

import com.hospital.service.RecommendationService;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final JComboBox<String> symptomBox;
    private final JComboBox<String> departmentBox;
    private final JComboBox<String> hospitalBox;
    private final JTextArea outputArea;

    public MainWindow() {

        setTitle("Hospital Recommendation System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel title = new JLabel(
            "Hospital Recommendation System",
            SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));

        symptomBox = new JComboBox<>(new String[]{
            "Skin rash",
            "Headache",
            "Joint pain",
            "Fever",
            "Chest discomfort"
        });

        departmentBox = new JComboBox<>();
        hospitalBox = new JComboBox<>();

        JButton departmentButton =
            new JButton("Find Departments");

        JButton hospitalButton =
            new JButton("Find Hospitals");

        JButton doctorButton =
            new JButton("Find Doctors");

        JPanel controls = new JPanel(
            new GridLayout(0, 2, 10, 10)
        );

        controls.add(new JLabel("Select symptom:"));
        controls.add(symptomBox);

        controls.add(new JLabel("Select department:"));
        controls.add(departmentBox);

        controls.add(new JLabel("Select hospital:"));
        controls.add(hospitalBox);

        controls.add(departmentButton);
        controls.add(hospitalButton);

        controls.add(new JLabel(""));
        controls.add(doctorButton);

        JPanel topPanel = new JPanel(
            new BorderLayout(10, 10)
        );

        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(controls, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(
            new Font("Monospaced", Font.PLAIN, 13)
        );
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(
            new JScrollPane(outputArea),
            BorderLayout.CENTER
        );

        add(panel);

        // FIND DEPARTMENTS

        departmentButton.addActionListener(e -> {

            try {

                String symptom =
                    (String) symptomBox.getSelectedItem();

                AbstractRecommendationService service =
        new RecommendationService();

                List<String> departments =
                    service.getDepartments(symptom);

                departmentBox.removeAllItems();

                for (String department : departments) {
                    departmentBox.addItem(department);
                }

                hospitalBox.removeAllItems();

                outputArea.setText(
                    "Departments matching " + symptom + ":\n"
                    + String.join("\n", departments)
                );

            } catch (SymptomNotFoundException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Symptom Not Found",
                    JOptionPane.WARNING_MESSAGE
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // FIND HOSPITALS

        hospitalButton.addActionListener(e -> {

            try {

                String department =
                    (String) departmentBox.getSelectedItem();

                if (department == null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Find departments first."
                    );
                    return;
                }

                RecommendationService service =
                    new RecommendationService();

                List<String> hospitals =
                    service.getHospitals(department);

                hospitalBox.removeAllItems();

                for (String hospital : hospitals) {
                    hospitalBox.addItem(hospital);
                }

                outputArea.setText(
                    hospitals.isEmpty()
                        ? "No hospitals found for " + department
                        : "Hospitals for " + department + ":\n"
                          + String.join("\n", hospitals)
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // FIND DOCTORS

        doctorButton.addActionListener(e -> {

            try {

                String hospital =
                    (String) hospitalBox.getSelectedItem();

                String department =
                    (String) departmentBox.getSelectedItem();

                if (department == null || hospital == null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Select a department and find hospitals first."
                    );
                    return;
                }

                RecommendationService service =
                    new RecommendationService();

                List<String> doctors =
                    service.getDoctors(hospital, department);

                outputArea.setText(
                    doctors.isEmpty()
                        ? "No doctors found for this selection."
                        : "Doctors at " + hospital
                          + " — " + department + ":\n\n"
                          + String.join("\n\n", doctors)
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

    }
}
