package lt.overdrive.trackparser.parsing.gpx;

import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.gpx.schema.GpxType;

/**
 * Created by bruenni on 07.01.17.
 */
public interface IDomainObjectTransformStrategy {
    /**
     * Transforms JAXB deserilaized types to domain objects.
     * @param gpx
     * @return
     */
    Trail transform(GpxType gpx);
}
