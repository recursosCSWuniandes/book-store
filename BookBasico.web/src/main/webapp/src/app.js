(function (ng) {

    var mainApp = ng.module('mainApp', [
        'bookModule',
        'authorModule',
        'ngRoute'
    ]);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/book', {
                    templateUrl: 'src/modules/book/book.tpl.html',
                    controller: 'bookCtrl',
                    controllerAs: 'ctrl'
                })
                .when('/author', {
                    templateUrl: 'src/modules/author/author.tpl.html',
                    controller: 'authorCtrl',
                    controllerAs: 'ctrl'
                })
                .otherwise('/book');
        }]);
})(window.angular);
