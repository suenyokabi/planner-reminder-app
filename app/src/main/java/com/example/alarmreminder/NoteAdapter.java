package com.example.alarmreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> implements Filterable {

 Context context;
 Activity activity;
 List<NoteModel>noteList;
List<NoteModel>newList;

    public NoteAdapter(Context context, Activity activity, List<NoteModel> noteList) {
        this.context = context;
        this.activity = activity;
        this.noteList = noteList;
        newList = new ArrayList<>(noteList);
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  NoteAdapter.MyViewHolder holder, int position) {
    holder.note_title.setText(noteList.get(position).getNote_title());
    holder.note_description.setText(noteList.get(position).getNote_description());
    holder.layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UpdateNotesActivity.class);
            intent.putExtra("note_title", noteList.get(position).getNote_title());
            intent.putExtra("note_description", noteList.get(position).getNote_description());
            intent.putExtra("id", noteList.get(position).getId());
        }
    });


    }

    @Override
    public int getItemCount() {

        return noteList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NoteModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NoteModel item : newList) {
                    if (item.getNote_title().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            noteList.clear();
            noteList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView note_title, note_description;
        RelativeLayout layout;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            note_title=itemView.findViewById(R.id.note_title);
            note_description= itemView.findViewById(R.id.note_description);
            layout= itemView.findViewById(R.id.note_layout);

        }
    }
    public List<NoteModel> getList() {
        return noteList;
    }
    public void removeItem(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(NoteModel item, int position) {
        noteList.add(position, item);
        notifyItemInserted(position);
    }

}
