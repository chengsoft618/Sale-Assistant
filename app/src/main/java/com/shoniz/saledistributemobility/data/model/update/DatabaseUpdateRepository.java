package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.app.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.model.update.api.IDatabaseUpdateApi;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.Enums;
import com.shoniz.saledistributemobility.utility.data.fileSystem.FileManager;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;

import java.io.IOException;

import javax.inject.Inject;

public class DatabaseUpdateRepository extends UpdateBase implements IDatabaseUpdateRepository {

    @Inject
    public DatabaseUpdateRepository(CommonPackage commonPackage, IDatabaseUpdateApi databaseUpdateApi) {
        this.commonPackage = commonPackage;
        this.databaseUpdateApi = databaseUpdateApi;
    }

    @Override
    public FileContentModel getSaleDb() throws BaseException {
        return databaseUpdateApi.getSaleDb();
    }

    @Override
    public void syncSaleDb() throws BaseException {
        String dbPath = DBHelper.getDatabasePath(commonPackage.getContext())
                + "/" + Enums.DBName.SaleDatabase + ".db";

        if(FileManager.isFileExist(dbPath))
            FileManager.deleteFile(dbPath);
        try {
            FileManager.saveBase64File(getSaleDb().FileContents, dbPath);
        } catch (IOException e) {
            throw new InOutError(commonPackage, e, "خطا در درج دیتابیس" );
        }
    }

    private CommonPackage commonPackage;
    private IDatabaseUpdateApi databaseUpdateApi;
}
