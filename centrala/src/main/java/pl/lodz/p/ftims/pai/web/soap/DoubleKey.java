package pl.lodz.p.ftims.pai.web.soap;

/**
 * Created by antosikj (Jakub Antosik) on 05/02/16.
 */
public class DoubleKey {
    public final Long id;
    public final Long dataSourceId;
    public final Long sourceDepartmentId;

    public DoubleKey(Long id, Long dataSourceId, Long sourceDepartmentId) {
        this.dataSourceId = dataSourceId;
        this.id = id;
        this.sourceDepartmentId = sourceDepartmentId;
    }


}
