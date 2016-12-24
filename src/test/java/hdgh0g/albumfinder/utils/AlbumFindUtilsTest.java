package hdgh0g.albumfinder.utils;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class AlbumFindUtilsTest {
    @Test
    public void findLatestReleasesForArtist() throws Exception {
        AlbumFindUtils.findLatestReleasesForArtist(251593L);
    }

    @Test
    public void findDiscogsArtistId() throws Exception {
        MatcherAssert.assertThat(AlbumFindUtils.findDiscogsArtistId("Green Day"), is(251593L));
    }

}