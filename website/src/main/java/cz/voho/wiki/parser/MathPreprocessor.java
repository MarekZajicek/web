package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.page.parsed.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.Pattern;

public class MathPreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(WikiContext context, WikiPageSource wikiPageSource, final String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("€€(.+?)€€", Pattern.DOTALL));
        return rp.replace(source, matchResult -> {
            final String mathJaxSource = matchResult.group(1);
            return "<div class='figure math'>£" + mathJaxSource + "£</div>";
        });
    }
}
