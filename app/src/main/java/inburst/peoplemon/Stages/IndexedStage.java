package inburst.peoplemon.Stages;

/**
 * Created by lennyhicks on 10/31/16.
 */

import com.davidstemmer.screenplay.stage.XmlStage;

public abstract class IndexedStage extends XmlStage {

    public final String id;

    protected IndexedStage(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedStage that = (IndexedStage) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
