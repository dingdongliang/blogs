document.addEventListener('DOMContentLoaded', function () {
    var btn = document.querySelector('.nav-bar-btn');
    /*
     * Changing state: 通过修改, 增加或者删除类名实现状态的改变
     */
    /*
     * btn.addEventListener('click', function (event) {
     *     var tabs = document.querySelector('.' + this.className.substr(0, 8) + 'tabs');
     *     var className = tabs.getAttribute('class');
     *     if (/open/.test(className))
     *         tabs.setAttribute('class', 'nav-bar-tabs');
     *     else
     *         tabs.setAttribute('class', 'nav-bar-tabs is-open');
     * });
     */

    btn.addEventListener('click', function (event) {
        var tabs = document.querySelector('.' + this.className.substr(0, 8) + 'tabs');
        if (tabs.dataset.state === 'open')
            tabs.dataset.state = 'close';
        else
            tabs.dataset.state = 'open';
    });

    var posts = document.querySelectorAll('.post-list .post');
    Array.prototype.forEach.call(posts, function(post, i){
        post.addEventListener('click', function (event) {
            var url = this.querySelector('a').href;
            window.location.href = url;
        });
    });

    window.smoothScrollToTop = (function () {
        var timeOut;
        var speed;
        return function scrollToTop() {
            speed = speed || document.body.scrollTop/25;
            if (document.body.scrollTop || document.documentElement.scrollTop){
                window.scrollBy(0, -1*speed);
                timeOut = setTimeout(scrollToTop, 0);
            }
            else {
                clearTimeout(timeOut);
                speed = null;
            }
        };
    })();

    function parseSearch(query) {
        var res = {};
        try {
            query.split('&').forEach(function (param) {
                var key = param.split('=')[0],
                    value = param.split('=')[1];
                res[key] = value;
            });
        } catch (err) {
            console.error(err);
        }
        return res;
    }

    // 滚动到之前的位置
    var previousPosition = parseSearch(window.location.search)['pos'] || 0;
    previousPosition && window.scrollTo(0, +previousPosition);

    var more = document.querySelector('.more a');
    if (more) {
        more.addEventListener('click', function (event) {
            event.preventDefault();
            // 保存当前在列表中的位置
            var url = event.target.href + '&pos=' + document.body.scrollTop;
            window.location.href = url;
        });
    }
});
