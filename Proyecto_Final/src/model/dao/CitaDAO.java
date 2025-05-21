package model.dao;

import java.util.*;

import model.dto.CitaDTO;

import java.io.*;

public class CitaDAO {
    private static final String FILE_PATH = "c://data/citas.dat";
    private List<CitaDTO> citas;

    public CitaDAO() {
        citas = cargarCitas();
    }

    @SuppressWarnings("unchecked")
    private List<CitaDTO> cargarCitas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<CitaDTO>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void guardarCitas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(citas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarCita(CitaDTO cita) {
        citas.add(cita);
        guardarCitas();
    }

    public void eliminarCita(String idCita) {
        citas.removeIf(c -> c.getIdCita().equals(idCita));
        guardarCitas();
    }

    public void modificarCita(CitaDTO citaModificada) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getIdCita().equals(citaModificada.getIdCita())) {
                citas.set(i, citaModificada);
                break;
            }
        }
        guardarCitas();
    }

    public List<CitaDTO> obtenerCitasPorPaciente(String idPaciente) {
        List<CitaDTO> result = new ArrayList<>();
        for (CitaDTO c : citas) {
            if (c.getIdPaciente().equals(idPaciente)) result.add(c);
        }
        return result;
    }

    public CitaDTO buscarCita(String idCita) {
        for (CitaDTO c : citas)
            if (c.getIdCita().equals(idCita)) return c;
        return null;
    }

    public List<CitaDTO> obtenerTodasLasCitas() {
        return new ArrayList<>(citas);
    }
}