(function (ng) {

    var mainApp = ng.module('mainApp', [
        'bookModule',
        'authorModule',
        'editorialModule',
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
                .when('/editorial', {
                    templateUrl: 'src/modules/editorial/editorial.tpl.html',
                    controller: 'editorialCtrl',
                    controllerAs: 'ctrl'
                })
                .otherwise('/book');
        }]);
})(window.angular);
