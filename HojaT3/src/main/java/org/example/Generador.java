package org.example;

import java.io.*;
import java.util.*;

public class Generador {
    private static final String FILE_NAME = "numeros.csv";
    private static final int NUM_COUNT = 5000;

    public static void main(String[] args) {
        generarNumerosAleatorios();
        Integer[] numeros = leerNumerosDesdeArchivo();

        if (numeros != null) {
            probarAlgoritmos(numeros);
        }
    }

    private static void generarNumerosAleatorios() {
        Random rand = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < NUM_COUNT; i++) {
                writer.write(rand.nextInt(10000) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    private static int[] convertirAEnteros(Integer[] arr) {
        return Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
    }

    private static float[] convertirAFlotantes(Integer[] arr) {
        float[] flotantes = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            flotantes[i] = arr[i].floatValue(); // Conversión directa
        }
        return flotantes;
    }
    private static Integer[] leerNumerosDesdeArchivo() {
        List<Integer> numeros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                numeros.add(Integer.parseInt(linea.trim()));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
        return numeros.toArray(new Integer[0]);
    }

    private static void probarAlgoritmos(Integer[] numeros) {
        probarOrdenamiento("Insertion Sort", convertirAEnteros(numeros), Sorts::insertionSort);
        probarOrdenamiento("Merge Sort", convertirAEnteros(numeros), arr -> Sorts.mergeSort(arr, 0, arr.length - 1));
        probarOrdenamiento("Quick Sort", convertirAEnteros(numeros), arr -> Sorts.quickSort(arr, 0, arr.length - 1));
        probarOrdenamiento("Radix Sort", convertirAEnteros(numeros), arr -> Sorts.radixSort(arr, arr.length));
        probarOrdenamiento("Counting Sort", convertirAEnteros(numeros), Sorts::countingSort);
        probarOrdenamientoFlotante("Bucket Sort", convertirAFlotantes(numeros), Sorts::bucketSort);
    }

    private static void probarOrdenamiento(String nombre, int[] arr, Ordenador ordenador) {
        System.out.println("Probando " + nombre + " con datos aleatorios...");
        medirTiempoDeEjecucion(arr, ordenador);
    }

    private static void probarOrdenamientoFlotante(String nombre, float[] arr, OrdenadorFlotante ordenador) {
        System.out.println("Probando " + nombre + " con datos aleatorios...");
        medirTiempoDeEjecucionFlotante(arr, ordenador);
    }

    private static void medirTiempoDeEjecucion(int[] arr, Ordenador ordenador) {
        long inicio = System.nanoTime();
        ordenador.ordenar(arr);
        long fin = System.nanoTime();
        System.out.println("Tiempo: " + (fin - inicio) / 1e6 + " ms");
    }

    private static void medirTiempoDeEjecucionFlotante(float[] arr, OrdenadorFlotante ordenador) {
        long inicio = System.nanoTime();
        ordenador.ordenar(arr);
        long fin = System.nanoTime();
        System.out.println("Tiempo: " + (fin - inicio) / 1e6 + " ms");
    }

    @FunctionalInterface
    interface Ordenador {
        void ordenar(int[] arr);
    }

    @FunctionalInterface
    interface OrdenadorFlotante {
        void ordenar(float[] arr);
    }
}
