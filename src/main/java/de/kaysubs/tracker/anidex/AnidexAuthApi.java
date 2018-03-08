package de.kaysubs.tracker.anidex;

import de.kaysubs.tracker.anidex.exception.CommunicationPermissionException;
import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.common.exception.HttpException;

/**
 * Api calls that require a authentication.
 * Use {@link AnidexApi#login(String, String)} to get an instance of this interface.
 */
public interface AnidexAuthApi extends AnidexApi {

    /**
     * Get your current account settings.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    Setting getAccountSettings();

    /**
     * Set profile settings.
     *
     * A common usage is to get the current
     * settings values, then turn it into a request
     * and update the values you want to change.
     *
     * @see SetFilterSettingRequest
     * @see SetProfileSettingRequest
     * @see SetUploadSettingRequest
     *
     * @throws HttpException networking error
     */
    void setAccountSettings(SetSettingRequest request);

    /**
     * Request a new API key.
     *
     * @throws HttpException networking error
     */
    void updateApiKey();

    /**
     * Write a comment below a torrent.
     *
     * @throws HttpException networking error
     */
    void postTorrentComment(int torrentId, String message);

    /**
     * Delete one of your comments below a torrent.
     *
     * The message id can be retrieved
     * through the getGroupInfo method.
     * @see Comment#getMessageId()
     *
     * @throws HttpException networking error
     */
    void deleteTorrentComment(int messageId);

    /**
     * Write a comment below a fansub group.
     *
     * @throws HttpException networking error
     */
    void postGroupComment(int groupId, String message);

    /**
     * Delete a comment below a fansub group.
     *
     * The message id can be retrieved
     * through the getGroupInfo method.
     * @see Comment#getMessageId()
     *
     * @throws HttpException networking error
     */
    void deleteGroupComment(int messageId);

    /**
     * Send a message/Start a communication with another user.
     *
     * @throws HttpException networking error
     */
    void sendMessage(String recipient, String subject, String message);

    /**
     * Reply to an existing communication.
     *
     * @throws HttpException networking error
     */
    void sendReply(int communicationId, String message);

    /**
     * List your communications with other users.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    CommunicationView[] listCommunications();

    /**
     * Get all messages of a communication and its subject.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws CommunicationPermissionException you do not have permissions to view this communication
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    Communication getCommunication(int communicationId);

}
