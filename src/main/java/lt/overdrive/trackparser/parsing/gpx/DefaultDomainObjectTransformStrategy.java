package lt.overdrive.trackparser.parsing.gpx;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.gpx.schema.GpxType;
import lt.overdrive.trackparser.parsing.gpx.schema.TrkType;
import lt.overdrive.trackparser.parsing.gpx.schema.TrksegType;
import lt.overdrive.trackparser.parsing.gpx.schema.WptType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DefaultDomainObjectTransformStrategy implements IDomainObjectTransformStrategy {
    public DefaultDomainObjectTransformStrategy() {
    }

    /**
     * Transforms JAXB deserilaized types to domain objects.
     * @param gpx
     * @return
     */
    @Override
    public Trail transform(GpxType gpx) {
        List<Track> tracks = new ArrayList<Track>();
        for (TrkType trk : gpx.getTrk()) {
            for (TrksegType seg : trk.getTrkseg()) {
                List<TrackPoint> points = Lists.transform(seg.getTrkpt(), new Function<WptType, TrackPoint>() {
                    @Override
                    public TrackPoint apply(WptType input) {
                        return convert2GpsTrackPoint(input);
                    }
                });
                tracks.add(new Track(trk.getName(), trk.getDesc(), points));
            }
        }
        return new Trail(tracks);
    }

    TrackPoint convert2GpsTrackPoint(WptType input) {
        BigDecimal elevation = input.getEle();
        return new TrackPoint(
                input.getLat().doubleValue(),
                input.getLon().doubleValue(),
                elevation != null ? elevation.doubleValue() : null,
                GpxJaxbTypeConverter.parseTime(input.getTime()));
    }
}