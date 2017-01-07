package lt.overdrive.trackparser.parsing.gpx;

import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.parsing.gpx.schema.TrkType;
import lt.overdrive.trackparser.parsing.gpx.schema.WptType;
import org.joda.time.DateTime;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.util.Optional;

/**
 * Created by bruenni on 30.12.16.
 */
class GpxJaxbTypeConverter {
    /**
     * Converts trkType to domain model
     * @param trkType
     * @param points
     * @return
     */
    public static Track toTrack(TrkType trkType, Iterable<TrackPoint> points)
    {
        return new Track(trkType.getName(), trkType.getDesc(), Lists.newArrayList(points));
    }

    /**
     * Converts Wpttype to domain model.
     * @param wptType
     * @return
     */
    public static  TrackPoint toTrackPoint(WptType wptType) {
        return new TrackPoint(wptType.getLat().doubleValue(), wptType.getLon().doubleValue(), wptType.getEle() != null ? wptType.getEle().doubleValue() : null, parseTime(wptType.getTime()));
    }

    public static DateTime parseTime(XMLGregorianCalendar timestamp) {
        if (timestamp != null)
            return DateTime.parse(timestamp.toXMLFormat());
        return null;
    }
}
