(function (ng) {
    var mod = ng.module('bookModule', ['ngCrud']);

    mod.constant('bookContext', 'books');

    mod.constant('bookModel', {
        fields: [{
                name: 'name',
                displayName: 'Name',
                type: 'String',
                required: true
            }, {
                name: 'isbn',
                displayName: 'Isbn',
                type: 'String',
                required: true
            }, {
                name: 'image',
                displayName: 'Image',
                type: 'String',
                required: true
            }, {
                name: 'description',
                displayName: 'Description',
                type: 'String',
                required: true
            }]});
})(window.angular);
