package Ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

interface Command {
    void execute();
    void undo();
}

class Documento {
    private String titulo;
    private List<String> notas = new ArrayList<>();

    public Documento(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void agregarNota(String nota) {
        notas.add(nota);
    }

    public String eliminarUltimaNota() {
        if (!notas.isEmpty()) {
            return notas.remove(notas.size() - 1);
        }
        return null;
    }

    public void mostrar() {
        System.out.println("titulo " + titulo);
        if (notas.isEmpty()) {
            System.out.println("sin notas registradas");
        } else {
            for (int i = 0; i < notas.size(); i++) {
                System.out.println((i + 1) + " " + notas.get(i));
            }
        }
    }
}

class CambiarTituloCommand implements Command {
    private Documento doc;
    private String nuevoTitulo;
    private String tituloAnterior;

    public CambiarTituloCommand(Documento doc, String nuevoTitulo) {
        this.doc = doc;
        this.nuevoTitulo = nuevoTitulo;
    }

    @Override
    public void execute() {
        tituloAnterior = doc.getTitulo();
        doc.setTitulo(nuevoTitulo);
        System.out.println("titulo cambiado a " + nuevoTitulo);
    }

    @Override
    public void undo() {
        doc.setTitulo(tituloAnterior);
        System.out.println("titulo restaurado a " + tituloAnterior);
    }
}

class AgregarNotaCommand implements Command {
    private Documento doc;
    private String nota;

    public AgregarNotaCommand(Documento doc, String nota) {
        this.doc = doc;
        this.nota = nota;
    }

    @Override
    public void execute() {
        doc.agregarNota(nota);
        System.out.println("nota agregada " + nota);
    }

    @Override
    public void undo() {
        doc.eliminarUltimaNota();
        System.out.println("nota revertida " + nota);
    }
}

class BorrarUltimaNotaCommand implements Command {
    private Documento doc;
    private String notaBorrada;

    public BorrarUltimaNotaCommand(Documento doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        notaBorrada = doc.eliminarUltimaNota();
        if (notaBorrada != null) {
            System.out.println("nota eliminada " + notaBorrada);
        } else {
            System.out.println("no hay notas para eliminar");
        }
    }

    @Override
    public void undo() {
        if (notaBorrada != null) {
            doc.agregarNota(notaBorrada);
            System.out.println("nota recuperada " + notaBorrada);
        }
    }
}

class GestorComandos {
    private Stack<Command> historial = new Stack<>();

    public void ejecutar(Command comando) {
        comando.execute();
        historial.push(comando);
    }

    public void deshacer() {
        if (!historial.isEmpty()) {
            System.out.println("desahciendo ultima accion");
            Command ultimo = historial.pop();
            ultimo.undo();
        } else {
            System.out.println("no hay acciones para deshacer");
        }
    }
}

public class AppEjercicio3 {
    public static void main(String[] args) {
        Documento doc = new Documento("notas iniciales");
        GestorComandos gestor = new GestorComandos();

        gestor.ejecutar(new CambiarTituloCommand(doc, "pendientes de laboratorio"));
        gestor.ejecutar(new AgregarNotaCommand(doc, "revisar codigo con Fiorela"));
        gestor.ejecutar(new AgregarNotaCommand(doc, "enviar avance a Miri"));
        gestor.ejecutar(new BorrarUltimaNotaCommand(doc));

        System.out.println("estado actual del documento");
        doc.mostrar();

        gestor.deshacer();
        gestor.deshacer();

        System.out.println("estado despues de deshacer");
        doc.mostrar();
    }
}