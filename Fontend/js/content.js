
app.filter('formatVND', function () {
	return function (value) {
		return new Intl.NumberFormat().format(value) + ' VNĐ'
	}
})
app.controller('controlconten', function ($scope, $http, $routeParams, $rootScope) {
	$rootScope.cart = JSON.parse(localStorage.getItem('cart')) || []
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
	$scope.addToCart = (content, quantity = 1) => {
		const item = $rootScope.cart.filter(item => item.content.id == content.id)[0];
		if (item) {
			$rootScope.cart = $rootScope.cart.map(item => {
				if (item.content.id == content.id) {
					item.quantity += quantity
				}
				return item
			})
		} else {
			$rootScope.cart = [...$rootScope.cart, { content, quantity, checked: false }]
			swal({
				title: "Thêm Vào Giỏ Hàng Thành Công",
				icon: "success",
			})
				.then((willDelete) => {
					if (willDelete) {
					}
				});
		}
		localStorage.setItem('cart', JSON.stringify($rootScope.cart))
	}
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
					$scope.detail = response.data;
					console.log($scope.detail)
					var x = $scope.detail.conten_likes.length
					var y = true
					const user = info.username
					for (let i = 0; i < x; i++) {
						if ($scope.detail.conten_likes[i] == user) {
							console.log(info.username)
							console.log($scope.detail.conten_likes[i])

							y = false
						}
					}
					console.log(y)
					if (y == false) {
						swal("Lỗi", "Đã có trong danh sách yêu thích !", "warning");

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
							// swal("Good job!", "You clicked the button!", "success");
							swal({
								title: "Thêm Vào Danh Sách Yêu Thích Thành Công",
								// text: "Once deleted, you will not be able to recover this imaginary file!",
								icon: "success",
								// buttons: true,
								// dangerMode: true,s
							})
								.then((willDelete) => {
									if (willDelete) {
										$window.location.reload();
									}
								});
						})
					}
				}).catch(error => {
					
					location.href = "http://127.0.0.1:5500/index.html#!/login/account"

				})
			// console.log(data)
		} catch (error) {
			
			swal("Lỗi", "Thêm Vào yêu thích thất bại !", "warning");
		}

	}
	$scope.comment = function () {
		try {


			$http({
				url: `http://localhost:8080/rest/comment`,
				method: 'POST',
				data: {
					'usernameid': info.username,
					'contentid': $scope.contentId,
					'subject': $scope.commentcontent
				}
			}).then(function (response) {
				swal({
					title: "Thông Báo !",
					text: "Bình Luận Thành công!",
					icon: "success",
					// buttons: true,
					// dangerMode: true,
				})
					.then((willDelete) => {
						if (willDelete) {
							$window.location.reload();


						}
					})
			})
		} catch (error) {
			swal({
				title: "Thông Báo !",
				text: "Bạn cần đăng nhập !",
				icon: "warning",
				// buttons: true,
				// dangerMode: true,
			})
				.then((willDelete) => {
					if (willDelete) {
						location.href = "http://127.0.0.1:5500/index.html#!/login/account"

					}
				})
		}
	}
	$http.get(`http://localhost:8080/rest/comment/${$scope.contentId}`)
		.then(function (response) {
			$scope.commentdetail = response.data
		}).catch(error => {
			console.log(error)
		})
})

app.controller('mypostcontroll', function ($scope, $http, $routeParams, $window, $location) {
	try {
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
				swal({
					title: "Thông Báo !",
					text: "Tạo nội dung thành công!",
					icon: "success",
					// buttons: true,
					// dangerMode: true,
				})
					.then((willDelete) => {
						if (willDelete) {
							location.href = "http://127.0.0.1:5500/index.html#!/manager/contens"

						}
					})
			}
			)
		}
	} catch (error) {
		swal({
			title: "Thông Báo !",
			text: "Bạn cần đăng nhập!",
			icon: "warning",
			// buttons: true,
			// dangerMode: true,
		})
			.then((willDelete) => {
				if (willDelete) {
					location.href = "http://127.0.0.1:5500/index.html#!/login/account"

				}
			})	
		}
	$scope.contentid = $routeParams.contentid
	$http.get(`http://localhost:8080/rest/contents/${$scope.contentid}`)
		.then(function (response) {
			$scope.content = response.data
			console.log($scope.content)
		})
	$scope.updatecontent = function () {

		$scope.image1 = $scope.files[0];
		$scope.image2 = $scope.files2[0];
		$scope.image3 = $scope.files3[0];
		$http({
			method: "PUT",
			url: `http://localhost:8080/rest/contents/${$scope.contentid}`,
			processData: false,
			transformRequest: function (data) {
				var formData = new FormData();
				console.log($scope.files[0])
				console.log($scope.files2[0])
				console.log($scope.files3[0])

				$scope.files[0];
				formData.append("files", $scope.image1);
				formData.append("files", $scope.image2);
				formData.append("files", $scope.image3);
				formData.append("usernameid", info.username);
				formData.append("categoryid", $scope.content.categoryid);
				formData.append("namecontent", $scope.content.namecontent);
				formData.append("subject", $scope.content.subject);
				formData.append("price", $scope.content.price);
				formData.append("email", $scope.content.email);
				formData.append("phone", $scope.content.phone);
				formData.append("address", $scope.content.address);
				// console.log($scope.image1)
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
			swal({
				title: "Thông Báo !",
				text: "Cập nhật nội dung thành công!",
				icon: "success",
				// buttons: true,
				// dangerMode: true,
			})
				.then((willDelete) => {
					if (willDelete) {
						location.href = "http://127.0.0.1:5500/index.html#!/manager/contens"

					}
				})
		})
	}
	//upload file 1
	$scope.uploadedFile = function (element) {
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
	try {


		$http.get(`http://localhost:8080/rest/contents/username/${info.username}`)
			.then(function (response) {
				$scope.content = response.data;
				console.log($scope.content)
				$scope.removecontent = function (id) {
					// var idcontent id
					$http.delete(`http://localhost:8080/rest/contents/${id}`)
						.success(
							swal({
								title: "Thông Báo !",
								text: "Bạn có chắc chắn muốn xóa nội dung!",
								icon: "warning",
								buttons: true,
								dangerMode: true,
							})
								.then((willDelete) => {
									if (willDelete) {
										$window.location.reload()
									}
									else {

									}
								}),
						)
				}
			})
	} catch (error) {
		swal({
			title: "Bạn cần đăng nhập",
			icon: "warning",
		})
			.then((willDelete) => {
				if (willDelete) {
					location.href = "http://127.0.0.1:5500/index.html#!/login/account"

				}
			});

	}
})
app.controller('managerlike', function ($scope, $http, $window) {
	try {

		$http.get(`http://localhost:8080/rest/likes/${info.username}`)
			.then(function (response) {
				$scope.content = response.data
				console.log($scope.content)
				$scope.dislike = function (id) {

					$http.delete(`http://localhost:8080/rest/likes/${id}`)
						.success(
							swal({
								title: "Thông Báo !",
								text: "Bạn có chắc chắn muốn bỏ thích!",
								icon: "warning",
								buttons: true,
								dangerMode: true,
							})
								.then((willDelete) => {
									if (willDelete) {
										$window.location.reload()
									}
									else {

									}
								}),

						)
						.catch(
							swal("Lỗi", "Bỏ thích Thất bại!", "warning")

						)
				}
			})
	} catch (error) {
		swal({
			title: "Bạn cần đăng nhập",
			icon: "warning",
		})
			.then((willDelete) => {
				if (willDelete) {
					location.href = "http://127.0.0.1:5500/index.html#!/login/account"

				}
			});


	}
})
app.controller('cartController',function($scope, $rootScope,$window,
	$rootScope, $http){
		$rootScope.cart = JSON.parse(localStorage.getItem('cart')) || []
		console.log($scope.address)
		$scope.numberPattern = /^\d+$/
		$scope.deleteItem = (id) => {
			swal({
				title: "Thông báo?",
				text: "Bạn có muốn bỏ khỏi giỏ hàng",
				icon: "warning",
				buttons: true, 
				dangerMode: true
			}).then((willDelete)=>{
				if(willDelete){
					$rootScope.cart = $rootScope.cart.filter(item => item.content.id != id);
					localStorage.setItem('cart', JSON.stringify($rootScope.cart));
					$window.location.reload()
				}else{
					swal("ok")
				}
			})
		}
		$scope.getTotal = () => {
			let total = 0;
			total = $rootScope.cart.reduce(
				(previous, curent) => {
					if(curent.checked == true){
						previous += curent.content.price * curent.quantity
					}
					return previous
				},total
			)
			return total
		}
		$scope.updateCart = () => {
			$rootScope.cart = $rootScope.cart.map(item => {
				if(item.quantity > 10){
					item.quantity = 10
				}
				if(item.quantity < 0){
					item.quantity = 0
				}
				return item
			})
			localStorage.setItem('cart', JSON.stringify($rootScope.cart))
		}
		$scope.getSelectedItems = () => {
			return $rootScope.cart.filter(item => item.checked == true);
		}

		$scope.order = {
			datetime: new Date(),
			address: "",
			usernameid: info.username,
			pay: "true",
			note: "",
			get orderDetails(){
				$scope.cartcheck = $rootScope.cart.filter(item => item.checked == true)
				return $scope.cartcheck.map(item => {
					return{
						content: {id: item.content.id},
						price: item.content.price,
						quantity: item.quantity
					}
				})
			},
			purchase(){
				var order = angular.copy(this);
				console.log(order)
				$http.post("http://localhost:8080/rest/order", order).then(
					resp => {
						swal("Thông báo", "Đặt hàng thành công!", "success");
					}).catch(error =>{
						swal("Thông báo", "Đặt hàng thất bại!", "warning");
					})
			}
		}
	})