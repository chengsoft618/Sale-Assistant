package com.shoniz.saledistributemobility.di;

import com.shoniz.saledistributemobility.data.model.location.LocationWorkerService;
import com.shoniz.saledistributemobility.framework.bluetooth.BootCompleteReceiver;
import com.shoniz.saledistributemobility.framework.exception.log.UncoughtExceptionIntentService;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionWorkerService;
import com.shoniz.saledistributemobility.infrastructure.install.DownloadCompleteReceiver;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonService;
import com.shoniz.saledistributemobility.location.LocationManagementService;
import com.shoniz.saledistributemobility.location.TrackingService;
import com.shoniz.saledistributemobility.message.management.MessageManagementService;
import com.shoniz.saledistributemobility.message.receiver.PersonMessageReceiver;
import com.shoniz.saledistributemobility.order.RequestListModule;
import com.shoniz.saledistributemobility.order.RequestListViewModel;
import com.shoniz.saledistributemobility.order.RequestsListActivity;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.order.sent.SentOrdersFragment;
import com.shoniz.saledistributemobility.order.sent.SentOrdersFragmentModule;
import com.shoniz.saledistributemobility.order.unsent.UnSentOrdersFragmentModule;
import com.shoniz.saledistributemobility.order.unsent.UnsentOrdersFragment;
import com.shoniz.saledistributemobility.order.unvisited.UnvisitedCustomerFragment;
import com.shoniz.saledistributemobility.view.branch.BranchActivity;
import com.shoniz.saledistributemobility.view.branch.BranchActivityModule;
import com.shoniz.saledistributemobility.view.catalog.CatalogFragment;
import com.shoniz.saledistributemobility.view.catalog.CatalogFragmentModule;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryFragment;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryFragmentModule;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivityModule;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexFragment;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexFragmentModule;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBaseFragmentModule;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicFragment;
import com.shoniz.saledistributemobility.view.dialog.orderdescription.DescriptionDialog;
import com.shoniz.saledistributemobility.view.dialog.orderdescription.DescriptionDialogModule;
import com.shoniz.saledistributemobility.view.main.MainActivity;
import com.shoniz.saledistributemobility.view.main.MainActivityModule;
import com.shoniz.saledistributemobility.view.message.MessageActivity;
import com.shoniz.saledistributemobility.view.message.MessageActivityModule;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivity;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivityModule;
import com.shoniz.saledistributemobility.view.ordering.detail.printissue.PrintIssueActivity;
import com.shoniz.saledistributemobility.view.ordering.detail.printissue.PrintIssueActivityModule;
import com.shoniz.saledistributemobility.view.ordering.operation.OperationActivity;
import com.shoniz.saledistributemobility.view.ordering.operation.OrderVerifyActivityModule;
import com.shoniz.saledistributemobility.view.ordering.operation.cancel.VerifyCancelFragment;
import com.shoniz.saledistributemobility.view.ordering.operation.cancel.VerifyCancelModule;
import com.shoniz.saledistributemobility.view.ordering.operation.verify.VerifyFragment;
import com.shoniz.saledistributemobility.view.ordering.operation.verify.VerifyModule;
import com.shoniz.saledistributemobility.view.path.customerlist.CustomerListFragment;
import com.shoniz.saledistributemobility.view.path.customerlist.CustomerListFragmentModule;
import com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment;
import com.shoniz.saledistributemobility.view.path.pathlist.PathListFragmentModule;
import com.shoniz.saledistributemobility.view.web.WebSiteActivity;
import com.shoniz.saledistributemobility.view.web.WebSiteActivityModule;
import com.shoniz.saledistributemobility.view.settings.SettingsActivity;
import com.shoniz.saledistributemobility.view.tracking.TrackingActivity;
import com.shoniz.saledistributemobility.view.tracking.TrackingActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {OrderVerifyActivityModule.class})
    abstract OperationActivity bindOperationActivity();

    @ContributesAndroidInjector(modules = {OrderDetailActivityModule.class})
    abstract OrderDetailActivity bindOrderDetailActivity();

    @ContributesAndroidInjector(modules = {MessageActivityModule.class})
    abstract MessageActivity bindMessageActivity();

    @ContributesAndroidInjector(modules = {VerifyModule.class})
    public abstract VerifyFragment verifyFragment();

    @ContributesAndroidInjector(modules = {VerifyCancelModule.class})
    public abstract VerifyCancelFragment cancelVerifyFragment();

    @ContributesAndroidInjector
    public abstract LocationManagementService bindLocationServices();

    @ContributesAndroidInjector
    public abstract WialonService bindWiolonService();

    @ContributesAndroidInjector
    public abstract SendOrderService bindSendOrderService();

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    public abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {CustomerActivityModule.class})
    public abstract CustomerActivity bindCustomerActivity();

    @ContributesAndroidInjector(modules = {TrackingActivityModule.class})
    public abstract TrackingActivity bindTrackingActivity();

    @ContributesAndroidInjector()
    public abstract DownloadCompleteReceiver bindDownloadCompleteReceiver();

    @ContributesAndroidInjector()
    public abstract TrackingService bindTrackingService();

    @ContributesAndroidInjector()
    public abstract LocationWorkerService bindLocationWorkerService();

    @ContributesAndroidInjector()
    public abstract ExceptionWorkerService bindExceptionWorkerService();

    @ContributesAndroidInjector()
    public abstract BootCompleteReceiver bindBootCompleteReceiver();

    @ContributesAndroidInjector()
    public abstract SettingsActivity.GeneralPreferenceFragment bindGeneralPreferenceFragment();

    @ContributesAndroidInjector(modules = RequestListModule.class)
    public abstract RequestsListActivity bindRequestsListActivity();

    @ContributesAndroidInjector
    public abstract UnvisitedCustomerFragment bindUnvisitedCustomerFragment();

    @ContributesAndroidInjector(modules = BranchActivityModule.class)
    public abstract BranchActivity bindBranchActivity();

    @ContributesAndroidInjector(modules = PathListFragmentModule.class)
    public abstract PathListFragment bindPathListFragment();

    @ContributesAndroidInjector(modules = CustomerListFragmentModule.class)
    public abstract CustomerListFragment bindCustomerListFragment();

    @ContributesAndroidInjector()
    public abstract PersonMessageReceiver bindPersonMessageReceiver();

    @ContributesAndroidInjector(modules = DescriptionDialogModule.class)
    public abstract DescriptionDialog bindDescriptionDialog();


    @ContributesAndroidInjector(modules = CardIndexFragmentModule.class)
    public abstract CardIndexFragment bindCardIndexFragment();

    @ContributesAndroidInjector(modules = CustomerBaseFragmentModule.class)
    public abstract CustomerBasicFragment bindCustomerBasicFragment();


    @ContributesAndroidInjector(modules = SubCategoryFragmentModule.class)
    public abstract SubCategoryFragment bindSubCategoryFragment();

    @ContributesAndroidInjector(modules = CatalogFragmentModule.class)
    public abstract CatalogFragment bindCatalogFragment();

    @ContributesAndroidInjector(modules = SentOrdersFragmentModule.class)
    public abstract SentOrdersFragment bindSentOrdersFragment();

    @ContributesAndroidInjector(modules = UnSentOrdersFragmentModule.class)
    public abstract UnsentOrdersFragment bindUnSentOrdersFragment();


    @ContributesAndroidInjector()
    public abstract MessageManagementService bindMessageManagementService();

    @ContributesAndroidInjector()
    public abstract UncoughtExceptionIntentService bindUncoughtExceptionIntentService();

    @ContributesAndroidInjector(modules = WebSiteActivityModule.class)
    public abstract WebSiteActivity bindWebSiteActivity();

    @ContributesAndroidInjector(modules = PrintIssueActivityModule.class)
    public abstract PrintIssueActivity bindPrintIssueActivity();


}
