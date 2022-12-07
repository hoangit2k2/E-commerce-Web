
app.filter('formatVND', function () {
	return function (value) {
		return new Intl.NumberFormat().format(value) + ' VNĐ'
	}
})
app.controller('controlconten', function ($scope, $http, $routeParams) {
	$scope.get = function (key) {
		$http.get('http://localhost:8080/rest/contents').then(
			function (response) {
				$scope.data = response.data
				console.log(response.data)
			}
		).catch(error => {
			console.error(error)
		})

		$http.get('http://localhost:8080/rest/categorys').then(
			function (response) {
				$scope.category = response.data
				console.log(response.data)
			}
		).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})

app.controller('contentFormController', function ($scope, $window, $routeParams, $http) {
	$scope.contentId = $routeParams.contentId;
	$scope.email = $routeParams.email;
	$scope.content = {}
	var usernameid
	var category
	if ($scope.contentId) {
		$http.get(`http://localhost:8080/rest/contents/${$scope.contentId}`)
			.then(function (response) {
				$scope.content = response.data;
				usernameid = response.data.usernameid
				category = response.data.categoryid
				$http.get(`http://localhost:8080/rest/contents/username/${usernameid}`)
					.then(function (response) {
						$scope.contentshop = response.data;
					}).catch(error => {
						console.log(error)
					})
				$http.get(`http://localhost:8080/rest/contents/category/${category}`)
					.then(function (response) {
						$scope.contentcategoryshop = response.data;
						console.log($scope.contentcategoryshop)
					}).catch(error => {
						console.log(error)
					})
			}).catch(error => {
				console.error(error)
			})
		// $http.
	}
	$scope.love = function () {
		try {
			$http.get(`http://localhost:8080/rest/contents/${$scope.contentId}`)
				.then(function (response) {
					$scope.content = response.data;
					var x = $scope.content.conten_likes.length
					var y = true
					for (let i = 0; i < x; i++) {
						if ($scope.content.conten_likes[i] = info.name) {
							y = false
						}
					}
					if (y == false) {
						alert('Nội dung đã có trong danh sách yêu thích')
					}
					else {
						$http({
							url: `http://localhost:8080/rest/likes`,
							method: 'POST',
							data: {
								'usernameid': info.username,
								'content': $scope.content
							}
						}).then(function (response) {
							var data = response.data
							console.log(data)
							alert('Thêm vào danh sách yêu thích thành công')
							$window.location.reload();
						})
					}
				}).catch(error => {
					alert('Bạn cần đăng nhập')
					$window.location.reload();
					location = "index.html"
				})
			// console.log(data)
		} catch (error) {
			alert('Thêm vào yêu thích thất bại')
		}

	}
})

app.controller('mypostcontroll', function ($scope, $http, $routeParams, $window, $location) {
	$http.get(`http://localhost:8080/rest/categorys`)
		.then(function (response) {
			$scope.category = response.data
			console.log($scope.category)
		})
	$scope.form = [];
	$scope.files = [];
	$scope.insert = function () {
		$scope.image1 = $scope.files[0];
		$scope.image2 = $scope.files2[0];
		$scope.image3 = $scope.files3[0];

		$http({
			method: 'POST',
			url: "http://localhost:8080/rest/contents/",
			processData: false,
			transformRequest: function (data) {
				var formData = new FormData();
				formData.append("files", $scope.image1);
				formData.append("files", $scope.image2);
				formData.append("files", $scope.image3);
				formData.append("usernameid", info.username);
				formData.append("categoryid", $scope.categoryid);
				formData.append("namecontent", $scope.namecontent);
				formData.append("subject", $scope.subject);
				formData.append("price", $scope.price);
				formData.append("email", $scope.email);
				formData.append("phone", $scope.phone);
				formData.append("address", $scope.address);
				return formData;
				return $scope.user;
				return $scope.categoryid;
				return $scope.namecontent;
				return $scope.subject;
				return $scope.price;
				return $scope.email;
				return $scope.phone;
				return $scope.address;

			},
			data: $scope.form,
			headers: {
				'Content-Type': undefined
			}
		}).then(function (response) {
			var data = response.data
			alert('Thêm thành công'),
				$location.path(`/manager/contens/${info.username}`)
		});
	}
	$scope.contentid = $routeParams.contentid
	$http.get(`http://localhost:8080/rest/contents/${$scope.contentid}`)
		.then(function (response) {
			$scope.content = response.data
			console.log($scope.content)
		})
	$scope.updatecontent = function () {
		// console.log($scope.files2.length)
		// if($scope.files.length != 0){
		// 	$scope.image1 = $scope.files[0];
		// }
		// if($scope.files2.length != 0){
		// 	$scope.image2 = $scope.files2[0];
		// }
		// if($scope.files3.length != 0){
		// 	$scope.image3 = $scope.files3[0];
		// }
		// $scope.image1 = $scope.files[0];
		// $scope.image2 = $scope.files2[0];
		// $scope.image3 = $scope.files3[0];
		$http({
			method: "PUT",
			url: `http://localhost:8080/rest/contents/${$scope.contentid}`,
			processData: false,
			transformRequest: function (data) {
				var formData = new FormData();
				var files = [];

				if (typeof $scope.image1 !== undefined) {
					files.push($scope.image1)
					// formData.append("files",  $scope.image1);
				}
				if (typeof $scope.image2 !== undefined) {
					files.push($scope.image2)
					
					// formData.append("files", $scope.image2);
				}
				if (typeof $scope.image3 !== undefined) {
					files.push($scope.image3)
					// formData.append("files", $scope.image3);
				}
				// formData.append(files)
				formData.append("files", files);
				// formData.append("files", $scope.image2);
				// formData.append("files", $scope.image3);
				formData.append("usernameid", info.username);
				formData.append("categoryid", $scope.content.categoryid);
				formData.append("namecontent", $scope.content.namecontent);
				formData.append("subject", $scope.content.subject);
				formData.append("price", $scope.content.price);
				formData.append("email", $scope.content.email);
				formData.append("phone", $scope.content.phone);
				formData.append("address", $scope.content.address);
				return formData;
				return $scope.user;
				return $scope.categoryid;
				return $scope.namecontent;
				return $scope.subject;
				return $scope.price;
				return $scope.email;
				return $scope.phone;
				return $scope.address;
			},
			data: $scope.form,
			headers: {
				'Content-Type': undefined
			}
		}).then(function (response) {

			var data = response.data
			alert('Cập Nhật Thành công'),
				$window.location.reload()

		})
	}
	//upload file 1
	$scope.uploadedFile = function (element) {
		$scope.image1 = element.files[0]
		$scope.currentFile = element.files[0];
		var reader = new FileReader();

		reader.onload = function (event) {
			var output = document.getElementById('output');
			output.src = URL.createObjectURL(element.files[0]);

			$scope.image_source = event.target.result
			$scope.$apply(function ($scope) {
				$scope.files = element.files;
			});
		}
		reader.readAsDataURL(element.files[0]);
	}

	$scope.uploadedFile2 = function (element) {
		console.log(element)
		$scope.image2 = element.files[0]
		$scope.currentFile2 = element.files[0];
		var reader = new FileReader();

		reader.onload = function (event) {
			var output1 = document.getElementById('output1');
			output1.src = URL.createObjectURL(element.files[0]);

			$scope.image_source = event.target.result
			$scope.$apply(function ($scope) {
				$scope.files2 = element.files;
			});
		}
		reader.readAsDataURL(element.files[0]);
	}
	$scope.uploadedFile3 = function (element) {
		$scope.image3 = element.files[0]
		$scope.currentFile3 = element.files[0];
		var reader = new FileReader();

		reader.onload = function (event) {
			var output2 = document.getElementById('output2');
			output2.src = URL.createObjectURL(element.files[0]);

			$scope.image_source = event.target.result
			$scope.$apply(function ($scope) {
				$scope.files3 = element.files;
			});
		}
		reader.readAsDataURL(element.files[0]);
	}
})
app.controller('managercontent', function ($scope, $http, $window) {
	$http.get(`http://localhost:8080/rest/contents/username/${info.username}`)
		.then(function (response) {
			$scope.content = response.data;
			console.log($scope.content)
			$scope.removecontent = function (id) {
				// var idcontent id
				$http.delete(`http://localhost:8080/rest/contents/${id}`)
					.success(
						alert('xóa Thành công !'),
						$window.location.reload()
					)
			}
		})
}
)
app.controller('managerlike', function ($scope, $http, $window) {
	$http.get(`http://localhost:8080/rest/likes/${info.username}`)
		.then(function (response) {
			$scope.content = response.data
			console.log($scope.content)
			$scope.dislike = function (id) {

				$http.delete(`http://localhost:8080/rest/likes/${id}`)
					.success(
						alert('Bỏ khỏi danh sách yêu thích thành công !'),
						$window.location.reload()

					)
					.catch(
						alert('Xóa thất bại')
					)
			}
		})
})