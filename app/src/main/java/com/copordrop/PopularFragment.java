package com.copordrop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by MJMJ2 on 20/05/17.
 */
public class PopularFragment extends Fragment {

    private RecyclerView mBlogList;

    private DatabaseReference mDatabase;
    private Query mLikeDatabase;

    private FirebaseRecyclerAdapter<New_Shoes, ShoeViewHolder> firebaseRecyclerAdapter;

    private String mPostKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_popular, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("New_Items");
        mDatabase.keepSynced(true);


        mBlogList = (RecyclerView) v.findViewById(R.id.popular_list);
        mBlogList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setStackFromEnd(true);
        llm.setReverseLayout(true);
        mBlogList.setLayoutManager(llm);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<New_Shoes, ShoeViewHolder>(
                New_Shoes.class, R.layout.popular_row, ShoeViewHolder.class, mDatabase.orderByChild("cop_count")
        ) {
            @Override
            protected void populateViewHolder(ShoeViewHolder viewHolder, New_Shoes model, final int position) {
                viewHolder.setShoeName(model.getShoe_name());
                viewHolder.setShoePrice(model.getShoe_price());
                viewHolder.setShoeImage(getActivity(), model.getShoe_image());
                viewHolder.setShoeBrand(getActivity(), model.getShoe_brand_image(), model.getShoe_brand());
                viewHolder.setCopCount(model.getCop_count());
                viewHolder.setDropCount(model.getDrop_count());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleShoeIntent = new Intent(getActivity(), ShoeSingleActivity.class);
                        singleShoeIntent.putExtra("shoe_id", getRef(position).getKey());
                        startActivity(singleShoeIntent);
                    }
                });

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ShoeViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ShoeViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setShoeName(String title) {
            TextView post_ShoeName = (TextView) mView.findViewById(R.id.post_shoeName);
            post_ShoeName.setText(title);
        }

        public void setShoePrice(String title) {
            TextView post_ShoePrice = (TextView) mView.findViewById(R.id.post_shoePrice);
            post_ShoePrice.setText(title);
        }

        public void setCopCount(String title) {
            TextView post_ShoePrice = (TextView) mView.findViewById(R.id.copCount_pop);
            post_ShoePrice.setText(title);
        }

        public void setDropCount(String title) {
            TextView post_ShoePrice = (TextView) mView.findViewById(R.id.dropCount_pop);
            post_ShoePrice.setText(title);
        }

        public void setShoeImage(final Context cxt, final String image) {

            final ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(cxt).load(image).networkPolicy(NetworkPolicy.OFFLINE).centerCrop().resize(350,250).into(post_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(cxt).load(image).centerCrop().resize(350,250).into(post_image);

                }
            });

        }

        public void setShoeBrand(final Context cxt, final String image, final String brand) {
            final ImageView post_adidas = (ImageView) mView.findViewById(R.id.adidas_image);
            final ImageView post_image = (ImageView) mView.findViewById(R.id.brand_image);
            Picasso.with(cxt).load(image).networkPolicy(NetworkPolicy.OFFLINE).centerCrop().resize(300,250).into(post_adidas, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(cxt).load(image).centerCrop().resize(300,250).into(post_adidas);

                }
            });

        }
    }
}
