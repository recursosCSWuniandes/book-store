(function (ng) {
    var mod = ng.module('bookModule', ['ui.bootstrap', 'reviewModule']);

    mod.constant('bookContext', 'webresources/books');

})(window.angular);
