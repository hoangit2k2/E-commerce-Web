app.controller('categoryControl', ($rootScope, $scope) => {
    (() => {
        $scope.get('categories'); 
        pathImg = 'uploads/imagecategory';
        $scope.entity.id = Math.round((Math.random()-1)*1000);
        $scope.breadcrumbs.splice(2, 2, {name:'phân loại nội dung'});
    })()

    // clear form
    $scope.clear2 = (e) => {
        $scope.clear(e);
        e.id = Math.round((Math.random()-1)*1000);
    }

    // set form images
    $scope.setImage = (evt) => {
        let url = evt.target.files;
        if (!$scope.entity) $scope.entity = {};
        showImage.src = url.length
            ? ($scope.entity.image = URL.createObjectURL(url[0]))
            : util.getImage(undefined)
    }
});