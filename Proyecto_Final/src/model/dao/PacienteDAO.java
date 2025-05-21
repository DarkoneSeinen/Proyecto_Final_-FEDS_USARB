package model.dao;

import java.util.*;

import model.dto.PacienteDTO;

import java.io.*;

public class PacienteDAO {
    private static final String FILE_PATH = "c://data/pacientes.dat";
    private List<PacienteDTO> pacientes;

    public PacienteDAO() {
        pacientes = cargarPacientes();
    }

    @SuppressWarnings("unchecked")
    private List<PacienteDTO> cargarPacientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<PacienteDTO>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void guardarPacientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(pacientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarPaciente(PacienteDTO paciente) {
        pacientes.add(paciente);
        guardarPacientes();
    }

    public PacienteDTO buscarPorId(String id) {
        for (PacienteDTO p : pacientes) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public List<PacienteDTO> listarPacientes() {
        return new ArrayList<>(pacientes);
    }

    // Puedes agregar métodos para editar y eliminar pacientes si lo requieres
}