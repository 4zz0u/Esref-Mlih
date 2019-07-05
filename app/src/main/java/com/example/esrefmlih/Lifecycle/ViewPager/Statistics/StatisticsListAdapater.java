package com.example.esrefmlih.Lifecycle.ViewPager.Statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.esrefmlih.R;

import java.util.List;

class StatisticsListAdapater extends ArrayAdapter<Statistics> {
     StatisticsListAdapater(Context context, List<Statistics> objects) {
        super(context, 0, objects);
    }

     @NonNull
     @Override
     public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         String string;

         View statItem = convertView;
         if (statItem == null) {
             statItem = LayoutInflater.from(getContext()).inflate(R.layout.statistics_list_item, parent, false);
         }

         Statistics categoryStats = getItem(position);

         TextView categoryTitle = statItem.findViewById(R.id.category_title);
         categoryTitle.setText(categoryStats.getmCategory());
         categoryTitle.setBackgroundResource(categoryStats.getmColorRessource());

         TextView meanDay = statItem.findViewById(R.id.mean_day);
         string = categoryStats.getmMeanPerday() + " DZ";
         meanDay.setText(string);

         TextView meanMonth = statItem.findViewById(R.id.mean_month);
         string = categoryStats.getmMeanPerMonth() + " DZ";
         meanMonth.setText(string);

         TextView total = statItem.findViewById(R.id.total);
         string = categoryStats.getmTotal() + " DZ";
         total.setText(string);

         final CardView statContainer = statItem.findViewById(R.id.stat_container);
         statContainer.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 statContainer.setCardElevation(50);
                 return false;
             }
         });

         return statItem;
     }
 }