package pl.lodz.p.ftims.pai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.pai.domain.User;
import pl.lodz.p.ftims.pai.repository.UserRepository;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationUsersResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by <a href="mailto:171131@edu.p.lodz.pl">Andrzej Lisowski</a> on 03.02.16.
 */
@Component
public class SynchronizationUsersProcessor {

    @Autowired
    private UserRepository userRepository;

    public SynchronizationUsersProcessor() {
    }

    public void synchronize(SynchronizationUsersResponse response) throws Exception {
        deleteNotExisting(response);
        updateOrSaveNew(response);
    }

    private void deleteNotExisting(SynchronizationUsersResponse response) {
        final List<Long> newUserIds = response.getUser().stream().map(User::getId).collect(Collectors.toList());

        final List<Long> oldUsersIds = userRepository.selectIds();

        oldUsersIds.stream().filter(oldId -> !newUserIds.contains(oldId)).forEach(userRepository::delete);
    }

    private void updateOrSaveNew(SynchronizationUsersResponse response){
        userRepository.save(response.getUser());
    }

}
