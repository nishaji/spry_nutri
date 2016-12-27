package com.Spry.nutritionix;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.myapplication.R;
import com.spry.MainActivity;
import com.spry.nutrition.sign.Gsign;


public class FirstLaunchingTutorial extends BaseAppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));

        setSlideOverAnimation();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, Gsign.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
