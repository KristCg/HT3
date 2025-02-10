package org.example;
import java.util.Scanner;

public class Generador {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op;

        System.out.println("¿Desea iniciar el programa?");
        System.out.println("1. Si");
        System.out.println("2. No");
        op = sc.nextInt();
        if (op == 1) {
            System.out.println("Generando números aleatorios...");
        } else if (op == 2) {
            System.out.println("Generando números");
        } else {
            System.out.println("Opción no valida");

        }
    }
}
}
