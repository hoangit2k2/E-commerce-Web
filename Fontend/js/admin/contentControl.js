app.controller('contentControl', ($scope) => {
    pathImg = 'uploads/imagecontent';

    $scope.read2 = (e) => {
        $scope.read(e);
        $scope.entity['datetime'] = new Date($scope.entity['datetime']);
    }

    $scope.setImage = (evt) => {
        let files = evt.target.files;
        if (!$scope.entity) $scope.entity = {};
        $scope.entity.content_images = [];
        for (f of files) $scope.entity.content_images.push(URL.createObjectURL(f));
    }

    $scope.clear2 = (e) => {
        $scope.clear(e);
        e.likes = []; e.content_images = [];
        e.id = Math.round((Math.random()-1)*1000);
    }

    (async (...getTo) => {
        $scope.entity.id = Math.round((Math.random()-1)*1000)
        for (to of getTo) if (typeof (to) == 'string') await $scope.get(to);
        else {
            let keys = Object.keys(to)
            for (k of keys) await $scope.get(to[k], k);
        };
    })('contents', { cates: 'categories' })
});