package com.example.crud_retrofit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


import androidx.fragment.app.DialogFragment;

import com.example.crud_retrofit.interfaces.EliminarInterface;

public class EliminarFragment  extends DialogFragment {

    private String message;
    private int id;
    private EliminarInterface eliminarInterface;

    public EliminarFragment(String message, int id, EliminarInterface eliminarInterface) {
        this.message = message;
        this.id = id;
        this.eliminarInterface = eliminarInterface;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message + id +"?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarInterface.delete(id);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Accion: ","cancelar" );
                    }
                });

        return builder.create();
    }
}
