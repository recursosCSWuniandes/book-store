(function (ng) {
    var mod = ng.module('editorialModule');

    mod.service('editorialService', ['$http', 'editorialContext', function ($http, context) {
            /**
             * Obtener la lista de editorials.
             * Hace una petición GET con $http a /editorials para obtener la lista
             * de editorials
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.fetchRecords = function () {
                return $http.get(context);
            };

            /**
             * Obtener un registro de editorials.
             * Hace una petición GET a /editorials/:id para obtener
             * los datos de un registro específico de editorials
             * @param {number} id del registro a obtener
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.fetchRecord = function (id) {
                return $http.get(context + "/" + id);
            };
            
            /**
             * Guardar un registro de editorials.
             * Si currentRecord tiene la propiedad id, hace un PUT a /editorials/:id con los
             * nuevos datos de la instancia de editorials.
             * Si currentRecord no tiene la propiedad id, se hace un POST a /editorials
             * para crear el nuevo registro de editorials
             * @param {object} currentRecord instancia de editorials a guardar/actualizar
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.saveRecord = function (currentRecord) {
                if (currentRecord.id) {
                    return $http.put(context + "/" + currentRecord.id, currentRecord);
                } else {
                    return $http.post(context, currentRecord);
                }
            };
            
            /**
             * Hace una petición DELETE a /editorials/:id para eliminar un editorial
             * @param {number} id identificador de la instancia de editorial a eliminar
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.deleteRecord = function (id) {
                return $http.delete(context + "/" + id);
            };
        }]);
})(window.angular);