package loopname.com.digitalspymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    EditText emailInicio;
    EditText contraseñaInicio;
    CheckBox guardar;
    EditText emailRegistro;
    EditText contraseñaRegistro;
    EditText contraseñaRegistro2;

    LinearLayout inicio;
    LinearLayout registro;
    LinearLayout paso1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInicio=(EditText)this.findViewById(R.id.emailInicio);
        contraseñaInicio=(EditText)this.findViewById(R.id.contraseñaInicio);
        guardar=(CheckBox)this.findViewById(R.id.cbGuardar);
        emailRegistro=(EditText)this.findViewById(R.id.emailRegistro);
        contraseñaRegistro=(EditText)this.findViewById(R.id.contraseñaRegistro);
        contraseñaRegistro2=(EditText)this.findViewById(R.id.contraseñaRegistro2);

        paso1=(LinearLayout)this.findViewById(R.id.Paso1);
        paso1.setVisibility(View.VISIBLE);
        inicio=(LinearLayout)this.findViewById(R.id.LayoutInicio);
        registro=(LinearLayout)this.findViewById(R.id.LayoutRegistro);
        inicio.setVisibility(View.GONE);
        registro.setVisibility(View.GONE);
    }

    public void iniciarSesion(View v){
        registro.setVisibility(View.GONE);
        inicio.setVisibility(View.VISIBLE);
    }

    public void registro(View v){
        inicio.setVisibility(View.GONE);
        registro.setVisibility(View.VISIBLE);
    }

    public void siguientePaso(View v){

        Intent intent=new Intent(this,StartActivity.class);
        this.startActivity(intent);
    }
}
