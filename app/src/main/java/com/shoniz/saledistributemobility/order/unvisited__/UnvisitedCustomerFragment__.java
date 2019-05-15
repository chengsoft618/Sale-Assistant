package com.shoniz.saledistributemobility.order.unvisited__;

import dagger.android.support.DaggerFragment;


public class UnvisitedCustomerFragment__ extends DaggerFragment {
//    UnvisitedCustomerAdapter__ unvisitedCustomerAdapter;
//    List<UnvisitedCustomerModel__> models;
//    LinearLayoutManager layoutManager;
//    @Inject
//    IOrderRepository orderRepository;
//    @Inject
//    IAppUpdater appUpdater;
//
//    private   AppCompatActivity activity;
//
//    public UnvisitedCustomerFragment__() {
//    }
//
//    public static UnvisitedCustomerFragment__ newInstance() {
//        return new UnvisitedCustomerFragment__();
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    SharedOrderViewModel sharedOrderViewModel;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = (AppCompatActivity) getActivity();
//        sharedOrderViewModel = ViewModelProviders.of(activity).get(SharedOrderViewModel.class);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        try {
//
//            View view = inflater.inflate(R.layout.fragment_request_list, container, false);
//            RecyclerView recyclerView = view.findViewById(R.id.recyclerCustomer);
//            models = RequestBusiness.getUnvisitedCustomerList(activity);
//
//            List<ReasonModel__> reasonModels = OrderBusiness.getUnvisitingReason(activity);
//
//            boolean isSent = AppPref.isReasonSendAll(activity);
//            for (UnvisitedCustomerModel__ model : models) {
//                model.IsSent = isSent;
//            }
//
//
//            unvisitedCustomerAdapter = new UnvisitedCustomerAdapter__(models, reasonModels, null);
//            Common.setRecycleViewLayoutManager(activity, recyclerView);
//            layoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(layoutManager);
//            layoutManager.setSmoothScrollbarEnabled(true);
//            recyclerView.setAdapter(unvisitedCustomerAdapter);
//
//            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
//            floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    List<UnvisitedCustomerModel__> unvisitedCustomerModels =
//                            unvisitedCustomerAdapter.getUnvisitedList();
//                    if (unvisitedCustomerModels.size() > 0) {
//                        sendReason();
//                    } else {
//                        Common.toast(activity, "آیتمی برای ارسال انتخاب وجود ندارد");
//                    }
//                }
//            });
//
//            return view;
//
//        } catch (Exception e) {
//
//            HandleException systemException = new HandleException(activity, e);
//            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
//                    systemException.getUserTitle(), systemException.getUserMessage(),
//                    systemException.getSystemMessage());
//        }
//
//        return null;
//    }
//
//
////    private void upDateRecycle(int personId, Boolean isError, String errorMessage) {
////        for (int i = 0; i < models.size(); i++) {
////            if (models.get(i).PersonID == personId) {
////                models.get(i).isSelectedToUpdate = false;
////                if (isError) {
////                    models.get(i).IsSent = false;
////                    models.get(i).ErrorMessage = errorMessage;
////                } else {
////                    models.get(i).IsSent = true;
////                    models.get(i).ErrorMessage = "";
////                }
////                layoutManager.scrollToPosition(i);
////                unvisitedCustomerAdapter.notifyItemChanged(i);
////                break;
////            }
////        }
////    }
//
//
//    private void sendReason() {
//        RunnableMethod runDo = new RunnableMethod() {
//            @Override
//            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
//                RunnableModel runnableModel = new RunnableModel();
//                List<Integer> lst = new LinkedList<>();
//                for (UnvisitedCustomerModel__ model : unvisitedCustomerAdapter.getUnvisitedList()) {
//                    lst.add(model.PersonID);
//                }
//                try {
//                    List<UnvisitedCustomerReasonEntity> ReasonDto = OrderBusiness.sendReasonAll(activity, lst);
//                    appUpdater.updateOrder();
//                    models = RequestBusiness.getUnvisitedCustomerList(activity);
//                } catch (Exception e) {
//                    runnableModel.exception = new HandleException(activity, e);
//                    runnableModel.HasError = true;
//                }
//                return runnableModel;
//            }
//        };
//
//
//        RunnableMethod<RunnableModel, Object> runPost =
//                new RunnableMethod<RunnableModel, Object>() {
//                    @Override
//                    public Object run(RunnableModel runnableModel, OnProgressUpdate onProgressUpdate) {
//                        if (!runnableModel.HasError) {
//                            unvisitedCustomerAdapter.setModel(models);
//                            unvisitedCustomerAdapter.notifyDataSetChanged();
//                        } else {
//                            ErrorDialog.showDialog(activity,
//                                    runnableModel.exception.getUserTitle(), runnableModel.exception.getUserMessage(),
//                                    runnableModel.exception.getSystemMessage());
//                            runnableModel.exception.log();
//                            sharedOrderViewModel.isRefresh.setValue(true);
//                        }
//                        return null;
//                    }
//                };
//
//        AsyncTaskDialog asyncTaskDialog = new AsyncTaskDialog(activity,
//                getString(R.string.wait),
//                getString(R.string.send_not_sale_resaon), null, runDo, runPost);
//        asyncTaskDialog.execute();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof AppCompatActivity) {
//            this.activity =  (AppCompatActivity) context;
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        activity = null;
//        super.onDetach();
//    }
}
