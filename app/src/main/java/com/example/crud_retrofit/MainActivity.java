package com.example.crud_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_retrofit.activities.CrearActivity;
import com.example.crud_retrofit.adapters.ProductosAdapter;
import com.example.crud_retrofit.interfaces.CRUDinterface;
import com.example.crud_retrofit.model.Producto;
import com.example.crud_retrofit.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    List<Producto> productos;
    CRUDinterface cruDinterface;

    ListView listView;
    FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callCreate();
            }
        });
        getAll();
    }



    private void getAll(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cruDinterface = retrofit.create(CRUDinterface.class);
        Call<List<Producto>> call = cruDinterface.getAll();
        call.enqueue(new Callback<List<Producto>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Producto>> call1, Response<List<Producto>> response){
                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }

                productos = response.body();
                ProductosAdapter productosAdapter = new ProductosAdapter(productos, getApplicationContext());
                listView.setAdapter(productosAdapter);
                productos.forEach(p -> Log.i("Prods: ", p.toString()));
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t){
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }

        });
    }

    private void callCreate() {
        Intent intent =  new Intent(getApplicationContext(), CrearActivity.class);
        startActivity(intent);
    }


}