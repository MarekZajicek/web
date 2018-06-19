package cz.voho.facade;

import cz.voho.common.utility.LambdaClient;
import cz.voho.wiki.parser.CustomWikiParser;
import cz.voho.wiki.parser.WikiParser;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.image.NoWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.DefaultParsedWikiPageRepository;
import cz.voho.wiki.repository.page.DefaultWikiPageSourceRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final boolean isLocalDev = System.getProperty("os.name").contains("Windows");
    private final LambdaClient lambdaClient = new LambdaClient();
    private final LambdaWikiImageRepository wikiImageRepositoryDelegate = new LambdaWikiImageRepository(lambdaClient);
    private final WikiImageRepository wikiImageRepository = isLocalDev ? new NoWikiImageRepository() : new CachingWikiImageRepository(wikiImageRepositoryDelegate);
    private final WikiParser wikiParser = new CustomWikiParser(wikiImageRepository);
    private final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
    private final ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiPageSourceRepository, wikiParser);
    private final WikiBackend wikiBackend = new WikiBackend(wikiPageSourceRepository, parsedWikiPageRepository, wikiImageRepository);
    private final RecentBackend recentBackend = new RecentBackend(lambdaClient);

    public RecentBackend getRecentBackend() {
        return recentBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
