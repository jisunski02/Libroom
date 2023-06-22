package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.databinding.LayoutBookv2Binding;
import edu.ucu.libraryapp.datamodels.ReservationDataModel;

public class ReturnedAdapter extends RecyclerView.Adapter<ReturnedAdapter.ViewHolder> {


    LayoutBookv2Binding binding;
    Context context;
    private final List<ReservationDataModel> reservationDataModelList;
    int selected_position = -1;


    public ReturnedAdapter(Context context, List<ReservationDataModel> reservationDataModelList   ){
        this.context = context;
        this.reservationDataModelList = reservationDataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutBookv2Binding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnedAdapter.ViewHolder holder, int position) {

        ReservationDataModel reservationDataModel = reservationDataModelList.get(position);

        holder.binding.tvTitle.setText(reservationDataModel.getTitle());
        holder.binding.tvAuthor.setText("by "+reservationDataModel.getTitleAuthor());
        holder.binding.tvReservationdate.setText(reservationDataModel.getReservation_date());

        holder.binding.linearBookfine.setVisibility(View.VISIBLE);
        if(reservationDataModel.getReturnPenalty().isEmpty()){

            holder.binding.tvFine.setText("Php 0.00");
        }

        else{

            holder.binding.tvFine.setText("Php "+reservationDataModel.getReturnPenalty()+".00");
        }

        holder.binding.linearReturnDate.setVisibility(View.VISIBLE);
        holder.binding.tvReturnedDate.setText(reservationDataModel.getReturnDate());

    }

    @Override
    public int getItemCount() {
        return reservationDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBookv2Binding binding;

        public ViewHolder(@NonNull LayoutBookv2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
