package com.shoniz.saledistributemobility.view.branch;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BusinessException;

public class NoBranchSelectedError extends BusinessException implements IDisplayExceptionLogger{

    public NoBranchSelectedError(CommonPackage commonPackage) {
        super(commonPackage);
        userMessage = "مرکزی انتخاب نشده است";
    }
}
