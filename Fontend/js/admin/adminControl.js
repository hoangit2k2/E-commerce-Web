const app = angular.module('app', ['ngRoute']);
const serverIO = 'http://localhost:8080';
var pathImg = 'uploads/default';
moment.locale('vi');

app.filter('dateRelative', () => {
    return (date, format) => `${moment(date).format(format)} (${moment(date).fromNow()})`
})

app.directive('convertToNumber', () => {
    return {
        require: 'ngModel',
        link: (s, e, a, ngModel) => {
            ngModel.$parsers.push((val) => {
                return val != null ? parseInt(val, 10) : null;
            });
            ngModel.$formatters.push((val) => {
                return val != null ? '' + val : null;
            });
        }
    };
});

app.config(($routeProvider, $httpProvider) => {
    $routeProvider.when(
        '/', { templateUrl: './views/home.htm', controller: 'homeControl'}
    ).when(
        '/user', { templateUrl: './views/user.htm' }
    ).when(
        '/category', { templateUrl: './views/category.htm' }
    ).when(
        '/content', { templateUrl: './views/content.htm' }
    ).when(
        '/order', { templateUrl: './views/order.htm' }
    ).when(
        '/statistic', { templateUrl: './views/statistic.htm' }
    ).otherwise({
        redirectTo: "/"
    });
})

app.controller('homeControl', ($scope) => {
    $scope.breadcrumbs.splice(2,2);
});

app.controller('control', ($scope, $http) => {
    ((...set) => {
        for(e of set) {
            let keys = Object.keys(e)
            for(k of keys) $scope[k] = e[k];
        }
    })({data:[], entity: {}, breadcrumbs: [
        {href: '/', name: 'Trang chủ'},
        {href: '#/home', name: 'quản lý'}
    ]});

    $scope.clear = $scope.setImage = (evt) => { }
    $scope.getImage = (name) => util.getImage(name);
    $scope.read = (e) => $scope.entity = angular.copy(e);

    $scope.clear = (e) => {
        let keys = Object.keys(e);
        for (k of keys) e[k] = undefined;
        if (prepareImg) for (f of [, 'file']) prepareImg.type = f;
        if (document.getElementById('showImage')) showImage.src = util.getImage(undefined)
    }

    // READ DATA
    $scope.get = (path, to) => $http.get(
        util.getLink(serverIO, 'api', path)
    ).then(r => $scope[to || 'data'] = r.data
    ).catch(e => console.error(e))

    // CREATE DATA
    $scope.post = (path, col, dir) => {
        if (!col || !path) return;
        let i = util.getIndex(col, $scope.entity[col], $scope.data)
        let title = 'Thêm dữ liệu'

        if (i < 0) $http.post(util.getLink(serverIO, 'api', path),
            ...util.getMultipart($scope.entity, prepareImg, dir)
        ).then(r => {
            $scope.data.push(angular.copy($scope.entity = r.data));
            util.alert(`Id: ${$scope.entity[col]} đã được thêm`, title, 'success');
            $scope.clear($scope.entity);
        }).catch(e => {
            util.alert(e.data ? e.data.message : 'Lỗi thêm dữ liệu!', title, 'error')
            console.error(e);
        }); else util.alert(`Id: ${$scope.entity[col]} đã tồn tại`, title, 'warning')
    }

    // UPDATE DATA
    $scope.put = (path, col, dir) => {
        if (!path || !col) return;
        let i = util.getIndex(col, $scope.entity[col], $scope.data)
        let title = 'Sửa dữ liệu'

        if (-1 < i) {
            $http.put(util.getLink(serverIO, 'api', path),
                ...util.getMultipart($scope.entity, prepareImg, dir)
            ).then(r => {
                $scope.entity = angular.copy($scope.data[i] = r.data);
                util.alert(`Id: ${$scope.entity[col]} đã được sửa`, title, 'success')
                $scope.clear($scope.entity);
            }).catch(e => {
                util.alert(e.data ? e.data.message : 'Lỗi sửa dữ liệu!', title, 'error')
                console.error(e);
            });
        } else util.alert(`Id: ${$scope.entity[col]} không tồn tại`, title, 'warning')
    }

    // DELETE DATA
    $scope.delete = (id, path, col, dir) => {
        if (id == undefined, !path || !col) return;
        let i = util.getIndex(col, id, $scope.data)
        let title = 'Xóa dữ liệu'

        if (-1 < i) {
            $http.delete(util.getLink(serverIO, 'api', path, dir ? `${id}?dir=${dir}` : id)
            ).then(r => {
                $scope.data.splice(i, 1);
                $scope.clear($scope.entity);
                util.alert(`Id: ${col + ' ' + id} đã được xóa`, title, 'success')
            }).catch(e => {
                util.alert(e.data ? e.data.message : 'Lỗi xóa dữ liệu!', title, 'error')
                console.error(e);
            });
        } else util.alert(`Id: ${col + ' ' + id} không tồn tại`, title, 'warning')
    }
})

const util = {
    // get index of array data
    getIndex: (column, value, array) => {
        if (!value || !array) return -1; // # 0==false
        if (column) for (let i = 0; i < array.length; i++) if (array[i][column] == value) return i;
        else for (let i = 0; i < array.length; i++) if (array[i] == value) return i; return -1;
    },
    // get link url EX: http://localhost:8080/api/...
    getLink: (host, ...paths) => {
        return !paths instanceof String ? host.concat('/', paths) : host.concat('/', paths).replaceAll(',', '/')
    },
    // get link url EX: http://localhost:8080/${pathImg}/...
    getImage: (imgName) => {
        return imgName
            ? imgName.startsWith('http') || imgName.startsWith('blob')
                ? imgName.trim() : util.getLink(serverIO, pathImg, imgName)
            : util.getLink(serverIO, pathImg, 'default.jpg');
    },
    // alert message
    alert: (title, text, icon) => {
        swal({ title: title, text: text, icon: icon });
    },
    // create parameters: default File:<files=...> & directory: <dir=...>
    getMultipart: (entity, input, dir) => {
        if(!input.files) return [entity];

        let files = input.files;
        let data = new FormData();
        let keys = Object.keys(entity);

        data.append('dir', dir)
        for (f of files) data.append('files', f) // prepare files
        for (k of keys) data.append(k, entity[k]) // prepare entity's fields

        return [data, {
            TransformRequest: angular.identity,
            headers: { 'content-Type': undefined }
        }];
    }
}