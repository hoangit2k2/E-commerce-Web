const app = angular.module('app', ["ngRoute"]);
var info = angular.fromJson(sessionStorage.getItem('info'))
app.config(function ($httpProvider) {
    if (info == null) {
    }
    else {
        var auth = `Basic ${btoa(`${info.username}:${info.password}`)}`
        $httpProvider.defaults.headers.common['Authorization'] = auth
        console.log(auth)
    }
});
app.controller('appController', function ($scope, $rootScope, $window, $location) {
    $rootScope.info = info;
    $rootScope.checktrue = true;
    $rootScope.checkfalse = false;
    if (info != null) {
        $rootScope.checktrue = false;
        $rootScope.checkfalse = true;

    }
    $scope.logout = function () {
        sessionStorage.removeItem('info')
        $rootScope.checktrue = true;
        $rootScope.checkfalse = false;
        alert('Đăng Xuất Thành Công')
        $window.location.reload();
    }
    $scope.checklogin = function () {
        if (info == null) {
            alert('Bạn Cần đăng nhập')
            $window.location.reload();
            $location.path("/login/account")
        }
            if (info.name == null || info.address == null || info.email == null || info.phone == null) {
                alert('Bạn cần bổ sung thông tin cá nhân')
                $location.path(`/account/${info.username}`)
            }
        else{
            $location.path("/create/content")

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
        .when("/account/:username", {
            templateUrl: "./html/infomationcustomer.html"
        })
        .when("/account/:username/password/", {
            templateUrl: "./html/changepassword.html"
        })
        .when("/manager/contens/:username", {
            templateUrl: "./html/managerconten.html"
        })
        .when("/updatecontent/:contentid", {
            templateUrl: "./html/changecontent.html"
        })
        .when("/list/content/like",{
            templateUrl: "./html/listlike.html"
        })
        .otherwise({
            templateUrl: "./components/error/404.html"
        });
});

