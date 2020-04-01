package com.gaurav.project.expensemanagementsystem.Activities.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gaurav.project.expensemanagementsystem.Activities.Model.ExpenseModel;
import com.gaurav.project.expensemanagementsystem.R;


import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    private Cursor cursor;

    OnItemClickListener mListener;
    private List<ExpenseModel> list;

    public ExpenseAdapter(Context context, List<ExpenseModel> list) {

        this.context = context;
        this.list = list;


        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        TextView id,name,amount,date;

        public MyViewHolder(View view) {
            super(view);

            id=view.findViewById(R.id.id);
            name=view.findViewById(R.id.name);
            amount=view.findViewById(R.id.amount);
            date=view.findViewById(R.id.date);


            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            String i,n,a,d;
                            i = id.getText().toString();
                            n = name.getText().toString();
                            a = amount.getText().toString();
                            d = date.getText().toString();
                            mListener.onEditClick(position,i,n,a,d);
                            return true;
                        case 2:
                            String s=id.getText().toString();
                            mListener.onDeleteClick(position,s);

                            return true;
                    }
                }
            }
            return false;
        }

    }
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expensedetails, parent, false);
        return new ExpenseAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

      final    ExpenseModel model=list.get(position);
          holder.id.setText(model.getId());
         holder.name.setText(model.getName());
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position,String i,String n,String a,String d);

        void onDeleteClick(int position,String s);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}