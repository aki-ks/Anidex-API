package de.kaysubs.tracker.anidex;

import de.kaysubs.tracker.anidex.exception.AnidexException;
import de.kaysubs.tracker.anidex.exception.LoginException;
import de.kaysubs.tracker.anidex.exception.UploadException;
import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.webscrape.*;
import de.kaysubs.tracker.common.HttpUtil;
import de.kaysubs.tracker.common.exception.HttpErrorCodeException;
import de.kaysubs.tracker.common.exception.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AnidexApiImpl implements AnidexApi {
    private final static AnidexApiImpl INSTANCE = new AnidexApiImpl();
    private final static Pattern URL_PATTERN = Pattern.compile("https://anidex.info/torrent/(.+)");

    public static AnidexApiImpl getInstance() {
        return INSTANCE;
    }

    protected <T> T parsePage(HttpResponse response, Parser<T> parser) {
        Document page = Jsoup.parse(HttpUtil.readIntoString(response));

        try {
            return parser.parsePage(page);
        } catch(AnidexException e) {
            throw e;
        } catch(Exception e) {
            throw new WebScrapeException(e);
        }
    }

    @Override
    public UploadResponse upload(UploadRequest request) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("api_key", request.getApiKey());
        builder.addTextBody("subcat_id", Integer.toString(request.getCategory().getId()));
        builder.addTextBody("lang_id", Integer.toString(request.getLanguage().getId()));

        File seedFile = request.getTorrentFile();
        ContentType torrentMime = ContentType.create("application/x-bittorrent");
        builder.addBinaryBody("file", seedFile, torrentMime, seedFile.getName());

        request.getName().ifPresent(name ->
                builder.addTextBody("torrent_name", name));

        request.getDescription().ifPresent(description ->
                builder.addTextBody("description", description));

        builder.addTextBody("group_id", Integer.toString(request.getGroupId().orElse(0)));

        if(request.isBatch())
            builder.addTextBody("batch", "1");

        if(request.isHentai())
            builder.addTextBody("hentai", "1");

        if(request.isReencode())
            builder.addTextBody("reencode", "1");

        if(request.isPrivate())
            builder.addTextBody("private", "1");

        if(request.isTokyoToshokan())
            builder.addTextBody("tt_api", "1");

        if(request.isDebug())
            builder.addTextBody("debug", "1");

        HttpPost post = new HttpPost("https://anidex.info/api/");
        post.setConfig(HttpUtil.WITH_TIMEOUT);
        post.setEntity(builder.build());

        // either the torrentUrl or an error message
        String response = HttpUtil.readIntoString(HttpUtil.executeRequest(post));

        Matcher m = URL_PATTERN.matcher(response);
        if(m.matches()) {
            return new UploadResponse(m.group(1));
        } else {
            throw new UploadException(response);
        }
    }

    public TorrentInfo getTorrentInfo(int torrentId) {
        HttpGet get = new HttpGet("https://anidex.info/torrent/" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return parsePage(HttpUtil.executeRequest(get), new TorrentInfoParser());
    }

    @Override
    public TorrentList search(SearchRequest request) {
        URI uri;
        try {
            URIBuilder builder = new URIBuilder()
                    .setScheme("https")
                    .setHost("anidex.info");

            request.getSearchTerm().ifPresent(term ->
                    builder.addParameter("q", term));

            request.getCategory().ifPresent(category -> {
                String idQuery = Arrays.stream(category)
                        .map(c -> Integer.toString(c.getId()))
                        .collect(Collectors.joining(","));

                builder.addParameter("id", idQuery);
            });

            request.getLanguage().ifPresent(language -> {
                String languageQuery = Arrays.stream(language)
                        .map(c -> Integer.toString(c.getId()))
                        .collect(Collectors.joining(","));

                builder.addParameter("lang_id", languageQuery);
            });

            request.getGroupId().ifPresent(groupId ->
                    builder.addParameter("group_id", Integer.toString(groupId)));

            request.getUserId().ifPresent(groupId ->
                    builder.addParameter("user_id", Integer.toString(groupId)));

            request.getOffset().ifPresent(offset ->
                    builder.addParameter("offset", Integer.toString(offset + 1)));

            if(request.isBatch())
                builder.addParameter("b", "1");

            if(request.isVerified())
                builder.addParameter("a", "1");

            if(request.isNoRemake())
                builder.addParameter("r", "1");

            request.getOrdering().ifPresent(ordering ->
                    builder.addParameter("o", ordering.getId()));

            request.getSort().ifPresent(sort ->
                    builder.addParameter("s", sort.getId()));

            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new HttpException("Cannot build URL", e);
        }

        HttpGet get = new HttpGet(uri);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return parsePage(HttpUtil.executeRequest(get), new SearchParser());
    }

    @Override
    public GroupPreview[] listGroups(Language language) {
        HttpGet get = new HttpGet("https://anidex.info/groups/" + language.getId());
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return parsePage(HttpUtil.executeRequest(get), new GroupListParser());
    }

    @Override
    public GroupInfo getGroupInfo(int groupId) {
        HttpGet get = new HttpGet("https://anidex.info/group/" + groupId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return parsePage(HttpUtil.executeRequest(get), new GroupInfoParser());
    }

    @Override
    public UserInfo getUserInfo(int userId) {
        HttpGet get = new HttpGet("https://anidex.info/user/" + userId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return parsePage(HttpUtil.executeRequest(get), new UserInfoParser());
    }

    @Override
    public AnidexAuthApiImpl login(String name, String password) {
        HttpPost post = new HttpPost("https://anidex.info/ajax/actions.ajax.php?function=login");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        post.setHeader("X-Requested-With", "XMLHttpRequest");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("login_username", name);
        builder.addTextBody("login_password", password);
        builder.addTextBody("remember_me", "1");
        post.setEntity(builder.build());

        CookieStore cookieStore = new BasicCookieStore();
        String response = HttpUtil.readIntoString(HttpUtil.executeRequest(post, cookieStore));

        Optional<Cookie> token = cookieStore.getCookies().stream()
                .filter(e -> e.getName().equals("anidex")).findFirst();

        if (response.trim().isEmpty() && token.isPresent()) {
            return new AnidexAuthApiImpl(new Session(token.get().getValue()));
        } else if (response.contains("Incorrect username")) {
            throw new LoginException("Incorrect username");
        } else if (response.contains("Incorrect password")) {
            throw new LoginException("Incorrect password");
        } else {
            throw new LoginException(response);
        }
    }
}
