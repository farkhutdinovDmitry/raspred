import lombok.var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;

import static java.lang.String.format;

public class StatCollector {
    private static final Logger logger = LogManager.getLogger(StatCollector.class.getName());
    private StatInfo statInfo;

    public StatCollector() {
        statInfo = new StatInfo();
    }

    public void collectStats() {
        try {
            XMLStreamReader reader = new StaxWrapper().getReader();
            while (reader.hasNext()) {
                var event = reader.next();
                if (event != XMLEvent.START_ELEMENT || !"node".equals(reader.getLocalName())) {
                    continue;
                }
                statInfo.incNumberOfNodes();
                var user = -1;
                var uid = -1;
                for (var i = 0; i < reader.getAttributeCount(); i++) {
                    final var attributeName = reader.getAttributeLocalName(i);
                    if ("user".equals(attributeName)) {
                        user = i;
                    }

                    if ("uid".equals(attributeName)) {
                        uid = i;
                    }
                }
                if (user != -1) {
                    statInfo.addEdit(reader.getAttributeValue(user));
                }
                if (uid != -1) {
                    statInfo.addName(reader.getAttributeValue(uid));
                }
            }
        } catch (XMLStreamException | IOException e) {
            logger.error("error in collect stats: " + e.getMessage());
        }
        logStat();
    }

    private void logStat() {
        var userEdits = statInfo.getUserEdits();
        var uniqueNames = statInfo.getUniqueNames();
        logger.info(format("Users: %s", userEdits.size()));
        logger.info(format("Number of nodes: %s", statInfo.getNumberOfNodes()));
        userEdits.entrySet().stream().limit(50).forEach(user ->
                logger.info((format("User %s - %s edits", user.getKey(), user.getValue()))));
        uniqueNames.entrySet().stream().limit(50).forEach(name ->
                logger.info((format("Uid %s - %s marks", name.getKey(), name.getValue()))));
    }
}
