package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.catalog.ResourceBusiness;
import com.shoniz.saledistributemobility.data.model.coding.CodingEntity;
import com.shoniz.saledistributemobility.data.model.coding.ICodingRepository;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.databinding.FragmentCustomerCardIndexBinding;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.location.LocationHelper;
import com.shoniz.saledistributemobility.view.ordering.order.SendOrderService;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Notification;

import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.catalog.CatalogFragment;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.customer.ActionSendType;
import com.shoniz.saledistributemobility.view.customer.CustomerBusiness;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.dialog.orderdescription.DescriptionDialog;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivity;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class CardIndexFragment extends BaseFragment<FragmentCustomerCardIndexBinding, CardIndexFragmentViewModel>
        implements ICardIndexNavigator {
    //public static final String CHANGE_SHORTCUT = "CHANGE_SHORTCUT";

    CardIndexAdapter cardIndexAdapter;
    LinearLayoutManager layoutManager;
    List<CardIndexDetailModel> models;
    Animation slide_in_left, slide_out_right;
    private Hashtable<Integer, Integer> shortcutsAvailability = new Hashtable<>();


    CatalogFragment catalogFragment;
    AppCompatActivity activity;

    SharedCustomerViewModel sharedCustomerActivityViewModel;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    IOrderRepository orderRepository;

    @Inject
    ICodingRepository codingRepository;

    @Inject
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory factoryShared;

    @Inject
    @Named("CardIndexFragmentViewModel")
    ViewModelProvider.Factory factory;

    @Inject
    ICustomerRepository customerRepository;
    CardIndexAdapter.OnCustomerCardIndexListener onCustomerCardIndexListener = new CardIndexAdapter.OnCustomerCardIndexListener() {


        @Override
        public void onItemClick(CardIndexDetailModel model) {
            // HighLightShortcut(Integer.parseInt(model.Shortcut));
            mViewDataBinding.cardIndexKeypad.setShortcut(model.Shortcut);
            if (model.RequestPackage != 0) {
                mViewDataBinding.cardIndexKeypad.focusPackageView();
            }
            if (model.RequestCarton != 0) {
                mViewDataBinding.cardIndexKeypad.focusCartonView();
            }
            try {
                sharedCustomerActivityViewModel.setShortcutSender(model.Shortcut, ChangeSender.CardIndexRecycler);
            } catch (IOException e) {
                handleError(new InOutError(commonPackage, e, "Shortcut image not found: " + model.Shortcut));
            }

        }

        @Override
        public void onItemLongClick(CardIndexDetailModel model) {

        }
    };

    BroadcastReceiver SendOrderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            mViewDataBinding.cardIndexKeypad.setProgressBar(false);
            if (bundle != null) {
                int personID = bundle.getInt(SendOrderService.PERSON_ID);

                Notification notification = new Notification(context, personID);
                notification.cancel();
                boolean isError = bundle.getBoolean(SendOrderService.IS_ERROR);
                if (isError) {
                    HandleException exception = new HandleException(context);
                    try {
                        CardIndexModel cardIndexModel = CardIndexOldDb.getCardIndexByPersonId(context, personID);
                        exception.AddException(new Exception(cardIndexModel.ErrorMessage));
                    } catch (IOException e) {
                        exception.AddException(e);
                    }
                    exception.Show();
                } else {
                    long OrderNo = bundle.getLong(SendOrderService.ORDER_NO);
                    Intent order = OrderDetailActivity.newInstance(activity, OrderNo);
                    startActivity(order);
                    getBaseActivity().finish();
                }
            }

        }
    };
    Location lastLocation;
    private String DESCRIPTION_DIALOG = "DESCRIPTION_DIALOG";
    // TODO: Rename and change types of parameters
    private int personID;


    public CardIndexFragment() {
    }

    public static CardIndexFragment newInstance(CustomerBasicEntity customerBasicModel) {
        CardIndexFragment fragment = new CardIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PERSON_ID, customerBasicModel.PersonID);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CardIndexFragment newInstance(int personID) {
        CardIndexFragment fragment = new CardIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PERSON_ID, personID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personID = getArguments().getInt(CustomerBasicModel.Column.PERSON_ID);
        }

        sharedCustomerActivityViewModel = ViewModelProviders.of(getBaseActivity(), factoryShared).get(SharedCustomerViewModel.class);
        sharedCustomerActivityViewModel.changeSender.observe(getBaseActivity(), new Observer<ChangeSender>() {
            @Override
            public void onChanged(@Nullable ChangeSender sender) {
                String shortcut = sharedCustomerActivityViewModel.getShortcutString();
                if (shortcut != null) {
                    if (sender != ChangeSender.TxtShortcut) {
                        mViewDataBinding.cardIndexKeypad.focusCartonText();
                        mViewDataBinding.cardIndexKeypad.setShortcut(shortcut);
                    }
                    refreshProductAmount();
                    HighLightShortcut(Integer.valueOf(shortcut));
                }

            }
        });
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        try {
            catalogFragment = CatalogFragment.newInstance();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, catalogFragment);
            transaction.commit();

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                slide_in_left = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
                slide_out_right = AnimationUtils.loadAnimation(activity, android.R.anim.slide_out_right);
                mViewDataBinding.viewanimatorCardIndex.setInAnimation(slide_in_left);
                mViewDataBinding.viewanimatorCardIndex.setOutAnimation(slide_out_right);
            }

            models = CustomerBusiness.getOrderByPersonId(activity, personID);
            shortcutsAvailability.putAll(orderRepository.getShortcutsAvailability());
            cardIndexAdapter = new CardIndexAdapter(models, onCustomerCardIndexListener,
                    shortcutsAvailability, this);
            layoutManager = new LinearLayoutManager(getActivity());
            mViewDataBinding.cardIndexRecycler.setLayoutManager(layoutManager);
            layoutManager.setSmoothScrollbarEnabled(false);
            cardIndexAdapter.setHasStableIds(true);
            RecyclerView recyclerView = mViewDataBinding.cardIndexRecycler;
            mViewDataBinding.cardIndexRecycler.setAdapter(cardIndexAdapter);

            initRequestView();
            setHasOptionsMenu(true);
            setHeaderViewConfigration();



            mViewDataBinding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewDataBinding.viewanimatorCardIndex.showNext();
                }
            });

            mViewDataBinding.fab.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view1) {

                    cardIndexAdapter.toggleCardindexView();
                    try {
                        setHeaderViewConfigration();
                    } catch (IOException e) {
                        handleError(new UncaughtException(commonPackage, e));
                    }
                    return true;
                }
            });
            updateShortcuts();
        } catch (Exception e) {
            HandleException.ShowException(activity, e);
        }

        sharedCustomerActivityViewModel.addCatalogResizeListener(catalogResizeListener);

        return view;


    }

    private SharedCustomerViewModel.OnCatalogResizeListener catalogResizeListener = new SharedCustomerViewModel.OnCatalogResizeListener() {
        @Override
        public void onCatalogIncreaseSize() {
            mViewDataBinding.cardIndexKeypad.setVisibility(View.GONE);
        }

        @Override
        public void onCatalogReduceSize() {
            mViewDataBinding.cardIndexKeypad.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public CardIndexFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CardIndexFragmentViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onChangeLocation(Location location) {
        lastLocation = location;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer_card_index;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        lastLocation = null;
        cardIndexAdapter = null;
        layoutManager = null;
        models = null;
        slide_in_left = null;
        slide_out_right = null;
        catalogFragment = null;
        Runtime.getRuntime().gc();
        System.gc();
    }


    @Override
    public void onResume() {
        super.onResume();

        activity.registerReceiver(SendOrderReceiver, new IntentFilter(SendOrderService.SEND_ORDER_COMPLETE));
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.unregisterReceiver(SendOrderReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //((CustomerActivity)getActivity()).FabShow();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        //((CustomerActivity)getActivity()).fabHide();
    }


    private void initRequestView() {

        int chequeDuration = 30;
        try {
            chequeDuration = settingRepository.getChequeDurationDay();
        } catch (Exception ex) {
            ex.getMessage();
        }

        try {

            CardIndexDto cardIndex = CardIndexBusiness.getCardIndexDto(getBaseActivity(), personID);
            if (cardIndex != null) {
                chequeDuration = cardIndex.ChequeDuration;
            }
        } catch (Exception ex) {

        }


        mViewDataBinding.cardIndexKeypad.setChequeDuration(chequeDuration);
        mViewDataBinding.cardIndexKeypad.setOnShortcutLostFocusListener(new RequestView.ShortcutLostFocusListener() {
            @Override
            public void OnLost(RequestView view) {
                lostFocusShortcut(view);
            }
        });
        mViewDataBinding.cardIndexKeypad.setOnRequestListener(new RequestView.RequestListener() {
            @Override
            public void OnClick(RequestView view) {
                CardIndexParam cardIndexParam = getCardIndexParam();
                try {
                    setRequire(cardIndexParam);
                    refreshAmount();
                } catch (Exception e) {
                    HandleException.ShowException(activity, e);
                }
                upDateCardIndexList(cardIndexParam, CardIndexBusiness.AmountType.Require);

            }
        });
        mViewDataBinding.cardIndexKeypad.setOnExistenceListener(new RequestView.ExistenceListener() {
            @Override
            public void OnClick(RequestView view) {
                CardIndexParam cardIndexParam = getCardIndexParam();
                try {
                    setExistence(cardIndexParam);
                } catch (Exception e) {
                    HandleException.ShowException(activity, e);
                }

                upDateCardIndexList(cardIndexParam, CardIndexBusiness.AmountType.Existence);
            }
        });
        mViewDataBinding.cardIndexKeypad.setOnSendListener(new RequestView.SendListener() {
            @Override
            public void OnClick(RequestView view) {

                try {
                    final CardIndexDto dto = CardIndexBusiness.getCardIndexDto(getActivity(), personID);
                    CardIndexBusiness.saveChequeDuration(activity, personID, view.chequeDurationValue);
                    if (dto != null) {
                        DescriptionDialog.DescriptionListener yesListener = (accDesc, saleDesc, addressID) -> {
                            try {
                                CardIndexBusiness.saveDescriptions(getActivity(), personID, accDesc, saleDesc, addressID);
                                SendRequestModel model;
                                if (dto.OrderNo == 0) {
                                    model = new SendRequestModel(personID, ActionSendType.NewOrder);
                                } else {
                                    model = new SendRequestModel(personID, ActionSendType.UpdateOrder);
                                }
                                if (lastLocation != null)
                                    model.location = LocationHelper.convertLocationToLocationEntity(lastLocation);
                                SendOrderService.send(activity, model);
                                mViewDataBinding.cardIndexKeypad.setProgressBar(true);
                            } catch (Exception e) {
                                HandleException.ShowException(activity, e);
                            }
                        };

                        DescriptionDialog descriptionDialog = DescriptionDialog.newInstance(dto.PersonId, dto.AccDesc, dto.SaleDesc);
                        descriptionDialog.setYesListener(yesListener);
                        descriptionDialog.show(activity.getSupportFragmentManager(), DESCRIPTION_DIALOG);


                    } else {
                        Common.toast(activity, getString(R.string.empty_basket));
                        Common.playSound(activity, R.raw.fail);
                    }
                } catch (Exception e) {
                    HandleException.ShowException(activity, e);
                }
            }
        });
        mViewDataBinding.cardIndexKeypad.setOnChequeDurationLostFocusListener(view1 -> {
            try {
                CardIndexBusiness.saveChequeDuration(activity, personID, view1.chequeDurationValue);
            } catch (Exception e) {
                handleError(new UncaughtException(commonPackage, e));
            }
        });

        mViewDataBinding.cardIndexKeypad.setOnAmountLongClick(new RequestView.AmountLongClickListener() {
            @Override
            public void OnLong(RequestView view) {
                String calc = "com.sec.android.app.popupcalculator";
                if (Device.isPackageInstalled(getBaseActivity(), calc)) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    i.setComponent(new ComponentName(
                            calc,
                            calc + ".Calculator"));
                    startActivity(i);
                }

            }
        });


        mViewDataBinding.cardIndexKeypad.setOnAmountClick(new RequestView.AmountClickListener() {
            @Override
            public void OnClick(RequestView view) {
                showProductsDialog();
            }
        });

        mViewDataBinding.cardIndexKeypad.setOnDeleteClick(new RequestView.DeleteListener() {
            @Override
            public void OnClick(RequestView view) {
                CardIndexParam cardIndexParam = getCardIndexParam();
                cardIndexParam.Carton = 0;
                cardIndexParam.Package = 0;
                try {
                    setRequire(cardIndexParam);
                    upDateCardIndexList(cardIndexParam, CardIndexBusiness.AmountType.Require);
                    HighLightShortcut(0);
                    refreshAmount();
                } catch (Exception e) {
                    HandleException.ShowException(activity, e);
                }

            }
        });
        refreshAmount();
    }

    private void lostFocusShortcut(RequestView view) {
        try {

            int shortcut = Integer.parseInt(view.shortcutString());

            CodingEntity product = codingRepository.getProduct(shortcut + "");
            if (product != null)
                commonPackage.getUtility().toast(product.ProductName);


            sharedCustomerActivityViewModel.setShortcutSender(
                    "" + view.shortcutString(),
                    ChangeSender.TxtShortcut);


            int color = R.color.md_grey_200;
            if (orderRepository.getShortcutsAvailability().containsKey(shortcut)) {
                int availability = orderRepository.getShortcutsAvailability().get(shortcut);
                color = Utility.getAvailabilityColor(availability);
            }
            Drawable mDrawable = getResources().getDrawable(R.drawable.ic_keypad_textfield_default);
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setShortcutBackground(mDrawable);
            } else {
                view.setShortcutBackground(mDrawable);
            }

        } catch (IOException e) {
            handleError(new UncaughtException(commonPackage, e));
        }
    }

    private void showProductsDialog() {

    }

//    private void SendChangeBroadcast(int shortcutValue) {

//        Intent intent = new Intent(CardIndexFragment.CHANGE_SHORTCUT);
//        intent.putExtra(SubCategoryDetailModel.Column.Shortcut, shortcutValue);
//        activity.sendBroadcast(intent);
//    }

    public void refreshProductAmount() {
        try {
            SubCategoryDetailModel subCategoryDetailModel = ResourceBusiness.getShortcut(activity, mViewDataBinding.cardIndexKeypad.shortcutString());
            if (subCategoryDetailModel != null) {
                mViewDataBinding.cardIndexKeypad.setAmountProduct(ResourceBusiness.getShortcut(activity, mViewDataBinding.cardIndexKeypad.shortcutString()).SalePrice);
            }
        } catch (IOException e) {
            HandleException.ShowException(activity, e);
        }

    }

    private void refreshAmount() {
        try {
            mViewDataBinding.cardIndexKeypad.setRequestAmount(CardIndexBusiness.getRequestAmount(activity, personID));
        } catch (IOException e) {
            HandleException.ShowException(activity, e);
        }
    }

    RunnableMethod<Integer, Object> runnableScroll = new RunnableMethod<Integer, Object>() {
        @Override
        public Object run(final Integer param, OnProgressUpdate onProgressUpdate) {
            getBaseActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layoutManager.scrollToPosition(param);
                }
            });
            return null;
        }
    };

    public void HighLightShortcut(int shortcutValue) {

        cardIndexAdapter.setShortcutSelected(shortcutValue, runnableScroll);

    }

    @NonNull
    private CardIndexParam getCardIndexParam() {
        cardIndexAdapter.setUnSelected();
        CardIndexParam cardIndexParam = new CardIndexParam();
        cardIndexParam.Carton = mViewDataBinding.cardIndexKeypad.cartonValue;
        cardIndexParam.Package = mViewDataBinding.cardIndexKeypad.packageValue;
        cardIndexParam.Shortcut = "" + mViewDataBinding.cardIndexKeypad.shortcutValue;
        cardIndexParam.PersonId = personID;
        return cardIndexParam;
    }

    private void refreshRequestView() {
        mViewDataBinding.cardIndexKeypad.emptyAll();
        mViewDataBinding.cardIndexKeypad.focusShortcutView();
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void upDateCardIndexList(CardIndexParam cardIndexParam, CardIndexBusiness.AmountType amountType) {
        try {
            List<CardIndexDetailModel> newModels = CustomerBusiness.getOrderByPersonId(activity, personID);
            cardIndexAdapter.setModelAndNotify(newModels, shortcutsAvailability);
            refreshRequestView();
        } catch (IOException e) {
            handleError(new UncaughtException(commonPackage, e));
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

    @Override
    public int getFragmentTitle() {
        return 0;
    }

    private void setHeaderViewConfigration() throws IOException {
        //View footer = view.findViewById(R.id.card_index_footer);
        mViewDataBinding.cardIndexHeader.setBackgroundColor(Color.parseColor("#009F77"));
        //  footer.setBackgroundColor(Color.parseColor("#009F77"));

        ImageView imgCartonPackage = mViewDataBinding.cardIndexHeader.findViewById(R.id.img_carton_package);
        List<String> cardIndexDates = CustomerBusiness.getOrderDatesByPersonId(activity, personID);
        TextView txtTour1Carton = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_tour1_carton);
        TextView txtTour2Carton = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_tour2_carton);
        TextView txtTour3Carton = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_tour3_carton);
        TextView txtRequest = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_request_carton);
        TextView txtExistence = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_existence_carton);
        TextView txtCode = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_code);
        TextView txtSum = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_sum_carton);
        TextView txtProductName = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_product_name);
        TextView txtPrice = mViewDataBinding.cardIndexHeader.findViewById(R.id.txt_tour_price);

        if (cardIndexAdapter.isProductDetailsShown()) {
            txtProductName.setVisibility(View.VISIBLE);
            txtPrice.setVisibility(View.VISIBLE);
            txtTour1Carton.setVisibility(View.GONE);
            txtTour2Carton.setVisibility(View.GONE);
            txtTour3Carton.setVisibility(View.GONE);
            txtExistence.setVisibility(View.GONE);
            imgCartonPackage.setVisibility(View.GONE);
        } else {
            txtProductName.setVisibility(View.GONE);
            txtPrice.setVisibility(View.GONE);
            txtTour1Carton.setVisibility(View.VISIBLE);
            txtTour2Carton.setVisibility(View.VISIBLE);
            txtTour3Carton.setVisibility(View.VISIBLE);
            txtExistence.setVisibility(View.VISIBLE);
            imgCartonPackage.setVisibility(View.VISIBLE);
        }


        txtTour1Carton.setText(cardIndexDates.size() > 0 ? cardIndexDates.get(0).substring(2) : "-");
        txtTour2Carton.setText(cardIndexDates.size() > 1 ? cardIndexDates.get(1).substring(2) : "-");
        txtTour3Carton.setText(cardIndexDates.size() > 2 ? cardIndexDates.get(2).substring(2) : "-");

        imgCartonPackage.setImageDrawable(null);

        txtTour1Carton.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtTour2Carton.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtTour3Carton.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtRequest.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtExistence.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtSum.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
        txtProductName.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.font_card_index_header));
    }

    public void setRequire(CardIndexParam cardIndexParam) throws Exception {
        CardIndexBusiness.saveRequireAmount(activity, cardIndexParam);
    }

    public void setExistence(CardIndexParam cardIndexParam) throws Exception {
        CardIndexBusiness.saveExistenceAmount(activity, cardIndexParam);
    }


    @Override
    public void updateShortcuts() {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                orderRepository.syncShortcutsAvailability();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            Hashtable<Integer, Integer> temp = orderRepository.getShortcutsAvailability();
            cardIndexAdapter.setModel(models, temp);
            shortcutsAvailability.clear();
            shortcutsAvailability.putAll(temp);
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
        commonAsyncTask.run();
    }



}
