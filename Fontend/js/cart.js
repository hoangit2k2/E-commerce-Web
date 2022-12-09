app.controller('orderController',function($http, $scope){
    $http.get(`http://localhost:8080/rest/order/${info.username}`).then(
        function(response){
            $scope.data = response.data
          
        }
    ).catch(error => {
        console.error(error)
    })
})
app.controller('detailController',function($scope, $routeParams, $http){
    $scope.id = $routeParams.id
    console.log($scope.id)

            $http.get(`http://localhost:8080/rest/orderdetail/${$scope.id}`).then(
            function(response){
            $scope.details = response.data
                console.log($scope.details)
            }
            )
})