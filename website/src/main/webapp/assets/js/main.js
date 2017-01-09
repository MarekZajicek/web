/*
 Forty by HTML5 UP
 html5up.net | @ajlkn
 Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
 */

(function ($) {

    skel.breakpoints({
        xlarge: '(max-width: 1680px)',
        large: '(max-width: 1280px)',
        medium: '(max-width: 980px)',
        small: '(max-width: 736px)',
        xsmall: '(max-width: 480px)',
        xxsmall: '(max-width: 360px)'
    });

    $(function () {

        var $window = $(window),
            $body = $('body'),
            $wrapper = $('#wrapper'),
            $header = $('#header'),
            $banner = $('#banner');

        // Disable animations/transitions until the page has loaded.
        $body.addClass('is-loading');

        $window.on('load pageshow', function () {
            window.setTimeout(function () {
                $body.removeClass('is-loading');
            }, 100);
        });

        // Clear transitioning state on unload/hide.
        $window.on('unload pagehide', function () {
            window.setTimeout(function () {
                $('.is-transitioning').removeClass('is-transitioning');
            }, 250);
        });

        // Fix: Enable IE-only tweaks.
        if (skel.vars.browser == 'ie' || skel.vars.browser == 'edge')
            $body.addClass('is-ie');

        // Fix: Placeholder polyfill.
        $('form').placeholder();

        // Prioritize "important" elements on medium.
        skel.on('+medium -medium', function () {
            $.prioritize(
                '.important\\28 medium\\29',
                skel.breakpoint('medium').active
            );
        });

        // Scrolly.
        $('.scrolly').scrolly({
            offset: function () {
                return $header.height() - 2;
            }
        });

        // Header.
        if (skel.vars.IEVersion < 9)
            $header.removeClass('alt');

        if ($banner.length > 0
            && $header.hasClass('alt')) {

            $window.on('resize', function () {
                $window.trigger('scroll');
            });

            $window.on('load', function () {
                window.setTimeout(function () {
                    $window.triggerHandler('scroll');
                }, 100);

            });
        }

        // Menu.
        var $menu = $('#menu'),
            $menuInner;

        $menu.wrapInner('<div class="inner"></div>');
        $menuInner = $menu.children('.inner');
        $menu._locked = false;

        $menu._lock = function () {

            if ($menu._locked)
                return false;

            $menu._locked = true;

            window.setTimeout(function () {
                $menu._locked = false;
            }, 350);

            return true;

        };

        $menu._show = function () {

            if ($menu._lock())
                $body.addClass('is-menu-visible');

        };

        $menu._hide = function () {

            if ($menu._lock())
                $body.removeClass('is-menu-visible');

        };

        $menu._toggle = function () {

            if ($menu._lock())
                $body.toggleClass('is-menu-visible');

        };

        $menuInner
            .on('click', function (event) {
                event.stopPropagation();
            })
            .on('click', 'a', function (event) {

                var href = $(this).attr('href');

                event.preventDefault();
                event.stopPropagation();

                // Hide.
                $menu._hide();

                // Redirect.
                window.setTimeout(function () {
                    window.location.href = href;
                }, 250);

            });

        $menu
            .appendTo($body)
            .on('click', function (event) {

                event.stopPropagation();
                event.preventDefault();

                $body.removeClass('is-menu-visible');

            })
            .append('<a class="close" href="#menu">Close</a>');

        $body
            .on('click', 'a[href="#menu"]', function (event) {

                event.stopPropagation();
                event.preventDefault();

                // Toggle.
                $menu._toggle();

            })
            .on('click', function (event) {

                // Hide.
                $menu._hide();

            })
            .on('keydown', function (event) {

                // Hide on escape.
                if (event.keyCode == 27)
                    $menu._hide();

            });

    });

})(jQuery);