const app = angular.module('app', ["ngRoute"]);
var info = angular.fromJson(localStorage.getItem('info'))
var cart = angular.fromJson(localStorage.getItem('cart'))
console.log(cart)

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
        localStorage.removeItem('info')
        $rootScope.checktrue = true;
        $rootScope.checkfalse = false;
        swal({
            title: "Thông Báo !",
            text: "Đăng xuất thành công!",
            icon: "success",
            // buttons: true,
            // dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    $window.location.reload()

                }
            })
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    }
    $scope.checkinfo = function () {
        if (info == null) {
            // $location.path("/login/account")
            // alert('Bạn cần bổ sung thông tin cá nhân')
            // location.href = "http://127.0.0.1:5500/index.html#!/login/account"
        }
        if (info.name == null || info.address == null || info.email == null || info.phone == null) {
            swal({
                title: "Thông Báo !",
                text: "Bạn cần bổ sung thông tin cá nhân!",
                icon: "warning",
                // buttons: true,
                // dangerMode: true,
            })
                .then((willDelete) => {
                    if (willDelete) {
<<<<<<< Updated upstream
                        location.href = (`http://127.0.0.1:5500/index.html#!/account/${info.username}`) 
=======
                        location.href = ("http://127.0.0.1:5500/index.html#!/account/hoa")
>>>>>>> Stashed changes
                    }
                })

        }
        else {
            location.href = ("http://127.0.0.1:5500/index.html#!/create/content")
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
        .when("/manager/contens", {
            templateUrl: "./html/managerconten.html"
        })
        .when("/updatecontent/:contentid", {
            templateUrl: "./html/changecontent.html"
        })
        .when("/list/content/like", {
            templateUrl: "./html/listlike.html"
        })
        .when("/shopping/cart", {
            templateUrl: "./html/shoping-cart.html"
<<<<<<< Updated upstream
        })
        .when("/order/list",{
            templateUrl: "./html/listorder.html"
        })
        .when("/order/list/:id",{
            templateUrl: "./html/listorderdetail.html"
        })
        .otherwise({
=======
        }).otherwise({
>>>>>>> Stashed changes
            templateUrl: "./html/account.html"
        });
});

