package net.proyecto.sigti.orden_servicio;

/**
 * Created by choqu_000 on 22/07/2015.
 */
public class Orden_Servicio {

    //Atributos
    private int id_orden_servicio;
    private int numero_wo;
    private int id_coordinador;
    private String documento;
    private String nombre;
    private String direccion;
    private int id_jornada;
    private int hora_resultado;
    private int id_categoria;
    private int id_servicio;
    private int codigo_ciudad;
    private int id_novedades;
    private String eventos;

    public Orden_Servicio(){}

    //Encapsulamiento o Propiedad
    public int getId_orden_servicio() {
        return id_orden_servicio;
    }

    public void setId_orden_servicio(int id_orden_servicio) {
        this.id_orden_servicio = id_orden_servicio;
    }

    public int getNumero_wo() {
        return numero_wo;
    }

    public void setNumero_wo(int numero_wo) {
        this.numero_wo = numero_wo;
    }

    public int getId_coordinador() {
        return id_coordinador;
    }

    public void setId_coordinador(int id_coordinador) {
        this.id_coordinador = id_coordinador;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getId_jornada() {
        return id_jornada;
    }

    public void setId_jornada(int id_jornada) {
        this.id_jornada = id_jornada;
    }

    public int getHora_resultado() {
        return hora_resultado;
    }

    public void setHora_resultado(int hora_resultado) {
        this.hora_resultado = hora_resultado;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getCodigo_ciudad() {
        return codigo_ciudad;
    }

    public void setCodigo_ciudad(int codigo_ciudad) {
        this.codigo_ciudad = codigo_ciudad;
    }

    public int getId_novedades() {
        return id_novedades;
    }

    public void setId_novedades(int id_novedades) {
        this.id_novedades = id_novedades;
    }

    public String getEventos() {
        return eventos;
    }

    public void setEventos(String eventos) {
        this.eventos = eventos;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
