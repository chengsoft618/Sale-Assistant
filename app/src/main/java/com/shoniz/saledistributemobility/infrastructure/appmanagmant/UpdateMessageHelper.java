package com.shoniz.saledistributemobility.infrastructure.appmanagmant;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.base.download.ProgressStage;
import com.shoniz.saledistributemobility.utility.StringHelper;

public class UpdateMessageHelper {
    public static String createMessage(Context context, ProgressStage stage, int currentProgress, int allProgress) {
        String preMessage = StringHelper.GenerateMessage(context.getString(R.string.progress_status), currentProgress, allProgress);
        switch (stage) {
            case GetEmployeePathsDb:
                return preMessage + context.getString(R.string.get_path_lists);
            case GetEmployeeCustomerBaseInfoDb:
                return preMessage + context.getString(R.string.get_customer_base);
            case GetCustomerBuyDb:
                return preMessage + context.getString(R.string.get_customer_buy);
            case GetEmployeeCustomerChequeDb:
                return preMessage + context.getString(R.string.get_customer_cheque);
            case GetEmployeeCustomerCreditDb:
                return preMessage + context.getString(R.string.get_customer_credit);
            case GetGeneralCardAllPaths:
                return preMessage + context.getString(R.string.show_update_path);
            case UpdatePaths:
                return preMessage + context.getString(R.string.get_path_lists);
            case UpdateCustomersBaseInfo:
                return preMessage + context.getString(R.string.get_customer_base);
            case UpdateCustomersBuy:
                return preMessage + context.getString(R.string.get_customer_buy);
            case UpdateCustomersCheque:
                return preMessage + context.getString(R.string.get_customer_cheque);
            case UpdateCustomersCredit:
                return preMessage + context.getString(R.string.get_customer_credit);
            case GetShortcutChanges:
                return preMessage + context.getString(R.string.get_coding_info);
            case GetProfileCategory:
                return preMessage + context.getString(R.string.get_profile_category);
            case GetCategory:
                return preMessage + context.getString(R.string.get_category);
            case GetSubCategory:
                return preMessage + context.getString(R.string.get_sub_category);
            case GetOrderList:
                return preMessage + context.getString(R.string.get_order_list);

            case GetSubCategoryDetail:
                return preMessage + context.getString(R.string.get_sub_category_detail);
            case GetBaseDb:
                return preMessage + context.getString(R.string.get_base_data);
            default:
                return "در حال بروز رسانی";
        }
    }
}

