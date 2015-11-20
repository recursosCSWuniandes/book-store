(function (ng) {

    var mainApp = ng.module('mainApp', [
        'authModule',
        'bookModule',
        'authorModule',
        'editorialModule',
        'ngRoute',
        'ngStorage'
    ]);
    
    mainApp.factory('authInterceptor', ['$localStorage', function (storage) {
        return {
            // automatically attach Authorization header
            request: function(config) {
                var token = storage.token;
                if(token) {
                   config.headers.Authorization = 'Bearer ' + token;
                }
                return config;
            },

            // If a token was sent back, save it
            response: function(res) {
                if(res.headers('Authorization')) {                    
                    storage.token = res.headers('Authorization');
                }
                return res;
            }
        }
    }]);

    mainApp.config(['$routeProvider','$httpProvider', function ($routeProvider,$httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
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
    mainApp.config(['authServiceProvider', function (auth) {
            auth.setValues({
                apiUrl: 'api/users/',
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
