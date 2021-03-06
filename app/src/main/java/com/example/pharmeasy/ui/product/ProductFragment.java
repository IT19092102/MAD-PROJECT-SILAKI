package com.example.pharmeasy.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmeasy.MainActivity;
import com.example.pharmeasy.R;
import com.example.pharmeasy.SignIn;
import com.example.pharmeasy.feedback;
import com.example.pharmeasy.ui.cart.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ProductFragment extends Fragment {

    private  View contactView;
    EditText inputSearch;
    private RecyclerView productList;
    private DatabaseReference productreference;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        contactView=inflater.inflate(R.layout.new_product,container,false);
        productList=(RecyclerView)contactView.findViewById(R.id.productList);
        inputSearch=contactView.findViewById(R.id.searchView);
        productList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productList.setNestedScrollingEnabled(true);
        productreference = FirebaseDatabase.getInstance().getReference().child("product");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null)
                {
                    loadData(s.toString());
                }
                else
                {
                      loadData("");
                }

            }
        });

        return contactView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData("");
    }

    public void loadData(String data) {
        Query query=productreference.orderByChild("productName").startAt(data).endAt(data+"\uf8ff");

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();
        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter
                = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override

            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Product model) {
                holder.productName.setText(model.getProductName());
                Picasso.get().load(model.getProductImage()).into(holder.productImage);
                holder.price.setText(model.getPrice());
                holder.addCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("cart");

                        String cartId = mDatabase.push().getKey();

                        Cart cart = new Cart(model.getPrice(),model.getProductName(),model.getProductImage(),model.getDescription(),cartId,"1");
                        mDatabase.child(cartId).setValue(cart);

                        Toast.makeText(getContext(), "Successfully Added To Cart", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcardview, parent, false);
                ProductViewHolder productViewHolder = new ProductViewHolder(view);
                return productViewHolder;
            }

        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        productList.setAdapter(adapter);
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView productName,price;
        ImageView productImage;
        Button addCart;

        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            addCart= itemView.findViewById(R.id.addtocartbtn);
            productName=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.Product_price);
            productImage=itemView.findViewById(R.id.product_image);

        }
    }
}
