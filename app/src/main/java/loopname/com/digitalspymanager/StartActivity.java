package loopname.com.digitalspymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import gestion.Posicion;

public class StartActivity extends AppCompatActivity
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        spinner = (Spinner) StartActivity.this.findViewById(R.id.spinner);
        ObtenerNombres nombres=new ObtenerNombres();
        nombres.execute();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ObtenerDatos datos=new ObtenerDatos();
                datos.execute(parent.getSelectedItem().toString());
            }
        });
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    class ObtenerNombres extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPostExecute(ArrayList<String> nombres) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(StartActivity.this, android.R.layout.simple_list_item_1, nombres);
            spinner.setAdapter(adapter);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            String host = "192.168.1.16";
            int puerto = 8000;
            ArrayList<String> nombres=new ArrayList<>();
            try {
                Socket sc = new Socket(host, puerto);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                //Envía código de aplicacion
                salida.println("ccc");


                //Recibe JSON
                BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
                String s=bf.readLine();
                System.out.println(s);
                JSONArray jarray=new JSONArray(s);
                int total=0;
                for(int i=0;i<jarray.length();i++){
                    JSONObject job=jarray.getJSONObject(i);
                    nombres.add(job.getString("nombre"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nombres;
        }
    }

    class ObtenerDatos extends AsyncTask<String,Void,ArrayList<Posicion>>{
        @Override
        protected void onPostExecute(ArrayList<Posicion> pos) {
            for(int i=0;i<pos.size();i++){
                final Posicion p=pos.get(i);
                AlertDialog.Builder cuadro=new AlertDialog.Builder(StartActivity.this);
                cuadro.setTitle("Localización");
                cuadro.setMessage("Ultima localizacion registrada de "+pos.get(i).getNombre()+" en lat "+pos.get(i).getLatitud()+" y lon "+pos.get(i).getLongitud());
                cuadro.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StartActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo://"+p.getLatitud()+","+p.getLongitud())));
                    }
                });
                cuadro.setNegativeButton(android.R.string.cancel,null);
                cuadro.show();
            }
        }

        @Override
        protected ArrayList<Posicion> doInBackground(String... params) {
            String host = "192.168.1.16";
            int puerto = 8000;
            ArrayList<Posicion> datos=new ArrayList<>();
            try {
                Socket sc = new Socket(host, puerto);
                PrintStream salida = new PrintStream(sc.getOutputStream());
                //Envía código de aplicacion
                salida.println("bbb");
                System.out.println(params[0]);
                salida.println(params[0]);

                //Recibe JSON
                BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
                String s=bf.readLine();
                System.out.println(s);
                JSONArray jarray=new JSONArray(s);
                int total=0;
                for(int i=0;i<jarray.length();i++){
                    JSONObject job=jarray.getJSONObject(i);
                    Posicion p=new Posicion(job.getString("nombre"),job.getDouble("longitud"),job.getDouble("latitud"));
                    datos.add(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return datos;
        }
    }
}
