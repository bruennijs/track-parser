package lt.overdrive.trackparser.parsing.gpx;

import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.AbstractParser;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.parsing.gpx.schema.GpxType;
import lt.overdrive.trackparser.parsing.gpx.schema.WptType;

import javax.xml.bind.UnmarshalException;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.InputStream;

import static lt.overdrive.trackparser.utils.ResourceUtils.loadSchema;

public class GpxParser extends AbstractParser {
    private final IDomainObjectTransformStrategy domainObjectTransformStrategy = new FlattenTrkSegmentsToTrackDomainObjectTransformStrategy();

    @Override
    protected Trail loadTrail(File file) throws Exception {
        GpxType gpx = (GpxType) loadXml(file, GpxType.class);
        return domainObjectTransformStrategy.transform(gpx);
    }

    @Override
    public Schema getSchema() throws ParserException {
        return loadSchema("gpx/gpx.xsd");
    }

    @Override
    protected Trail loadTrail(InputStream is) throws Exception {
        GpxType gpxType = (GpxType) loadXml(is, GpxType.class);
        return domainObjectTransformStrategy.transform(gpxType);
    }

    @Override
    protected Trail throwInvalidFileException(UnmarshalException pe) throws ParserException {
        throw new InvalidGpxFile("Invalid gpx file.", pe);
    }
}
