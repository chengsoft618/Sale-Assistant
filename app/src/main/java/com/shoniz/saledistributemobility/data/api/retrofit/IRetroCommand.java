package com.shoniz.saledistributemobility.data.api.retrofit;

import com.shoniz.saledistributemobility.framework.InOutError;

import java.io.IOError;
import java.io.IOException;

public interface IRetroCommand<M, T > {
    M exec(T service) throws IOException, ApiException;
}
