package de.kaysubs.tracker.anidex;

import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.exception.*;
import de.kaysubs.tracker.common.exception.*;

public interface AnidexApi {
    static AnidexApi getInstance() {
        return AnidexApiImpl.getInstance();
    }

    /**
     * Upload a torrent to anidex (Official API)
     *
     * @throws UploadException server responded with error message
     * @throws HttpException networking error
     */
    UploadResponse upload(UploadRequest request);

    /**
     * Search for torrents.
     * If you do not set any filters the
     * latest torrents will be returned.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentList search(SearchRequest request);

    /**
     * Get a list of all fansub-groups that translate to a certain language.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    GroupPreview[] listGroups(Language language);

    /**
     * Get informations about a fansub group
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    GroupInfo getGroupInfo(int groupId);

    /**
     * View a user's profile.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws NoSuchUserException the user does not exist
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    UserInfo getUserInfo(int userId);

    /**
     * Get informations about a torrent
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentInfo getTorrentInfo(int torrentId);

    /**
     * Login with username and password.
     *
     * @throws LoginException wrong credentials
     * @throws HttpException networking error
     */
    AnidexAuthApi login(String name, String password);

}
