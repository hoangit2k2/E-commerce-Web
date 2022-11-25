
app.controller("accountCtrl", function($scope, $http, $window){
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
        // console.log($scope.username)
        // console.log($scope.password)
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
                $window.location.reload();
                location = "index.html"
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
            alert('Thêm thành công')
        })
    }
})

  //     $http.post(s
    //         "wwwww", {
    //             'username' : $scope.username,
    //             'password' : $scope.password,
    //             'btnName' : $scope.btnName
    //         }
    //     ).senccess(function(data){
    //         alert(data);
    //         if(data == "username already exists. User the different Username"){
    //         return;
    //     }
    //     $window.location.reload();
    //     if(data == "login Successful"){
    //         location = ""
    //     }
    // })