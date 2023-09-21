package com.example.crud_retrofit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.crud_retrofit.R;
import com.example.crud_retrofit.activities.DetalleActivity;
import com.example.crud_retrofit.model.Producto;

import java.util.List;

public class ProductosAdapter extends BaseAdapter {

    List<Producto> productos;
    Context context;
    TextView nombreText;
    Button viewButton;

    public ProductosAdapter(List<Producto> productos, Context context) {
        this.productos = productos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productos.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.producto_list, viewGroup, false);
        }
        nombreText = view.findViewById(R.id.nombreText);
        nombreText.setText(productos.get(position).getNombre());
        viewButton = view.findViewById(R.id.viewButton);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 callDetail(productos.get(position).getId());
            }
        });
        return view;
    }

    private void callDetail(int id) {
        Intent intent = new Intent(context, DetalleActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
