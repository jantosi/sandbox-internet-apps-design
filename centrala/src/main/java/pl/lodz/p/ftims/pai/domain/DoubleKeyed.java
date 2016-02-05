package pl.lodz.p.ftims.pai.domain;

import pl.lodz.p.ftims.pai.web.soap.DoubleKey;

/**
 * Created by antosikj (Jakub Antosik) on 05/02/16.
 */
public interface DoubleKeyed {
    Long getId();
    Long getDataSourceId();

    default DoubleKey key(){
        return new DoubleKey(getDataSourceId(), getId());
    }
}
