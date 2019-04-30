package com.shoniz.saledistributemobility.view.dialog.orderdescription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.databinding.DialogDescriptionBinding;
import com.shoniz.saledistributemobility.framework.Convertor;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.base.addapter.AppAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatDialogFragment;


/**
 * Created by ferdos.s on 5/27/2017.
 */

@SuppressLint("ValidFragment")
public class DescriptionDialog extends DaggerAppCompatDialogFragment {


    private static final String ACC_DESC = "ACC_DESC";
    private static final String SALE_DESC = "SALE_DESC";
    private static final String PERSON_ID = "PERSON_ID";
    @Inject
    public ICustomerRepository customerRepository;
    DescriptionDialogViewModel descriptionDialogViewModel;
    AlertDialog.Builder builder;
    @Inject
    ViewModelProvider.Factory factory;
    FragmentActivity activity;
    private int personID;
    private int addressID = 1;

    public void setYesListener(DescriptionListener yesListener) {
        this.yesListener = yesListener;
    }

    public void setNoListener(DescriptionListener noListener) {
        this.noListener = noListener;
    }

    private DescriptionListener yesListener;
    private DescriptionListener noListener;

    public static DescriptionDialog newInstance(int personID, String accDesc, String saleDesc) {
        DescriptionDialog dialog = new DescriptionDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ACC_DESC, accDesc);
        bundle.putString(SALE_DESC, saleDesc);
        bundle.putInt(PERSON_ID, personID);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String accDesc = getArguments().getString(ACC_DESC);
            String saleDesc = getArguments().getString(SALE_DESC);
            personID = getArguments().getInt(PERSON_ID);
            descriptionDialogViewModel = ViewModelProviders.of(this, factory).get(DescriptionDialogViewModel.class);
            descriptionDialogViewModel.load(accDesc, saleDesc);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogDescriptionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),
                R.layout.dialog_description, null, false);
        binding.setViewModel(descriptionDialogViewModel);
        binding.setLifecycleOwner(this);

        AddressAdapter addressAdapter = new
                AddressAdapter(activity, R.layout.item_customer_address,
                customerRepository.getCustomerAddress(personID));



        builder = new AlertDialog.Builder(activity);
        builder.setView(binding.getRoot())
                .setPositiveButton(R.string.send_request, (dialog, id) -> {
                    if (yesListener != null) {
                        yesListener.onClick(descriptionDialogViewModel.accDesc.getValue(),
                                descriptionDialogViewModel.saleDesc.getValue(), addressID);
                    }
                    dialog.dismiss();
                }).setNegativeButton(R.string.cancel, (dialog, which) -> {
            if (noListener != null) {
                noListener.onClick(descriptionDialogViewModel.accDesc.getValue(),
                        descriptionDialogViewModel.saleDesc.getValue(), addressID);
            }
            dialog.dismiss();
        });
        binding.spnAddress.setAdapter(addressAdapter);
        binding.spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id)  {
              addressID=  ((CustomerAddressEntity)parent.getItemAtPosition(pos)).AddressID;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return builder.create();
    }

    public interface DescriptionListener {
        void onClick(String accDesc, String saleDesc, int addressId);
    }
}
