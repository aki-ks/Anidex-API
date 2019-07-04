# Anidex-API
Java API implementation for https://anidex.info/

This api uses the official upload api call and parses webpages to provide additional features.

# Installation
One way to use this library is to inculde it through jitpack.
How to use it with your build tool is explained at [their page](https://jitpack.io/#aki-ks/Anidex-API).

If you're building with sbt, you may add this library as a remote project in your `build.sbt` and let your project depend on it.
Sbt builds the library from source and will apply all new commits to the repository.
``` sbt
lazy val anidex = RootProject(uri("https://github.com/kaysubs/Anidex-API.git"))
dependsOn(anidex)
```

# Usage
The entry point of this API are the `AnidexApi` and `AnidexAuthApi` interface.

Here's an example usage:
```Java
TorrentPreview[] torrents = AnidexApi.getInstance().search(new SearchRequest()
        .setSearchTerm("Overlord II")
        .setLanguage(Language.GERMAN)).getTorrents();

for(TorrentPreview torrent : torrents)
    System.out.println(torrent.getName());
```

Use the `SearchIterator` to iterate over all search results:
```Java
import de.kaysubs.tracker.anidex.utils.SearchIterator;

SearchIterator iter = new SearchIterator(new SearchRequest().setGroupId(310));

System.out.println("'k subs has published " + (iter.getTotal()) + " torrents:");
iter.forEachRemaining(torrent -> System.out.println(torrent.getName()));
```

For more examples you may have a look at the `de.kaysubs.tracker.anidex.examples` package.
