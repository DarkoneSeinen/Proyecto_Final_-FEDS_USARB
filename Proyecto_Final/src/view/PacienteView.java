package view;

import controller.Controller;
import model.*;
import model.dto.CitaDTO;
import model.dto.MedicoDTO;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class PacienteView extends JFrame {
    private Controller controller;
    private JTextField idField;
    private JButton agendarBtn, modificarBtn, cancelarBtn, asistenciaBtn, pagoBtn, verCitasBtn, volverBtn;
    private JTextArea citasArea;

    public PacienteView(Controller controller) {
        this.controller = controller;
        setTitle("Módulo Paciente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID Paciente:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        add(idField);

        agendarBtn = new JButton("Agendar Cita");
        agendarBtn.setBounds(20, 60, 130, 25);
        add(agendarBtn);

        modificarBtn = new JButton("Modificar Cita");
        modificarBtn.setBounds(160, 60, 130, 25);
        add(modificarBtn);

        cancelarBtn = new JButton("Cancelar Cita");
        cancelarBtn.setBounds(300, 60, 130, 25);
        add(cancelarBtn);

        asistenciaBtn = new JButton("Marcar Asistencia");
        asistenciaBtn.setBounds(440, 60, 130, 25);
        add(asistenciaBtn);

        pagoBtn = new JButton("Pagar Cita");
        pagoBtn.setBounds(20, 100, 130, 25);
        add(pagoBtn);

        verCitasBtn = new JButton("Ver Mis Citas");
        verCitasBtn.setBounds(160, 100, 130, 25);
        add(verCitasBtn);

        volverBtn = new JButton("Volver");
        volverBtn.setBounds(300, 100, 130, 25);
        add(volverBtn);

        citasArea = new JTextArea();
        citasArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(citasArea);
        scroll.setBounds(20, 140, 550, 200);
        add(scroll);

        agendarBtn.addActionListener(e -> agendarCita());
        modificarBtn.addActionListener(e -> modificarCita());
        cancelarBtn.addActionListener(e -> cancelarCita());
        asistenciaBtn.addActionListener(e -> marcarAsistencia());
        pagoBtn.addActionListener(e -> pagarCita());
        verCitasBtn.addActionListener(e -> verCitas());
        volverBtn.addActionListener(e -> {
            new MainMenuView(controller);
            dispose();
        });

        setVisible(true);
    }

    private void agendarCita() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<MedicoDTO> medicos = controller.obtenerMedicos();
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay médicos registrados.");
            return;
        }
        String[] medicoNombres = medicos.stream().map(m -> m.getId() + " - " + m.getNombre()).toArray(String[]::new);
        String medicoSel = (String) JOptionPane.showInputDialog(this, "Seleccione médico:", "Médicos",
                JOptionPane.QUESTION_MESSAGE, null, medicoNombres, medicoNombres[0]);
        if (medicoSel == null) return;
        String idMedico = medicoSel.split(" - ")[0];

        String fechaStr = JOptionPane.showInputDialog(this, "Ingrese fecha y hora de la cita (cualquier formato):");
        if (fechaStr == null || fechaStr.trim().isEmpty()) return;
        controller.agendarCita(idPaciente, idMedico, fechaStr.trim());
        JOptionPane.showMessageDialog(this, "Cita agendada correctamente.");
    }

    private void modificarCita() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente);
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes citas registradas.");
            return;
        }
        String[] citaIds = citas.stream()
            .map(c -> c.getIdCita() + " - " + c.getFechaHora())
            .toArray(String[]::new);
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita a modificar:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];

        String fechaStr = JOptionPane.showInputDialog(this, "Nueva fecha y hora de la cita (cualquier formato):");
        if (fechaStr == null || fechaStr.trim().isEmpty()) return;
        controller.modificarCita(idCita, fechaStr.trim());
        JOptionPane.showMessageDialog(this, "Cita modificada correctamente.");
    }

    private void cancelarCita() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente);
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes citas registradas.");
            return;
        }
        String[] citaIds = citas.stream()
            .map(c -> c.getIdCita() + " - " + c.getFechaHora())
            .toArray(String[]::new);
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita a cancelar:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];
        controller.cancelarCita(idCita);
        JOptionPane.showMessageDialog(this, "Cita cancelada.");
    }

    private void marcarAsistencia() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente);
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes citas registradas.");
            return;
        }
        String[] citaIds = citas.stream()
            .map(c -> c.getIdCita() + " - " + c.getFechaHora())
            .toArray(String[]::new);
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita para marcar asistencia:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];
        controller.marcarAsistencia(idCita);
        JOptionPane.showMessageDialog(this, "Asistencia marcada.");
    }

    private void pagarCita() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente);
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes citas registradas.");
            return;
        }
        String[] citaIds = citas.stream()
            .map(c -> c.getIdCita() + " - " + c.getFechaHora())
            .toArray(String[]::new);
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita a pagar:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];
        controller.pagarCita(idCita);
        JOptionPane.showMessageDialog(this, "Cita pagada.");
    }

    private void verCitas() {
        String idPaciente = idField.getText().trim();
        if (idPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su ID de paciente.");
            return;
        }
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente);
        citasArea.setText("");
        if (citas.isEmpty()) {
            citasArea.append("No tienes citas registradas.\n");
        } else {
            citasArea.append("Tus citas:\n");
            for (CitaDTO c : citas) {
                citasArea.append("ID: " + c.getIdCita() +
                    "\nFecha: " + c.getFechaHora() +
                    "\nMédico: " + c.getIdMedico() +
                    "\nAsistió: " + (c.isAsistio() ? "Sí" : "No") +
                    "\nPagada: " + (c.isPagada() ? "Sí" : "No") +
                    "\n------------------\n");
            }
        }
    }
}