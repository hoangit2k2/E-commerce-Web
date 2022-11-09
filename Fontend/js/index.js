const app = angular.module('app', ["ngRoute"]);
app.controller('appController', function ($scope, $rootScope) {   
    $rootScope.cart = localStorage.getItem('cart') || [];
});
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "./html/home.html"
        })
        .when("/:contentId", {
            templateUrl: "./html/detail.html"
        })
        .when("/create/content", {
            templateUrl: "./html/createcontent.html"
        })
        .when("/list/:categoryId", {
            templateUrl: "./html/list.html"
        })
        .when("/shop/:username", {
            templateUrl: "./html/contenshop.html"
        })
        .otherwise({
            templateUrl: "./components/error/404.html"
        });
});
