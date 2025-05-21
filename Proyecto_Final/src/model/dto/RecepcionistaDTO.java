package model.dto;
import java.io.Serializable;

public class RecepcionistaDTO implements Serializable {
    private String id;
    private String nombre;

    public RecepcionistaDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}