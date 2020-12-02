package android.teste.matheusaguilar.bafometro_b1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 10, RETURN_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviarDados(View view){
        EditText editPeso = (EditText) findViewById(R.id.pesoEditText);
        EditText editSexo = (EditText) findViewById(R.id.sexoEditText);
        EditText editCopos = (EditText) findViewById(R.id.coposEditText);
        EditText editJejum = (EditText) findViewById(R.id.jejumEditText);

        double peso;
        int copos;
        String sexo, jejum;

        try{
            peso = Double.parseDouble(editPeso.getText().toString());
        }
        catch(Exception e){
            Toast.makeText(this,"Peso com formato inválido!", Toast.LENGTH_LONG).show();
            return;
        }

        if(peso <= 0){
            Toast.makeText(this,"O peso deve ser maior do que 0!", Toast.LENGTH_LONG).show();
            return;
        }

        sexo = editSexo.getText().toString();

        try{
            copos = Integer.parseInt(editCopos.getText().toString());
        }
        catch(Exception e){
            Toast.makeText(this,"Número de copos inválido!", Toast.LENGTH_LONG).show();
            return;
        }

        jejum = editJejum.getText().toString();

        Bundle bundle = new Bundle();

        bundle.putDouble("peso", peso);
        bundle.putString("sexo", sexo);
        bundle.putInt("copos", copos);
        bundle.putString("jejum", jejum);

        Intent it = new Intent("ACAO_CALCULO_BAFOMETRO");
        it.putExtras(bundle);

        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int codReq, int codRet, Intent it){

        super.onActivityResult(codReq, codRet, it);

        if(it == null){
            Toast.makeText(this,"Falha ao recuperar os dados!", Toast.LENGTH_LONG).show();
            return;
        }
        else if(codReq == REQUEST_CODE && codRet == RETURN_CODE){

            double taxa = it.getDoubleExtra("taxa", 0.0);
            String classificacao = it.getStringExtra("classificacao");

            String msg = "Taxa de Alcoolemia: " + Double.toString(taxa) + "\n" + "Classificação: " + classificacao;

            Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        }
    }
}