package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.databinding.LayoutSectionBinding;
import edu.ucu.libraryapp.datamodels.SectionDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    LayoutSectionBinding binding;
    Context context;
    private final List<SectionDataModel> sectionDataModelList;
    int selected_position = -1;
    private OnItemClickListener onItemClickListener;

    public SectionAdapter(Context context, List<SectionDataModel> sectionDataModelList){
        this.context = context;
        this.sectionDataModelList = sectionDataModelList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutSectionBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.ViewHolder holder, int position) {

        SectionDataModel sectionDataModel = sectionDataModelList.get(position);

        holder.binding.tvSection.setText(sectionDataModel.getSectionName());
        holder.binding.tvDeweyDecimal.setText(sectionDataModel.getDeweyDecimal());

    }

    @Override
    public int getItemCount() {
        return sectionDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutSectionBinding binding;
        public ViewHolder(@NonNull LayoutSectionBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                            notifyItemChanged(selected_position);
                            selected_position = getAdapterPosition();
                            notifyItemChanged(selected_position);

                        }

                    }
                }
            });

        }

    }
}
