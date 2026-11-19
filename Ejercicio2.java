package Ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Producto {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}

interface EstrategiaDescuento {
    double calcularPrecioFinal(List<Producto> productos);
}

class SinDescuento implements EstrategiaDescuento {
    @Override
    public double calcularPrecioFinal(List<Producto> productos) {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }
}

class DescuentoFijo implements EstrategiaDescuento {
    @Override
    public double calcularPrecioFinal(List<Producto> productos) {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total * 0.90;
    }
}

class DescuentoPorcentual implements EstrategiaDescuento {
    @Override
    public double calcularPrecioFinal(List<Producto> productos) {
        double total = 0;
        boolean hayDosIguales = false;
        for (int i = 0; i < productos.size(); i++) {
            total += productos.get(i).getPrecio();
            for (int j = i + 1; j < productos.size(); j++) {
                if (productos.get(i).getNombre().equalsIgnoreCase(productos.get(j).getNombre())) {
                    hayDosIguales = true;
                }
            }
        }
        if (hayDosIguales) {
            return total * 0.70;
        }
        return total;
    }
}

class DescuentoPorcentualAcumulado implements EstrategiaDescuento {
    @Override
    public double calcularPrecioFinal(List<Producto> productos) {
        if (productos.isEmpty()) {
            return 0;
        }
        double total = 0;
        double precioMenor = productos.get(0).getPrecio();
        for (Producto p : productos) {
            total += p.getPrecio();
            if (p.getPrecio() < precioMenor) {
                precioMenor = p.getPrecio();
            }
        }
        if (productos.size() >= 3) {
            return total - (precioMenor * 0.50);
        }
        return total;
    }
}

class CalculadoraDePrecios {
    private EstrategiaDescuento estrategia;

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(List<Producto> productos) {
        return estrategia.calcularPrecioFinal(productos);
    }
}

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CalculadoraDePrecios calculadora = new CalculadoraDePrecios();
        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto("mouse", 100.0));
        productos.add(new Producto("mouse", 100.0));
        productos.add(new Producto("pad", 40.0));

        int opcion = -1;

        do {
            System.out.println("menu de estrategias de descuento");
            System.out.println("1 agregar producto");
            System.out.println("2 sin descuento");
            System.out.println("3 descuento fijo 10 porciento");
            System.out.println("4 descuento porcentual 30 porciento por 2 iguales");
            System.out.println("5 descuento porcentual acumulado 50 porciento al mas barato");
            System.out.println("0 salir");
            System.out.println("elija una opcoin");

            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("ingrese nombre del producto");
                String nombre = sc.nextLine();
                System.out.println("ingrese precio del producto");
                double precio = Double.parseDouble(sc.nextLine());
                productos.add(new Producto(nombre, precio));
                System.out.println("producto agregado total productos " + productos.size());
            } else if (opcion == 2) {
                calculadora.setEstrategia(new SinDescuento());
                System.out.println("precio final sin descuento " + calculadora.calcular(productos));
            } else if (opcion == 3) {
                calculadora.setEstrategia(new DescuentoFijo());
                System.out.println("precio final con descuento fijo " + calculadora.calcular(productos));
            } else if (opcion == 4) {
                calculadora.setEstrategia(new DescuentoPorcentual());
                System.out.println("precio final con descuento porcentual " + calculadora.calcular(productos));
            } else if (opcion == 5) {
                calculadora.setEstrategia(new DescuentoPorcentualAcumulado());
                System.out.println("precio final con descuento acumulado " + calculadora.calcular(productos));
            } else if (opcion != 0) {
                System.out.println("opcion no valida");
            }
        } while (opcion != 0);

        System.out.println("programa finalizado");
        sc.close();
    }
}