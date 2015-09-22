(function (ng) {
    var mod = ng.module('bookModule');

    mod.controller('bookCtrl', ['$scope', 'bookService', 'editorialService', function ($scope, svc, editorialSvc) {
            $scope.currentRecord = {};
            $scope.records = [];
            $scope.alerts = [];

            //Alertas
            this.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            function showMessage(msg, type) {
                var types = ['info', 'danger', 'warning', 'success'];
                if (types.some(function (rc) {
                    return type === rc;
                })) {
                    $scope.alerts.push({type: type, msg: msg});
                }
            }

            this.showError = function (msg) {
                showMessage(msg, 'danger');
            };

            function responseError(response) {
                self.showError(response.data);
            }

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
                }, responseError);
            };

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.records = response.data;
                    $scope.currentRecord = {};
                    self.editMode = false;
                    return response;
                }, responseError);
            };
            this.saveRecord = function () {
                return svc.saveRecord($scope.currentRecord).then(function () {
                    self.fetchRecords();
                }, responseError);
            };
            this.deleteRecord = function (record) {
                return svc.deleteRecord(record.id).then(function () {
                    self.fetchRecords();
                }, responseError);
            };

            editorialSvc.fetchRecords().then(function (response) {
                $scope.editorials = response.data;
            });

            this.fetchRecords();
        }]);

    mod.controller('authorsCtrl', ['$scope', 'authorService', '$modal', 'bookService', function ($scope, svc, $modal, bookSvc) {
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
                $scope.records = [];
                $scope.refId = args.id;
                bookSvc.getAuthors(args.id).then(function (response) {
                    $scope.records = response.data;
                });
            }

            $scope.$on('post-create', onCreateOrEdit);
            $scope.$on('post-edit', onCreateOrEdit);

            this.removeAuthor = function (index) {
                bookSvc.removeAuthor($scope.refId, $scope.records[index].id).then(function () {
                    $scope.records.splice(index, 1);
                });
            };

            this.showList = function () {
                var modal = $modal.open({
                    animation: true,
                    templateUrl: 'src/modules/book/authorModal.tpl.html',
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
                    bookSvc.replaceAuthors($scope.refId, data).then(function (response) {
                        $scope.records.splice(0, $scope.records.length);
                        $scope.records.push.apply($scope.records, response.data);
                    });
                });
            };
        }]);
})(window.angular);
