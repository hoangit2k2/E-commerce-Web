
const app = angular.module('app', []);
const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/accounts"; // get all entities
const defaultMes = `<span class="text-muted">#double click the row to read</span>`;
var defaultImg = "default.png"; // default image

app.filter('fil2Many', function() {
    return function (array, columns) {
        try {
            var column = Object.keys(columns);
            return array.filter(x => columns[column].includes(x[column]));
        } catch (error) {return};
    }
});

app.controller('control', function ($scope, $http) {
    message.innerHTML = defaultMes;

    function format(entity) {
        if (entity) entity = angular.copy(entity); else return;

        // format entity...
        if (prepareImg.files.length > 0) entity['image'] = prepareImg.files[0].name;
        entity['regDate'] = entity.regDate ? new Date(entity.regDate) : new Date();
        return entity;
    }

    // IN CLIENT - ACCOUNTS
    $scope.read = function (data) {
        for(t of [,'file']) prepareImg.type = t;
        // string is type of username
        if(typeof data === 'string') {
            let i = getIndex('username', data, $scope.data);
            if (-1 < i) {
                $scope.entity = format(angular.copy($scope.data[i]));
                refresh(`<span class="text-success"><b>Read</b> <u>${data}</u> has name's <em>${$scope.entity.name}</em></span>`);
            } else refresh(`<span class="text-danger"><b>Cannot read because</b> <u>${data}</u> does not exists</span>`);
        } else {
            $scope.entity = format(angular.copy(data));
            refresh(`<span class="text-success"><b>Read</b> <u>${data.username}</u> has name's <em>${data.name}</em></span>`);
        }
    }

    $scope.getImage = function (name, director) { return getImage(name, director) };

    // SEND CRUD REQUEST TO SERVER
    $scope.get = function (key) { // get all entities or get one entity
        $http.get(getLink(serverIO, path, key)).then(result => {
            $scope.data = result.data;
            refresh(`<span class="text-success"><b>Data received successfully</b></span>`);
        }).catch(error => {
            refresh(`<span class="text-danger"><b>Getting data failed</b></span>`);
            console.error('get error: ' + error);
        });
    };

    $scope.post = function (entity) { // post to save and return data saved
        if (entity) entity['likes'] = [];
        let i = getIndex('username', entity.username, $scope.data);

        if (i < 0) $http.post(getLink(serverIO, path), format(entity)).then(result => {
            if (result.status == 200) {
                $scope.entity = format(angular.copy(result.data));
                $scope.data.push(result.data); // add in client or reload to $http.get();
                if (prepareImg.files.length > 0) $scope.pushFile(result.data); // add image if input.files already;
                refresh(`<span class="text-success"><b>saved successfully</b></span>`);
            } refresh(`<span class="text-success"><b>saved successfully</b></span>`);
        }).catch(error => {
            refresh(`<span class="text-danger"><b>data failed to save</b></span>`);
            console.error('save failed:', error);
        }); else refresh(`<span class="text-danger"><b>${entity.username} already exists!</b></span>`);
    };

    $scope.put = function (entity) { // put to update and return data updated
        if (!entity) return; // entity is null don't save
        let i = getIndex('username', entity.username, $scope.data);

        if (-1 < i) $http.put(getLink(serverIO, path), format(entity)).then(result => {
            if (result.status == 200) {
                $scope.data[i] = result.data; // update in client or reload to $http.get();
                if (prepareImg.files.length > 0) $scope.pushFile(result.data); // add image if input.files already;
                refresh(`<span class="text-success"><b>updated successfully</b></span>`);
            }
        }).catch(error => {
            refresh(`<span class="text-danger"><b>data failed to update</b></span>`);
            console.error('update failed:', error);
        }); else refresh(`<span class="text-danger"><b>${entity.username} does not exists!</b></span>`);
    };

    $scope.delete = function (key) { // delete data by key("id") and #not return.
        if (!key) return; // key undefined, definitely does not exist.
        let i = getIndex('username', key, $scope.data);
        
        if(-1 < i) $http.delete(getLink(serverIO, path, key)).then(result => {
            if(result.status == 200) {
                $scope.data.splice(i,1); // delete in array data at the client
                refresh(`<span class="text-secondary"><b>delete successfully</b></span>`);
            }
        }).catch(error => {
            refresh(`<span class="text-danger"><b>failed to delete</b></span>`);
            console.error('delete failed:', error);
        });
    };

    $scope.pushFile = function (account) {
        // pepare form data and link
        var formFiles = new FormData(); // <form></form>
        for (let f of prepareImg.files) {
            formFiles.append('files', f);
        } // param's named 'files' in post method on server
        let url = getLink(serverIO, 'rest/dir/images/account');

        // post to save file
        $http.post(url, formFiles, {
            TransformRequest: angular.identity,
            headers: { 'content-Type': undefined }
        }).then((resp) => {
            if (resp.status == 200) console.log('file saved');
            else console.error(`file failed to save - status: ${resp.status}`);
        }).catch((err) => {
            if (err.status == 500) refresh(`<span class="text-danger"><b>${err.data.message}</b></span>`);
            else console.error(err);
        });
    }

    $scope.get(); // first get data
    $http.get(getLink(serverIO, 'rest/contents')).then(resp => {
        $scope.contents = resp.data;
    }).catch(error => {
        console.error('get error: ' + error);
    });
});

function getImage(name, director) {
    if(name) return name.startsWith('http') ? name : getLink(serverIO, director ? director : 'data/images/account', name);
    return getLink(serverIO, director ? director : 'data/images/account', 'default.png');
}

function setImage(input, toSet) {
    // set image
    toSet.src = input.files[0]
        ? URL.createObjectURL(input.files[0])
        : getImage();
}

function refresh(alert, entity) {
    message.innerHTML = alert;
    if (!entity) entity = { username: undefined, regDate: new Date() };
}