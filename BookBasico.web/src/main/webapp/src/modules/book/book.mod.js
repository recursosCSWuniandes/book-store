(function (ng) {
    var mod = ng.module('bookModule', ['ui.bootstrap', 'reviewModule', 'authorModule']);

    mod.constant('bookContext', 'webresources/books');

})(window.angular);
