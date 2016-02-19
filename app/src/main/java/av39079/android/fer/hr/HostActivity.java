package av39079.android.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import av39079.android.fer.hr.av39079.android.fer.hr.operations.Operations;

/**
 * Activity nudi mogucnost unosa dva broja te racunanje
 * vrijednosti operacija zbrajanja, oduzimanja, mnozenja i
 * djeljenja nad tim brojevima.
 * Moguce je izvjestaj o provedenoj operaciji poslati mailom,
 * kao i izracunati vrijednost na googleu.
 * Moguce je i pozvati kontakt.
 * @author jelena
 */
public class HostActivity extends AppCompatActivity {

    /**
     * Koristeni JMBAG.
     */
    private final String JMBAG = "1191227371";

    /**
     * Primatelj maila s izvjestajem o operaciji.
     */
    private  static final String RECIPIENT = "ana.baotic@infinum.com";

    /**
     * Pohrana prve vrijednosti u intent
     */
    public static final String EXTRAS_VALA = "VALA";

    /**
     * Pohrana druge vrijednosti u intent
     */
    public static final String EXTRAS_VALB = "VALB";

    /**
     * TextView u kojem prikazujemo rezultat operacije, ili
     * '-' ako je doslo do pogreske.
     */
    private TextView tvOperation;

    /**
     * TextView za prikaz informacije o pogreski.
     */
    private TextView tvError;

    /**
     * EditText za unos prve varijable
     */
    private EditText etVariableA;

    /**
     * EditText za unos druge varijable
     */
    private EditText etVariableB;

    /**
     * Gumb za poziv
     */
    private Button btnCall;

    /**
     * Gumb za racunanje u googleu
     */
    private Button btnOpen;

    /**
     * Gumb za slanje maila
     */
    private Button btnSend;

    /**
     * RadioGroup, buttoni koji predstavljaju operacije
     */
    private RadioGroup operations;

    /**
     * Poruka o neupisanoj vrijednosti
     */
    public static final String ERROR_EMPTY = "Unesite vrijednosti s kojima želite računati!";

    /**
     * Pohrana identifikatora operacije u intent
     */
    public static final String OPERATION_ID = "OPID";

    /**
     * Lista pogresaka
     */
    public static List<String> errors = new ArrayList<>();

    /**
     * Rezultat operacije
     */
    private double result;

    /**
     * Greska kod izracunavanja izraza
     */
    private String operationError = "";

    /**
     * Index selektiranog gumba operacije
     */
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);

        tvOperation = (TextView) findViewById(
                R.id.tvSum);
        //na pocetku nemamo rezultat
        tvOperation.setText("Rezultat operacije je:-");
        tvError = (TextView) findViewById(R.id.tvError);
        etVariableA = (EditText) findViewById(
                R.id.etFirstVariable);
        etVariableB = (EditText) findViewById(
                R.id.etSecondVariable);
        Button btnCalculate = (Button) findViewById(
                R.id.btnCalculate);
        //operacije
        operations = (RadioGroup) findViewById(R.id.operations);

        btnCall = (Button) findViewById(R.id.btnCall);
        btnOpen = (Button) findViewById(R.id.btnView);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnCalculate.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String varA = etVariableA.getText()
                            .toString();
                    String varB = etVariableB.getText()
                            .toString();

                    int valueA = 0;
                    try {
                        valueA = Integer.parseInt(varA);
                    } catch (Exception e) {
                        errors.add(ERROR_EMPTY);
                    }
                    int valueB = 0;
                    try {
                        valueB = Integer.parseInt(varB);
                    } catch (Exception e) {
                        errors.add(ERROR_EMPTY);
                    }
                    int radioButtonID = operations.getCheckedRadioButtonId();
                    View radioButton = operations.findViewById(radioButtonID);
                    index = operations.indexOfChild(radioButton);

                    Intent i = new Intent(
                            HostActivity.this,
                            CalculusActivity.class);

                    i.putExtra(EXTRAS_VALA, valueA);
                    i.putExtra(EXTRAS_VALB, valueB);
                    i.putExtra(OPERATION_ID, index);

                    startActivityForResult(i, 666);

                }
            });

        btnCall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(
                                Intent.ACTION_CALL,
                                Uri.parse(
                                        "tel:09912345567"));
                        startActivity(i);
                    }
                });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                //prikaz smislenih appova za slanje maila
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{RECIPIENT});

                i.putExtra(Intent.EXTRA_SUBJECT, JMBAG +  ": dz report");

                String body = "Rezultat operacije " + Operations.getById(index) + " je: ";
                if(operationError.length() > 0) {
                    body += "- \n";
                    body += "Izvođenje je bilo neuspješno, uzrok:\n" + operationError;
                }else {
                    body += result;
                }

                i.putExtra(Intent.EXTRA_TEXT   , body);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HostActivity.this, "Ne postoje instalirani email klijenti", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnOpen.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String varA = etVariableA.getText()
                            .toString();
                    String varB = etVariableB.getText()
                            .toString();

                    int valueA = 0;
                    try {
                        valueA = Integer.parseInt(varA);
                    } catch (Exception ignorable) {
                    }
                    int valueB = 0;
                    try {
                        valueB = Integer.parseInt(varB);
                    } catch (Exception ignorable) {
                    }

                    Intent i = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://www.google.hr/?q="
                                            + valueA
                                            + "%2B"
                                            + valueB));
                    startActivity(i);
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode,
            int resultCode, Intent data) {
        if (requestCode == 666 && resultCode == RESULT_OK
                && data != null) {
            result = data.getDoubleExtra(CalculusActivity.EXTRAS_RESULT,
                    0.0);
            operationError = data.getStringExtra(CalculusActivity.ERROR);

            if(operationError.length() > 0) {
                tvOperation.setText("Rezultat operacije je:-");
                tvError.setText("Izvođenje je bilo neuspješno, uzrok: \n" + operationError);
            }else {
                tvOperation.setText("Rezultat operacije " + Operations.getById(index) + " je: " + String.valueOf(result));
                tvError.setText("");
            }
        }
    }
}
