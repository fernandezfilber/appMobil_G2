package com.sise.appmobil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {

    private ArrayList<Curso> listaCursos;

    public CursoAdapter(ArrayList<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = listaCursos.get(position);
        holder.tvNombre.setText(curso.getNombre());
        holder.tvProfesor.setText("Profesor: " + curso.getProfesor());
        holder.tvPromedio.setText(String.valueOf(curso.getPromedio()));
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvProfesor, tvPromedio;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreCurso);
            tvProfesor = itemView.findViewById(R.id.tvProfesorCurso);
            tvPromedio = itemView.findViewById(R.id.tvPromedioCurso);
        }
    }
}
