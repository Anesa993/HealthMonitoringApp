package com.example.healthyme.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyme.Database.DbHelper;
import com.example.healthyme.Entities.HistoryBPModel;
import com.example.healthyme.Entities.InfoModel;
import com.example.healthyme.R;

import java.util.ArrayList;

public class HistoryBPAdapter extends RecyclerView.Adapter<HistoryBPAdapter.ViewHolder> {
    Context context;
    ArrayList<HistoryBPModel> arrBP;
    DbHelper dbHelper;
    public HistoryBPAdapter(Context context, ArrayList<HistoryBPModel> arrBP) {
        this.context = context;
        this.arrBP = arrBP;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_bp_history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryBPModel model = arrBP.get(position);
        holder.sys.setText(arrBP.get(position).getSystolic());
        holder.dia.setText(arrBP.get(position).getDiastolic());
        holder.pulse.setText(arrBP.get(position).getPulse());
        holder.date.setText(arrBP.get(position).getDate());
        holder.time.setText(arrBP.get(position).getTime());
        /*holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to delete the record from the database
                boolean isDeleted = dbHelper.deleteBPRecord(model.getId());
                if (isDeleted) {
                    // Remove the item from the ArrayList
                    arrBP.remove(position);
                    // Notify the adapter about the removal
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrBP.size());
                    Toast.makeText(context, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete record", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        {return arrBP.size();}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sys, dia, pulse, date, time;
        ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sys = itemView.findViewById(R.id.history_systolic);
            dia = itemView.findViewById(R.id.history_diastolic);
            pulse = itemView.findViewById(R.id.history_pulse);
            date = itemView.findViewById(R.id.history_bp_date);
            time = itemView.findViewById(R.id.history_bp_time);
           // btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
