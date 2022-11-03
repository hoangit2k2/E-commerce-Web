const app = angular.module('app', ["ngRoute"]);
app.controller('appController', function ($scope, $rootScope) {   
    $rootScope.cart = localStorage.getItem('cart') || [];
});
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "./html/list.html"
        })
        .when("/:productId", {
            templateUrl: "./html/detail.html"
        })
        .when("/create/content", {
            templateUrl: "./html/createcontent.html"
        })
        .when("/blog/blog", {
            templateUrl: "./blog.html"
        })
        .when("/contact/contact", {
            templateUrl: "./contact.html"
        })
        .when("/about/about", {
            templateUrl: "./about.html"
        })
        // .otherwise({
        //     templateUrl: "./components/error/404.html"
        // });
});
