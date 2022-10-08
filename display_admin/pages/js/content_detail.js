const app = angular.module('app', []);
const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/contents"; // get all entities\


/*
    ____________________IDs
    entity > form
    fields > div
    prepareImg > input[file]
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

app.directive('ngEnter', function () {
    return function (scope,element,attrs) {
        element.bind("keydown keypress", function (e) {
            if (e.which === 13) { // 13 == "enter key"
                scope.$apply(scope.$eval(attrs.ngEnter));
            }
        });
    };
});

app.controller('control', function ($scope, $http) {
    // prepare
    function format(entity) {
        if(!entity) return {
            id: Math.round((Math.random()-1)*1000),
            regTime: new Date(),
            views: 0, 
            categories:[],
            content_images: []
        };

        // modifiy type of column to required value
        entity['regTime'] = new Date(entity.regTime);
        if(entity.content_images) if(entity.content_images.length > 0) {
            for (let j = i = 0; i < entity.content_images.length; i++) {
                // get the name of URL-Local to post data
                if(entity.content_images[i].startsWith('blob')) 
                    entity.content_images[i] = prepareImg.files[j++].name;
            }
        }

        return entity;
    }

    $scope.random = function() {
        $scope.entity.id = Math.round((Math.random()-1)*1000);
    }

    $scope.isFile = function(str) {
        if(str) return str.lastIndexOf('.') > -1;
    }

    // categories
    $scope.hasAny = function (entity, array) {
        return !entity || !array ? false : getIndex(undefined, entity.id, array) > -1;
    }

    $scope.setAny = function (entity, array) {
        let i = getIndex(undefined, entity.id, array);
        if(!array) array = []; // i == -1 && new Array();
        if(!document.getElementById(entity.id).checked) array.push(entity.id);
        else if (-1<i) array.splice(i,1);
    }

    // read in client
    $scope.read = function (data) {
        for(t of [,'file']) prepareImg.type = t;
        // number is type of id
        if(typeof data === 'number') {
            let i = getIndex('id', data, $scope.data);
            if (-1 < i) {
                $scope.entity = format(angular.copy($scope.data[i]));
                refresh(`<span class="text-success"><b>Read</b> <u>${data}</u> has subject's <em>${$scope.entity.subject}</em></span>`);
            } else refresh(`<span class="text-danger"><b>Cannot read because</b> <u>${data}</u> does not exists</span>`);
        } else {
            $scope.entity = format(angular.copy(data));
            refresh(`<span class="text-success"><b>Read</b> <u>${data?data.id:data}</u> has subject's <em>${$scope.entity.subject}</em></span>`);
        }
    }

    // get account
    $scope.read_A = function(username) {
        if(!username) return
        let i = getIndex('username',username,$scope.accounts);
        if(-1<i) return $scope.accounts[i];
        return
    }

    // ________________________________________________________________________ CRUD
    $scope.get = function (key) {
        $http.get(getLink(serverIO, path, key ? key : 'active?a')).then(resp => {
            $scope.data = resp.data;
            $scope.entity = format($scope.entity);
            refresh(`<span class="text-success"><b>Data received successfully</b></span>`);
        }).catch(error => {
            refresh(`<span class="text-danger"><b>Getting data failed</b></span>`);
            console.error('get error: ' + error);
        });
    }

    $scope.getTo = function(uri,to) {
        if(!to || !uri) return;
        $http.get(getLink(serverIO, path, uri)).then(
            resp => console.log($scope[to] = resp.data)
        ).catch(
            error => console.error('get error: ' + error)
        );
    }

    $scope.post = function (entity) {
        $http.post(getLink(serverIO, path), format(entity)).then(result => {
            if (result.status == 200) {
                if (prepareImg.files.length > 0) $scope.pushFile(entity); // add image if input.files already;
                $scope.entity = format(angular.copy(result.data)); // new form inputs
                $scope.data.push(result.data); // add in client or reload to $http.get();               
                refresh(`<span class="text-success"><b>saved successfully</b></span>`);
            } refresh(`<span class="text-success"><b>saved successfully</b></span>`);
        }).catch(error => {
            refresh(`<span class="text-danger"><b>data failed to save</b></span>`);
            console.error('save failed:', error);
        });
    }

    $scope.put = function (entity) {
        if (!entity) return; // entity is null don't update
        let i = getIndex('id', entity.id, $scope.data);

        if (-1 < i) $http.put(getLink(serverIO, path), format(entity)).then(result => {
            if (result.status == 200) {
                if (prepareImg.files.length > 0) $scope.pushFile(entity); // add image if input.files already;
                $scope.entity = format(angular.copy(result.data)); // new form inputs
                $scope.data[i] = result.data; // update in client or reload to $http.get();
                refresh(`<span class="text-success"><b>updated successfully</b></span>`);
            }
        }).catch(error => {
            refresh(`<span class="text-danger"><b>data failed to update</b></span>`);
            console.error('update failed:', error);
        }); else refresh(`<span class="text-danger"><b>${entity.id} does not exists!</b></span>`);
    }

    $scope.delete = function (key) {
        if (!key) return; // key undefined, definitely does not exist.
        let i = getIndex('id', key, $scope.data);
        
        if(-1 < i) $http.delete(getLink(serverIO, path, key)).then(result => {
            if(result.status == 200) {
                $scope.data.splice(i,1); // delete in array data at the client
                refresh(`<span class="text-secondary"><b>delete successfully</b></span>`);
            }
        }).catch(error => {
            refresh(`<span class="text-danger"><b>failed to delete</b></span>`);
            console.error('delete failed:', error);
        });
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

    $scope.pushFile = function (entity) {
        // prepare form data and url to post
        var formFiles = new FormData(); // <form></form>
        for (let f of prepareImg.files) {
            formFiles.append('files', f);
        } // param's named 'files' in post method on server
        let url = getLink(serverIO, 'rest/dir/images/content');

        // post to save file
        $http.post(url, formFiles, {
            TransformRequest: angular.identity,
            headers: { 'content-Type': undefined }
        }).then((resp) => {
            if (resp.status == 200) {
                for(t of [,'file']) prepareImg.type = t;
            } else console.error(`file failed to save - status: ${resp.status}`);
        }).catch((err) => {
            if (err.status == 500) refresh(`<span class="text-danger"><b>${err.data.message}</b></span>`);
            else console.error(err);
        });
    }

    // load first to get data
    $scope.get(); // get contents

    $http.get(getLink(serverIO, 'rest/categories')).then(resp => {
        $scope.categories = resp.data; // get categories
    }).catch(error => {
        console.error('get error: ' + error);
    });

});

function refresh(alert) {
    message.innerHTML = alert;
}

function getImage(name, director) {
    if(name) return name.startsWith('http') ? name : getLink(serverIO, director, name);
    return getLink(serverIO, director, name);
}