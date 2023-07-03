package edu.ucu.libraryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.libraryapp.ViewPdfActivity;
import edu.ucu.libraryapp.databinding.LayoutSectionBinding;
import edu.ucu.libraryapp.databinding.PdfLayoutBinding;
import edu.ucu.libraryapp.datamodels.PDFDataModel;
import edu.ucu.libraryapp.datamodels.SectionDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    PdfLayoutBinding binding;
    Context context;
    private final List<PDFDataModel> pdfDataModelList;

    public PdfAdapter(Context context, List<PDFDataModel> pdfDataModelList){
        this.context = context;
        this.pdfDataModelList = pdfDataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PdfLayoutBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.ViewHolder holder, int position) {

        PDFDataModel pdfDataModel = pdfDataModelList.get(position);

        holder.binding.pdfTitle.setText(pdfDataModel.getPdfTitle());
        holder.binding.pdfFileName.setText(pdfDataModel.getPdfFileName());

        holder.binding.viewpdf.setOnClickListener(v->{
            Intent intent = new Intent(context, ViewPdfActivity.class);
            intent.putExtra("file_name", pdfDataModel.getPdfFileName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pdfDataModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PdfLayoutBinding binding;
        public ViewHolder(@NonNull PdfLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
