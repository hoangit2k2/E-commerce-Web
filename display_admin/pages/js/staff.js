
const app = angular.module('app', []);
const serverIO = "http://localhost:8080"; // protocol://host:port
const path = "rest/staffs"; // get all entities
const defaultMes = `<span class="text-muted">#double click the row to read</span>`;
var defaultImg = "default.png"; // default image

app.controller('control', function ($scope, $http) {
    message.innerHTML = defaultMes;

    function format(entity) {
        if (entity) entity = angular.copy(entity); else return;

        // format entity...
        if (prepareImg.files.length > 0) entity['image'] = prepareImg.files[0].name;
        return entity;
    }

    // IN CLIENT - STAFF
    $scope.read = function (data) {
        for(t of [,'file']) prepareImg.type = t;
        // string is type of username
        if(typeof data === 'string') {
            let i = getIndex('username', data, $scope.data);
            if (-1 < i) {
                $scope.entity = angular.copy($scope.data[i]);
                refresh(`<span class="text-success"><b>Read</b> <u>${data}</u> has role's <em>${$scope.entity.role ? 'admin' : 'staff'}</em></span>`);
            } else refresh(`<span class="text-danger"><b>Cannot read because</b> <u>${data}</u> does not exists</span>`);
        } else {
            $scope.entity = angular.copy(data);
            refresh(`<span class="text-success"><b>Read</b> <u>${data.username}</u> has role's <em>${data.role ? 'admin' : 'staff'}</em></span>`);
        }
    }

    $scope.getImage = function (name) { return getImage(name) };

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
        if (!entity) return; // entity is null don't save
        let i = getIndex('username', entity.username, $scope.data);

        if (i < 0) $http.post(getLink(serverIO, path), format(entity)).then(result => {
            if (result.status == 200) {
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
        if (!entity) return; // entity is null don't update
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

    $scope.pushFile = function (entity) {
        // pepare form data and link
        var formFiles = new FormData(); // <form></form>
        for (let f of prepareImg.files) {
            formFiles.append('files', f);
        } // param's named 'files' in post method on server
        let url = getLink(serverIO, 'rest/dir/images/staff');

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
});

function getImage(name) {
    if(name) return name.startsWith('http') ? name : getLink(serverIO, 'data/images/staff', name);
    return getLink(serverIO, 'data/images/staff', 'default.png');
}

function setImage(input, toSet){
    // set image
    toSet.src = input.files[0]
        ? URL.createObjectURL(input.files[0])
        : getImage();
}

function refresh(alert, entity) {
    message.innerHTML = alert;
}