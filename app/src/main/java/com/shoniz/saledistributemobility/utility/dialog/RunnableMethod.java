package com.shoniz.saledistributemobility.utility.dialog;

public interface RunnableMethod<TIN,TOUT> {
    TOUT run(TIN param, OnProgressUpdate onProgressUpdate);
}
