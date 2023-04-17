package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.databinding.LayoutBookBinding;
import edu.ucu.libraryapp.databinding.LayoutBookv2Binding;
import edu.ucu.libraryapp.datamodels.BookDataModel;
import edu.ucu.libraryapp.datamodels.ReservationDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {


    LayoutBookv2Binding binding;
    Context context;
    private final List<ReservationDataModel> reservationDataModelList;
    int selected_position = -1;


    public ReservationAdapter(Context context, List<ReservationDataModel> reservationDataModelList   ){
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
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {

        ReservationDataModel reservationDataModel = reservationDataModelList.get(position);

        holder.binding.tvTitle.setText(reservationDataModel.getTitle());
        holder.binding.tvAuthor.setText("by "+reservationDataModel.getTitleAuthor());
        holder.binding.tvReservationdate.setText(reservationDataModel.getReservation_date());

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
