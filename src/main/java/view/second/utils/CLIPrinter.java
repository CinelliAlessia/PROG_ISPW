package view.second.utils;

public class CLIPrinter {

    private CLIPrinter(){}

    public static void print(String message){
        System.out.print(message);
    }
    public static void println(String message){
        System.out.println(message);
    }

    public static void errorPrint(String message){
        // Sequenza di escape ANSI per il colore rosso
        String redColorCode = "\u001B[31m";

        // Sequenza di escape ANSI per ripristinare il colore predefinito
        String resetColorCode = "\u001B[0m";

        // Stampa il testo in rosso
        System.out.print(redColorCode + message + resetColorCode);
    }
}
