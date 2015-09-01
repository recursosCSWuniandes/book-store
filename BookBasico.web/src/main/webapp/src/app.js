(function (ng) {

    var mainApp = ng.module('mainApp', [
        //'ngCrudMock',
        'authModule',
        'bookModule',
        'ngRoute',
        'ngCrud'
    ]);

    mainApp.config(['$routeProvider', 'CrudTemplateURL', 'CrudCtrlAlias', function ($routeProvider, tplUrl, alias) {
            $routeProvider
                .when('/book', {
                    templateUrl: tplUrl,
                    controller: 'bookCtrl',
                    controllerAs: alias
                })
                .otherwise('/');
        }]);
})(window.angular);
