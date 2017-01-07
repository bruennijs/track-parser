package lt.overdrive.trackparser.parsing.gpx;

import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.gpx.schema.GpxType;
import lt.overdrive.trackparser.parsing.gpx.schema.TrkType;
import lt.overdrive.trackparser.parsing.gpx.schema.WptType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.01.17.
 */
public class FlattenTrkSegmentsToTrackDomainObjectTransformStrategy implements IDomainObjectTransformStrategy {
    @Override
    public Trail transform(GpxType gpx) {
        return new Trail(Lists.newArrayList(convertToDomainObject(gpx)));
    }

    private Iterable<Track> convertToDomainObject(GpxType gpxType) {

        ArrayList<Track> trackList = new ArrayList();

        for (TrkType trkType : gpxType.getTrk())
        {
            List<WptType> waypointsOfAllSegments = reduceWaypointsOfAllSegments(trkType);

            Track track = GpxJaxbTypeConverter.toTrack(trkType, waypointsOfAllSegments.stream().map(GpxJaxbTypeConverter::toTrackPoint).collect(Collectors.toList()));

            trackList.add(track);
        }

        return trackList;
    }

    /**
     * Reduces all segment's track points to flat list of waypoints
     * @param trkType
     * @return
     */
    private List<WptType> reduceWaypointsOfAllSegments(TrkType trkType) {
        List<WptType> pointsOfAllSegments = new ArrayList();

        trkType.getTrkseg().stream().reduce(pointsOfAllSegments, (accumulated, trkseg) -> {
            trkseg.getTrkpt().stream().forEach(pointsOfAllSegments::add);
            return accumulated;

        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });

        return pointsOfAllSegments;
    }
}
