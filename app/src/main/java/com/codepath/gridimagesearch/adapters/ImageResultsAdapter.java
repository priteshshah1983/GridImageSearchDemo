package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    private static final int MAX_COMMENTS = 2;

    // View lookup cache
    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
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
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));
        viewHolder.ivImage.setImageResource(0);

        Picasso.with(getContext())
                .load(imageResult.getThumbUrl())
//                .placeholder(R.drawable.default_avatar)
                .into(viewHolder.ivImage);

        // Return the completed view to render on screen
        return convertView;
    }

//    private String getFormattedCaptionText(String username, String caption) {
//        int darkBlueColor = getContext().getResources().getColor(R.color.dark_blue_color);
//        String hexDarkBlueColor = getHexColor(darkBlueColor);
//        int darkGrayColor = getContext().getResources().getColor(R.color.dark_gray_color);
//        String hexDarkGrayColor = getHexColor(darkGrayColor);
//        return "<font color=\"" + hexDarkBlueColor + "\">" + username + "</font>&nbsp;" + "<font color=\"" + hexDarkGrayColor + "\">" + caption + "</font>";
//    }
}
