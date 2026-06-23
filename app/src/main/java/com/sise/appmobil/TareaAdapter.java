package com.sise.appmobil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private ArrayList<Tarea> listaTareas;

    public TareaAdapter(ArrayList<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvFecha.setText("Entrega: " + tarea.getFechaEntrega());
        holder.tvEstado.setText(tarea.getEstado());
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvFecha, tvEstado;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloTarea);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionTarea);
            tvFecha = itemView.findViewById(R.id.tvFechaTarea);
            tvEstado = itemView.findViewById(R.id.tvEstadoTarea);
        }
    }
}