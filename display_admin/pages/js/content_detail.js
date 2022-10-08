const app = angular.module('app', []);
const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/contents"; // get all entities\
const toast = new bootstrap.Toast(liveToast);


/*
    ____________________IDs
    entity > form
    fields > div
    contentImage > div
    showImage > img
    message > div
    offcanvasTop > div
    offcanvasTopLabel > h5
    {id, views, active, viewImgs, [...categories.id]}
*/

app.filter('fil2Many', function() {
    return function (array, columns) {
        if(!array || !columns) return [];
        try {
            var column = Object.keys(columns);
            return array.filter(x => columns[column].includes(x[column]));
        } catch (error) {return};
    }
});

app.controller('control', function ($scope, $http) {

    $scope.isFile = function(str) {
        if(str) return str.lastIndexOf('.') > -1;
    }

    // has any Object[id] set to array
    $scope.hasAny = function (entity, array) { 
        return !entity || !array ? false : getIndex(undefined, entity.id, array) > -1;
    }

    // set id to array when entity.id exist
    $scope.setAny = function (entity, array) {
        let i = getIndex(undefined, entity.id, array);
        if(!array) array = []; // i == -1 && new Array();
        if(!document.getElementById(entity.id).checked) array.push(entity.id);
        else if (-1<i) array.splice(i,1);
    }

    // read in client
    $scope.read = function (entity) {
        if (!entity) return; // entity is null don't update
        if (entity['id'] == $scope.entity['id']) return; // this callback, don't update

        let i = getIndex('id', entity.id, $scope.data); // get data on server and upload views
        if(i > -1) $http.get(getLink(serverIO, path, 'upview', entity.id)).then(result => {
            if(result.status == 200) $scope.entity = $scope.data[i] = result.data
        }).catch(error => console.error(`upviews ${entity.id} failed:`, error));

        $scope.mes = {b:'bg-info', t:`Xem chi tiết thông tin ${entity.subject}`, c:entity.content}
        toast.show();
    }

    // get data and post to
    $scope.getTo = function(uri,to) {
        if(!to || !uri) return;
        $http.get(getLink(serverIO, path, uri)).then(
            resp => {
                if(resp.status == 200) len = ($scope[to] = resp.data).length;
                else console.warn(`status is ${resp.status}`);
                $scope.mes = {b:'bg-info', t:`Lấy thông tin liên quan`, c: `Đã lấy tổng cộng ${len} dữ liệu`}
            }
        ).catch(error => console.error('get error: ' + error));

        
        toast.show();
    }

    // prepare image
    $scope.getImage = function (name, director) {
        return getImage(name, director ? `data/images/${director}` : 'data/images/content')
    };

    $scope.setImage = function(input) {
        if(input.files) {
            for(let i = 0; i < $scope.entity.content_images.length; i++) {
                if($scope.entity.content_images[i].startsWith('blob'))
                    $scope.entity.content_images.splice(i--,1);
            } for (const f of input.files) {
                $scope.entity.content_images.push(URL.createObjectURL(f));
            }
            viewImgs.value = $scope.entity.content_images.toString();
        }
    }

    // load first to get contents data
    $http.get(getLink(serverIO, path)).then(resp => {
        $scope.data = resp.data;
        $scope.entity = $scope.entity ? $scope.entity :  $scope.data[0];
    }).catch(error => {
        console.error('get error: ' + error);
    });

    // get categories
    $http.get(getLink(serverIO, 'rest/categories')).then(resp => {
        $scope.categories = resp.data; // get categories
    }).catch(error => {
        console.error('get error: ' + error);
    });

});

function getImage(name, director) {
    if(name) return name.startsWith('http') ? name : getLink(serverIO, director, name);
    return getLink(serverIO, director, name);
}