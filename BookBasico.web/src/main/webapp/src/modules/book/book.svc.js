(function(ng){
    var mod = ng.module('bookModule');
    
    mod.service('bookService', ['CrudCreator','bookContext', function(CrudCreator, context){
            CrudCreator.extendService(this, context);
    }]);
})(window.angular);
