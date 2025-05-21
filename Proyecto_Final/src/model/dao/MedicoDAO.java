package model.dao;

import java.util.*;

import model.dto.MedicoDTO;

import java.io.*;

public class MedicoDAO {
    private static final String FILE_PATH = "c://data/medicos.dat";
    private List<MedicoDTO> medicos;

    public MedicoDAO() {
        medicos = cargarMedicos();
    }

    @SuppressWarnings("unchecked")
    private List<MedicoDTO> cargarMedicos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<MedicoDTO>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void guardarMedicos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(medicos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarMedico(MedicoDTO medico) {
        medicos.add(medico);
        guardarMedicos();
    }

    public List<MedicoDTO> listarMedicos() {
        return new ArrayList<>(medicos);
    }
    
    
}