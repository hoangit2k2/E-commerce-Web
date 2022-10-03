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
		}).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})