package hdgh0g.albumfinder.utils;

import api.torrents.ReleaseType;
import api.torrents.artist.TorrentGroup;
import com.google.gson.JsonSyntaxException;
import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AlbumFindUtils {

    public static Set<Album> findAllAlbums(Collection<Artist> artists) {
        if (!WhatCdConnectionUtils.isConnected()) {
            return null;
        }
        Set<Album> foundAlbums = new HashSet<>();
        artists.forEach(artist -> {
            foundAlbums.addAll(findAllAlbums(artist));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
        });
        return foundAlbums;
    }

    private static Set<Album> findAllAlbums(Artist artist) {
        return artist == null ? new HashSet<>() : findAllAlbums(artist.getName());
    }

    private static Set<Album> findAllAlbums(String artist) {
        api.torrents.artist.Artist whatCdArtist;
        try {
            whatCdArtist = api.torrents.artist.Artist.fromName(artist);
        } catch (JsonSyntaxException e) {
            whatCdArtist = null;
        }
        if (whatCdArtist == null) {
            return new HashSet<>();
        }
        Set<TorrentGroup> torrentGroups = whatCdArtist.getReleases().getReleases().get(ReleaseType.ALBUM);
        return torrentGroups.stream()
                .map(torrentGroup -> new Album(new Artist(artist), torrentGroup.getGroupName(), torrentGroup.getGroupYear().intValue()))
                .collect(Collectors.toSet());
    }
}