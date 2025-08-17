// Ejercicio 1 – Inventario y Ventas de Tienda
// Autor: Andrew Barbosa
// Fecha: 2025

import java.util.*;
import java.util.stream.Collectors;

// Clase Producto: representa un producto
class Producto {
    String nombre;
    String categoria;
    double precio;
    int stock;
    int vendidos;

    public Producto(String nombre, String categoria, double precio, int stock, int vendidos) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.vendidos = vendidos;
    }

    @Override
    public String toString() {
        return nombre + " | " + categoria + " | $" + precio + " | stock: " + stock + " | vendidos: " + vendidos;
    }

    // Getters para ordenar
    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}

// Clase Tienda: maneja la lista de productos
class Tienda {
    List<Producto> productos;

    public Tienda(List<Producto> productos) {
        this.productos = productos;
    }

    // 1. Imprimir productos de electrónica con stock > 0
    public void imprimirElectronicosConStock() {
        System.out.println("=== Electrónica con stock disponible ===");
        productos.stream()
                .filter(p -> p.categoria.equalsIgnoreCase("Electronics") && p.stock > 0)
                .sorted(Comparator.comparing(p -> p.nombre))
                .forEach(System.out::println);
    }

    // 2. Aumentar precio en Hogar si stock < 5
    public void aumentarPreciosHogar() {
        productos.stream()
                .filter(p -> p.categoria.equalsIgnoreCase("Home") && p.stock < 5)
                .forEach(p -> p.precio *= 1.10);
    }

    // 3. Calcular ingresos por categoría
    public Map<String, Double> calcularIngresosPorCategoria() {
        Map<String, Double> ingresos = new HashMap<>();
        for (Producto p : productos) {
            double total = p.precio * p.vendidos;
            ingresos.put(p.categoria, ingresos.getOrDefault(p.categoria, 0.0) + total);
        }
        return ingresos;
    }

    // 4. Mostrar categoría con más ingresos
    public void imprimirCategoriaMayorIngreso() {
        Map<String, Double> ingresos = calcularIngresosPorCategoria();
        String maxCategoria = Collections.max(ingresos.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("💰 Categoría con mayor ingreso: " + maxCategoria + " ($" + ingresos.get(maxCategoria) + ")");
    }

    // 5. Ordenar productos por precio desc y stock asc
    public List<String> obtenerProductosOrdenadosPorPrecioYStock() {
        return productos.stream()
                .sorted(Comparator.comparing(Producto::getPrecio).reversed()
                        .thenComparing(Producto::getStock))
                .map(p -> p.nombre)
                .collect(Collectors.toList());
    }
}

// Clase Principal: donde se prueba el programa
public class Principal {
    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>();

        // Productos de ejemplo
        productos.add(new Producto("Laptop", "Electronics", 1200.0, 10, 5));
        productos.add(new Producto("Smartphone", "Electronics", 800.0, 0, 20));
        productos.add(new Producto("TV", "Electronics", 1500.0, 3, 8));
        productos.add(new Producto("Aspiradora", "Home", 200.0, 2, 15));
        productos.add(new Producto("Sofá", "Home", 500.0, 10, 4));
        productos.add(new Producto("Camisa", "Clothing", 30.0, 50, 100));

        Tienda tienda = new Tienda(productos);

        // 1
        tienda.imprimirElectronicosConStock();

        // 2
        tienda.aumentarPreciosHogar();
        System.out.println("\n=== Productos después de ajuste de precios ===");
        productos.forEach(System.out::println);

        // 3
        System.out.println("\n=== Ingresos por categoría ===");
        tienda.calcularIngresosPorCategoria().forEach((cat, ing) ->
                System.out.println(cat + ": $" + ing));

        // 4
        tienda.imprimirCategoriaMayorIngreso();

        // 5
        System.out.println("\n=== Productos ordenados por precio (desc) y stock (asc) ===");
        tienda.obtenerProductosOrdenadosPorPrecioYStock().forEach(System.out::println);
    }
}
