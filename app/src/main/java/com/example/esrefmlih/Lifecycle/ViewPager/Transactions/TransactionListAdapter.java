package com.example.esrefmlih.Lifecycle.ViewPager.Transactions;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Lifecycle.ExpenditureViewModel;
import com.example.esrefmlih.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionListAdapter extends ArrayAdapter<Expenditure> implements DatePickerDialog.OnDateSetListener{

    //Declare an instance of ViewModel, that will take the store the Expenditure data and store it into the database
    private ExpenditureViewModel mExpenditureViewModel;

    private EditText dateEditText;
    private EditText amountEditText;
    private Dialog dialog;
    private ImageView closePopup;
    private Button confirmBtn;

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
            {"Restaurant", "Cigarette", "Investissement", "Transport", "Santé", "Nourriture", "Taxes", "Voyages", "Voiture", "Sport", "Communication"};

    TransactionListAdapter(Context context, List<Expenditure> expenditures, FragmentActivity activity) {
        super(context, 0, expenditures);

        // Initializing the ViewModel
        mExpenditureViewModel = ViewModelProviders.of(activity).get(ExpenditureViewModel.class);
        // Initializing the context
        dialog = new Dialog(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        DateFormat dateFormat = new SimpleDateFormat("E dd MMMM yyyy", Locale.FRENCH);
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

        // A listener that shows the edit/delete popup
        transactionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, position);
            }
        });

        return transactionItemView;

    }

    /**
     * Shows a PopupMenu that includes two operations : Edit and Delete
     * @param v
     */
    private void showPopupMenu(View v, final int position) {
        PopupMenu popup = new PopupMenu(getContext(), v);

        popup.setGravity(Gravity.END);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_popup:
                        showEditTransactionPopup(position);
                        return true;
                    case R.id.delete_popup:
                        showDeleteTransactionPopup(position);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.transaction_menu_popup);
        popup.show();
    }

    /**
     * Shows the edition dialog to edit any information about the expenditure in position "position"
     * @param position
     *
     */
    private void showEditTransactionPopup(int position){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_edit_transaction);
        amountEditText = dialog.findViewById(R.id.amountEditText2);
        dateEditText = dialog.findViewById(R.id.dateEditText2);
        closePopup = dialog.findViewById(R.id.closeEditPopup);
        confirmBtn = dialog.findViewById(R.id.confirmBtn2);

        final Expenditure expenditure = getItem(position);
        amountEditText.setText("" + expenditure.getAmount());
        String date = new SimpleDateFormat("dd/MM/yyyy").format(expenditure.getDate());
        dateEditText.setText(date);

        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;

                if(TextUtils.isEmpty(amountEditText.getText())) {
                    // Showing this toast after confirming the transaction
                    toast = Toast.makeText(getContext(), "Vous devez selectioner une somme", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();
                } else if (dateEditText.getText().toString().equals("")){
                    toast = Toast.makeText(getContext(), "vous devez selectioner une date", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();
                }
                else {
                    try {
                        String amountString = amountEditText.getText().toString();
                        int amount = Integer.parseInt(amountString);
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateEditText.getText().toString());
                        expenditure.setAmount(amount);
                        expenditure.setDate(date);

                        //updating the expenditure
                        mExpenditureViewModel.update(expenditure);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Somme très large", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        return;
                    }


                    // Showing this toast after confirming the transaction
                    toast = Toast.makeText(getContext(), "Dépense a été modifiée", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();

                    dialog.dismiss();
                }
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    /**
     * This method shows the Date Picker dialog
     */
    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // gets the current day
        datePickerDialog.show();
    }

    /**
     * This method shows the deletion dialog for an expenditure
     * @param position
     */
    private void showDeleteTransactionPopup(final int position){
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.popup_delete_transaction);
            confirmBtn = dialog.findViewById(R.id.confirmDeleteBtn);
            closePopup = dialog.findViewById(R.id.closeDeletePopup);
            closePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Expenditure expenditure = getItem(position);
                    mExpenditureViewModel.delete(expenditure);
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        dateEditText.setText(date);

        // Showing this toast after picking the date
        Toast dateToast = Toast.makeText(getContext(), "date selectionnée", Toast.LENGTH_SHORT);
        dateToast.setGravity(Gravity.BOTTOM, 0, 0);
        dateToast.setMargin(0, 0.05f);
        dateToast.show();
    }
}
