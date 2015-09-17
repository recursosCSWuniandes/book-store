(function (ng) {
    var mod = ng.module('authorModule');

    mod.controller('authorCtrl', ['$scope', 'authorService', function ($scope, svc) {
            $scope.currentRecord = {};
            $scope.records = [];

            //Variables para el controlador
            this.readOnly = false;
            this.editMode = false;

            this.changeTab = function (tab) {
                $scope.tab = tab;
            };

            var self = this;
            this.createRecord = function () {
                $scope.$broadcast('pre-create', $scope.currentRecord);
                this.editMode = true;
                $scope.currentRecord = {};
                $scope.$broadcast('post-create', $scope.currentRecord);
            };

            this.editRecord = function (record) {
                $scope.$broadcast('pre-edit', $scope.currentRecord);
                return svc.fetchRecord(record.id).then(function (response) {
                    $scope.currentRecord = response.data;
                    self.editMode = true;
                    $scope.$broadcast('post-edit', $scope.currentRecord);
                    return response;
                });
            };

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.records = response.data;
                    $scope.currentRecord = {};
                    self.editMode = false;
                    return response;
                });
            };
            this.saveRecord = function () {
                return svc.saveRecord($scope.currentRecord).then(function () {
                    self.fetchRecords();
                });
            };
            this.deleteRecord = function (record) {
                return svc.deleteRecord(record.id).then(function () {
                    self.fetchRecords();
                });
            };

            this.fetchRecords();
        }]);
})(window.angular);