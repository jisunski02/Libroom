package edu.ucu.libraryapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.BookOverviewActivity;
import edu.ucu.libraryapp.LoginActivity;
import edu.ucu.libraryapp.R;
import edu.ucu.libraryapp.databinding.LayoutBookv2Binding;
import edu.ucu.libraryapp.datamodels.ReservationDataModel;

public class ReservationBorrowedAdapter extends RecyclerView.Adapter<ReservationBorrowedAdapter.ViewHolder> {


    LayoutBookv2Binding binding;
    Context context;
    private final List<ReservationDataModel> reservationDataModelList;
    int selected_position = -1;


    public ReservationBorrowedAdapter(Context context, List<ReservationDataModel> reservationDataModelList   ){
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
    public void onBindViewHolder(@NonNull ReservationBorrowedAdapter.ViewHolder holder, int position) {

        ReservationDataModel reservationDataModel = reservationDataModelList.get(position);

        holder.binding.tvTitle.setText(reservationDataModel.getTitle());
        holder.binding.tvAuthor.setText("by "+reservationDataModel.getTitleAuthor());
        holder.binding.tvReservationdate.setText(reservationDataModel.getReservation_date());

        if(reservationDataModel.isBorrowed()){
            holder.binding.iconDisclaimer.setVisibility(View.VISIBLE);
            holder.binding.iconDisclaimer.setOnClickListener(v->{
                Dialog dialogDisclaimer = new Dialog(context);
                dialogDisclaimer.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                dialogDisclaimer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogDisclaimer.setContentView(R.layout.dialog_disclaimer);

                Button proceedToLogin = dialogDisclaimer.findViewById(R.id.btn_proceed_tologin);

                proceedToLogin.setOnClickListener(v2->{
                    dialogDisclaimer.dismiss();
                });

                dialogDisclaimer.setCancelable(true);
                dialogDisclaimer.show();
            });
        }

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
