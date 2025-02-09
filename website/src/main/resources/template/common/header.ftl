<!--

   _____               _   _                 _                            
  / ____|             | | (_)               | |                           
 | |  __ _ __ ___  ___| |_ _ _ __   __ _ ___| |                           
 | | |_ | '__/ _ \/ _ \ __| | '_ \ / _` / __| |                           
 | |__| | | |  __/  __/ |_| | | | | (_| \__ \_|                           
  \_____|_|  \___|\___|\__|_|_| |_|\__, |___(_)           _               
 | |  | |                           __/ (_)              | |              
 | |__| | __ ___   _____    __ _   |___/ _  ___ ___    __| | __ _ _   _   
 |  __  |/ _` \ \ / / _ \  / _` | | '_ \| |/ __/ _ \  / _` |/ _` | | | |  
 | |  | | (_| |\ V /  __/ | (_| | | | | | | (_|  __/ | (_| | (_| | |_| |_ 
 |_|  |_|\__,_| \_/ \___|  \__,_| |_| |_|_|\___\___|  \__,_|\__,_|\__, ( )
 \ \    / /  (_) |                                                 __/ |/ 
  \ \  / /__  _| |_ __ _                                          |___/   
   \ \/ / _ \| | __/ _` |                                                 
    \  / (_) | | || (_| |                                                 
     \/ \___/| |\__\__,_|                                                 
            _/ |                                                          
           |__/                                                           

-->
<header id="header">
    <div class="inner">
        <h1 class="logo">
            <a href="/" title="${website_extended_name}"><strong>${website_short_name}</strong></a>
            <#include "../wiki/breadcrumbs.ftl"/>
        </h1>
        <span id="search">
            <form action="http://google.cz/search" method="get">
                <input id="search-query" name="q" type="text" size="15" placeholder="Hledat"/>
                <input name="hl" type="hidden" value="cs"/>
                <input name="sitesearch" type="hidden" value="voho.eu"/>
            </form>
        </span>
    </div>
</header>
