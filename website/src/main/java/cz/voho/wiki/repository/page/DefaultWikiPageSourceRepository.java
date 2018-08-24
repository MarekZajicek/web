package cz.voho.wiki.repository.page;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.ApiUtility;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.WikiPageSource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class DefaultWikiPageSourceRepository implements WikiPageSourceRepository {
    private static final String GITHUB_RAW_URL_FORMAT = "https://gitcdn.link/repo/voho/web/master/website/src/main/resources/wiki/%s";
    private static final String GITHUB_NEW_ISSUE_URL_FORMAT = "https://github.com/voho/web/issues/new?title=%s&body=%s";
    private static final String GITHUB_HISTORY_URL_FORMAT = "https://github.com/voho/web/blame/master/website/src/main/resources/%s";
    private static final String GITHUB_SOURCE_URL_FORMAT = "https://github.com/voho/web/blob/master/website/src/main/resources/%s";

    private final Map<String, WikiPageSource> wikiPageIdToResourceName;

    public DefaultWikiPageSourceRepository() {
        this.wikiPageIdToResourceName = new TreeMap<>();

        try {
            ClassPath
                    .from(Thread.currentThread().getContextClassLoader())
                    .getResources()
                    .stream()
                    .map(ClassPath.ResourceInfo::getResourceName)
                    .filter(WikiLinkUtility::isValidWikiPageSource)
                    .map(this::load)
                    .forEach(wikiPageSource -> wikiPageIdToResourceName.put(wikiPageSource.getId(), wikiPageSource));
        } catch (IOException e) {
            throw new InitializationException("Error while loading wiki page sources.", e);
        }
    }

    @Override
    public ImmutableSet<String> getWikiPageIds() {
        return ImmutableSet.copyOf(wikiPageIdToResourceName.keySet());
    }

    @Override
    public WikiPageSource getWikiPageSourceById(final String wikiPageId) {
        final WikiPageSource page = wikiPageIdToResourceName.get(wikiPageId);

        if (page == null) {
            throw new ContentNotFoundException("Wiki page not found: " + wikiPageId);
        }

        return page;
    }

    private WikiPageSource load(String resourceName) {
        if (resourceName.startsWith("webapps/ROOT/WEB-INF/classes/")) {
            // hack to make it work in AWS :) - too lazy to figure that out
            resourceName = resourceName.substring("webapps/ROOT/WEB-INF/classes/".length());
        }

        final WikiPageSource page = new WikiPageSource();
        page.setId(extractId(resourceName));
        page.setParentId(extractParentId(resourceName));
        page.setMarkdownSource(loadSource(resourceName) + "\r\n\r\n");
        page.setGithubUrl(getRepositoryPath(resourceName));
        page.setGithubRawUrl(getRawRepositoryPath(resourceName));
        page.setGithubIssueUrl(getIssueRepositoryPath(resourceName));
        page.setGithubHistoryUrl(getHistoryRepositoryPath(resourceName));
        return page;
    }

    private String extractId(final String resourceName) {
        return WikiLinkUtility.resolveWikiPageId(resourceName);
    }

    private String extractParentId(final String resourceName) {
        return WikiLinkUtility.resolveParentWikiPageId(resourceName);
    }

    private String loadSource(final String resourceName) {
        final URL resource = Resources.getResource(resourceName);

        try {
            return Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ContentNotFoundException("Wiki page not found: " + resourceName);
        }
    }

    private String getRepositoryPath(final String resourceName) {
        return String.format(GITHUB_SOURCE_URL_FORMAT, resourceName);
    }

    private String getRawRepositoryPath(final String resourceName) {
        return String.format(GITHUB_RAW_URL_FORMAT, resourceName);
    }

    private String getIssueRepositoryPath(final String resourceName) {
        return String.format(
                GITHUB_NEW_ISSUE_URL_FORMAT,
                ApiUtility.escapeUrlFragment(String.format("%s (chyba na Wiki)", resourceName)),
                ApiUtility.escapeUrlFragment("")
        );
    }

    private String getHistoryRepositoryPath(final String resourceName) {
        return String.format(GITHUB_HISTORY_URL_FORMAT, resourceName);
    }
}
