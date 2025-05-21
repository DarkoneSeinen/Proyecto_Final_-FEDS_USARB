package model.dto;
import java.io.Serializable;

public class CitaDTO implements Serializable {
    private String idCita;
    private String idPaciente;
    private String idMedico;
    private String fechaHora;   // Ahora es String
    private boolean asistio;
    private boolean pagada;

    public CitaDTO(String idCita, String idPaciente, String idMedico, String fechaHora) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaHora = fechaHora;
        this.asistio = false;
        this.pagada = false;
    }

    public String getIdCita() { return idCita; }
    public String getIdPaciente() { return idPaciente; }
    public String getIdMedico() { return idMedico; }
    public String getFechaHora() { return fechaHora; }
    public boolean isAsistio() { return asistio; }
    public boolean isPagada() { return pagada; }
    public void setAsistio(boolean asistio) { this.asistio = asistio; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}