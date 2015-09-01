(function (ng) {
    var mod = ng.module('bookModule');

    mod.controller('bookCtrl', ['CrudCreator', '$scope', 'bookService', 'bookModel', function (CrudCreator, $scope, svc, model) {
            CrudCreator.extendController(this, svc, $scope, model, 'book', 'Book');
            this.fetchRecords();
        }]);
})(window.angular);
