package av39079.android.fer.hr.av39079.android.fer.hr.operations;

/**
 * Implementacije ovog sucelja reprezentiraju konkretne
 * aritmeticke operacije nad argumentima tipa double.
 * metoda calculate() racuna vrijednost odgovarajuce operacije.
 * @author jelena
 * Created by jelena on 03.07.15..
 */
public interface IOperation {

    /**
     * Metoda racuna vrijednost operacije nad proslijedenim vrijednostima.
     * Operacije su specificirane konkretnim implementacijama ovog sucelja.
     * U slucaju pogreske izbacuje prikladnu iznimku.
     * @param valA prva vrijednost.
     * @param valB druga vrijednost.
     * @return rezultat operacije.
     */
    public double calculate(double valA, double valB);

    /**
     * Metoda vraca naziv operacije.
     * @return naziv operacije.
     */
    public String toString();
}
