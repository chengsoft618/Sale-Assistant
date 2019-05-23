package com.shoniz.saledistributemobility.data.model.path.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.framework.CommonPackage;

public class PathRetrofit implements IPathApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public PathRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }
}
