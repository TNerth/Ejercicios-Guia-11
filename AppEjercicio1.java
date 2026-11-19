package Ejercicio1;
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void actualizar(Notificacion notificacion);
}

class Notificacion {
    private String tipo;
    private String mensaje;

    public Notificacion(String tipo, String mensaje) {
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }
}

class Usuario implements Observer {
    private String nombre;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void suscribirse(SistemaNotificaciones sistema) {
        sistema.suscribir(this);
    }

    public void desuscribirse(SistemaNotificaciones sistema) {
        sistema.desuscribir(this);
    }

    @Override
    public void actualizar(Notificacion notificacion) {
        System.out.println(nombre + " recibio notificacon de " + notificacion.getTipo() + " " + notificacion.getMensaje());
    }
}

class SistemaNotificaciones {
    private List<Observer> usuarios = new ArrayList<>();

    public void suscribir(Observer usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }

    public void desuscribir(Observer usuario) {
        usuarios.remove(usuario);
    }

    public void enviarNotificacion(Notificacion notificacion) {
        for (Observer usuario : usuarios) {
            usuario.actualizar(notificacion);
        }
    }
}

public class AppEjercicio1 {
    public static void main(String[] args) {
        SistemaNotificaciones sistema = new SistemaNotificaciones();

        Usuario u1 = new Usuario("Nyevm");
        Usuario u2 = new Usuario("Kristel");
        Usuario u3 = new Usuario("Nahaydi");

        u1.suscribirse(sistema);
        u2.suscribirse(sistema);

        System.out.println("enviando primera alerta");
        sistema.enviarNotificacion(new Notificacion("promocion", "descuento del 20 porciento en monitores"));

        u3.suscribirse(sistema);
        u1.desuscribirse(sistema);
        System.out.println("se suscribio Nahaydi y se desuscribio Nyevm");

        System.out.println("enviando segunda alerta");
        sistema.enviarNotificacion(new Notificacion("actualizacion de producto", "nuevo stock de teclados mecanicos"));
    }
}