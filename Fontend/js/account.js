
app.controller("accountCtrl", function($scope, $http, $window, $routeParams, $location){
    $scope.islogin = false
    $scope.btnName = "Login";
    $scope.cpassword1 = true;
    $scope.showsignup = false;
    $scope.showlogin = true;
    $scope.signuppage = function(){
        $scope.showsignup = $scope.showsignup ? false : true
        $scope.showlogin = false;
        $scope.btnName = "sign up";
        $scope.signupform.$setPristine();
        $scope.username = null;
        $scope.password = null;
        $scope.cpassword1 = false;
    }
    $scope.loginpage = function(){
        $scope.showlogin = $scope.showlogin ? false : true
        $scope.showsignup = false;
        $scope.btnName = "Login";
        $scope.signupform.$setPristine();
        $scope.username = null;
        $scope.password = null;
        $scope.cpassword1 = true;
    }
    $scope.login = function(){
        if($scope.btnName == "sign up"){
            if($scope.username != null && $scope.password != null && $scope.cpassword != null){
                $scope.resgister();
            }
        }
        else if ($scope.btnName == "Login"){
            if($scope.username != null && $scope.password != null){
                $scope.postf();
            }
        }
    }
    $scope.postf = function(){
        var url = `http://localhost:8080/rest/users/${$scope.username}`;
        $http.get(url).then(resp => {
            $scope.db = resp.data;
            if(resp.data.password == $scope.password){
                sessionStorage.setItem('info', angular.toJson(resp.data))
                alert('Đăng nhập thành công')
                $window.location.reload()

                $location.path('/')
            }
            else{
                alert('Mật Khẩu Sai')
            }
         

        }).catch(error =>{
            alert('Tên Đăng Nhập Không tồn tại')
        })
}
    $scope.resgister = function(){
        $http({
            method: "POST",
            url: "http://localhost:8080/rest/users",
            data: ({
                'username': $scope.username,
                'password': $scope.password
            }),
            // headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response){
            var data = response.data
            alert('Tạo tài khoản thành công')
            $location.path('/')

        })
    }
    $scope.updateinfo = function(){
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
        }).then(function(response){
            var url = `http://localhost:8080/rest/users/${$scope.username}`;
            $http.get(url).then(resp => {
                $scope.db = resp.data;
                    sessionStorage.setItem('info', angular.toJson(resp.data))
                    $window.location.reload();
            })
            alert('Cập Nhật thông tin thành công')
        })
        .catch(function(err){
            console.log(err)
        })
    }
    //update password user
    $scope.updatepassword = function(){
        $scope.username = $routeParams.username
        if($scope.password != info.password){
            alert('Mật khẩu củ không chính xác !')
            return
        }
        if($scope.newpassword != $scope.newpassword1){
            alert('Mật khẩu không trùng nhau')
            return
        }
        if($scope.newpassword == info){
            alert('Mật khẩu mới trùng với mật khẩu củ')
            return
        }
        else{
        $http({
            method: "PUT",
            url: `http://localhost:8080/rest/users/${$scope.username}/password`,
            data: ({
                'password': $scope.newpassword
            }),
        }).then(function(response){
            $http.defaults.headers.common.Authorization = ' ';
            var url = `http://localhost:8080/rest/users/${$scope.username}`;
            $http.get(url).then(resp => {
                $scope.db = resp.data;
                    sessionStorage.setItem('info', angular.toJson(resp.data))
                    $window.location.reload();
                    location = "index.html#!/"
            })
            alert('Cập nhật mật khẩu thành công')
        })
        // .catch(function(error){

        // })
    }
    }

})