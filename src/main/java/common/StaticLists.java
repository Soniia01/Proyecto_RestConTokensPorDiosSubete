package common;

import domain.model.Autor;
import domain.model.Credenciales;
import domain.model.Libro;

import java.util.ArrayList;
import java.util.List;

public class StaticLists {
    //Autor
    public static List<Autor> autores = new ArrayList<>(List.of(
            new Autor("Haruki Murakami", 15),
            new Autor("J.K. Rowling", 20),
            new Autor("Stephen King", 70),
            new Autor("Paula Hawkins", 3),
            new Autor("George R.R. Martin", 15),
            new Autor("Gillian Flynn", 3),
            new Autor("Elena Ferrante", 8),
            new Autor("John Green", 7),
            new Autor("Donna Tartt", 4),
            new Autor("Philip Pullman", 30),
            new Autor("Kazuo Ishiguro", 15),
            new Autor("Margaret Atwood", 10),
            new Autor("Dan Brown", 20),
            new Autor("Yuval Noah Harari", 3),
            new Autor("Elena Poniatowska", 15),
            new Autor("Suzanne Collins", 10),
            new Autor("Jojo Moyes", 15),
            new Autor("Michelle Obama", 2),
            new Autor("Ken Follett", 30),
            new Autor("Neil Gaiman", 25)));
    //Libro
    public static List<Libro> libros = new ArrayList<>(List.of(
            new Libro("Tokio Blues", List.of("Ficción"), "Haruki Murakami"),
            new Libro("Harry Potter y la Piedra Filosofal", List.of("Fantasía"), "J.K. Rowling"),
            new Libro("It", List.of("Terror"), "Stephen King"),
            new Libro("La chica del tren", List.of("Suspenso"), "Paula Hawkins"),
            new Libro("Juego de Tronos", List.of("Fantasía épica"), "George R.R. Martin"),
            new Libro("Perdida", List.of("Suspenso psicológico"), "Gillian Flynn"),
            new Libro("La amiga estupenda", List.of("Ficción"), "Elena Ferrante"),
            new Libro("Bajo la misma estrella", List.of("Romance"), "John Green"),
            new Libro("El jilguero", List.of("Ficción"), "Donna Tartt"),
            new Libro("La brújula dorada", List.of("Fantasía"), "Philip Pullman"),
            new Libro("Los restos del día", List.of("Ficción histórica"), "Kazuo Ishiguro"),
            new Libro("El cuento de la criada", List.of("Distopía"), "Margaret Atwood"),
            new Libro("El código Da Vinci", List.of("Misterio"), "Dan Brown"),
            new Libro("Sapiens: De animales a dioses", List.of("No ficción"), "Yuval Noah Harari"),
            new Libro("La noche de Tlatelolco", List.of("Ensayo"), "Elena Poniatowska"),
            new Libro("Los juegos del hambre", List.of("Ciencia ficción"), "Suzanne Collins"),
            new Libro("Yo antes de ti", List.of("Romance"), "Jojo Moyes"),
            new Libro("Mi historia", List.of("Biografía"), "Michelle Obama"),
            new Libro("Los pilares de la Tierra", List.of("Ficción histórica"), "Ken Follett"),
            new Libro("American Gods", List.of("Fantasía contemporánea"), "Neil Gaiman"),
            new Libro("Escrito en el agua", List.of("Suspenso"), "Paula Hawkins"),
            new Libro("Choque de reyes", List.of("Fantasía épica"), "George R.R. Martin"),
            new Libro("Lugares oscuros", List.of("Suspenso psicológico"), "Gillian Flynn"),
            new Libro("Días de abandono", List.of("Ficción"), "Elena Ferrante"),
            new Libro("Ciudades de papel", List.of("Romance"), "John Green"),
            new Libro("El secreto", List.of("Ficción"), "Donna Tartt"),
            new Libro("La daga", List.of("Fantasía"), "Philip Pullman"),
            new Libro("Nunca me abandones", List.of("Ficción histórica"), "Kazuo Ishiguro"),
            new Libro("21 lecciones para el siglo XXI", List.of("No ficción"), "Yuval Noah Harari"),
            new Libro("Tinísima", List.of("Ensayo"), "Elena Poniatowska")));
    //Credenciales
    public static List<Credenciales> credenciales = new ArrayList<>(List.of(
            new Credenciales("9722af24-8579-4c0c-abdd-6ce43dc8e293","sonia","PBKDF2WithHmacSHA256:2048:HrkEvNhEC3sAsYNCixYdokP+t8NtWkelUs1kP5Hrti4=:NTjqMzugEG+OI13ZEZJUl1xlVmIlGcgQTgyp12/7m2I=","soniasf00@gmail.com",true,"zd-C0FzmPd5-w7rqzsdAVNrDkkBrk-Pm5UXpyYee6NY=","Admin")
    ));
}
