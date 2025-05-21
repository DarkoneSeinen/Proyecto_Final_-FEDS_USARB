package model.dao;

import java.util.*;

import model.dto.RecepcionistaDTO;

import java.io.*;

public class RecepcionistaDAO {
    private static final String FILE_PATH = "c://data/recepcionista.dat";
    private List<RecepcionistaDTO> recepcionistas;

    public RecepcionistaDAO() {
        recepcionistas = cargarRecepcionistas();
    }

    @SuppressWarnings("unchecked")
    private List<RecepcionistaDTO> cargarRecepcionistas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<RecepcionistaDTO>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void guardarRecepcionistas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(recepcionistas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarRecepcionista(RecepcionistaDTO recepcionista) {
        recepcionistas.add(recepcionista);
        guardarRecepcionistas();
    }

    public List<RecepcionistaDTO> listarRecepcionistas() {
        return new ArrayList<>(recepcionistas);
    }
}