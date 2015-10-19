(function (ng) {

    var mainApp = ng.module('mainApp', [
        'bookModule',
        'editorialModule',
        'authModule',
        'ngRoute'
    ]);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                    .when('/book', {
                        templateUrl: 'src/modules/book/book.tpl.html',
                        controller: 'bookCtrl',
                        controllerAs: 'ctrl'
                    })
                    .when('/editorial', {
                        templateUrl: 'src/modules/editorial/editorial.tpl.html',
                        controller: 'editorialCtrl',
                        controllerAs: 'ctrl'
                    })
                    .otherwise('/book');
        }]);
    mainApp.config(['authServiceProvider', function (auth) {
            auth.setValues({
                apiUrl: 'webresources/users/',
                successPath: '/catalog',
                loginPath: '/login',
                registerPath: '/register',
                logoutRedirect: '/login',
                loginURL: 'login',
                registerURL: 'register',
                logoutURL: 'logout',
                nameCookie: 'userCookie'
            });
        }]);
})(window.angular);
