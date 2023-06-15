package com.thislucasme.pdvapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VendedorListModel extends ViewModel {
    private MutableLiveData<Boolean> showBottomSheet = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowBottomSheet(){
        return  showBottomSheet;
    }
    public  void setShowBottomSheet(boolean show){
        showBottomSheet.setValue(show);
    }
}
