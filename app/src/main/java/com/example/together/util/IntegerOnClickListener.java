package com.example.together.util;

import android.view.View;

public class IntegerOnClickListener implements View.OnClickListener
{

    private int index;
    public IntegerOnClickListener(int index) {
        this.index = index;
    }

    @Override
    public void onClick(View v)
    {
        //read your lovely variable
    }

    public int getIndex() {
        return this.index;
    }

};