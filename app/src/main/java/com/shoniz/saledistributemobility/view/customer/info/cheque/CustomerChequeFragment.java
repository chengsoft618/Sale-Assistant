package com.shoniz.saledistributemobility.view.customer.info.cheque;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.base.BaseFragment;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

import java.util.List;


public class CustomerChequeFragment extends BaseFragment {
    // TODO: Rename and change types of parameters
    private int personID;
    private RecyclerView recyclerView;
    private CustomerChequeAdapter.OnCustomerChequeListener onCustomerChequeListener;

    private OnFragmentInteractionListener mListener;

    public CustomerChequeFragment() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        activity=null;
        recyclerView=null;
        onCustomerChequeListener=null;
        mListener=null;
        Runtime.getRuntime().gc();
        System.gc();
    }


    public static CustomerChequeFragment newInstance(CustomerBasicEntity customerBasicModel) {
        CustomerChequeFragment fragment = new CustomerChequeFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PERSON_ID,customerBasicModel.PersonID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            personID = getArguments().getInt(CustomerBasicModel.Column.PERSON_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_cheque, container, false);
        try {
            List<CustomerChequeModel> models = CustomerData.getCustomerChequeByPersonId(activity, personID);
            if (view instanceof RecyclerView) {
                recyclerView = (RecyclerView) view;
                CustomerChequeAdapter customerChequeAdapter = new CustomerChequeAdapter(models, onCustomerChequeListener);
                Common.setRecycleViewLayoutManager(activity,recyclerView,1);
                recyclerView.setAdapter(customerChequeAdapter);
            }
        } catch (Exception e) {
            HandleException systemException = new HandleException(activity, e);
            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                    systemException.getUserTitle(), systemException.getUserMessage(),
                    systemException.getSystemMessage());
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
