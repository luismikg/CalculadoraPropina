package mx.ipn.cic.geo.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;        // Cantidad ingresada por el usuario.
    private double percent = 0.15;          // Porcentaje inicial de propina.
    private TextView amountTextView;        // Muestra el monto del consumo con formato.
    private TextView percentTextView;       // Muestra el porcentaje de propina.
    private TextView tipTextView;           // Muestra el monto de propina calculada.
    private TextView totalTextView;         // Muestra el monto total de la cuenta con formato.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los views.
        this.amountTextView = (TextView)this.findViewById(R.id.amountTextView);
        this.percentTextView = (TextView)this.findViewById(R.id.percentTextView);
        this.tipTextView = (TextView)this.findViewById(R.id.tipTextView);
        this.totalTextView = (TextView)this.findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));      // Establecer el texto a un valor de 0.
        totalTextView.setText(currencyFormat.format(0));    // Establecer el texto a un valor de 0.

        // Asignar el TextWatcher al amountEditText.
        EditText amountText = (EditText)this.findViewById(R.id.amountEditText);
        amountText.addTextChangedListener(amountEditTextWatcher);

        // Asignar el OnSeekBarChangeListeer al percentSeekBar.
        SeekBar percentSeekBar = (SeekBar)this.findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    // Calcula y despliega los montos de propina y total.
    private void calculate() {
        // Da formato al porcentaje y lo despliega en percentTextView.
        this.percentTextView.setText(percentFormat.format(this.percent));

        // Calcula la propina y el total.
        double tip = this.billAmount * this.percent;
        double total = this.billAmount + tip;

        // Despliega la propina y el total formateados como moneda.
        this.tipTextView.setText(this.currencyFormat.format(tip));
        this.totalTextView.setText(this.currencyFormat.format(total));
    }

    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;     // Establecer la variable de porcentaje acorde a progress.
            calculate();    // Calcular y desplegar la propina y total.
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // Listener object para los eventos cuando se modifica el texto de un EditText.
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {   // Obtener el monto del consumo y desplegar el valor con formato de moneda.
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch(NumberFormatException e)
            {   // Si s es nulo o un valor no numérico.
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
