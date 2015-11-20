(function (ng) {

    var mainApp = ng.module('mainApp', [
        'authModule',
        'bookModule',
        'authorModule',
        'editorialModule',
        'ngRoute',
        'ngStorage'
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
            auth.setRoles({'user': [{id: 'registeredUsers', label: 'user', icon: 'list-alt', url: '#/author'}, {id: 'indexBook', label: 'book', icon: 'list-alt', url: '#/book'}],
                'provider': [{id: 'registeredProviders', label: 'provider', icon: 'inbox', url: '#/editorial'}]});

            auth.setJwtConfig({'saveIn': 'sessionStorage'})// En teoria se pude cambiar el nombre, pero en el servicio tiene por default 'Autorization'        
        }]);
})(window.angular);
