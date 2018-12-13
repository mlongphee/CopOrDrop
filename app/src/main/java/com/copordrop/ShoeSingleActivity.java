package com.copordrop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Scanner;

import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class ShoeSingleActivity extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;
    private DatabaseReference mCommentDatabase;
    private DatabaseReference mDatabaseUser;

    private DatabaseReference mDatabaseCop;
    private DatabaseReference mDatabaseDrop;

    private TextView mShoeSingleName;
    private TextView mShoeSingleBrand;
    private TextView mShoeSinglePrice;
    private ImageView mShoeSingleImage;
    private ImageView mShoeBrandWhite;

    private ImageButton copButton;
    private ImageButton dropButton;

    private ImageView mAmazonLink;
    private ImageView mNikeLink;
    private ImageView mAdiLink;
    private ImageView mEbayLink;

    private boolean mProcessCop;
    private boolean mProcessDrop;
    private boolean mProcessCommentLike;

    private Button mPostComment;
    private EditText mCommentField;
    private TextView mCommentCount;

    private TextView mShoeNameTitle;

    private static String mUsers_Name;

    private RecyclerView mCommentList;

    private static long mDropCount;
    private static long mCopCount;
    private static long mLikeCount;

    private BannerSlider bannerslider;

    private String mShoeString_1;
    private String mShoeString_2;
    private String mShoeName;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_single);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPost_key = getIntent().getExtras().getString("shoe_id");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("New_Items");
        mCommentDatabase = FirebaseDatabase.getInstance().getReference().child("New_Items").child(mPost_key).child("Comments");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabaseCop = FirebaseDatabase.getInstance().getReference().child("New_Items").child(mPost_key).child("Cop");
        mDatabaseDrop = FirebaseDatabase.getInstance().getReference().child("New_Items").child(mPost_key).child("Drop");

        mCommentDatabase.keepSynced(true);
        mDatabase.keepSynced(true);
        mDatabaseUser.keepSynced(true);
        mDatabaseCop.keepSynced(true);
        mDatabaseDrop.keepSynced(true);


        mShoeSingleName = (TextView) findViewById(R.id.shoe_name);
        mShoeSingleBrand = (TextView) findViewById(R.id.shoe_brand);
        mShoeSinglePrice = (TextView) findViewById(R.id.shoe_price);
        mShoeSingleImage = (ImageView) findViewById(R.id.shoe_image);
        mShoeBrandWhite = (ImageView) findViewById(R.id.brand_ImageWhite);
        mShoeNameTitle = (TextView) findViewById(R.id.shoeNameTitle);

        copButton = (ImageButton) findViewById(R.id.copBtn);
        dropButton = (ImageButton) findViewById(R.id.dropBtn);

        mAmazonLink = (ImageView) findViewById(R.id.amazon_link);
        mNikeLink = (ImageView) findViewById(R.id.nike_link);
        mEbayLink = (ImageView) findViewById(R.id.ebay_link);
        mAdiLink = (ImageView) findViewById(R.id.adidas_link);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mShoeName = dataSnapshot.child("shoe_name").getValue().toString().trim();
                String brand = dataSnapshot.child("shoe_brand").getValue().toString();
                Scanner scanner = new Scanner(mShoeName);
                String inString = scanner.nextLine();
                mShoeString_1 = inString.replaceAll(" ", "%20");
                mShoeString_2 = inString.replaceAll(" ", "+");
                String TAG = "URL ";
                Log.v(TAG, mShoeString_1);
                if(brand.equals("Nike")){
                    mAdiLink.setVisibility(View.GONE);
                }
                if(brand.equals("Adidas")){
                    mNikeLink.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAmazonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=" + mShoeString_2));
                startActivity(intent);
            }
        });

        mNikeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://store.nike.com/ca/en_gb/pw/n/1j7?sl=" + mShoeString_1));
                startActivity(intent);
            }
        });

        mAdiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.adidas.ca/en/search?q=" + mShoeString_2));
                startActivity(intent);
            }
        });

        mEbayLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.ebay.com/sch/"+mShoeString_1));
                startActivity(intent);

            }
        });

        bannerslider = (BannerSlider) findViewById(R.id.banner_slider);
        bannerslider.addBanner(new DrawableBanner(R.drawable.side_left));
        bannerslider.addBanner(new DrawableBanner(R.drawable.side_right));
        bannerslider.addBanner(new DrawableBanner(R.drawable.top));
        bannerslider.addBanner(new DrawableBanner(R.drawable.back));
        bannerslider.addBanner(new DrawableBanner(R.drawable.bottom));

        mCommentList = (RecyclerView) findViewById(R.id.comment_list);
        LinearLayoutManager llm = new LinearLayoutManager(ShoeSingleActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        mCommentList.setLayoutManager(llm);

        mPostComment = (Button) findViewById(R.id.postCommentButton);
        mCommentField = (EditText) findViewById(R.id.postCommentField);
        mCommentCount = (TextView) findViewById(R.id.commentAmount);

        mProgress = new ProgressDialog(this);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_ShoeName = (String) dataSnapshot.child("shoe_name").getValue();
                final String post_ShoeBrand = (String) dataSnapshot.child("shoe_brand").getValue();
                String post_ShoePrice = (String) dataSnapshot.child("shoe_price").getValue();
                String post_ShoeImage = (String) dataSnapshot.child("shoe_image").getValue();
                final String post_BrandWhite = (String) dataSnapshot.child("shoe_brand_white").getValue();

                int width = 0;
                int height = 0;
                if (post_ShoeBrand.equals("Nike")) {
                    width = 500;
                    height = 500;

                }
                if (post_ShoeBrand.equals("Adidas")) {
                    width = 600;
                    height = 400;
                }

                //Picasso.with(ShoeSingleActivity.this).load(post_ShoeImage).into(mShoeSingleImage);
                Picasso.with(ShoeSingleActivity.this).load(post_BrandWhite).networkPolicy(NetworkPolicy.OFFLINE).resize(width, height).into(mShoeBrandWhite, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        int width = 0;
                        int height = 0;
                        if (post_ShoeBrand.equals("Nike")) {
                            width = 500;
                            height = 500;

                        }
                        if (post_ShoeBrand.equals("Adidas")) {
                            width = 600;
                            height = 400;
                        }
                        Picasso.with(ShoeSingleActivity.this).load(post_BrandWhite).resize(width, height).into(mShoeBrandWhite);

                    }
                });

                mShoeSingleName.setText(post_ShoeName);
                mShoeSingleBrand.setText(post_ShoeBrand);
                mShoeSinglePrice.setText("(" + post_ShoePrice + ")");
                mShoeNameTitle.setText(post_ShoeName);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postComment();
            }
        });

        copButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProcessCop = true;
                final DatabaseReference newCount = mDatabase.child(mPost_key);

                    mDatabaseCop.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (mProcessCop) {

                                if (dataSnapshot.hasChild(MainActivity.user_id)) {

                                    mDatabaseCop.child(MainActivity.user_id).removeValue();
                                    mDatabase.child(mPost_key).child(MainActivity.user_id).removeValue();

                                    copButton.setImageResource(R.drawable.copbutton2);

                                    mProcessCop = false;

                                } else {
                                    mDatabaseCop.child(MainActivity.user_id).setValue(MainActivity.user_id);

                                    copButton.setImageResource(R.drawable.copbutton4);
                                    mDatabase.child(mPost_key).child(MainActivity.user_id).setValue(MainActivity.user_id);
                                    mDatabaseDrop.child(MainActivity.user_id).removeValue();

                                    mProcessCop = false;
                                }

                            }
                            mCopCount = dataSnapshot.getChildrenCount();

                            newCount.child("cop_count").setValue(mCopCount);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
        });

        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProcessDrop = true;
                final DatabaseReference newCount = mDatabase.child(mPost_key);

                mDatabaseDrop.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (mProcessDrop) {

                            if (dataSnapshot.hasChild(MainActivity.user_id)) {

                                mDatabaseDrop.child(MainActivity.user_id).removeValue();

                                mProcessDrop = false;

                            } else {
                                mDatabaseDrop.child(MainActivity.user_id).setValue(MainActivity.user_id);

                                mDatabaseCop.child(MainActivity.user_id).removeValue();
                                mDatabase.child(mPost_key).child(MainActivity.user_id).removeValue();


                                mProcessDrop = false;
                            }


                        }

                        mDropCount = dataSnapshot.getChildrenCount();
                        newCount.child("drop_count").setValue(mDropCount);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Comments_Shoe, CommentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comments_Shoe, CommentViewHolder>(
                Comments_Shoe.class, R.layout.comments_row, CommentViewHolder.class, mCommentDatabase
        ) {
            @Override
            protected void populateViewHolder(final CommentViewHolder viewHolder, Comments_Shoe model, final int position) {
                viewHolder.setUsersComment(model.getComment());
                viewHolder.setUsersName(model.getUsers_name());
                viewHolder.setCommentTimeStamp(model.getTimestamp());
                viewHolder.setCommentLikeCount(model.getLike_count());

                viewHolder.mView.findViewById(R.id.litTextView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProcessCommentLike = true;
                        final DatabaseReference newCount = mCommentDatabase.child(mPost_key).child("Comments").child(getRef(position).getKey());

                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (mProcessCommentLike) {

                                    if (dataSnapshot.hasChild(MainActivity.user_id)) {

                                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").child(MainActivity.user_id).removeValue();
                                        viewHolder.mView.findViewById(R.id.litTextView).setVisibility(View.VISIBLE);
                                        viewHolder.mView.findViewById(R.id.litColorTextView).setVisibility(View.GONE);

                                        mProcessCommentLike = false;

                                    } else {
                                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").child(MainActivity.user_id).setValue(MainActivity.user_id);
                                        //viewHolder.mView.findViewById(R.id.litTextView).setBackgroundResource(R.color.colorPrimary);
                                        viewHolder.mView.findViewById(R.id.litTextView).setVisibility(View.GONE);
                                        viewHolder.mView.findViewById(R.id.litColorTextView).setVisibility(View.VISIBLE);

                                        mProcessCommentLike = false;
                                    }


                                }

                                mLikeCount = dataSnapshot.getChildrenCount();
                                mCommentDatabase.child(getRef(position).getKey()).child("like_count").setValue(mLikeCount);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });

                viewHolder.mView.findViewById(R.id.litColorTextView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProcessCommentLike = true;
                        final DatabaseReference newCount = mCommentDatabase.child(mPost_key).child("Comments").child(getRef(position).getKey());

                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (mProcessCommentLike) {

                                    if (dataSnapshot.hasChild(MainActivity.user_id)) {

                                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").child(MainActivity.user_id).removeValue();
                                        viewHolder.mView.findViewById(R.id.litTextView).setVisibility(View.VISIBLE);
                                        viewHolder.mView.findViewById(R.id.litColorTextView).setVisibility(View.GONE);

                                        mProcessCommentLike = false;

                                    } else {
                                        mCommentDatabase.child(getRef(position).getKey()).child("Likes").child(MainActivity.user_id).setValue(MainActivity.user_id);
                                        //viewHolder.mView.findViewById(R.id.litTextView).setBackgroundResource(R.color.colorPrimary);
                                        viewHolder.mView.findViewById(R.id.litTextView).setVisibility(View.GONE);
                                        viewHolder.mView.findViewById(R.id.litColorTextView).setVisibility(View.VISIBLE);

                                        mProcessCommentLike = false;
                                    }


                                }

                                mLikeCount = dataSnapshot.getChildrenCount();
                                mCommentDatabase.child(getRef(position).getKey()).child("like_count").setValue(mLikeCount);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });


            }
        };

        mCommentList.setAdapter(firebaseRecyclerAdapter);

        mCommentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numComments = (int) dataSnapshot.getChildrenCount();
                mCommentCount.setText("(" + numComments + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void postComment(){
        final String mPostComment = mCommentField.getText().toString().trim();

        if(!TextUtils.isEmpty(mPostComment)){
            mProgress.setMessage("Posting ...");
            mProgress.show();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.child(MainActivity.user_id).getValue(Users.class);
                    DatabaseReference newComment = mCommentDatabase.push();

                    newComment.child("comment").setValue(mPostComment);
                    newComment.child("users_name").setValue(users.getFirst_name() + " " + users.getLast_name());
                    newComment.child("time").setValue(ServerValue.TIMESTAMP);
                    newComment.child("timestamp").setValue("");

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    mCommentField.setText("");
                    mProgress.dismiss();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setUsersName(String title) {
            TextView post_UsersName = (TextView) mView.findViewById(R.id.name_comment);
            post_UsersName.setText(title);
        }

        public void setUsersComment(String title) {
            TextView post_UsersComment = (TextView) mView.findViewById(R.id.desc_comment);
            post_UsersComment.setText(title);
        }

        public void setCommentTimeStamp(String time){
            TextView mCommentTimeStamp = (TextView) mView.findViewById(R.id.time_comment);
            mCommentTimeStamp.setText(time);
        }

        public void setCommentLikeCount(int like){
            TextView mCommentLikeCount = (TextView) mView.findViewById(R.id.litLikeCount);
            mCommentLikeCount.setText(Integer.toString(like));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
