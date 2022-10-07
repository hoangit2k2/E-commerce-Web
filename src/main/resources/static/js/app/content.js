const app = angular.module('app', []);
const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/contents"; // get all entities
const directory = "data/images/account"; // folder to get images

//get infomation content
app.controller('controlconten', function($scope, $http) {
	$scope.get = function(key) {
		$http.get(getLink(serverIO, path, key)).then(result => {
			if(result.status==200) $scope.data = result.data;
			else console.error(`status: ${result.status}`)
			console.log(result.data)
		}).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})

//get datail by id
app.controller('controldetail',function($scope, $http) {
	$scope.get = function(){
		var currentLocation = window.location.pathname
		var id = currentLocation.substr(currentLocation.length-1)
		console.log(id)
		$http.get(getLink(serverIO,path,id)).then(result => {
			if(result.status == 200) $scope.data = result.data;
			else console.error(`status: ${result.status}`)
			console.log(result.data)
		}).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})