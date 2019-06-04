package com.example.esrefmlih.Lifecycle.ViewPager.Transactions;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionListAdapter extends ArrayAdapter<Expenditure> {

    // Fetch the image ressources to stamp them back in the transaction's imageview widget
    private int[] categories_imageviews =
            {R.drawable.baseline_restaurant_24,
            R.drawable.twotone_smoking_rooms_24,
            R.drawable.twotone_business_center_24,
            R.drawable.twotone_directions_transit_24,
            R.drawable.baseline_local_hospital_24,
            R.drawable.twotone_fastfood_24,
            R.drawable.baseline_account_balance_24,
            R.drawable.baseline_flight_24,
            R.drawable.baseline_directions_car_24,
            R.drawable.baseline_fitness_center_24,
            R.drawable.baseline_call_24};

    private String[] categories_name =
            {"Restaurant", "Smoke", "Business", "Transport", "Health", "Food", "Taxes", "Trips", "Car", "Sport", "Communication"};

    TransactionListAdapter(Context context, List<Expenditure> expenditures) {
        super(context, 0, expenditures);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View transactionItemView = convertView;
        if (transactionItemView == null) {
            transactionItemView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_item, parent, false);
        }

        //Instance of expenditure
        Expenditure expenditure = getItem(position);

        //getting the expenditure IMAGE RESOURCE from the instance and push it to the item ImageView
        CircleImageView expenditureImg = transactionItemView.findViewById(R.id.ic_expenditure_category);
        expenditureImg.setImageResource(categories_imageviews[expenditure.getCategory()]);

        //getting the expenditure CATEGORY from the instance and push it to the item Category TextView
        TextView expenditureCategory = transactionItemView.findViewById(R.id.expenditure_category);
        expenditureCategory.setText(categories_name[expenditure.getCategory()]);

        //getting the expenditure DATE from the instance and push it to the item Date TextView
        TextView expenditureDate = transactionItemView.findViewById(R.id.expenditure_date);
        Date date = expenditure.getDate();
        DateFormat dateFormat = new SimpleDateFormat("E dd MMMM yyyy", Locale.ROOT);
        String dateString = dateFormat.format(date);
        expenditureDate.setText(dateString);

        //getting the expenditure AMOUNT from the instance and push it to the item Amount TextView
        TextView expenditureAmount = transactionItemView.findViewById(R.id.expenditure_amount);
        String amount = "-" + expenditure.getAmount() + ".00DZ";
        expenditureAmount.setText(amount);

        // If the expenditure is smoke, then put the background color to red otherwise keep it white (for consistency)
        if (expenditure.getCategory() == 1) {
            transactionItemView.setBackgroundResource(R.color.colorRedDark);
        } else transactionItemView.setBackgroundColor(Color.WHITE);

        return transactionItemView;

    }
}
