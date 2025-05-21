package model.dto;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class MedicoDTO implements Serializable {
    private String id;
    private String nombre;
    private String especialidad;
    private List<String> disponibilidad; // ej: ["LUNES 08:00-12:00"]

    public MedicoDTO(String id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponibilidad = new ArrayList<>();
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public List<String> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(List<String> disponibilidad) { this.disponibilidad = disponibilidad; }
}