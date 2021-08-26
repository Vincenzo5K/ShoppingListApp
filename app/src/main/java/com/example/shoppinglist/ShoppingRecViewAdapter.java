package com.example.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingRecViewAdapter extends RecyclerView.Adapter<ShoppingRecViewAdapter.ViewHolder>
{

    private static final String TAG ="ShoppingRecViewAdapter";
    private final Context context;
    private final ArrayList<ShoppingListItem> shoppingListItems;

    public ShoppingRecViewAdapter(Context context, ArrayList<ShoppingListItem> shoppingListItems) {
        this.context = context;
        this.shoppingListItems = shoppingListItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.Name.setText(shoppingListItems.get(position).getName());
        holder.Price.setText(shoppingListItems.get(position).getPrice());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, shoppingListItems.get(position).getName() + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to delete " + shoppingListItems.get(position).getName() + "?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Utils.getInstance(context).removeFromShoppingListItems(shoppingListItems.get(position)))
                        {
                            shoppingListItems.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO NOTHING
                    }
                });

                builder.create().show();
                return true;
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(shoppingListItems.get(position).getImgUrl())
                .into(holder.Img);

    @Override
    public int getItemCount() {
        return shoppingListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView parent;
        private RelativeLayout expandedRelLayout;
        private ImageView Img;
        private TextView Name, Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            Img = itemView.findViewById(R.id.Img);
            Name = itemView.findViewById(R.id.Name);
            Price = itemView.findViewById(R.id.Price);
        }
    }
}
