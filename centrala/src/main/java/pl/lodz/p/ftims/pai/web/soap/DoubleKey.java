package pl.lodz.p.ftims.pai.web.soap;

/**
 * Created by antosikj (Jakub Antosik) on 05/02/16.
 */
public class DoubleKey {
    final Long dataSourceId;
    final Long id;

    public DoubleKey(Long dataSourceId, Long id) {
        this.dataSourceId = dataSourceId;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleKey doubleKey = (DoubleKey) o;

        if (dataSourceId != null ? !dataSourceId.equals(doubleKey.dataSourceId) : doubleKey.dataSourceId != null)
            return false;
        return id != null ? id.equals(doubleKey.id) : doubleKey.id == null;

    }

    @Override
    public int hashCode() {
        int result = dataSourceId != null ? dataSourceId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
