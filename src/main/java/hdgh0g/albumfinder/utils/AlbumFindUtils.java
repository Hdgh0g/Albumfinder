package hdgh0g.albumfinder.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AlbumFindUtils {

    private static final String SECRET = "MELTyclzDTtMBrnwazcsDxAxeNmQmJRD";
    private static final String KEY = "xYnMhWnVxznqpBUiMVYF";
    private static final String DISCOGS_SEARCH_URL = "https://api.discogs.com/database/search";
    private static final String DISCOGS_ARTISTS_URL = "https://api.discogs.com/artists";

    public static Set<Album> findAllAlbums(Collection<Artist> artists, Consumer<Integer> consumer) {
        Set<Album> foundAlbums = new HashSet<>();
        final int[] processed = {0};
        artists.forEach(artist -> {
            foundAlbums.addAll(findAllAlbums(artist.getName()).stream()
                    .filter(album -> album.getAlbumArtist().equals(artist))
                    .collect(Collectors.toList()));
            consumer.accept(processed[0]++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });
        return foundAlbums;
    }

    private static Set<Album> findAllAlbums(String artist) {
        Long discogsArtistId = findDiscogsArtistId(artist);
        return findLatestReleasesForArtist(discogsArtistId);
    }

    static Set<Album> findLatestReleasesForArtist(Long discogsArtistId) {
        System.out.println("checikng id " + discogsArtistId);
        if (discogsArtistId == null) {
            return Collections.emptySet();
        }
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(Collections.singletonList(
                        new BasicHeader("Authorization", "Discogs key=" + KEY + ", secret=" + SECRET)))
                .build()) {

            URI uri = new URIBuilder(DISCOGS_ARTISTS_URL + "/" + discogsArtistId.toString() + "/releases" )
                    .addParameter("sort", "year")
                    .addParameter("sort_order", "desc")
                    .build();

            String response = httpClient.execute(new HttpGet(uri), new BasicResponseHandler());
            Object releases = new JsonParser().parse(response).getAsJsonObject().get("releases");

            System.out.println("Get artist releases");

            Set<Album> albums = new HashSet<>();
            if (releases instanceof JsonArray) {
                for (JsonElement next : ((JsonArray) releases)) {
                    JsonObject jsonItem = next.getAsJsonObject();
                    String artist = jsonItem.get("artist").getAsString();
                    String title = jsonItem.get("title").getAsString();
                    Integer year = jsonItem.get("year").getAsInt();
                    albums.add(new Album(new Artist(artist), title, year));
                }
            }

            return albums;
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    static Long findDiscogsArtistId(String artist) {
        System.out.println("checking artist " + artist);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultHeaders(Collections.singletonList(
                            new BasicHeader("Authorization", "Discogs key=" + KEY + ", secret=" + SECRET)))
                    .build()) {

            URI uri = new URIBuilder(DISCOGS_SEARCH_URL).addParameter("q", artist).build();
            String response = httpClient.execute(new HttpGet(uri), new BasicResponseHandler());
            Object results = new JsonParser().parse(response).getAsJsonObject().get("results");

            System.out.println("Get search results");

            if (results instanceof JsonArray) {
                for (JsonElement next : ((JsonArray) results)) {
                    JsonObject jsonItem = next.getAsJsonObject();
                    String type = jsonItem.get("type").getAsString();
                    if (type.equals("artist")) {
                        return jsonItem.get("id").getAsLong();
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}