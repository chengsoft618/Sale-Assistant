package com.shoniz.saledistributemobility.data.api.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.data.api.ApiError;
import com.shoniz.saledistributemobility.data.api.core.ICoreApi;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.model.ApiMessage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.data.api.ApiConsts;
import com.shoniz.saledistributemobility.utility.network.Connectivity;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroManager<M, S> {


    CommonPackage commonPackage;
    BranchEntity branchEntity;
    ICoreApi coreApi;


    public RetroManager(CommonPackage commonPackage) {
        this.commonPackage = commonPackage;
        branchEntity = commonPackage.getSettingPref().getBranchEntity();
        this.coreApi = coreApi;
    }

    public static <M> Response<M> execCaller(Call<M> caller) throws IOException, ApiException {

        Response<M> response = caller.execute();
        if (!response.isSuccessful()) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            ApiMessage apiMessage = gson.fromJson(response.errorBody().string(),
                    new TypeToken<ApiMessage>() {
                    }.getType());
            throw new ApiException(apiMessage.Message);
        }
        return response;

    }

    public M callOfficeApi(IRetroCommand<M, S> command, Class<S> serviceClass) throws BaseException {
        BaseException baseException = null;
        if (Connectivity.isConnectedWifi(commonPackage.getContext())) {
            try {

                return command.exec(makeService(makeUrl(branchEntity.LanIp), serviceClass));
            } catch (IOException e) {
                baseException = new InOutError(commonPackage, e, branchEntity.LanIp);
            } catch (ApiException e) {
                baseException = new ApiError(commonPackage, e);
            }
        }
        try {
            return command.exec(makeService(makeUrl(branchEntity.WanIp1), serviceClass));
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, branchEntity.WanIp1, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        try {
            M m = command.exec(makeService(makeUrl(branchEntity.WanIp2), serviceClass));
            commonPackage.getSettingPref().switchUrlPriority();
            return m;
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, branchEntity.WanIp2, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        throw baseException;
    }

    public String getOfficeActiveUrl(IRetroCommand<M, S> command, Class<S> serviceClass) throws BaseException {
        BaseException baseException = null;
        if (Connectivity.isConnectedWifi(commonPackage.getContext())) {
            try {
                command.exec(makeService(makeUrl(branchEntity.LanIp), serviceClass));
                return makeBaseUrl(branchEntity.LanIp);
            } catch (IOException e) {
                baseException = new InOutError(commonPackage, e, branchEntity.LanIp);
            } catch (ApiException e) {
                baseException = new ApiError(commonPackage, e);
            }
        }
        try {
            command.exec(makeService(makeUrl(branchEntity.WanIp1), serviceClass));
            return makeBaseUrl(branchEntity.WanIp1);
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, branchEntity.WanIp1, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        try {
            command.exec(makeService(makeUrl(branchEntity.WanIp2), serviceClass));
            return makeBaseUrl(branchEntity.WanIp2);
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, branchEntity.WanIp2, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        throw baseException;
    }


    public M callShonizApi(IRetroCommand<M, S> command, Class<S> serviceClass) throws BaseException {
        BaseException baseException = null;

        try {
            return command.exec(makeService(ApiConsts.CENTRAL_LOCAL_URL_BASE_SITE, serviceClass));
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, ApiConsts.CENTRAL_LOCAL_URL_BASE_SITE);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        try {
            return command.exec(makeService(ApiConsts.CENTRAL_EXTERNAL_URL_BASE_SITE, serviceClass));
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, ApiConsts.CENTRAL_EXTERNAL_URL_BASE_SITE, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }
        try {
            return command.exec(makeService(ApiConsts.CENTRAL_DEVELOP_URL_BASE_SITE, serviceClass));
        } catch (IOException e) {
            baseException = new InOutError(commonPackage, e, ApiConsts.CENTRAL_DEVELOP_URL_BASE_SITE, baseException);
        } catch (ApiException e) {
            baseException = new ApiError(commonPackage, e, baseException);
        }

        throw baseException;
    }


    private S makeService(String urlPath, Class<S> serviceClass) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlPath)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }

    private String makeUrl(String url) {
        return MessageFormat.format("http://{0}:{1}/{2}",
                url,
                branchEntity.ServicePort,
                ApiConsts.API_PRE_ROUTE);
    }

    private String makeBaseUrl(String url) {
        return MessageFormat.format("http://{0}:{1}",
                url,
                branchEntity.ServicePort);
    }

}
