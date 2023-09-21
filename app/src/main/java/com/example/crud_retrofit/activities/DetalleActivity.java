package com.example.crud_retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_retrofit.MainActivity;
import com.example.crud_retrofit.R;
import com.example.crud_retrofit.fragments.EliminarFragment;
import com.example.crud_retrofit.interfaces.CRUDinterface;
import com.example.crud_retrofit.interfaces.EliminarInterface;
import com.example.crud_retrofit.model.Producto;
import com.example.crud_retrofit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleActivity extends AppCompatActivity implements EliminarInterface {

    TextView idText;
    TextView nombreText;
    TextView precioText;

    Button editButton;
    Button deleteButton;
    CRUDinterface cruDinterface;

    Producto producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        idText = findViewById(R.id.idText);
        nombreText = findViewById(R.id.nombreText);
        precioText = findViewById(R.id.precioText);
        int id = getIntent().getExtras().getInt("id");
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callEdit();
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(producto.getId());
            }
        });
        cruDinterface = getCruDinterface();
        getOne(id);

    }

    private void getOne(int id) {

        Call<Producto> call = cruDinterface.getOne(id);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {

                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }

                producto = response.body();
                idText.setText(String.valueOf(producto.getId()));
                nombreText.setText(producto.getNombre());
                precioText.setText(String.valueOf(producto.getPrecio()));

            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

    private void callEdit(){
        Intent intent =  new Intent(getApplicationContext(), EditarActivity.class);
        intent.putExtra("producto", producto);
        startActivity(intent);
    }

    @Override
    public void showDeleteDialog(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EliminarFragment eliminarFragment = new EliminarFragment("Eliminar producto", producto.getId(), this);
        eliminarFragment.show(fragmentManager, "Alerta");
    }

    @Override
    public void delete(int id) {

       cruDinterface = getCruDinterface();
       Call<Producto> call = cruDinterface.delete(id);
       call.enqueue(new Callback<Producto>() {
           @Override
           public void onResponse(Call<Producto> call, Response<Producto> response) {

               if (!response.isSuccessful()){
                   Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                   toast.show();
                   Log.e("Response err: ", response.message());
                   return;
               }

               producto = response.body();
               Toast toast = Toast.makeText(getApplicationContext(), producto.getNombre() + " Eliminado!!", Toast.LENGTH_LONG);
               toast.show();
               callMain();

           }

           @Override
           public void onFailure(Call<Producto> call, Throwable t) {
               Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
               toast.show();
               Log.e("Throw err: ", t.getMessage());
           }
       });
    }

    private CRUDinterface getCruDinterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cruDinterface = retrofit.create(CRUDinterface.class);
       return cruDinterface;
    }

    private void callMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}