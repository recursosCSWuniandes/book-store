(function (ng) {
    var mod = ng.module('bookModule');

    mod.service('bookService', ['$http', 'bookContext', function ($http, context) {
            this.fetchRecords = function () {
                return $http.get(context);
            };

            this.fetchRecord = function (id) {
                return $http.get(context + "/" + id);
            };
            this.saveRecord = function (currentRecord) {
                if (currentRecord.id) {
                    return $http.put(context + "/" + currentRecord.id, currentRecord);
                } else {
                    return $http.post(context, currentRecord);
                }
            };
            this.deleteRecord = function (id) {
                return $http.delete(context + "/" + id);
            };
            this.replaceAuthors = function (bookId, authors) {
                return $http.put(context + "/" + bookId + "/authors", authors);
            };

            this.getAuthors = function (id) {
                return $http.get(context + "/" + id + "/authors");
            };
            this.removeAuthor = function(bookId, authorId){
                return $http.delete(context + "/" + bookId + "/authors/" + authorId);
            };
        }]);
})(window.angular);
