package pl.lodz.p.ftims.pai.web.soap;

import pl.lodz.p.ftims.pai.domain.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user"
})
@XmlRootElement(name = "synchronizationUsersResponse")
public class SynchronizationUsersResponse {

    @XmlElement(required = true)
    protected List<User> user;

    public List<User> getUser() {
        if(null == user){
            user = new ArrayList<>();
        }
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
