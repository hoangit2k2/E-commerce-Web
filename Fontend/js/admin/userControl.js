app.controller('userControl', ($scope) => {
    pathImg = 'uploads/imageUser';

    $scope.setImage = (evt) => {
        let url = evt.target.files;
        if (!$scope.entity) $scope.entity = {};
        showImage.src = url.length
            ? ($scope.entity.image = URL.createObjectURL(url[0]))
            : util.getImage(undefined)
    }

    $scope.get('users');
});