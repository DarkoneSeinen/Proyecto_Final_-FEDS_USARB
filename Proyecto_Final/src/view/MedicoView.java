package view;

import controller.Controller;
import model.*;
import model.dto.CitaDTO;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MedicoView extends JFrame {
    private Controller controller;
    private JTextField idField;
    private JButton disponibilidadBtn, verAgendaBtn, atenderCitaBtn, volverBtn;
    private JTextArea citasArea;

    public MedicoView(Controller controller) {
        this.controller = controller;
        setTitle("Módulo Médico");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID Médico:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        add(idField);

        disponibilidadBtn = new JButton("Agregar Disponibilidad");
        disponibilidadBtn.setBounds(20, 60, 180, 25);
        add(disponibilidadBtn);

        verAgendaBtn = new JButton("Ver Agenda");
        verAgendaBtn.setBounds(220, 60, 130, 25);
        add(verAgendaBtn);

        atenderCitaBtn = new JButton("Atender Cita");
        atenderCitaBtn.setBounds(370, 60, 130, 25);
        add(atenderCitaBtn);

        volverBtn = new JButton("Volver");
        volverBtn.setBounds(510, 60, 70, 25);
        add(volverBtn);

        citasArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(citasArea);
        scroll.setBounds(20, 100, 560, 250);
        add(scroll);

        disponibilidadBtn.addActionListener(e -> agregarDisponibilidad());
        verAgendaBtn.addActionListener(e -> verAgenda());
        atenderCitaBtn.addActionListener(e -> atenderCita());
        volverBtn.addActionListener(e -> {
            new MainMenuView(controller);
            dispose();
        });

        setVisible(true);
    }

    private void agregarDisponibilidad() {
        String idMedico = idField.getText().trim();
        if (idMedico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de médico.");
            return;
        }
        String disp = JOptionPane.showInputDialog(this, "Ejemplo: LUNES 08:00-12:00");
        if (disp != null && !disp.trim().isEmpty()) {
            controller.agregarDisponibilidadMedico(idMedico, disp.trim());
            JOptionPane.showMessageDialog(this, "Disponibilidad agregada.");
        }
    }

    private void verAgenda() {
        String idMedico = idField.getText().trim();
        if (idMedico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de médico.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasMedico(idMedico);
        citasArea.setText("");
        if (citas.isEmpty()) {
            citasArea.append("No tienes citas agendadas.\n");
        } else {
            citasArea.append("Tu agenda:\n");
            for (CitaDTO c : citas) {
                citasArea.append("ID: " + c.getIdCita() +
                    "\nFecha: " + c.getFechaHora() +
                    "\nPaciente: " + c.getIdPaciente() +
                    "\nAsistió: " + (c.isAsistio() ? "Sí" : "No") +
                    "\n------------------\n");
            }
        }
    }

    private void atenderCita() {
        String idMedico = idField.getText().trim();
        if (idMedico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de médico.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasMedico(idMedico);
        citas = citas.stream().filter(c -> !c.isAsistio()).toList();
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes citas pendientes de atender.");
            return;
        }
        String[] citaIds = citas.stream()
            .map(c -> c.getIdCita() + " - " + c.getFechaHora())
            .toArray(String[]::new);
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita a marcar como atendida:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];
        controller.marcarAsistencia(idCita);
        JOptionPane.showMessageDialog(this, "Cita marcada como atendida.");
    }
}