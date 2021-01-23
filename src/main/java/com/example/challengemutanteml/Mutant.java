package com.example.challengemutanteml;

import java.util.Collections;
import java.util.Objects;

public class Mutant {
    public final static String ADNVacioException = "El ADN no puede ser vacío";
    public final static String LetrasNoAceptadasException = "Las letras aceptadas son A, T, C y G";
    public final static String MatrizNoCuadradaException = "La matriz de ADN debe ser cuadrada";

    public static int CantidadLetrasMutante = 4;

    private static void validarADN(String[] adnList) throws Exception {
        for (String adn : adnList) {
            if (Objects.isNull(adn) || adn.equals("")) {
                throw new Exception(ADNVacioException);
            }

            if (!adn.matches("[ATCG]+")) {
                throw new Exception(LetrasNoAceptadasException);
            }

            if (adn.length() != adnList.length) {
                throw new Exception(MatrizNoCuadradaException);
            }
        }
    }

    private static boolean contieneSecuenciaMutante(String adn) {
        String ARepeat = String.join("", Collections.nCopies(CantidadLetrasMutante, "A"));
        String TRepeat = String.join("", Collections.nCopies(CantidadLetrasMutante, "T"));
        String CRepeat = String.join("", Collections.nCopies(CantidadLetrasMutante, "C"));
        String GRepeat = String.join("", Collections.nCopies(CantidadLetrasMutante, "G"));

        return adn.contains(ARepeat) || adn.contains(TRepeat) || adn.contains(CRepeat) || adn.contains(GRepeat);
    }

    private static int cantSecuenciasOblicuas(String[] adnList, int fila) {
        int cantSecuenciasMutantes = 0;

        for (int i = 0; i < adnList.length; i++) {
            // Estas variables se usan para que si está en una fila que no sea la primera, le permita seguir analizando esa secuencia diagonal
            boolean auxDerecha = false;
            boolean auxIzquierda = false;

            String secuenciaOblicuaDerecha = "";
            String secuenciaOblicuaIzquierda = "";

            for (int j = 0; j < adnList.length; j++) {
                char letra;

                // Analizo oblicuo hacia la derecha
                // Chequeo que no se pase de los límites y que solo analice elementos de la primer fila o primer columna (para no pasar por una misma secuencia más de una vez)
                if (fila + j < adnList.length && i + j < adnList.length && (fila == 0 || fila != 0 && i == 0 || auxDerecha)) {
                    letra = adnList[fila+j].charAt(i+j);

                    secuenciaOblicuaDerecha += letra;

                    // Chequeo que haya llegado al límite
                    if (fila + j == adnList.length - 1 || i + j == adnList.length - 1) {
                        if (contieneSecuenciaMutante(secuenciaOblicuaDerecha))
                            cantSecuenciasMutantes++;
                    }

                    // Habilito a que pueda seguir analizando esta diagonal
                    auxDerecha = true;
                }


                // Analizo oblicuo hacia la izquierda
                // Chequeo que no se pase de los límites y que solo analice elementos de la primer fila o última columna (para no pasar por una misma secuencia más de una vez)
                if (fila + j < adnList.length && i - j >= 0 && (fila == 0 || fila != 0 && i == adnList.length - 1 || auxIzquierda)) {
                    letra = adnList[fila+j].charAt(i-j);

                    secuenciaOblicuaIzquierda += letra;

                    // Chequeo que haya llegado al límite
                    if (fila + j == adnList.length - 1 || i - j == 0) {
                        if (contieneSecuenciaMutante(secuenciaOblicuaIzquierda))
                            cantSecuenciasMutantes++;
                    }

                    // Habilito a que pueda seguir analizando esta diagonal
                    auxIzquierda = true;
                }
            }
        }

        return cantSecuenciasMutantes;
    }

    private static int cantSecuenciasVerticales(String[] adnList, int fila) {
        StringBuilder secuenciaVertical = new StringBuilder();

        for (int j = 0; j < adnList.length; j++) {
            char letra = adnList[j].charAt(fila);

            secuenciaVertical.append(letra);

            // Una vez que armé el ADN vertical, me fijo si contiene la secuencia mutante
            if (j == adnList.length - 1) {
                if (contieneSecuenciaMutante(secuenciaVertical.toString()))
                    return 1;
                else
                    return 0;
            }
        }

        return 0;
    }

    private static int cantSecuenciasHorizontales(String[] adnArray, int fila) {
        String adn = adnArray[fila];

        if (contieneSecuenciaMutante(adn))
            return 1;
        else
            return 0;
    }

    private static int cantSecuenciasMutantes(String[] adnArray) {
        int cantSecuenciasMutantes = 0;
        int i = 0;

        // Si tiene por lo menos dos secuencias mutantes, ya alcanza para decir que es mutante
        while (cantSecuenciasMutantes < 2 && i < adnArray.length) {
            cantSecuenciasMutantes += cantSecuenciasHorizontales(adnArray, i);

            if (cantSecuenciasMutantes >= 2)
                return cantSecuenciasMutantes;

            cantSecuenciasMutantes += cantSecuenciasVerticales(adnArray, i);

            if (cantSecuenciasMutantes >= 2)
                return cantSecuenciasMutantes;

            cantSecuenciasMutantes += cantSecuenciasOblicuas(adnArray, i);

            i++;
        }

        return cantSecuenciasMutantes;
    }

    public static boolean isMutant(String[] adnList, int cantLetrasMutante) throws Exception {
        CantidadLetrasMutante = cantLetrasMutante;

        int cantSecuenciasMutantes;

        // Valido que ninguna fila esté vacía, tenga letras no admitidas o sea una matriz no cuadrada
        validarADN(adnList);

        // Obtengo la cantidad de secuencias mutantes encontradas de manera horizontal, vertical y oblicua
        cantSecuenciasMutantes = cantSecuenciasMutantes(adnList);

        return cantSecuenciasMutantes >= 2;
    }
}