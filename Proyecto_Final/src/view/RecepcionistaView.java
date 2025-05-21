package view;

import controller.Controller;
import model.*;
import model.dto.CitaDTO;
import model.dto.MedicoDTO;
import model.dto.PacienteDTO;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class RecepcionistaView extends JFrame {
    private Controller controller;
    private JButton registrarPacienteBtn, registrarMedicoBtn, agendarCitaBtn, modificarCitaBtn, cancelarCitaBtn;
    private JButton actualizarAgendaBtn, pagarCitaBtn, informesBtn, volverBtn;
    private JTextArea area;

    public RecepcionistaView(Controller controller) {
        this.controller = controller;
        setTitle("Módulo Recepcionista");
        setSize(780, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        registrarPacienteBtn = new JButton("Registrar Paciente");
        registrarPacienteBtn.setBounds(20, 20, 180, 25);
        add(registrarPacienteBtn);

        registrarMedicoBtn = new JButton("Registrar Médico");
        registrarMedicoBtn.setBounds(220, 20, 180, 25);
        add(registrarMedicoBtn);

        agendarCitaBtn = new JButton("Agendar Cita");
        agendarCitaBtn.setBounds(420, 20, 140, 25);
        add(agendarCitaBtn);

        modificarCitaBtn = new JButton("Modificar Cita");
        modificarCitaBtn.setBounds(580, 20, 140, 25);
        add(modificarCitaBtn);

        cancelarCitaBtn = new JButton("Cancelar Cita");
        cancelarCitaBtn.setBounds(20, 60, 140, 25);
        add(cancelarCitaBtn);

        actualizarAgendaBtn = new JButton("Actualizar Disponibilidad Médico");
        actualizarAgendaBtn.setBounds(180, 60, 260, 25);
        add(actualizarAgendaBtn);

        pagarCitaBtn = new JButton("Registrar Pago");
        pagarCitaBtn.setBounds(460, 60, 140, 25);
        add(pagarCitaBtn);

        informesBtn = new JButton("Generar Informes");
        informesBtn.setBounds(620, 60, 120, 25);
        add(informesBtn);

        volverBtn = new JButton("Volver");
        volverBtn.setBounds(650, 500, 90, 25);
        add(volverBtn);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(20, 110, 720, 380);
        add(scroll);

        registrarPacienteBtn.addActionListener(e -> registrarPaciente());
        registrarMedicoBtn.addActionListener(e -> registrarMedico());
        agendarCitaBtn.addActionListener(e -> agendarCita());
        modificarCitaBtn.addActionListener(e -> modificarCita());
        cancelarCitaBtn.addActionListener(e -> cancelarCita());
        actualizarAgendaBtn.addActionListener(e -> actualizarDisponibilidadMedico());
        pagarCitaBtn.addActionListener(e -> pagarCita());
        informesBtn.addActionListener(e -> generarInformes());
        volverBtn.addActionListener(e -> {
            new MainMenuView(controller);
            dispose();
        });

        setVisible(true);
    }

    private void registrarPaciente() {
        String id = JOptionPane.showInputDialog(this, "ID del paciente:");
        String nombre = JOptionPane.showInputDialog(this, "Nombre:");
        String tel = JOptionPane.showInputDialog(this, "Teléfono:");
        String email = JOptionPane.showInputDialog(this, "Email:");
        if (id != null && nombre != null && tel != null && email != null) {
            controller.registrarPaciente(id.trim(), nombre.trim(), tel.trim(), email.trim());
            JOptionPane.showMessageDialog(this, "Paciente registrado.");
        }
    }

    private void registrarMedico() {
        String id = JOptionPane.showInputDialog(this, "ID del médico:");
        String nombre = JOptionPane.showInputDialog(this, "Nombre del médico:");
        String especialidad = JOptionPane.showInputDialog(this, "Especialidad:");
        if (id != null && nombre != null && especialidad != null) {
            controller.registrarMedico(id.trim(), nombre.trim(), especialidad.trim());
            JOptionPane.showMessageDialog(this, "Médico registrado.");
        }
    }

    private void agendarCita() {
        String idPaciente = JOptionPane.showInputDialog(this, "ID del paciente:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;
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
        controller.agendarCita(idPaciente.trim(), idMedico, fechaStr.trim());
        JOptionPane.showMessageDialog(this, "Cita agendada correctamente.");
    }

    private void modificarCita() {
        String idPaciente = JOptionPane.showInputDialog(this, "ID del paciente:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente.trim());
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El paciente no tiene citas registradas.");
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
        String idPaciente = JOptionPane.showInputDialog(this, "ID del paciente:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente.trim());
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El paciente no tiene citas registradas.");
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

    private void actualizarDisponibilidadMedico() {
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
        String disp = JOptionPane.showInputDialog(this, "Ejemplo: LUNES 08:00-12:00");
        if (disp != null && !disp.trim().isEmpty()) {
            controller.agregarDisponibilidadMedico(idMedico, disp.trim());
            JOptionPane.showMessageDialog(this, "Disponibilidad agregada.");
        }
    }

    private void pagarCita() {
        String idPaciente = JOptionPane.showInputDialog(this, "ID del paciente:");
        if (idPaciente == null || idPaciente.trim().isEmpty()) return;
        List<CitaDTO> citas = controller.obtenerCitasPaciente(idPaciente.trim());
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El paciente no tiene citas registradas.");
            return;
        }
        String[] citaIds = citas.stream()
                .filter(c -> !c.isPagada())
                .map(c -> c.getIdCita() + " - " + c.getFechaHora())
                .toArray(String[]::new);
        if (citaIds.length == 0) {
            JOptionPane.showMessageDialog(this, "Todas las citas ya están pagadas.");
            return;
        }
        String citaSel = (String) JOptionPane.showInputDialog(this, "Seleccione cita a pagar:", "Citas",
                JOptionPane.QUESTION_MESSAGE, null, citaIds, citaIds[0]);
        if (citaSel == null) return;
        String idCita = citaSel.split(" - ")[0];
        controller.pagarCita(idCita);
        JOptionPane.showMessageDialog(this, "Cita pagada.");
    }

    private void generarInformes() {
        String[] opciones = {"Citas por Médico", "Pagos Realizados", "Pacientes Registrados"};
        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione informe:", "Informes",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return;

        area.setText("");
        if (seleccion.equals("Citas por Médico")) {
            List<MedicoDTO> medicos = controller.obtenerMedicos();
            for (MedicoDTO m : medicos) {
                List<CitaDTO> citas = controller.obtenerCitasMedico(m.getId());
                area.append("Médico: " + m.getNombre() + " (" + m.getId() + ")\n");
                for (CitaDTO c : citas) {
                    area.append("  Cita: " + c.getIdCita() + "  Fecha: " + c.getFechaHora() + "  Paciente: " + c.getIdPaciente() + "\n");
                }
                area.append("--------------------------\n");
            }
        } else if (seleccion.equals("Pagos Realizados")) {
            List<CitaDTO> citas = controller.obtenerTodasLasCitas();
            List<CitaDTO> pagadas = citas.stream().filter(CitaDTO::isPagada).collect(Collectors.toList());
            for (CitaDTO c : pagadas) {
                area.append("Cita: " + c.getIdCita() + "  Fecha: " + c.getFechaHora() + "  Paciente: " + c.getIdPaciente() + "  Médico: " + c.getIdMedico() + "\n");
            }
            area.append("Total pagos registrados: " + pagadas.size() + "\n");
        } else if (seleccion.equals("Pacientes Registrados")) {
            List<PacienteDTO> pacientes = controller.obtenerPacientes();
            for (PacienteDTO p : pacientes) {
                area.append("ID: " + p.getId() + "  Nombre: " + p.getNombre() + "  Tel: " + p.getTelefono() + "  Email: " + p.getEmail() + "\n");
            }
            area.append("Total pacientes: " + pacientes.size() + "\n");
        }
    }
}