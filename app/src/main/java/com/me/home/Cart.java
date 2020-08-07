package com.me.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    private Toolbar cart_toolbar;
    private RecyclerView cartRecyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference cartrefence;
    DatabaseReference user_reference;
    List<Cart_list> cart_lists;
    CartAdapter cartAdapter;
    String user_phone;
    private Button order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        order_button=findViewById(R.id.order_btn);
        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cart.this, "order is placed", Toast.LENGTH_SHORT).show();

            }
        });

        user_phone= getIntent().getStringExtra("user_phone");
        cart_toolbar=findViewById(R.id.cart_toolbar);
        setSupportActionBar(cart_toolbar);

        cartRecyclerView=findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setHasFixedSize(true);
        cart_lists=new  ArrayList<>();

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        implement the database
        firebaseDatabase= FirebaseDatabase.getInstance();
//        getting the reference of the Cart
        cartrefence = firebaseDatabase.getReference("Cart");
//        here we will pass the user phone number and from that we fetch of
//        particular user
        user_reference= cartrefence.child(user_phone);
        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot :snapshot.getChildren()){
                    Cart_list cart_list =postSnapshot.getValue(Cart_list.class);
//                    adding the item to the list which we get from the data base
                    cart_lists.add(cart_list);
                    Log.d("fetched",cart_list.getName());
                }
                cartAdapter =new CartAdapter(Cart.this,cart_lists);
                cartRecyclerView.setAdapter(cartAdapter);
//                Log.d("adpter","data is fetched in adapter");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new
            ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                   cart_lists.remove(viewHolder.getAdapterPosition());
                   cartAdapter.notifyDataSetChanged();
                }
            };

}