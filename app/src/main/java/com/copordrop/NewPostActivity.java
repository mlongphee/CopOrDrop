package com.copordrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewPostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mpostShoeName;
    private EditText mPostShoePrice;
    private fr.ganfra.materialspinner.MaterialSpinner mShoeBrand;

    private Button mSubmitBtn;

    private Uri mImageUri = null;

    private static final int GALLERY_REQUEST = 1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSelectImage = (ImageButton)findViewById(R.id.imageSelect);

        mpostShoeName = (EditText) findViewById(R.id.shoeName_new);
        mPostShoePrice = (EditText) findViewById(R.id.shoePrice_new);

        mShoeBrand = (fr.ganfra.materialspinner.MaterialSpinner) findViewById(R.id.shoeBrand_new);
        String[] Items = {"Nike", "Adidas","Reebok","Jordans"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mShoeBrand.setAdapter(adapter);

        mSubmitBtn = (Button) findViewById(R.id.activateButton);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("New_Items");
        mDatabase.keepSynced(true);

        mProgress = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST );

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                startPosting();

            }
        });
    }

    public void startPosting(){
        final String shoeName_val = mpostShoeName.getText().toString().trim();
        final String shoePrice_val = mPostShoePrice.getText().toString().trim();
        final String shoeBrand_val = mShoeBrand.getSelectedItem().toString().trim();

        if(!TextUtils.isEmpty(shoeName_val) && !TextUtils.isEmpty(shoePrice_val) && !TextUtils.isEmpty(shoeBrand_val)){
            mProgress.setMessage("Posting ...");
            mProgress.show();

            StorageReference filepath = mStorage.child("Shoe_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newPost = mDatabase.push();

                    newPost.child("shoe_name").setValue(shoeName_val);
                    newPost.child("shoe_price").setValue(shoePrice_val);
                    newPost.child("shoe_image").setValue(downloadUrl.toString());
                    newPost.child("shoe_brand").setValue(shoeBrand_val);
                    if(shoeBrand_val.equals("Nike")){
                        newPost.child("shoe_brand_image").setValue("https://firebasestorage.googleapis.com/v0/b/cop-or-drop-56527.appspot.com/o/Shoe_Images%2Fnike_grey_logo.png?alt=media&token=4fff9bf8-9f1d-46b0-9299-258f93294f47");
                        newPost.child("shoe_brand_white").setValue("https://firebasestorage.googleapis.com/v0/b/cop-or-drop-56527.appspot.com/o/Shoe_Images%2Fnike_logo_white.png?alt=media&token=c14d8c6e-4357-44d6-a085-4e6314713e9e");
                    }
                    if(shoeBrand_val.equals("Adidas")){
                        newPost.child("shoe_brand_image").setValue("https://firebasestorage.googleapis.com/v0/b/cop-or-drop-56527.appspot.com/o/Shoe_Images%2Fadidas_logo_grey.png?alt=media&token=407039f0-1044-4fc8-8846-9394256f54b3");
                        newPost.child("shoe_brand_white").setValue("https://firebasestorage.googleapis.com/v0/b/cop-or-drop-56527.appspot.com/o/Shoe_Images%2Flogo_adidas_white.png?alt=media&token=764735f1-c35d-46ec-85d9-c9bff19aeed8");
                    }
                    if(shoeBrand_val.equals("Reebok")){
                        newPost.child("shoe_brand_image").setValue(shoeBrand_val);
                    }
                    if(shoeBrand_val.equals("Reebok")){
                        newPost.child("shoe_brand_image").setValue(shoeBrand_val);
                    }
                    if(shoeBrand_val.equals("Reebok")){
                        newPost.child("shoe_brand_image").setValue(shoeBrand_val);
                    }

                    newPost.child("time").setValue(ServerValue.TIMESTAMP);


                   FirebaseDatabase.getInstance().getReference().child("postCount").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           newPost.child("timestamp").setValue(dataSnapshot.getValue());
                           long num = (long)dataSnapshot.getValue();
                           int num2 = (int)num;

                           dataSnapshot.getRef().setValue(num2 - 1);
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
                    newPost.child("cop_count").setValue(0);
                    newPost.child("drop_count").setValue(0);

                    startActivity(new Intent(NewPostActivity.this, MainActivity.class));
                }
            });


        }

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);

        if(requestcode == GALLERY_REQUEST && resultcode == RESULT_OK){

            mImageUri = data.getData();

            mSelectImage.setImageURI(mImageUri);


        }
    }
}
