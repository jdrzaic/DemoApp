package av39079.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import av39079.android.fer.hr.av39079.android.fer.hr.operations.IOperation;
import av39079.android.fer.hr.av39079.android.fer.hr.operations.Operations;


/**
 * Activity sluzi za izracunavanje odgovarajucih aritmetickih operacija.
 * Prikazuje rezultat operacije koja je provedena, te gumb koji omogucava
 * povratak na HostActivity, kojem proslijeduje rezultat i eventualne pogreske.
 * @author jelena
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * Pohranjivanje rezultata u Intent
     */
    public static final String EXTRAS_RESULT = "result";

    /**
     * Labela u koju ispisujemo rezultat operacije.
     */
    private TextView tvLabel;

    /**
     * Rezultat operacije.
     */
    private double result;

    /**
     * Button za povratak na HostActivity.
     */
    private Button btnReturn;

    /**
     * Tekst pogreske prilikom racunanja.
     */
    private String error = "";

    /**
     * Pohranjivanje greske u Intent
     */
    public static String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculus);

        tvLabel = (TextView) findViewById(R.id.tvLabel);

        btnReturn = (Button) findViewById(R.id.btnReturn);

        Bundle mapa = getIntent().getExtras();

        double valA = 1.0 * mapa.getInt(HostActivity.EXTRAS_VALA, 0);
        double valB = 1.0 * mapa.getInt(HostActivity.EXTRAS_VALB, 0);

        //dphvati id operacije, pa pomocu njega dodi do operacije.
        int operationId = mapa.getInt(HostActivity.OPERATION_ID, 0);

        result = obtain(valA, valB, operationId);
        //nema pogreske
        if(error.length() == 0) {
            tvLabel.setText("Rezultat operacije " + Operations.getById(operationId) + " je: " + result);
        }else{
            tvLabel.setText("Greska: " + error);
        }


        btnReturn.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra(EXTRAS_RESULT, result);
                    i.putExtra(ERROR, error);
                    setResult(RESULT_OK, i);
                    finish();
                }
            });

    }

    /**
     * Dohvati rezultat operacije s id-om operationId,
     * nad proslijedenim parametrima valA i valB,
     * respektivno.
     * @param valA prvi parametar operacije
     * @param valB drugi parametar operacije
     * @param operationId identifikator konkretne operacije
     * @return rezultat operacije, ako se dogodila greska,
     * vracamo 0.
     */
    private double obtain(double valA, double valB, int operationId) {

        IOperation operation = Operations.getById(operationId);
        try {
            return operation.calculate(valA,valB);

        }catch (Exception e) {
            error = e.getMessage();
            return 0;
        }
    }
}
