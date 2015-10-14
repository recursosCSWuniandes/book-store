(function (ng) {

    var mainApp = ng.module('mainApp', [
        'bookModule',
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
                    .otherwise('/book');
        }]);
    mainApp.config(['authServiceProvider', function (auth) {
            auth.setValues({
                apiUrl: 'users',
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
