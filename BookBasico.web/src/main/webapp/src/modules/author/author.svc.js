(function (ng) {
    var mod = ng.module('authorModule');

    mod.service('authorService', ['$http', 'authorContext', function ($http, context) {

            /**
             * Obtener la lista de authors.
             * Hace una petición GET con $http a /authors para obtener la lista
             * de authors
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.fetchRecords = function () {
                return $http.get(context);
            };

            /**
             * Obtener un registro de authors.
             * Hace una petición GET a /authors/:id para obtener
             * los datos de un registro específico de authors
             * @param {number} id del registro a obtener
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.fetchRecord = function (id) {
                return $http.get(context + "/" + id);
            };
            
            /**
             * Guardar un registro de authors.
             * Si currentRecord tiene la propiedad id, hace un PUT a /authors/:id con los
             * nuevos datos de la instancia de authors.
             * Si currentRecord no tiene la propiedad id, se hace un POST a /authors
             * para crear el nuevo registro de authors
             * @param {object} currentRecord instancia de authors a guardar/actualizar
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
             * Hace una petición DELETE a /authors/:id para eliminar un author
             * @param {number} id identificador de la instancia de author a eliminar
             * @returns {promise} promise para leer la respuesta del servidor
             */
            this.deleteRecord = function (id) {
                return $http.delete(context + "/" + id);
            };
        }]);
})(window.angular);