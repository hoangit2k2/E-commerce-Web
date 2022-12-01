app.controller('contenshopFormController', function($scope, $rootScope, $location,
    $routeParams, $http){
        $scope.username = $routeParams.username
        if($scope.username){
            $http.get(`http://localhost:8080/rest/contents/username/${$scope.username}`)
            .then(function(response){
                $scope.content = response.data;
            }).catch(error => {
                console.log(error)
            } )
        }

    }) 