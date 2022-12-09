
app.controller("accountCtrl", function ($scope, $http, $window, $routeParams, $location, $route) {
    $scope.islogin = false
    $scope.btnName = "Login";
    $scope.cpassword1 = true;
    $scope.showsignup = false;
    $scope.showlogin = true;
    $scope.signuppage = function () {
        $scope.showsignup = $scope.showsignup ? false : true
        $scope.showlogin = false;
        $scope.btnName = "sign up";
        $scope.signupform.$setPristine();
        $scope.username = null;
        $scope.password = null;
        $scope.cpassword1 = false;
    }
    $scope.loginpage = function () {
        $scope.showlogin = $scope.showlogin ? false : true
        $scope.showsignup = false;
        $scope.btnName = "Login";
        $scope.signupform.$setPristine();
        $scope.username = null;
        $scope.password = null;
        $scope.cpassword1 = true;
    }
    $scope.login = function () {
        if ($scope.btnName == "sign up") {
            if ($scope.username != null && $scope.password != null && $scope.cpassword != null) {
                $scope.resgister();
            }
        }
        else if ($scope.btnName == "Login") {
            if ($scope.username != null && $scope.password != null) {
                $scope.postf();
            }
        }
    }
    $scope.postf = function () {
        var url = `http://localhost:8080/rest/users/${$scope.username}`;
        $http.get(url).then(resp => {
            $scope.db = resp.data;
            if (resp.data.password == $scope.password) {
                localStorage.setItem('info', angular.toJson(resp.data))

                swal({
                    title: "Thông Báo !",
                    text: "Đăng nhập thành công!",
                    icon: "success",
                    // buttons: true,
                    // dangerMode: true,
                })
                    .then((willDelete) => {
                        if (willDelete) {
                            $window.location.reload();
                            location = "index.html"
                        }
                    })
            }
            else {
                swal("Lỗi", "Mật Khẩu sai !", "warning");

            }
        }).catch(error => {
            swal("Lỗi", "Tên đăng nhập không tồn tại !", "warning");
        })
    }
    $scope.resgister = function () {
        $http({
            method: "POST",
            url: "http://localhost:8080/rest/users",
            data: ({
                'username': $scope.username,
                'password': $scope.password
            }),
            // headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            var data = response.data
            swal({
                title: "Thông Báo !",
                text: "Tạo tài khoản thành công!",
                icon: "success",
                // buttons: true,
                // dangerMode: true,
            })
                .then((willDelete) => {
                    if (willDelete) {
                        $window.location.reload()
                    }
                })

        })
    }
    $scope.updateinfo = function () {
        $scope.username = $routeParams.username
        $http({
            method: "PUT",
            url: `http://localhost:8080/rest/users/${$scope.username}`,
            data: ({
                'name': $scope.info.name,
                'address': $scope.info.address,
                'phone': $scope.info.phone,
                'email': $scope.info.email
            }),
            // headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            var url = `http://localhost:8080/rest/users/${$scope.username}`;
            $http.get(url).then(resp => {
                $scope.db = resp.data;
                localStorage.setItem('info', angular.toJson(resp.data))
                $window.location.reload();
            })
            swal("Thông báo", "Cập nhật thông tin thành công !", "success");
        })
            .catch(function (err) {
                console.log(err)
            })
    }
    //update password user
    $scope.updatepassword = function () {
        $scope.username = $routeParams.username
        if ($scope.password != info.password) {

            swal("Lỗi", "Mật khẩu củ không chính xác!", "warning");
            return
        }
        if ($scope.newpassword != $scope.newpassword1) {
            swal("Lỗi", "Mật khẩu không trùng nhau!", "warning");

            return
        }
        if ($scope.newpassword == info.password) {
            swal("Lỗi", "Mật khẩu mới trùng mới mật khẩu củ!", "warning");
            return
        }
        else {
            $http({
                method: "PUT",
                url: `http://localhost:8080/rest/users/${$scope.username}/password`,
                data: ({
                    'password': $scope.newpassword
                }),
            }).then(function (response) {
                $http.defaults.headers.common.Authorization = ' ';
                var url = `http://localhost:8080/rest/users/${$scope.username}`;
                $http.get(url).then(resp => {
                    $scope.db = resp.data;
                    localStorage.setItem('info', angular.toJson(resp.data))	
                })
                swal("Thông báo", "Cập nhật mật khẩu thành công!", "success");
                location.href = "http://127.0.0.1:5500/index.html#!/"
                $window.location.reload();

            })
            // .catch(function(error){

            // })
        }
    }

})