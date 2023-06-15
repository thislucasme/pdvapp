package com.thislucasme.pdvapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TesteViewModel extends ViewModel {
    private MutableLiveData<String> textState = new MutableLiveData("Texto inicial");

    public MutableLiveData<String> getText(){
        return textState;
    }
    public void setText(String newText){
        textState.setValue(newText);
    }
}
