package de.kaysubs.tracker.anidex.utils;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.*;

import java.util.Iterator;

/**
 * Iterate over all torrents match the response.
 *
 * Sending request with offsets is handle for you
 */
public class SearchIterator implements Iterator<TorrentPreview> {

    private SearchRequest request;
    private TorrentList cache = null;
    private int index;

    public SearchIterator(SearchRequest request) {
        this.request = request;
    }

    private void verifyCache() {
        if(cache == null || (index >= cache.getTorrents().length && cache.getLastIndex() < cache.getTotal())) {
            this.request.setOffset(cache == null ? null : cache.getLastIndex());

            cache = AnidexApi.getInstance().search(this.request);
            index = 0;
        }
    }

    public int getTotal() {
        verifyCache();
        return cache.getTotal();
    }

    @Override
    public boolean hasNext() {
        verifyCache();
        return index < cache.getTorrents().length;
    }

    @Override
    public TorrentPreview next() {
        verifyCache();
        return cache.getTorrents()[index++];
    }
}
