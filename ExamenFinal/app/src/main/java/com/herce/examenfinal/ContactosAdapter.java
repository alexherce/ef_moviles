package com.herce.examenfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

// Los adaptadores para recyclers deben contener una clase interna  extienda de
// RecyclerView.ViewHolder.
public class ContactosAdapter extends
        RecyclerView.Adapter<ContactosAdapter.ViewHolder> {

    // creamos un ArrayList con contendra las marcas
    // creamos el contexto
    ArrayList<Contactos> list = null;
    Context context = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    // Éste adaptador sigue el patrón de diseño view holder, que significa que define una
    // clase interna que extienda de RecyclerView.ViewHolder. Éste patrón minimiza el número
    // de llamadas al costoso método findViewById.
    public ContactosAdapter(Context context, ArrayList<Contactos> list) {
        this.context = context;
        this.list = list;
    }

    // Debemos de crear estos dos métodos: onCreateViewHolder() y onBindViewHolder()
    // Hay sobreescribir estos dos métodos para inflar la vista y vincular datos a la vista
    // Encargado de crear los nuevos objetos ViewHolder necesarios para los elementos de la lista.
    @Override
    public ContactosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.contactos_card, parent, false);
        return new ViewHolder(childView);
    }

    // Encargado de actualizar los datos de un ViewHolder ya existente.
    // onBindViewHolder nos sirve para especificar el contenido de cada elemento del RecyclerView.
    // Éste método es muy similar al método getView de un adaptador de ListView.
    // Aquí es donde tenemos que establecer los valores de los campos nombre y letra del CardView.
    @Override
    public void onBindViewHolder(ContactosAdapter.ViewHolder holder,int position) {
        String name = list.get(position).getNombre();
        Log.d("Name", name);
        String imageURL = list.get(position).getImagen();
        String telefono = list.get(position).getTelefono();
        String calle = list.get(position).getCalle();
        String ciudad = list.get(position).getCiudad();
        String estado = list.get(position).getEstado();
        String codigoPostal = list.get(position).getCodigoPostal();

        holder.nombreTxt.setText(name);
        holder.telefonoTxt.setText(telefono);
        holder.calleTxt.setText(calle);
        holder.ciudadTxt.setText(ciudad);
        holder.estadoTxt.setText(estado);
        holder.codigopostalTxt.setText(codigoPostal);
        holder.image.setImageUrl(imageURL, imageLoader);
    }

    // Indica el número de elementos del set de datos.
    // y nos la regresa
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Ésta clase es llamada cuando el ViewHolder necesita ser inicializado.
    // Especificamos el layout que cada elemento de RecyclerView debería usar.
    // Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado al
    // constructor del ViewHolder.
    public class ViewHolder extends RecyclerView.ViewHolder{
        // definimos los componentes de nuestra vista
        TextView nombreTxt, telefonoTxt, calleTxt, ciudadTxt, estadoTxt, codigopostalTxt;
        CardView carta;
        NetworkImageView image;

        public ViewHolder(View verElemento) {
            super(verElemento);
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            carta = (CardView)  verElemento.findViewById(R.id.contactos_card);
            nombreTxt = (TextView)  verElemento.findViewById(R.id.nameCardText);
            telefonoTxt = (TextView) verElemento.findViewById(R.id.telefonoCardText);
            calleTxt = (TextView) verElemento.findViewById(R.id.calleCardText);
            ciudadTxt = (TextView) verElemento.findViewById(R.id.ciudadCardText);
            estadoTxt = (TextView) verElemento.findViewById(R.id.estadoCardText);
            codigopostalTxt = (TextView) verElemento.findViewById(R.id.codigopostalCardText);
            image = (NetworkImageView) verElemento.findViewById(R.id.thumbnail);

            carta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Intent mainIntent = new Intent(AppController.getInstance().getApplicationContext(), ContactoDetailActivity.class);
                    Bundle id = new Bundle();
                    id.putString("id", list.get(getAdapterPosition()).getID().toString());
                    mainIntent.putExtras(id);
                    AppController.getInstance().getApplicationContext().startActivity(mainIntent);
                }
            });

        }

    }
}
