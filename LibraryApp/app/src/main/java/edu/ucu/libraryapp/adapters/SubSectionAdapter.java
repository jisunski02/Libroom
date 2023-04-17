package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.R;
import edu.ucu.libraryapp.databinding.LayoutSubsectionBinding;
import edu.ucu.libraryapp.datamodels.SubSectionDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class SubSectionAdapter extends RecyclerView.Adapter<SubSectionAdapter.ViewHolder> {

    LayoutSubsectionBinding binding;
    Context context;
    private final List<SubSectionDataModel> subSectionDataModelList;
    int selected_position = -1;
    private OnItemClickListener onItemClickListener;

    public SubSectionAdapter(Context context, List<SubSectionDataModel> subSectionDataModelList){
        this.context = context;
        this.subSectionDataModelList = subSectionDataModelList;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutSubsectionBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubSectionAdapter.ViewHolder holder, int position) {

        SubSectionDataModel subSectionDataModel = subSectionDataModelList.get(position);

        holder.binding.tvSubsectionName.setText(subSectionDataModel.getSubSectionName());
        holder.binding.tvDeweyDecimal.setText(subSectionDataModel.getDeweyDecimal());

        holder.binding.tvDeweyDecimal.setBackgroundResource(selected_position == position ? R.drawable.bg_left_side : R.drawable.bg_left_side2);
        holder.binding.tvSubsectionName.setTextColor(Color.BLACK);
        holder.binding.tvSubsectionName.setTypeface(Typeface.defaultFromStyle(selected_position == position ? Typeface.BOLD : Typeface.NORMAL));

    }

    @Override
    public int getItemCount() {
        return subSectionDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutSubsectionBinding binding;
        public ViewHolder(@NonNull LayoutSubsectionBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                        notifyItemChanged(selected_position);
                        selected_position = getAdapterPosition();
                        notifyItemChanged(selected_position);
                    }
                }
            });
        }

    }
}
