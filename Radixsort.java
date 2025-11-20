package RADIXSORT;

import java.io.*;
import java.util.*;

public class Radixsort {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Leer archivo y regresar lista de enteros 
    public List<Integer> fileToList(String fileName) {
        List<Integer> datos = new ArrayList<>();
        File file;
        FileReader reader;
        BufferedReader buffer;
        String linea;

        try {
            file = new File("C:\\archivos\\" + fileName);
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            while ((linea = buffer.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                for (String p : partes) {
                    datos.add(Integer.parseInt(p));
                }
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error al leer archivo: " + e.toString());
        }

        return datos;
    }

    // Guardar arreglo ordenado 
    public void writeArrayToFile(String fileName, int[] array) {
        FileWriter file;
        PrintWriter writer;

        try {
            file = new FileWriter("C:\\archivos\\" + fileName);
            writer = new PrintWriter(file);

            for (int num : array) {
                writer.println(num);
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Error al crear archivo: " + e.toString());
        }
    }

    // Obtener el máximo 
    public int obtenerMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        return max;
    }

    // Radix Sort 
    public void radixSort(int[] arr) {
        int max = obtenerMax(arr);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    // Counting Sort para Radix
    public void countingSort(int[] arr, int exp) {
        int[] salida = new int[arr.length];
        int[] conteo = new int[10];

        for (int num : arr) {
            conteo[(num / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            int digito = (arr[i] / exp) % 10;
            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;
        }

        System.arraycopy(salida, 0, arr, 0, arr.length);
    }

    //Método principal del proceso
    public void ordenarArchivo() {
        try {
            System.out.print("Nombre del archivo a ordenar: ");
            String entrada = br.readLine();

            System.out.print("Nombre del archivo de salida: ");
            String salida = br.readLine();

            // Leer archivo
            List<Integer> lista = fileToList(entrada);
            int[] array = lista.stream().mapToInt(i -> i).toArray();

            // Ordenar
            radixSort(array);

            // Guardar archivo
            writeArrayToFile(salida, array);

            System.out.println("Ordenamiento completado. Archivo guardado como: " + salida);

        } catch (IOException e) {
            System.out.println("Error al leer datos del usuario: " + e.toString());
        }
    }

    public static void main(String[] args) {
        Radixsort app = new Radixsort();
        app.ordenarArchivo();
    }
}
