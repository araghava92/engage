var app = angular.module('engage', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui.tree']);

app.controller('CategoriesController', function($scope, $http, $uibModal) {

    $scope.categories = [];

    $scope.newCategory = {
        name:'',
        parent:''
    };

    function getCategories() {
        $http.get('http://localhost:8080/categories/').
            then(function(response) {
                console.log(response);
                $scope.categories = response.data;
           });
    }

    function findCategory(id) {
        for (var i = 0;i < $scope.categories.length; i++) {
            if ($scope.categories[i].id == parseInt(id)) {
                return $scope.categories[i];
            }
        }
    }

    $scope.addCategory = function() {
        $http({
            url: 'http://localhost:8080/categories/',
            method: 'PUT',
            params: {
                "name": $scope.newCategory.name,
                "parent": $scope.newCategory.parent
            }
        }).then(function(response) {
               $scope.newCategory = {
                   name:'',
                   parent:''
               };
               getCategories();
        });
    };

    $scope.updateCategory = function(id, name) {
        $http({
            url: 'http://localhost:8080/categories/',
            method: 'POST',
            params: {
                "id": id,
                "name": name
            }
        }).then(function(response) {
               getCategories();
           });
    };

    $scope.deleteCategory = function(id) {
        $http({
            url: 'http://localhost:8080/categories/' + id,
            method: 'DELETE'
        }).then(function(response) {
               getCategories();
           });
    };

    // UI Controls
    $scope.expand = function(e) {
        var parentEl = e.target.parentElement;
        var catId = parentEl.id.split("-")[1];
        var cat = findCategory(catId);
        $scope.children = cat.children;
        var html = '<div ng-repeat="category in children" id="category-{{category.id}}">'
                    + '<button ng-click="expand($event)">+</button>'
                    + '<span>{{category.name}}</span>'
                    + '</div>'
        parentEl.innerHtml += html;
    }

    $scope.openEditModal = function(category) {

        var pc = this;
        pc.updatedCategory = {
            id: category.id,
            name: category.name
        }

        var modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'editModal.html',
          controller: 'EditModalInstanceCtrl',
          controllerAs: 'pc',
          size: 'lg',
          resolve: {
            updatedCategory: function () {
              return pc.updatedCategory;
            }
          }
        });


        modalInstance.result.then(function () {
            $scope.updateCategory(pc.updatedCategory.id, pc.updatedCategory.name);
        });
    }

    $scope.openProductsModal = function(category) {

        var pc = this;
        pc.categoryId = category.id;

        var modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'productsModal.html',
          controller: 'ProductsModalInstanceCtrl',
          controllerAs: 'pc',
          size: 'lg',
          resolve: {
            categoryId: function() {
                return pc.categoryId;
            }
          }
        });


        modalInstance.result.then(function () {

        });
    }

    getCategories();
});

app.controller('EditModalInstanceCtrl', function ($scope, $uibModalInstance, updatedCategory) {

     var pc = this;
     pc.updatedCategory = updatedCategory;
     $scope.categoryId = updatedCategory.id;
     $scope.categoryName = updatedCategory.name;

     pc.update = function () {
       pc.updatedCategory.name = $scope.categoryName;
       $uibModalInstance.close();
     };

     pc.cancel = function () {
       $uibModalInstance.dismiss('cancel');
     };
});



app.controller('ProductsModalInstanceCtrl', function ($scope, $http, $uibModalInstance, categoryId) {

     var pc = this;
     $scope.products = [];
     $scope.newProduct = {
        name: '',
        upc: '',
        price: ''
     }


    // Products rest api calls

    function getProducts(categoryId) {
        $http({
            url: 'http://localhost:8080/products/',
            method: 'GET',
            params: {
                categoryId: categoryId
            }
        }).then(function(response) {
                console.log(response);
                $scope.products = response.data;
           });
    }

     $scope.addProduct = function() {
        $http({
            url: 'http://localhost:8080/products/',
            method: 'PUT',
            params: {
                "name": $scope.newProduct.name,
                "UPC": $scope.newProduct.upc,
                "price": $scope.newProduct.price,
                "categoryId": categoryId
            }
        }).then(function(response) {
               $scope.newProduct = {
                  name: '',
                  upc: '',
                  price: ''
               };
               getProducts(categoryId);
        });
     };

     $scope.deleteProduct = function() {
        $http({
            url: 'http://localhost:8080/products/' + id,
            method: 'DELETE'
        }).then(function(response) {
               getProducts();
        });
     }

     pc.cancel = function () {
       $uibModalInstance.dismiss('cancel');
     };

     getProducts(categoryId);
});