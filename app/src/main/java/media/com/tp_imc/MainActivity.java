package media.com.tp_imc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Bouton de calcul
    private Button boutonCalcul = null;
    //Bouton de remise à zéro des données
    private Button boutonRaz = null;
    //Poids
    private EditText poids = null;
    //Taille
    private EditText taille = null;
    //Unité (Mètre ou Centimètre)
    private RadioGroup unite = null;
    //Eloge
    private CheckBox eloge = null;
    //Résultat
    private TextView resultat = null;
    //Informations
    private TextView infos = null;

    //Résultat si la checkbox est cochée
    private String defEloge = "Quel IMC formidable ! Les dieux ne sont pas aussi bien.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poids = (EditText)findViewById(R.id.poids);
        taille = (EditText)findViewById(R.id.taille);
        unite = (RadioGroup)findViewById(R.id.group);
        eloge = (CheckBox)findViewById(R.id.checkbox);
        resultat = (TextView)findViewById(R.id.resultat);
        infos = (TextView)findViewById(R.id.informations);

        //Action sur le bouton de calcul de l'IMC
        boutonCalcul = (Button)findViewById(R.id.calcul);
        boutonCalcul.setOnClickListener(this);

        //Action sur le bouton de Remise à Zéro
        boutonRaz = (Button)findViewById(R.id.raz);
        boutonRaz.setOnClickListener(this);

        //Action sur les changements des tailles et poids
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultat.setText(R.string.resultat);
            infos.setText(R.string.informations);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.calcul:
                //On vérifie que le poids, la taille et l'unité sont rentrés
                if(!poids.getText().toString().isEmpty() && !taille.getText().toString().isEmpty() && unite.getCheckedRadioButtonId() != -1){
                    //On vérifie qu'on a bien indiqué l'unité
                    if(taille.getText().toString().equals("0")){
                        Toast toast = Toast.makeText(MainActivity.this, "Une taille de 0 ? Really ?", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        //Si on est en mètre, on lance le calcul et met à jour le resultat
                        if(unite.getCheckedRadioButtonId()==R.id.radio1){
                            float fPoids = Float.parseFloat(poids.getText().toString());
                            float fTaille = Float.parseFloat(taille.getText().toString());

                            float valeur = fPoids/(float)Math.pow(fTaille, 2);

                            resultat.setText(getString(R.string.resultat)+" "+valeur);
                        }
                        //Sinon on converti les centimètres en mètre et on calcul le résultat
                        else{
                            float tailleM = Float.parseFloat(taille.getText().toString());
                            float fPoids = Float.parseFloat(poids.getText().toString());
                            //On converti la taille en mètre
                            tailleM = tailleM/100;

                            float valeur = fPoids/(float)Math.pow(tailleM, 2);

                            resultat.setText(getString(R.string.resultat)+" "+valeur);
                            infos.setText("");
                        }

                        //On regarde si la checkbox de la "mega fonction" est cliquée
                        if(eloge.isChecked()){
                            infos.setText(defEloge);
                        }
                    }
                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this, "Veuillez rentrer votre poids, votre taille et son unité pour faire le calcul", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case R.id.raz:
                resultat.setText(R.string.resultat);
                infos.setText(R.string.informations);
                poids.getText().clear();
                taille.getText().clear();
                break;
        }
    }
}
