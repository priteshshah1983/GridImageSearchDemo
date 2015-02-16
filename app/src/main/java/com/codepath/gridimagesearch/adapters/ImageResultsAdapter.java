package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    // View lookup cache
    private static class ViewHolder {
        DynamicHeightImageView ivImage;
//        TextView tvTitle;
    }

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ImageResult imageResult = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder.ivImage = (DynamicHeightImageView) convertView.findViewById(R.id.ivImage);
//            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
//        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));
        viewHolder.ivImage.setImageResource(0);
        double aspectRatio = imageResult.getThumbHeight() / (double) imageResult.getThumbWidth();
        viewHolder.ivImage.setHeightRatio(aspectRatio);
//        viewHolder.ivImage.setHeightRatio(1.0);

        Picasso.with(getContext())
                .load(imageResult.getThumbUrl())
//                .placeholder(R.drawable.default_avatar)
                .into(viewHolder.ivImage);

        // Return the completed view to render on screen
        return convertView;
    }
}
