package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView shoppingRecView;
    private ShoppingRecViewAdapter adapter;
    private FloatingActionButton fab;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private ShoppingListItem defaultNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        shoppingRecView = findViewById(R.id.shoppingRecView);
        shoppingListItems = new ArrayList<>();

        adapter = new ShoppingRecViewAdapter(this, shoppingListItems);

        shoppingRecView.setLayoutManager(new LinearLayoutManager(this));
        shoppingRecView.setAdapter(adapter);

        prepareList();

        fab.setOnClickListener(onAddingListener());
    }

    private void prepareList()
    {
        shoppingListItems.clear();
        shoppingListItems.addAll(Utils.getInstance(this).getShoppingListItems());
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener onAddingListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_dialog); //layout for dialog
                dialog.setTitle("Add a new Item");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText ItemName = dialog.findViewById(R.id.Name);
                EditText ItemPrice = dialog.findViewById(R.id.Price);

                View btnAdd = dialog.findViewById(R.id.btn_add);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnAdd.setOnClickListener(onConfirmListener(ItemName, ItemPrice, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                dialog.show();
            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText name, final EditText price, final Dialog dialog)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("") && !price.getText().toString().equals(""))
                {
                    defaultNewItem = new ShoppingListItem(6, name.getText().toString(), "$" + price.getText().toString(), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5evP0YW3A9HiaxtTeL0n9zhsqG6mcoj8HGg&usqp=CAU");
                    if (Utils.getInstance(MainActivity.this).addToShoppingList(defaultNewItem))
                    {
                        shoppingListItems.add(defaultNewItem);
                        adapter.notifyItemRangeChanged(shoppingListItems.size()-1, 1);
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "New Item Added", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onCancelListener(final Dialog dialog)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }
}
