package com.shoniz.saledistributemobility.data.api;

import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.utility.data.api.ApiConsts;

import java.io.IOException;
import java.text.MessageFormat;

public class URLManager {


    private CommonPackage commonPackage;
    private BranchEntity branchEntity;


    public URLManager(CommonPackage commonPackage) {
        this.commonPackage = commonPackage;
        this.branchEntity = commonPackage.getSettingPref().getBranchEntity();
    }

    public void callOfficeApi(execUrl command) throws InOutError {
        InOutError inOutError = null;
        try {
            command.call(makeUrl(branchEntity.LanIp));
            return;
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.LanIp);
        }
        try {
            command.call(makeUrl(branchEntity.WanIp1));
            return;
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.WanIp1, inOutError);
        }
        try {
            command.call(makeUrl(branchEntity.WanIp2));
            return;
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.WanIp2, inOutError);
        }
        throw inOutError;
    }

    public void callShonizApi(execUrl command) throws InOutError {
        InOutError inOutError = null;
        try {
            command.call(ApiConsts.CENTRAL_LOCAL_URL_BASE_SITE);
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.LanIp);
        }
        try {
            command.call(ApiConsts.CENTRAL_EXTERNAL_URL_BASE_SITE);
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.LanIp);
        }
        try {
            command.call(ApiConsts.CENTRAL_DEVELOP_URL_BASE_SITE);
        } catch (IOException e) {
            inOutError = new InOutError(commonPackage, e, branchEntity.LanIp);
        }

        throw inOutError;

    }

    private String makeUrl(String url) {
        return MessageFormat.format("http://{0}:{1}",
                url,
                branchEntity.ServicePort);
    }

    public interface execUrl {
        void call(String url) throws IOException, InOutError;
    }
}
