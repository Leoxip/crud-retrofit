package com.example.crud_retrofit.interfaces;

import com.example.crud_retrofit.dto.ProductoDTO;
import com.example.crud_retrofit.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CRUDinterface {

    @GET("producto")
    Call<List<Producto>> getAll();

    @GET("producto/{id}")
    Call<Producto> getOne(@Path("id") int id);

    @POST("producto")
    Call<Producto> create(@Body ProductoDTO dto);

    @PUT("producto/{id}")
    Call<Producto> edit(@Path("id") int id, @Body ProductoDTO dto);

    @DELETE("producto/{id}")
    Call<Producto> delete(@Path("id") int id);

}


