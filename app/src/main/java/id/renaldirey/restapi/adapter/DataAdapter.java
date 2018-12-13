package id.renaldirey.restapi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.renaldirey.restapi.R;
import id.renaldirey.restapi.listener.OnDeleteClickListener;
import id.renaldirey.restapi.listener.OnUpdateClickListener;
import id.renaldirey.restapi.model.Data;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    Context context;
    List<Data> data;

    OnDeleteClickListener onDeleteClickListener;
    OnUpdateClickListener onUpdateClickListener;

    public DataAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void add(Data item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List<Data> items) {
        for (Data item : items) {
            add(item);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnUpdateClickListener(OnUpdateClickListener onUpdateClickListener) {
        this.onUpdateClickListener = onUpdateClickListener;
    }

    public Data getData(int position) {
        return data.get(position);
    }

    public void remove(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomer;
        TextView tvName;
        Button btnEdit;
        Button btnRemove;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data, parent, false));
            initViews();

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClickListener.onUpdateClick(getAdapterPosition());
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
        }

        public void bind(Data item) {
            int nomer = getAdapterPosition() + 1;
            tvNomer.setText(String.valueOf(nomer));
            tvName.setText(item.getName());
        }

        public void initViews() {
            tvNomer = (TextView) itemView.findViewById(R.id.tv_nomer);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);
            btnRemove = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }
}
