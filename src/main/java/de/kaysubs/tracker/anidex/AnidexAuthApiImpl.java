package de.kaysubs.tracker.anidex;

import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.webscrape.*;
import de.kaysubs.tracker.common.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class AnidexAuthApiImpl extends AnidexApiImpl implements AnidexAuthApi {
    private final Session session;

    public AnidexAuthApiImpl(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public Setting getAccountSettings() {
        HttpGet get = new HttpGet("https://anidex.info/settings");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });

        return parsePage(response, new AccountSettingsParser());
    }

    @Override
    public void setAccountSettings(SetSettingRequest request) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=" + request.getFormName());
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        request.buildForm(builder);
        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void updateApiKey() {
        HttpGet get = new HttpGet("https://anidex.info/ajax/actions.ajax.php?function=request_new_upload_api_key");
        get.setConfig(HttpUtil.WITH_TIMEOUT);
        get.setHeader("X-Requested-With", "XMLHttpRequest");

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void postTorrentComment(int torrentId, String message) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=torrent_comment&id=" + torrentId);
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("comment", message);
        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void deleteTorrentComment(int commentId) {
        HttpGet get = new HttpGet("https://anidex.info/ajax/actions.ajax.php?function=torrent_comment_delete&id=" + commentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);
        get.setHeader("X-Requested-With", "XMLHttpRequest");

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void postGroupComment(int groupId, String message) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=group_comment&id=" + groupId);
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("comment", message);
        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void deleteGroupComment(int messageId) {
        HttpGet get = new HttpGet("https://anidex.info/ajax/actions.ajax.php?function=group_comment_delete&id=" + messageId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);
        get.setHeader("X-Requested-With", "XMLHttpRequest");

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void sendMessage(String recipient, String subject, String message) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=msg_send");
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("recipient", recipient);
        builder.addTextBody("subject", subject);
        builder.addTextBody("message", message);
        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);
    }

    @Override
    public void sendReply(int communicationId, String message) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=msg_reply&id=" + communicationId);
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("reply", message);
        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
        HttpUtil.requireStatusCode(response, 200);

    }

    @Override
    public CommunicationView[] listCommunications() {
        HttpGet get = new HttpGet("https://anidex.info/messages");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });

        return parsePage(response, new CommunicationListParser());
    }

    @Override
    public Communication getCommunication(int communicationId) {
        HttpGet get = new HttpGet("https://anidex.info/message/" + communicationId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });

        return parsePage(response, new CommunicationParser());
    }
}
