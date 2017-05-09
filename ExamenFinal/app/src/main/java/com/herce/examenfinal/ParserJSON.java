package com.herce.examenfinal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by herce on 5/8/17.
 */

public class ParserJSON {

    public static ArrayList<Contactos> _array_products = new ArrayList<>();

    public static ArrayList<Contactos> parseaArregloContactos(JSONArray arr) {

        JSONObject obj = null;
        Contactos contacto = null;
        _array_products.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                contacto = new Contactos();

                contacto.setID(obj.getInt("id"));
                contacto.setNombre(obj.getString("nombre"));
                contacto.setTelefono(obj.getString("telefono"));
                contacto.setCalle(obj.getString("calle"));
                contacto.setCiudad(obj.getString("ciudad"));
                contacto.setEstado(obj.getString("estado"));
                contacto.setCodigoPostal(obj.getString("codigopostal"));
                contacto.setLatitud(obj.getString("latitud"));
                contacto.setLongitud(obj.getString("longitud"));
                contacto.setImagen(obj.getString("imagen"));

                _array_products.add(contacto);
            }

            Log.d("Array", "" + _array_products);
            return _array_products;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
