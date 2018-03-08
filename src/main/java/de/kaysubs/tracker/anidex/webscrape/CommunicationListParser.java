package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.CommunicationView;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommunicationListParser implements Parser<CommunicationView[]> {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static Pattern ID_URL_PATTERN = Pattern.compile("/message/([0-9]+)");

    @Override
    public CommunicationView[] parsePage(Document page) {
        return page.selectFirst("form#inbox_form")
                .selectFirst("table")
                .selectFirst("tbody")
                .select("tr").stream()
                .map(this::parseRow)
                .filter(c -> c != null)
                .toArray(CommunicationView[]::new);
    }

    private CommunicationView parseRow(Element row) {
        Element[] cells = row.select("td").toArray(new Element[0]);

        String sender = cells[1].text();

        // Website does sometimes contain a non-existent communication
        if(sender.isEmpty())
            return null;

        Element link = cells[2].selectFirst("a");
        int communicationId = parseCommunicationId(link.attr("href"));
        String subject = link.text();

        Date date = parseDate(cells[3].text());

        return new CommunicationView(sender, subject, date, communicationId);
    }

    private Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new WebScrapeException("Cannot parse communication date");
        }
    }

    private int parseCommunicationId(String href) {
        Matcher m = ID_URL_PATTERN.matcher(href);

        if(m.matches()) {
            return Integer.parseInt(m.group(1));
        } else {
            throw new WebScrapeException("Cannot parse communication id: " + href);
        }
    }

}
