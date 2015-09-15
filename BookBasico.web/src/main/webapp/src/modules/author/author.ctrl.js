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
    
    mod.controller('authorsCtrl', ['$scope', 'authorService', '$modal', function ($scope, svc, $modal) {
            $scope.currentRecord = {};
            $scope.records = [];
            $scope.refName = 'authors';

            //Variables para el controlador
            this.readOnly = false;
            this.editMode = false;

            //Escucha de evento cuando se selecciona un registro maestro
            function onCreateOrEdit(event, args) {
                var childName = 'authors';
                if (args[childName] === undefined) {
                    args[childName] = [];
                }
                $scope.records = args[childName];
                $scope.refId = args.id;
            }

            $scope.$on('post-create', onCreateOrEdit);
            $scope.$on('post-edit', onCreateOrEdit);

            this.showList = function () {
                var modal = $modal.open({
                    animation: true,
                    templateUrl: 'src/modules/author/authorModal.tpl.html',
                    controller: ['$scope', '$modalInstance', 'items', 'currentItems', function ($scope, $modalInstance, items, currentItems) {
                            $scope.records = items.data;
                            $scope.allChecked = false;

                            function loadSelected(list, selected) {
                                ng.forEach(selected, function (selectedValue) {
                                    ng.forEach(list, function (listValue) {
                                        if (listValue.id === selectedValue.id) {
                                            listValue.selected = true;
                                        }
                                    });
                                });
                            }

                            $scope.checkAll = function (flag) {
                                this.records.forEach(function (item) {
                                    item.selected = flag;
                                });
                            };

                            loadSelected($scope.records, currentItems);

                            function getSelectedItems() {
                                return $scope.records.filter(function (item) {
                                    return !!item.selected;
                                });
                            }

                            $scope.ok = function () {
                                $modalInstance.close(getSelectedItems());
                            };

                            $scope.cancel = function () {
                                $modalInstance.dismiss('cancel');
                            };
                        }],
                    resolve: {
                        items: function () {
                            return svc.fetchRecords();
                        },
                        currentItems: function () {
                            return $scope.records;
                        }
                    }
                });
                modal.result.then(function (data) {
                    $scope.records.splice(0, $scope.records.length);
                    $scope.records.push.apply($scope.records, data);
                });
            };
        }]);
})(window.angular);