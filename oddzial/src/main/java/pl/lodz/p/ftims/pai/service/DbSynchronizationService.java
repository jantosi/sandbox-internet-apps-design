package pl.lodz.p.ftims.pai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationBusinessDataResponse;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationUsersResponse;

import java.util.concurrent.TimeUnit;

/**
 * Created by antosikj (Jakub Antosik) on 21/01/16.
 */
@Service
public class DbSynchronizationService {
    private static final Logger LOG = LoggerFactory.getLogger(DbSynchronizationService.class);

    @Autowired
    private SynchronizationBusinessDataSoapService synchronizationBusinessDataSoapService;

    @Autowired
    private SynchronizationUsersSoapService synchronizationUsersSoapService;

    @Autowired
    private SynchronizationUsersProcessor synchronizationUsersProcessor;

    public void startDatabaseSyncBusinessData() throws DbSynchronizationException, InterruptedException {
        try {
            SynchronizationBusinessDataResponse synchronizationBusinessDataResponse = synchronizationBusinessDataSoapService.sendSynchronizationRequest();
        } catch (Exception e) {
            LOG.error("An error has occurred during synchronization of the business data", e);
            TimeUnit.MINUTES.sleep(2);
            startDatabaseSyncBusinessData();
        }
    }

    public void startDatabaseSyncUsers() throws InterruptedException {
        try {
            SynchronizationUsersResponse synchronizationUsersResponse = synchronizationUsersSoapService.sendSynchronizationRequest();
            synchronizationUsersProcessor.synchronize(synchronizationUsersResponse);
        } catch (Exception e) {
            LOG.error("An error has occurred during synchronization of the users", e);
            TimeUnit.MINUTES.sleep(2);
            startDatabaseSyncUsers();
        }
    }
}
