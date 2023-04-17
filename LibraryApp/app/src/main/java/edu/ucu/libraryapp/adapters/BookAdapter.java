package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.databinding.LayoutBookBinding;
import edu.ucu.libraryapp.datamodels.BookDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    LayoutBookBinding binding;
    Context context;
    private final List<BookDataModel> bookDataModelList;
    int selected_position = -1;
    private OnItemClickListener onItemClickListener;

    public BookAdapter(Context context, List<BookDataModel> bookDataModelList   ){
        this.context = context;
        this.bookDataModelList = bookDataModelList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutBookBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {

        BookDataModel bookDataModel = bookDataModelList.get(position);

        holder.binding.tvDeweyDecimal.setText(bookDataModel.getSectionDewey());
        holder.binding.tvSubDeweyDecimal.setText(bookDataModel.getSubSectionDewey());
        holder.binding.tvTitle.setText(bookDataModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return bookDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutBookBinding binding;
        public ViewHolder(@NonNull LayoutBookBinding binding, OnItemClickListener onItemClickListener) {
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
