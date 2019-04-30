package com.shoniz.saledistributemobility.utility.data.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.view.customer.ActionSendType;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDto;
import com.shoniz.saledistributemobility.order.detail.OrderDetailModel;
import com.shoniz.saledistributemobility.order.OrderModel;
import com.shoniz.saledistributemobility.catalog.CategoryModel;
import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.customer.info.bought.CustomerBuyModel;
import com.shoniz.saledistributemobility.view.customer.info.cheque.CustomerChequeModel;
import com.shoniz.saledistributemobility.view.customer.info.credit.CustomerCreditModel;
import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.order.unvisited.ReasonDto;
import com.shoniz.saledistributemobility.view.path.PathModel;
import com.shoniz.saledistributemobility.catalog.ProfileCategoryModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.order.ResultModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.utility.device.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ferdos.s on 6/1/2017.
 */

public class OfficeApi {
    private Context context;

    public OfficeApi(Context context) {
        this.context = context;
    }

    public OfficeApi() {
    }

    private <T> T getData(String url, PostModel postModel, TypeToken<T> typeToken) throws OldApiException, IOException, ConnectionException {
        Response response = new CallApi<T>(Connection.getApiUrl(context))
                .Post(url, postModel);

        if (response.code() != 200) throw new OldApiException(context, response);

        if(typeToken.getType() == Void.class)
            return null;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        T obj = gson.fromJson(response.body().charStream(),
                typeToken.getType());

        return obj;
    }

    public FileContentModel getSaleDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_SALE_DATA );
    }

    private FileContentModel getDbData(String url)throws OldApiException, IOException, ConnectionException {
        TypeToken<FileContentModel> typeToken = new TypeToken<FileContentModel>() {};
        return getData(url, getPostModel(), typeToken);
    }

    private PostModel getPostModel() {
        PostModel postModel = new PostModel();
        postModel.Imei = new Device(context).getIMEI();
        postModel.ApplicationVersion = Common.getVersionCode(context);
        postModel.DataBaseVersion = "1.8.0.0";

        return postModel;
    }



    public List<RoleEntity> getRoles() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<RoleEntity>> typeToken = new TypeToken<List<RoleEntity>>() {
        };
        return getData(ApiConsts.Api.GET_Roles, getPostModel(), typeToken);
    }
    public List<UserEntity> getUsers() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<UserEntity>> typeToken = new TypeToken<List<UserEntity>>() {
        };
        return getData(ApiConsts.Api.GET_Users, getPostModel(), typeToken);
    }

    public void sendCustomerLocation(int customerId,LocationEntity locationEntity) throws OldApiException, IOException, ConnectionException {
        TypeToken<Void> typeToken = new TypeToken<Void>() {
        };
        PostModel postModel = getPostModel();
        postModel.Accuracy = locationEntity.accuracy;
        postModel.Latitude = locationEntity.latitude;
        postModel.Longitude = locationEntity.longitude;
        postModel.PersonId = customerId;
        getData(ApiConsts.Api.SEND_CUSTOMER_LOCATION, postModel, typeToken);
    }

    public List<PathModel> getVisitorPaths(List<Integer> pathsCodes) throws OldApiException, IOException, ConnectionException {
        return getPaths(pathsCodes);
    }


    private List<PathModel> getPaths(List<Integer> pathsCodes) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<PathModel>> typeToken = new TypeToken<List<PathModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathsCodes;
        return getData(ApiConsts.Api.GET_VISITOR_PATHS, postModel, typeToken);
    }


    public FileContentModel getVisitorPathsDb() throws OldApiException, IOException, ConnectionException {
        return  getDbData(ApiConsts.Api.GET_EMPLOYEE_PATHS_DB);
    }

    public FileContentModel getEmployeeCustomerBaseInfoDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BASE_INFO_DB);
    }

    public List<CustomerBasicModel> getEmployeeCustomerBaseInfoByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerBasicModel>> typeToken = new TypeToken<List<CustomerBasicModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BASE_INFO_BY_PERSON_IDS, postModel, typeToken);
    }

    public List<CustomerBasicModel> getEmployeeCustomerBaseInfoByPath(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerBasicModel>> typeToken = new TypeToken<List<CustomerBasicModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BASE_INFO_BY_PATH, postModel, typeToken);
    }

    public FileContentModel getEmployeeCustomerCreditDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CREDIT_DB);
    }

    public List<CustomerCreditModel> getEmployeeCustomerCreditByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerCreditModel>> typeToken = new TypeToken<List<CustomerCreditModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CREDIT_BY_PERSON_IDS, postModel, typeToken);
    }

    public List<CustomerCreditModel> getEmployeeCustomerCreditByPath(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerCreditModel>> typeToken = new TypeToken<List<CustomerCreditModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CREDIT_BY_PATH, postModel, typeToken);
    }

    public FileContentModel getEmployeeCustomerSumBuyAndBuyReturnedDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_CUSTOMER_BUY_DB);
    }

    public List<CustomerBuyModel> getEmployeeCustomerSumBuyAndBuyReturnedByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerBuyModel>> typeToken = new TypeToken<List<CustomerBuyModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_SUM_BUY_AND_BUY_RETURNED_BY_PERSON_IDS, postModel, typeToken);
    }

    public List<CustomerBuyModel> getEmployeeCustomerSumBuyAndBuyReturnedByPath(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerBuyModel>> typeToken = new TypeToken<List<CustomerBuyModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_SUM_BUY_AND_BUY_RETURNED_BY_PATH, postModel, typeToken);
    }

    public FileContentModel getEmployeeCustomerChequeDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CHEQUE_DB);
    }

    public List<CustomerChequeModel> getEmployeeCustomerChequeByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerChequeModel>> typeToken = new TypeToken<List<CustomerChequeModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CHEQUE_BY_PERSON_IDS, postModel, typeToken);
    }

    public List<CustomerChequeModel> getEmployeeCustomerChequeByPath(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<CustomerChequeModel>> typeToken = new TypeToken<List<CustomerChequeModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_CHEQUE_BY_PATH, postModel, typeToken);
    }

    public List<ProductImageModel> getShortcutChanges(List<ResourceModel> resourceModels) throws OldApiException, IOException, ConnectionException {
        TypeToken<List<ProductImageModel>> typeToken = new TypeToken<List<ProductImageModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.ResourceFiles = resourceModels;
        return getData(ApiConsts.Api.GET_SHORTCUT_CHANGES, postModel, typeToken);
    }

    public ProductImageModel getShortcutImage(ProductImageModel shortcutModel) throws OldApiException, IOException, ConnectionException {
        TypeToken<ProductImageModel> typeToken = new TypeToken<ProductImageModel>() {
        };
        PostModel postModel = getPostModel();
        postModel.Shortcut = shortcutModel.Shortcut;
        postModel.VersionNo = shortcutModel.VersionNo;
        return getData(ApiConsts.Api.GET_SHORTCUT_IMAGE, postModel, typeToken);
    }

    public FileContentModel GetOrderDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_ORDER_DB);
    }

    public List<OrderModel> getBusinessDocByPaths(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<OrderModel>> typeToken = new TypeToken<List<OrderModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_BY_PATH, postModel, typeToken);
    }
    public List<OrderDetailModel> getBusinessDocDetailByPaths(List<Integer> pathIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<OrderDetailModel>> typeToken = new TypeToken<List<OrderDetailModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = pathIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_DETAIL_BY_PATH, postModel, typeToken);
    }

    public List<OrderModel> getBusinessDocByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<OrderModel>> typeToken = new TypeToken<List<OrderModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_BY_PERSON_IDS, postModel, typeToken);
    }
    public List<OrderDetailModel> getBusinessDocDetailByPersonIds(List<Integer> personIds) throws OldApiException, ConnectionException, IOException {
        TypeToken<List<OrderDetailModel>> typeToken = new TypeToken<List<OrderDetailModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = personIds;
        return getData(ApiConsts.Api.GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_DETAIL_BY_PERSON_IDS, postModel, typeToken);
    }





    public ProfileCategoryModel getProfileCategory(List<Integer> ids) throws OldApiException, IOException, ConnectionException {
        TypeToken<ProfileCategoryModel> typeToken = new TypeToken<ProfileCategoryModel>() {
        };
        PostModel postModel = getPostModel();
        postModel.Ids = ids;
        return getData(ApiConsts.Api.GET_PROFILE_CATEGORY, postModel, typeToken);
    }

    public List<ProfileCategoryModel> getProfileCategoryAll() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<ProfileCategoryModel>> typeToken = new TypeToken<List<ProfileCategoryModel>>() {
        };
        return getData(ApiConsts.Api.GET_PROFILE_CATEGORY_ALL, getPostModel(), typeToken);
    }

    public List<CategoryModel> getCategoryAll() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<CategoryModel>> typeToken = new TypeToken<List<CategoryModel>>() {
        };
        return getData(ApiConsts.Api.GET_CATEGORY_ALL, getPostModel(), typeToken);
    }

    public List<SubCategoryModel> getSubCategoryAll() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<SubCategoryModel>> typeToken = new TypeToken<List<SubCategoryModel>>() {
        };
        return getData(ApiConsts.Api.GET_SUB_CATEGORY_ALL, getPostModel(), typeToken);
    }

    public List<SubCategoryDetailModel> getSubCategoryDetailAll() throws OldApiException, IOException, ConnectionException {
        TypeToken<List<SubCategoryDetailModel>> typeToken = new TypeToken<List<SubCategoryDetailModel>>() {
        };
        return getData(ApiConsts.Api.GET_SUB_CATEGORY_DETAIL_ALL, getPostModel(), typeToken);
    }

    public List<ResourceModel> getResourceChanges(List<ResourceModel> resourceModels) throws OldApiException, IOException, ConnectionException {
        TypeToken<List<ResourceModel>> typeToken = new TypeToken<List<ResourceModel>>() {
        };
        PostModel postModel = getPostModel();
        postModel.ResourceFiles = resourceModels;
        return getData(ApiConsts.Api.GET_RESOURCE_IMAGE, postModel, typeToken);
    }

    public ResourceModel getResource(ResourceModel resourceModel) throws OldApiException, IOException, ConnectionException {
        TypeToken<ResourceModel> typeToken = new TypeToken<ResourceModel>() {
        };
        PostModel postModel = getPostModel();
        postModel.ResourceFileId = resourceModel.ResourceFileId;
        postModel.VersionNo = resourceModel.VersionNo;
        return getData(ApiConsts.Api.GET_RESOURCE_FILE, postModel, typeToken);
    }

    public FileContentModel getBaseDb() throws OldApiException, IOException, ConnectionException {
        return getDbData(ApiConsts.Api.GET_BASE_DATA);
    }

    public ResultModel sendOrder(CardIndexDto cardIndexDto, SendRequestModel sendRequestModel) throws IOException, ConnectionException, OldApiException {
        TypeToken<ResultModel> typeToken = new TypeToken<ResultModel>() {
        };
        PostModel postModel = getPostModel();
        postModel.RequiresList = cardIndexDto.RequiresList;
        postModel.ExistenceList = cardIndexDto.ExistenceList;
        postModel.PersonId = cardIndexDto.PersonId;
        postModel.IsDelete = false;
        postModel.IsNew = sendRequestModel.actionSendType == ActionSendType.NewOrder;
        postModel.ChequeDuration = cardIndexDto.ChequeDuration;
        postModel.PaymentTypeID = cardIndexDto.ChequeDuration == 0 ? 1 : 2;
        postModel.SaleDesc = cardIndexDto.SaleDesc.length()==0? " ":cardIndexDto.SaleDesc;
        postModel.AccDesc = cardIndexDto.AccDesc.length()==0? " ":cardIndexDto.AccDesc;
        postModel.GpsStatus = 1;
        postModel.OrderNo = cardIndexDto.OrderNo;
        postModel.AddressID=cardIndexDto.AddressID;
        postModel.NeedDate = PersianCalendar.getPersianDate();
        if(sendRequestModel.location!=null){
            postModel.Accuracy = sendRequestModel.location.accuracy;
            postModel.Latitude = sendRequestModel.location.latitude;
            postModel.Longitude = sendRequestModel.location.longitude;
        }
        postModel.BatteryStatus = com.shoniz.saledistributemobility.framework.Device.getBatteryCurrentLevel(context);
        return getData(ApiConsts.Api.SEND_ORDER, postModel, typeToken);
    }

    public ResultModel deleteOrder(SendRequestModel sendRequestModel) throws IOException, ConnectionException, OldApiException {
        TypeToken<ResultModel> typeToken = new TypeToken<ResultModel>() {
        };
        PostModel postModel = getPostModel();
        postModel.PersonId = sendRequestModel.PersonID;
        postModel.IsDelete = true;
        postModel.OrderNo = sendRequestModel.OrderNo;
        postModel.PaymentTypeID = 1;
        postModel.GpsStatus = 1;
        return getData(ApiConsts.Api.SEND_ORDER, postModel, typeToken);
        // TODO : set Latitude,Longitude,Accuracy,BatteryStatus,GPSStatus
    }

    public void SetReasonAll(ReasonDto reasonDto) throws IOException, ConnectionException, OldApiException {
        TypeToken<Void> typeToken = new TypeToken<Void>() {
        };
        PostModel postModel = getPostModel();
        postModel.PersonId = reasonDto.PersonID;
        postModel.NotSaleReasonId = reasonDto.NotSallReasonID;
        postModel.FromDate = reasonDto.PersianDate;
        List<ReasonDto> reasonDtos = new ArrayList<>();
        reasonDtos.add(reasonDto);
        postModel.ReasonList = reasonDtos;
        // TODO : set Latitude,Longitude,Accuracy,BatteryStatus,GPSStatus
        getData(ApiConsts.Api.SET_REASON, postModel, typeToken);
    }

    public List<UnvisitedCustomerReasonEntity> SetReasonAll(List<ReasonDto> reasonDto) throws IOException, ConnectionException, OldApiException {
        TypeToken<List<UnvisitedCustomerReasonEntity>> typeToken = new TypeToken<List<UnvisitedCustomerReasonEntity>>() {
        };
        PostModel postModel = getPostModel();
        postModel.ReasonList = reasonDto;
        // TODO : set Latitude,Longitude,Accuracy,BatteryStatus,GPSStatus
        return getData(ApiConsts.Api.SET_REASON_ALL, postModel, typeToken);
    }
}
