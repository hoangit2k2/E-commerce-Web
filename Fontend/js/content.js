const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/contents"; // get all entities
const directory = "data/images/account"; // folder to get images
app.controller('controlconten', function($scope, $http, $routeParams) {
	$scope.get = function(key) {
		$http.get('http://localhost:8080/rest/contents').then(
			function (response){
			$scope.data = response.data
			console.log(response.data)
			}
		).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})
app.directive('fileModel', ['$parse', function($parse){
	return {
		restrict: 'A',
		link: function(scope,element,attrs){
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files[0]);
				})
			})
		}
	}
}])
app.service('multipartForm', ['$http', function($http){
	this.post = function(uploadUrl, data){
		var fd = new FormData();
		for(var key in data){
			fd.append(key, data[key]);
		$http.post(uploadUrl, fd, {
			TransformRequest: angular.indentity,
			headers: {'Content-type': undefined}
		})
		}
		console.log(data)
	}
}])

app.controller('submitController', ['$scope','$http', 'multipartForm', function($http, $scope, multipartForm){
	$scope.customer = {};
	var fd = new FormData();
	this.post = function(uploadUrl, data){
		for(var key in data){
			fd.append(key, data[key]);
		$http.post(uploadUrl, fd, {
			TransformRequest: angular.indentity,
			headers: {'Content-type': undefined}
		})
		}
	}

	// $scope.uploadFiles = function() {
	// 	var request = {
	// 		method: 'POST',
	// 		url: 'http://localhost:8080/rest/uploadfile',
	// 		data: formdata,
	// 		headers: {
	// 			'Content-Type': undefined
	// 		}}


	// $scope.Submit = function(){
	// 	var uploadUrl  = 'http://localhost:8080/rest/uploadfile';
	// 	console.log($scope.customer)
	// 	multipartForm.post(uploadUrl, data);
	// }

}])





// app.controller('fupController', function($scope, $http){
// 	var formdata = new FormData();
// 	$scope.getTheFiles = function($files){
// 		angular.forEach($files, function(value, key){
// 			formdata.append(key, value);
// 		});
// 	};
// 	// console.log()

// 	$scope.uploadFiles = function() {
// 		var request = {
// 			method: 'POST',
// 			url: 'http://localhost:8080/rest/uploadfile',
// 			data: formdata,
// 			headers: {
// 				'Content-Type': undefined
// 			}
// 		};
// 	//send The FIles.
// 	$http(request).success(function (d) {
// 		alert(d);
// 	})
// 	.error(function(){

// 	});
// 	}
// });







	// $scope.getImage = function (name, director) {
	// 	return getImage(name, director ? `data/images/${director}` : 'data/images/content')
	// };
	
// function getImage(name, director) {
	//  return `http://localhost:8080/data/images/content/${name[0]}`
// }

// app.controller('contentFormController',function($scope, $rootScope, $http, $routeParams) {
// 	console.log('ok')
// 	$scope.id = $routeParams.id;
// 	console.log($scope.id)
// 	$scope.content = {}
// 	if($scope.id){
// 		try {
// 		$http.get(getLink(serverIO,path,$scope.id))
// 		.then(function(response){
// 			$scope.content = response.data;
// 			console.log('data: ' + $scope.id)
// 		})
// 	} catch (error) {
// 			console.log(error)
// 	}
// 	}
// })	
