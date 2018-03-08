package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.exception.CommunicationPermissionException;
import de.kaysubs.tracker.anidex.model.Comment;
import de.kaysubs.tracker.anidex.model.Communication;
import de.kaysubs.tracker.anidex.utils.ConversionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.Optional;

public class CommunicationParser implements Parser<Communication> {
    @Override
    public Communication parsePage(Document page) {
        Element contentDiv = page.selectFirst("div#content");
        boolean noPermissions = contentDiv.select("div.alert.alert-danger").stream()
                .anyMatch(e -> e.text().contains("You are not the sender nor recipient of this thread."));

        if(noPermissions) {
            throw new CommunicationPermissionException();
        } else {
            Element table = contentDiv.selectFirst("table");
            String subject = table.selectFirst("thead").selectFirst("td").text();
            Communication.Message[] messages = parseMessage(table.selectFirst("tbody"));

            return new Communication(subject, messages);
        }
    }

    public static Communication.Message[] parseMessage(Element commentTable) {
        return commentTable.select("tr").stream().map(row -> {
            Element userCell = row.selectFirst(".uploader");
            int userId = Integer.parseInt(userCell.attr("id"));
            String userName = userCell.text();
            Date date = ConversionUtils.parseDate(row.select("span.pull-right").text());

            Elements avatarElement = row.select("img[title=User Logo]");
            Optional<String> avatarUrl = avatarElement.hasAttr("src") ?
                    Optional.of(avatarElement.attr("src")) :
                    Optional.empty();

            String comment = ParseUtils.parseCommentMessage(row.select("td").get(1));

            return new Communication.Message(userId, userName, avatarUrl, date, comment);
        }).toArray(Communication.Message[]::new);
    }
}
