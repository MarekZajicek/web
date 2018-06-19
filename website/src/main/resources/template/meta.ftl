<!DOCTYPE HTML>
<html lang="cs">
<#assign title = "${website_author} (informace o webu)"/>
<#include "html/html-head.ftl"/>
<body>

<#include "common/header.ftl"/>

<div id="universe">

    <section class="wiki" id="wiki-content">
        <div class="inner">
            <header class="major">
                <h1>Meta</h1>
            </header>

        <#if todos?has_content>
            <section>
                <header class="major">
                    <h2>TODO</h2>
                </header>
                <ul>
                    <#list todos as todo>
                        <li><a href="/wiki/${todo.wikiPageId}/">${todo.wikiPageId}</a></li>
                    </#list>
                </ul>
            </section>
        </#if>

        <#if missing?has_content>
            <section>
                <header class="major">
                    <h2>Neplatné odkazy</h2>
                </header>
                <ul>
                    <#list missing as item>
                        <li>
                            <a href="/wiki/${item.sourceWikiPageId}/">${item.sourceWikiPageId}</a> &rarr; ${item.missingWikiPageId}
                        </li>
                    </#list>
                </ul>
            </section>
        </#if>

        <#if quotes?has_content>
            <section>
                <header class="major">
                    <h2>Citáty</h2>
                </header>
                <#list quotes as quote>
                    <blockquote>
                        <p>${quote.text}<br/><em>${quote.author}</em></p>
                    </blockquote>
                </#list>
            </section>
        </#if>

            <section>
                <header class="major">
                    <h2>Cache</h2>
                </header>
                <ul>
                    <li>
                        Velikost cache pro obrázky:
                        <em>${debug_image_cache_size_bytes} bajtů</em> /
                        <em>${debug_image_cache_size_items} položek</em>
                    </li>
                </ul>
            </section>

        </div>
    </section>

<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>