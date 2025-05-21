# Proyecto Final - Clínica Buena Piel

## Descripción

Este proyecto es una aplicación de escritorio desarrollada como parte de la materia **Fundamento de Diseño de Software**. El objetivo es gestionar la información y operaciones de una clínica, permitiendo la administración de pacientes, médicos, recepcionistas y citas médicas.

## Tecnologías Utilizadas

- **Entorno de dearrollo**: Eclipse IDE.
- **Java 17**: Lenguaje principal de desarrollo.
- **Swing**: Para la creación de la interfaz gráfica de usuario (GUI).
- **Serialización de Objetos (Persistencia en Archivos)**: Los datos de pacientes, médicos, recepcionistas y citas se almacenan y recuperan mediante archivos binarios utilizando la serialización de objetos en Java.
- **Estructura MVC**: El proyecto está organizado siguiendo el patrón Modelo-Vista-Controlador para separar la lógica de negocio, la interfaz y el acceso a datos.

## Estructura del Proyecto

- `controller/`: Lógica de control y coordinación entre la vista y el modelo.
- `model/dao/`: Acceso y persistencia de datos (archivos binarios).
- `model/dto/`: Clases de transferencia de datos (DTO).
- `view/`: Interfaces gráficas para cada tipo de usuario (Paciente, Médico, Recepcionista).

## Autor

**Diego Alejandro Moreno Pelaez**

---

Este proyecto demuestra el uso de Java y Swing para aplicaciones de escritorio, así como la implementación de persistencia básica usando archivos, todo en el contexto de buenas prácticas de diseño de software.