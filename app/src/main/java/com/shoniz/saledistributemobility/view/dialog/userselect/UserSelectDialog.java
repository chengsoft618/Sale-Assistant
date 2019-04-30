package com.shoniz.saledistributemobility.view.dialog.userselect;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

import javax.inject.Inject;

public class UserSelectDialog {

    private List<UserEntity> users;

    @Inject
    public UserSelectDialog(IUserRepository userRepository, ISettingRepository settingRepository) {
        users = userRepository.getUsersExpect(settingRepository.getEmployeeInfoEntity().EmployeeId);

    }

    public void show(AppCompatActivity activity,ChooseUser ChooseUser) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        mBuilder.setTitle(R.string.verify_sent_to);
        String[] userArray = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userArray[i]=activity.getString(R.string.common_employee_id_name,users.get(i).userId,users.get(i).fullName);
        }
        mBuilder.setSingleChoiceItems(userArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ChooseUser.onChooseUser(users.get(i));
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public interface ChooseUser {
        void onChooseUser(UserEntity userEntity);
    }
}
