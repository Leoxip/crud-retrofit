package com.example.crud_retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crud_retrofit.MainActivity;
import com.example.crud_retrofit.R;
import com.example.crud_retrofit.dto.ProductoDTO;
import com.example.crud_retrofit.interfaces.CRUDinterface;
import com.example.crud_retrofit.model.Producto;
import com.example.crud_retrofit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CrearActivity extends AppCompatActivity {
    EditText nombreText;
    EditText precioText;

    Button createButton;

    CRUDinterface cruDinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        nombreText = findViewById(R.id.nombreText);
        precioText = findViewById(R.id.precioText);
        createButton = findViewById(R.id.createButton);
        nombreText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        precioText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        createButton.setEnabled(buttonEnabled());

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductoDTO dto = new ProductoDTO(nombreText.getText().toString(), Integer.valueOf(precioText.getText().toString()));
                create(dto);
            }
        });
    }

    private void create(ProductoDTO dto){
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
      cruDinterface = retrofit.create(CRUDinterface.class);
        Call<Producto> call = cruDinterface.create(dto);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {

                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }

                Producto producto = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), producto.getNombre() + " agregado!!", Toast.LENGTH_LONG);
                toast.show();

             CallMain();
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });

    }

    private void CallMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nombreText.getText().toString().trim().length() > 0 && precioText.getText().toString().trim().length() > 0;
    }
}