package com.example.examenminim2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenminim2.api.Repos;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<Repos> dades;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<Repos> reposList, Context context) {
        this.dades = reposList;
        this.mInflater = LayoutInflater.from((Context) context);
        this.context = (Context) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.activity_user_list, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(dades.get(position));
    }

    @Override
    public int getItemCount() {
        return dades.size();
    }

    public void setItems(List<Repos> items){
        dades=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, languageTV;

        ViewHolder(View itemView){
            super(itemView);
            nameTV = itemView.findViewById(R.id.name);
            languageTV = itemView.findViewById(R.id.language);
        }

        void bindData(final Repos r){
            nameTV.setText(r.getName());
            languageTV.setText(r.getLanguage());
        }


    }
}
