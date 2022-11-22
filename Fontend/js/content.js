
app.filter('formatVND', function () {
    return function (value) {
        return new Intl.NumberFormat().format(value) + ' VNĐ'
    }
})
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

		$http.get('http://localhost:8080/rest/categorys').then(
			function (response){
				$scope.category = response.data
				console.log(response.data)
			}
			).catch(error => {
			console.error(error)
		})
	}
	$scope.get();
})

app.controller('contentFormController', function($scope,$window,$routeParams, $http){
		$scope.contentId = $routeParams.contentId;
		$scope.email = $routeParams.email;
		$scope.content = {}
		if($scope.contentId){

			$http.get(`http://localhost:8080/rest/contents/${$scope.contentId}`)
				.then(function(response){
					$scope.content = response.data;
					// console.log(response.data);
				}).catch(error => {
					console.error(error)
				})
		}
		$scope.love = function(){
            try {
				$http.get(`http://localhost:8080/rest/contents/${$scope.contentId}`)
				.then(function(response){
					$scope.content = response.data;
					var x = $scope.content.conten_likes.length
					var y = true
				for (let i = 0; i < x; i++) {
					if($scope.content.conten_likes[i] = info.name){
						y = false
					}
				}
					if(y == false){
						alert('Nội dung đã có trong danh sách yêu thích')
					}
					else{
						$http({
							url: `http://localhost:8080/rest/likes`,
							method: 'POST',
							data: {
								'usernameid': info.username,
								'contentid': $scope.contentId
							}
						}).then(function(response){
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

app.controller('mypostcontroll', function($scope, $http){
	$scope.form = [];
	$scope.files = [];
	$scope.insert = function() {
		$scope.image1 = $scope.files[0];
		$scope.image2 = $scope.files2[0];
		$scope.image3 = $scope.files3[0];

			$http({
				method:'POST',
				url:"http://localhost:8080/rest/contents/",
				processData:false,
				transformRequest:function(data){
					var formData=new FormData();
					formData.append("files", $scope.image1);
					formData.append("files", $scope.image2);
					formData.append("files", $scope.image3);
					formData.append("usernameid", $scope.user);
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
			data : $scope.form,
			headers: {
				'Content-Type': undefined
			}
		}).then(function(response){
			var data = response.data
			alert('Thêm thành công')
		});
	} 
	//upload file 1
	$scope.uploadedFile = function(element){
		$scope.currentFile = element.files[0];
		var reader = new FileReader();
		
		reader.onload = function(event){
			var output = document.getElementById('output');
			output.src = URL.createObjectURL(element.files[0]);
			
		$scope.image_source = event.target.result
		$scope.$apply(function($scope){
			$scope.files = element.files;
		});
	}
		reader.readAsDataURL(element.files[0]);
	}

	$scope.uploadedFile2 = function(element){
		$scope.currentFile2 = element.files[0];
		var reader = new FileReader();
		
		reader.onload = function(event){
			var output1 = document.getElementById('output1');
			output1.src = URL.createObjectURL(element.files[0]);
			
		$scope.image_source = event.target.result
		$scope.$apply(function($scope){
			$scope.files2 = element.files;
		});
	}
		reader.readAsDataURL(element.files[0]);
	}
	$scope.uploadedFile3 = function(element){
		$scope.currentFile3 = element.files[0];
		var reader = new FileReader();
		
		reader.onload = function(event){
			var output2 = document.getElementById('output2');
			output2.src = URL.createObjectURL(element.files[0]);
			
		$scope.image_source = event.target.result
		$scope.$apply(function($scope){
			$scope.files3 = element.files;
		});
	}
		reader.readAsDataURL(element.files[0]);
	}
})