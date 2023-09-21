package com.example.crud_retrofit.dto;

public class ProductoDTO {
    private String nombre;
    private  int precio;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
