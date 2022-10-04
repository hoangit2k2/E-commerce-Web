const app = angular.module('app', ['ngRoute']);
const serverIO = 'http://localhost:8080';
var message = document.getElementById('message');
var qrCode = document.querySelector("#qrCode");

app.controller('control', function($scope, $http) {
	$scope.serverIO = serverIO;
	$scope.entityIO = ['rest', 'roles']
	generalQR(qrCode, "input text");

	// ___________________________________________________________________________________ DATA
	// FUNCTIONS
	$scope.get = function(message) {
		let url = getLink($scope.serverIO, $scope.entityIO);
		generalQR(qrCode, url);

		$http.get(url).then(
			result => { // get
				$scope.showDataJson = JSON.stringify($scope.data = result.data, null, "\t");
				mes = message ? message : `Get data from ${url}`, `status:${result.status}`
				refresh(`<p class='text-info'>Success: url:"<a href='${url}'>${url}</a>" - status: <span class='text-success'>${result.status}</span></p>`, $scope.data);
			}
		).catch(err => {
			console.error(`Errors`, err);
			refresh(`<p class='text-danger'>Errors: url:"<a href='${url}'>${url}</a>" - status: ${err.status}</p>`, $scope.data = undefined);
		});
	};

	$scope.post = function() { // post to save
		let url = getLink($scope.serverIO, $scope.entityIO);
		generalQR(qrCode, url);
		try {
			$scope.data = JSON.parse($scope.showDataJson);

			$http.post(url, $scope.data).then(result => $scope.get(`Save data from ${url}`, `status:${result.status}`)).catch(err => {
				console.error(`Errors`, err);
				refresh(`<p class='text-danger'>Errors: url:"<a href='${url}'>${url}</a>" - status: ${err.status}</p>`);
			});
		} catch (error) {
			console.error(error);
			refresh(`<p class='text-danger'>Errors: ${error}`);
		}
	};

	$scope.put = function() { // put to save
		let url = getLink($scope.serverIO, $scope.entityIO);
		generalQR(qrCode, url);
		try {
			$scope.data = JSON.parse($scope.showDataJson);

			$http.put(url, $scope.data).then(
				result => result => $scope.get(`Update data from ${url}`, `status:${result.status}`)).catch(err => {
					console.error(`Errors`, err);
					refresh(`<p class='text-danger'>Errors: url:"<a href='${url}'>${url}</a>" - status: ${err.status}</p>`);
				});
		} catch (error) {
			console.error(error);
			refresh(`<p class='text-danger'>Errors: ${error}`);
		}
	};

	$scope.delete = function() { // delete
		let url = getLink($scope.serverIO, $scope.entityIO);
		generalQR(qrCode, url);
		$http.delete(url).then(
			result => result => $scope.get(`Delete data from ${url}`, `status:${result.status}`)).catch(err => {
				console.error(`Errors`, err);
				refresh(`<p class='text-danger'>Errors: url:"<a href='${url}'>${url}</a>" - status: ${err.status}</p>`);
			});
	};

	// ___________________________________________________________________________________ FILE
	var fileData = new FormData();
	// PREPARE FILE
	$scope.prepare = function(getFiles) {
		fileData = new FormData(); // new data before call append method
		for (const e of getFiles.files) fileData.append("files", e);
		showFiles("#showPrepareFiles", getFiles);
	}

	$scope.getAllFiles = function() {
		$http.get(getLink(serverIO, "rest", $scope.folder)).then(
			(result) => $scope.files = result.data
		).catch((err) => console.error(err));
	}

	// Save all files
	$scope.postFiles = function() {
		let url = getLink(serverIO, "rest", $scope.folder)
		$http.post(url, fileData, {
			TransformRequest: angular.identity,
			headers: { 'content-Type': undefined }
		}).then((resp) => {
			if (resp.status == 200) {
				$scope.getAllFiles();
				refresh(`Files saved, sent data to server with: ${url}`, `status:${resp.status}`);
			} else console.log("cannot save");
		}
		).catch((err) => console.error(err));
	}

	$scope.deleteFile = function(fileName) {
		var index = $scope.files.findIndex(name => name === fileName);
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		let url = getLink(serverIO, "rest", $scope.folder, fileName);

		$http.delete(url).then((resp) => {
			if (resp.status == 200) {
				$scope.getAllFiles();
				refresh(`Files saved, sent data to server with: ${url}`, `status:${resp.status}`);
			} else console.log("cannot delete")
		}).catch((err) => console.error(err));
	}
});

// ___________________________________________________________________________________ UTILS
function refresh(alert) {
	message.innerHTML = alert;
	let getFiles = document.querySelector("#getFiles");
	getFiles.setAttribute("type", "text")
	getFiles.setAttribute("type", "file");
	showFiles("#showPrepareFiles", getFiles);
}

function showFiles(toSetup, input) {
	var imageParent = document.querySelector(toSetup);

	if (imageParent != null && input) { // null !== undefined
		while (imageParent.lastChild) imageParent.removeChild(imageParent.firstChild);

		for (let i = 0; i < input.files.length; i++) {
			const reader = new FileReader();
			reader.onload = (e) => {
				var img = document.createElement("img");
				img.src = e.target.result;
				img.setAttribute("class", "m-1 p-1 rounded border o-fit-cover size-3");
				img.setAttribute("title", img.alt = input.files[i].name);
				imageParent.appendChild(img);
			}
			reader.readAsDataURL(input.files[i]);
		}
	} else imageParent.innerHTML = null;
}

function generalQR(element, qrText) {
	element.innerHTML = null;
	new QRCode(element, {
		text: qrText,
		width: 128,
		height: 128,
		colorDark: "#000000",
		colorLight: "transparent",
		correctLevel: QRCode.CorrectLevel.H
	})
}