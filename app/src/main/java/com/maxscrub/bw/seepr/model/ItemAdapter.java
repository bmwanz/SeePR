package com.maxscrub.bw.seepr.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxscrub.bw.seepr.BuildConfig;
import com.maxscrub.bw.seepr.DiffActivity;
import com.maxscrub.bw.seepr.R;

import java.util.List;

import timber.log.Timber;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context applicationContext, List<Item> itemList) {
        this.context = applicationContext;
        this.items = itemList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
//        view.setOnClickListener(recyclerViewItemOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.prTitle.setText(items.get(i).getPrTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView prTitle;

        public ViewHolder(View view) {
            super(view);
            prTitle = (TextView) view.findViewById(R.id.prTitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Item clickedData = items.get(position);
                        Intent intent = new Intent(context, DiffActivity.class);
                        intent.putExtra("pullTitle", items.get(position).getPrTitle());
                        intent.putExtra("diffUrl", items.get(position).getDiffUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        if (BuildConfig.DEBUG) {
                            Timber.d("ItemAdapter onClick, start diffActivity - %s", clickedData.getPrTitle());
                        }
                    }
                }
            });
        }

    }

//    private final View.OnClickListener recyclerViewItemOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
}
