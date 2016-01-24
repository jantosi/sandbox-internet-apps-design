package pl.lodz.p.ftims.pai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationResponse;

/**
 * Created by antosikj (Jakub Antosik) on 21/01/16.
 */
@Service
public class DbSynchronizationService {
    private static final Logger LOG = LoggerFactory.getLogger(DbSynchronizationService.class);

    @Autowired
    private SynchronizationSoapService synchronizationSoapService;

    @Autowired
    private SynchronizationProcessor synchronizationProcessor;

    public void startDatabaseSyncWithHeadquarters() throws DbSynchronizationException {
        try {
            SynchronizationResponse synchronizationResponse = synchronizationSoapService.sendSynchronizationRequest();
            synchronizationProcessor.synchronize(synchronizationResponse);
        } catch (Exception e) {
            LOG.error("An error has occurred during synchronization", e);
        }
    }

}
