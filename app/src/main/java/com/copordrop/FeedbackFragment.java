package com.copordrop;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by MJMJ2 on 02/06/17.
 */
public class FeedbackFragment extends Fragment {

    private fr.ganfra.materialspinner.MaterialSpinner mFeedbackTitle;
    private EditText mfeedbackDesc;

    private Button mSumbitFeedback;

    private DatabaseReference mDatabaseFeedback;

    private ImageView mCheckMark;
    private TextView mSuccess;

    private ProgressDialog mProgress;
    private AlphaAnimation alphaAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_feedback, container, false);

        mFeedbackTitle = (fr.ganfra.materialspinner.MaterialSpinner)v.findViewById(R.id.feedbackTitle);
        String[] Items = {"Issue", "General Feedback","Report Bug","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,Items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mFeedbackTitle.setAdapter(adapter);

        mfeedbackDesc = (EditText) v.findViewById(R.id.feedbackDesc);
        mSumbitFeedback = (Button) v.findViewById(R.id.feedbackSubmitButton);

        mDatabaseFeedback = FirebaseDatabase.getInstance().getReference().child("Feedback");

        mSumbitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFeedback();
            }
        });

        mCheckMark = (ImageView) v.findViewById(R.id.checkMark);
        mSuccess = (TextView) v.findViewById(R.id.success);

        alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setStartOffset(2000);
        alphaAnimation.setDuration(400);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCheckMark.setVisibility(View.VISIBLE);
                mSuccess.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCheckMark.setVisibility(View.INVISIBLE);
                mSuccess.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mProgress = new ProgressDialog(getActivity());


        return v;
    }

    public void postFeedback(){
        final String feedbackTitle_val = mFeedbackTitle.getSelectedItem().toString().trim();
        final String feedbackDesc_val = mfeedbackDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(feedbackTitle_val) && !TextUtils.isEmpty(feedbackDesc_val)){
            mProgress.setMessage("Sending...");
            mProgress.show();

            DatabaseReference newFeedback = mDatabaseFeedback.push();

            newFeedback.child("feedback_title").setValue(feedbackTitle_val);
            newFeedback.child("feedback_Desc").setValue(feedbackDesc_val);
            newFeedback.child("user").setValue(MainActivity.user_id);

            mProgress.dismiss();

            mCheckMark.setAnimation(alphaAnimation);
            mSuccess.setAnimation(alphaAnimation);

            mfeedbackDesc.setText("");
            mFeedbackTitle.setSelection(0);

        }
    }
}
