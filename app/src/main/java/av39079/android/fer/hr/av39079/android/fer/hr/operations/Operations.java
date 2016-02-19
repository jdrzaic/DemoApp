package av39079.android.fer.hr.av39079.android.fer.hr.operations;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred sluzi za dohvat konkretnih implementacija IOperation
 * sucelja. Mapirane su pod rednim brojevima, npr. zbrajanje-0,
 * oduzimanje-1, mnozenje-2, dijeljenje-3.
 * Navedene operacije trenutno su dostupne.
 * @author jelena
 * Created by jelena on 03.07.15..
 */
public class Operations {

    /**
     * Identifikator zbrajanja
     */
    public static final int ADD = 0;

    /**
     * Identifikator oduzimanja
     */
    public static final int SUB = 1;

    /**
     * Identifikator mnozenja
     */
    public static final int MULT = 2;

    /**
     * Identifikator dijeljenja
     */
    public static final int DIV = 3;

    /**
     * Mapa identifikatora i pripadajucih operacija
     */
    private static Map<Integer, IOperation> operationMap = new HashMap<>();

    /**
     * Prihvatljivo odstupanje od 0,
     * za provjeru je li neki broj 0.
     */
    private static final double DELTA = 10E-8;

    /**
     * Inicijalizacija mape operacija.
     */
    static {

        /*
         * Dodavanje operacije zbrajanja.
         */
        operationMap.put(ADD, new IOperation() {
            @Override
            public double calculate(double valA, double valB) {
                return valA + valB;
            }
            @Override
            public String toString() {
                return "zbrajanje";
            }
        });

        /*
         * Dodavanje operacije oduzimanja.
         */
        operationMap.put(SUB, new IOperation() {
            @Override
            public double calculate(double valA, double valB) {
                return valA - valB;
            }
            @Override
            public String toString() {
                return "oduzimanje";
            }
        });

        /*
         * Dodavanje operacije mnozenja.
         */
        operationMap.put(MULT, new IOperation() {
            @Override
            public double calculate(double valA, double valB) {
                return valA * valB;
            }

            @Override
            public String toString() {
                return "množenje";
            }
        });

        /*
         * Dodavanje operacije djeljenja.
         */
        operationMap.put(DIV, new IOperation() {

            @Override
            public double calculate(double valA, double valB) {
                //djelimo s 0
                if(valB < DELTA) {
                    throw new IllegalArgumentException("Dijeljenje s nulom!");
                }
                return valA / valB;
            }
            @Override
            public String toString() {
                return "dijeljenje";
            }
        });
    }

    /**
     * Metoda dohvaca operaciju mapiranu na proslijedeni parametar.
     * @param id parametar za koji dohvacamo pripadnu operaciju.
     * @return mapirana operacija, ako proslijedeni id postoji u mapi,
     * null inace.
     */
    public static IOperation getById(int id) {
        return operationMap.get(id);
    }
}
