
app.controller('controlcategory', function($scope, $http, $routeParams){
    $scope.get = function(key){
        $http.get('http://localhost:8080/rest/categorys').then(
			function (response){
				$scope.category = response.data
				console.log(response.data)
			}
			).catch(error => {
			console.error(error)
		})
    }
})

app.controller('categoryFormController', function($scope, $rootScope, $location,
    $routeParams, $http){
        $scope.categoryId = $routeParams.categoryId;
		$scope.categorydetail = {}     
        console.log($scope.categoryId)
        if($scope.categoryId) {
            $http.get(`http://localhost:8080/rest/contents/category/${$scope.categoryId}`)
            .then(function(response){
                $scope.content = response.data;

            }).catch(error => {
                console.log(error)
            })

            $http.get(`http://localhost:8080/rest/categorys/${$scope.categoryId}`)
            .then(function(response){
                $scope.categorydetail = response.data;
                console.log(response.data.name)
            }).catch(error => {
                console.log(error)
            }) 
        }

    })