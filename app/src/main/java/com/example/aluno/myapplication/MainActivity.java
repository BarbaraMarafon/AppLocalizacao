package com.example.aluno.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textLatitude;
    private TextView textLongitude;
    private TextView textCidade;
    private TextView textEstado;
    private TextView textPais;
    private Button btnPL;

    private Location localizacao;
    private LocationManager localizacaoM;

    private Address endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLatitude = (TextView) findViewById(R.id.textLatitude);
        textLongitude = (TextView) findViewById(R.id.textLongitude);
        textCidade = (TextView) findViewById(R.id.textCidade);
        textEstado = (TextView) findViewById(R.id.textEstado);
        textPais = (TextView) findViewById(R.id.textPais);

        btnPL = (Button) findViewById(R.id.btnPL);

        double latidute = 0.0;
        double longitude = 0.0;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else {
            localizacaoM = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            localizacao =
                    localizacaoM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (localizacao != null) {
            longitude = localizacao.getLongitude();
            latidute = localizacao.getLatitude();
        }

        textLongitude.setText("Longitude: " + longitude);
        textLatitude.setText("Latitude: " + latidute);

        try {
            endereco = buscarEndereco(latidute, longitude);

            textCidade.setText("Cidade: " + endereco.getLocality());
            textEstado.setText("Estado: " + endereco.getAdminArea());
            textPais.setText("País: " + endereco.getCountryName());

        } catch (IOException e) {
            Log.i("GPS", e.getMessage());
        }

    }

        public Address buscarEndereco(double latitude, double longitude)
                throws IOException {

            Geocoder geocoder;
            Address address = null;
            List<Address> addresses;

            geocoder = new Geocoder(getApplicationContext());

            addresses = geocoder.getFromLocation(latitude,longitude,1);
            if (addresses.size() >0 ) {
                address = addresses.get(0);
            }
            return address;
    }

    }

