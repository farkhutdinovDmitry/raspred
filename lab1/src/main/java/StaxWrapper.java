import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StaxWrapper implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private static final String fileName = "RU-NVS.osm.bz2";
    private final XMLStreamReader reader;

    public StaxWrapper() throws XMLStreamException, IOException {
        InputStream fin = this.getClass().getClassLoader().getResourceAsStream(fileName);

        assert fin != null;
        BufferedInputStream in = new BufferedInputStream(fin);
        reader = FACTORY.createXMLStreamReader(new BZip2CompressorInputStream(in));
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    @Override
    public void close() throws XMLStreamException {
        reader.close();
    }
}
