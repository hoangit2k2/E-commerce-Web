app.controller('userControl', ($scope) => {

    (() => {
        $scope.get('users');
        pathImg = 'uploads/imageUser';
        $scope.breadcrumbs.splice(2, 2, {name:'tài khoản'});
    })();

    $scope.setImage = (evt) => {
        let url = evt.target.files;
        if (!$scope.entity) $scope.entity = {};
        showImage.src = url.length
            ? ($scope.entity.image = URL.createObjectURL(url[0]))
            : util.getImage(undefined)
    }
});