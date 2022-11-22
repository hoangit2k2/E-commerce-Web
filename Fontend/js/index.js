const app = angular.module('app', ["ngRoute"]);
var info = angular.fromJson(sessionStorage.getItem('info'))
console.log(info)

app.config(function($httpProvider){
    // var auth = `Basic ${btoa("cus:123")}`
    if(info == null){

    }
    else{
    var auth = `Basic ${btoa(`${info.username}:${info.password}`)}`
    $httpProvider.defaults.headers.common['Authorization'] = auth
    }
});
app.controller('appController', function ($scope, $rootScope,$window) {
    $rootScope.info = info;
    $rootScope.checktrue = true;
    $rootScope.checkfalse = false;
    if (info != null) {
        $rootScope.checktrue = false;
        $rootScope.checkfalse = true;

    }
    $scope.logout = function() {
        sessionStorage.removeItem('info')
        $rootScope.checktrue = true;
        $rootScope.checkfalse = false;
        alert('Đăng Xuất Thành Công')
        $window.location.reload();
        location = "index.html"
    }
    $scope.checklogin = function(){
        if(info == null){
            alert('Bạn Cần đăng nhập')
             $window.location.reload();
             location = "#html/account.html"
            }
    }
    
});
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "./html/home.html",
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
        .when("/login/account", {
            templateUrl: "./html/account.html"
        })
        .otherwise({
            templateUrl: "./components/error/404.html"
        });
});
// app.run(function($rootScope,$window){
//     $rootScope.load = function(url) {
//         $window.location.reload();
//     location = "http://127.0.0.1:5500/index.html#!/login/account"
//     }
    
// })


