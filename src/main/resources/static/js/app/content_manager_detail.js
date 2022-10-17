const app = angular.module('app', []);
const serverIO = "http://127.0.0.1:8080"; // protocol://host:port
const path = "rest/contents"; // get all entities
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
        // getLink(serverIO, path, 'upview', entity.id) to upviews
        if(i > -1) $http.get(getLink(serverIO, path, entity.id)).then(result => {
            if(result.status == 200) $scope.entity = $scope.data[i] = result.data
        }).catch(error => console.error(`upviews ${entity.id} failed:`, error));

        $scope.mes = {b:'bg-info', t:`Xem chi tiết thông tin ${entity.subject}`, c:entity.content}
        toast.show();
    }

    $scope.all = function () {
        $scope.r_contents = [];
    }

    // get data and post to
    $scope.getTo = function(uri,to) {
        if(!to) return;
        $http.get(getLink(serverIO, path, uri)).then(
            resp => {
                if(resp.status == 200) len = ($scope[to] = resp.data).length;
                else console.warn(`status is ${resp.status}`);
                if($scope[to]) $scope.entity = $scope[to][0];
                $scope.mes = {b:'bg-info', t:`Lấy thông tin liên quan`, c: `Đã lấy tổng cộng ${len} dữ liệu`}
            }
        ).catch(error => console.error('get error: ' + error));
        toast.show();
    }

    $scope.toggleActive = function() {
        $scope.entity.active = !$scope.entity.active;
        // update array data
        if($scope.data) {
            let i = getIndex('id', $scope.entity.id, $scope.data)
            if(i >-1 ) $scope.data[i].active = $scope.entity.active;
        }
        // update array r_contents
        if($scope.r_contents) { 
            let i = getIndex('id', $scope.entity.id, $scope.r_contents)
            if(i >-1 ) $scope.r_contents[i].active = $scope.entity.active;
        }
        // update on server
        $http.put(getLink(serverIO, path), $scope.entity).then(
            r => $scope.entity = r.status==200 ? r.data : $scope.entity
        ).catch(err => console.error(e.data ? e.data.message : e));
    }

    $scope.closeByAccountId = function() {
        let username = $scope.entity.account.username;
        var arr = $scope.arr.filter(x => {
            // only update content is active and content of account
            if(x.account.username == username && x.active) {
                x.active = !x.active;
                $http.put(getLink(serverIO, path), x).then(
                    r => x = r.status==200 ? r.data : x
                ).catch(err => console.error(e.data ? e.data.message : e));
            }
        });
    }       

    $scope.delete = function() {
        if(!$scope.entity) return
        else if(confirm(`Xác nhận xóa nội dung "${$scope.entity.subject}"?`)) {
            // delete on server before remove element in client
            $http.delete(getLink(serverIO,path,$scope.entity.id)).then(r => {
                if(r.status == 200) {
	                let i = 0; // Delete in array named data
                    if($scope.data) {
                        i = getIndex('id', $scope.entity.id, $scope.data);
                        if(i > -1) $scope.data.splice(i,1);
                    }
                    // Delete in array named r_contents
                    if($scope.r_contents) {
                        i = getIndex('id', $scope.entity.id, $scope.r_contents);
                        if(i > -1) $scope.r_contents.splice(i,1);
                    }
                    let len = $scope.arr.length-1;
                    $scope.entity = i<len ? $scope.arr[i++] : $scope.arr[len]
                }else console.warn(`Status is ${r.status}`, r);
            }).catch(e => console.log(e.data ? e.data.message : e))
        }
    }

    // prepare image
    $scope.getImage = function (name, director) {
        return getImage(name, director ? `data/images/${director}` : 'data/images/content')
    };

    // document has element id="viewImgs"
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
    $scope.getTo(null, 'data');

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