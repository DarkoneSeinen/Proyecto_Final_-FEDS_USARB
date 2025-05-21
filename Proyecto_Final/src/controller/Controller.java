package controller;

import java.time.LocalDateTime;
import java.util.*;

import model.*;
import model.dao.CitaDAO;
import model.dao.MedicoDAO;
import model.dao.PacienteDAO;
import model.dao.RecepcionistaDAO;
import model.dto.CitaDTO;
import model.dto.MedicoDTO;
import model.dto.PacienteDTO;
import model.dto.RecepcionistaDTO;



public class Controller {
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;
    private RecepcionistaDAO recepcionistaDAO;
    private CitaDAO citaDAO;

    public Controller() {
        pacienteDAO = new PacienteDAO();
        medicoDAO = new MedicoDAO();
        recepcionistaDAO = new RecepcionistaDAO();
        citaDAO = new CitaDAO();
    }

    // PACIENTE
    public void registrarPaciente(String id, String nombre, String tel, String email) {
        pacienteDAO.agregarPaciente(new PacienteDTO(id, nombre, tel, email));
    }
    public List<PacienteDTO> obtenerPacientes() { return pacienteDAO.listarPacientes(); }

    // MÉDICO
    public void registrarMedico(String id, String nombre, String especialidad) {
        medicoDAO.agregarMedico(new MedicoDTO(id, nombre, especialidad));
    }
    public List<MedicoDTO> obtenerMedicos() { return medicoDAO.listarMedicos(); }

    // RECEPCIONISTA
    public void registrarRecepcionista(String id, String nombre) {
        recepcionistaDAO.agregarRecepcionista(new RecepcionistaDTO(id, nombre));
    }
    public List<RecepcionistaDTO> obtenerRecepcionistas() { return recepcionistaDAO.listarRecepcionistas(); }
    
    
    // --- CITAS ---
    public void agendarCita(String idPaciente, String idMedico, String fechaHora) {
        String idCita = UUID.randomUUID().toString();
        citaDAO.agregarCita(new CitaDTO(idCita, idPaciente, idMedico, fechaHora));
    }

    public void modificarCita(String idCita, String nuevaFechaHora) {
        CitaDTO cita = citaDAO.buscarCita(idCita);
        if (cita != null) {
            cita.setFechaHora(nuevaFechaHora);
            citaDAO.modificarCita(cita);
        }
    }

    public void cancelarCita(String idCita) {
        citaDAO.eliminarCita(idCita);
    }

    public List<CitaDTO> obtenerCitasPaciente(String idPaciente) {
        return citaDAO.obtenerCitasPorPaciente(idPaciente);
    }

    public List<CitaDTO> obtenerTodasLasCitas() {
        return citaDAO.obtenerTodasLasCitas();
    }

    public void marcarAsistencia(String idCita) {
        CitaDTO cita = citaDAO.buscarCita(idCita);
        if (cita != null) {
            cita.setAsistio(true);
            citaDAO.modificarCita(cita);
        }
    }

    public void pagarCita(String idCita) {
        CitaDTO cita = citaDAO.buscarCita(idCita);
        if (cita != null) {
            cita.setPagada(true);
            citaDAO.modificarCita(cita);
        }
    }
    
    
    // Funcionalidades medicos
    
    public void agregarDisponibilidadMedico(String idMedico, String disponibilidad) {
        List<MedicoDTO> medicos = obtenerMedicos();
        for (MedicoDTO m : medicos) {
            if (m.getId().equals(idMedico)) {
                m.getDisponibilidad().add(disponibilidad);
                medicoDAO.guardarMedicos(); 
                break;
            }
        }
    }

    public List<String> obtenerDisponibilidadMedico(String idMedico) {
        List<MedicoDTO> medicos = obtenerMedicos();
        for (MedicoDTO m : medicos) {
            if (m.getId().equals(idMedico)) {
                return m.getDisponibilidad();
            }
        }
        return new ArrayList<>();
    }
    
    public List<CitaDTO> obtenerCitasMedico(String idMedico) {
        List<CitaDTO> todas = obtenerTodasLasCitas();
        List<CitaDTO> result = new ArrayList<>();
        for (CitaDTO c : todas) {
            if (c.getIdMedico().equals(idMedico)) {
                result.add(c);
            }
        }
        return result;
    }

	
}