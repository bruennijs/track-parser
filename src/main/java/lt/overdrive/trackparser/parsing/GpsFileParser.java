package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.Trail;

import javax.xml.validation.Schema;
import java.io.File;
import java.io.InputStream;

public interface GpsFileParser {
    Trail parse(File file) throws ParserException;
    Trail parse(InputStream is) throws ParserException;
    Schema getSchema() throws ParserException;
}
