
const app = angular.module('app', ['ngRoute']);
const serverIO = "http://localhost:8080"; // protocol://host:port
const toast = new bootstrap.Toast(liveToast);

app.controller('control', function ($scope, $http) {

    $scope.read = function (data, entityName) {
        let id="";
        if(data) $scope[entityName] = angular.copy(data);
        if(typeof data.id != 'object') id = data.id;
        else for(i of Object.keys(data.id)) id += ` ${data.id[i]}`;
        
        $scope.mes = {b:'bg-info', t:`Lấy dữ liệu ${id}`, c:`Đọc thông tin của: ${id}`}
        toast.show();
    }
    
    $scope.setTime = function(entity, column){
		entity[column] = new Date(entity[column]);
	}
    
    // _______________________________________________________________________ CRUD DATA
    $scope.get = function (get, toSet) {
        if(!get) return;
        let name = get.substring(get.lastIndexOf('/')+1), isHTTP = false;
        if(get.lastIndexOf('/') == get.length-1) get = get.substring(0,get.length-1);
        let path = (isHTTP = get.startsWith('http')) ? get.replace('0/data', '0/rest/dirmap') : `${serverIO}/rest/${get}`;
        let title = isHTTP ? `Tải thư mục: ${name}` : `Tải dữ liệu: ${get}`;

        $http.get(getLink(path)).then(result => {
            if(get) $scope[toSet ? toSet : get] = result.status==200 ? result.data : [];
            var data = $scope[toSet ? toSet : get];
            $scope.mes = {b:'bg-primary', t:title,
                c:`Đã tải ${data.length ? data.length : data.files.length} dữ liệu`
            }
        }).catch(err => {
            console.error('get data error', err);
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi tải dữ liệu!`}
        });
        toast.show();
    }

    $scope.post = function (entity, arrName, ...otherPaths) { // post to save and return data saved
        let i = getIndex('id', entity.id, $scope[arrName]);
        let path = getLink(serverIO,'rest',arrName, otherPaths);
        let title = `Thêm mới dữ liệu ${arrName}`;

        if(i < 0) $http.post(path, entity).then(result => {
            if(result.status == 200) {
                $scope[arrName].unshift(entity = result.data);
                console.log(`${entity.id} has been created.`);
                $scope.mes = {b:'bg-primary', t:title, c:`Đã thêm mới dữ liệu`}
            } else {
                console.warn(`status is ${result.status} cannot create!`);
                $scope.mes = {b:'bg-warning', t:title, c:`Thêm dữ liệu không thành công!`}
            }
        }).catch(err => {
            console.error(err.data 
                ? `error's ${err.data.message} cannot create!` 
                : 'create error', err
            );
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi thêm dữ liệu ${err.data ? err.data.message : err.status}`}
        }); else $scope.mes = {b:'bg-warning', t:title, c:`${entity.id} đã tồn tại, không thể thêm`}
        toast.show();
    };

    $scope.put = function (entity, arrName) { // put to update and return data updated
        if (!entity) return; // entity is null don't save
        let i = getIndex('id', entity.id, $scope[arrName]);
        let path = getLink(serverIO,'rest',arrName);
        let title = `Cập nhật dữ liệu ${arrName}`;
        
        if (-1 < i) $http.put(path, entity).then(result => {
            if(result.status == 200) {
                $scope[arrName][i] = entity = result.data;
                console.log(`Update ${entity.id} successfully.`);
                $scope.mes = {b:'bg-primary', t:title, c:`Đã cập nhật dữ liệu`}
            } else {
                console.warn(`status is ${result.status} cannot update!`);
                $scope.mes = {b:'bg-warning', t:title, c:`Cập nhật thông tin không thành công!`}
            }
        }).catch(err => {
            console.error(err.data 
                ? `error's ${err.data.message} cannot create!` 
                : 'update error', err
            );
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi cập nhật dữ liệu ${err.data ? err.data.message : err.status}`}
        }); else $scope.mes = {b:'bg-warning', t:title, c:`${entity.id} không tồn tại, không thể cập nhật thông tin`}
        toast.show();
    };

    $scope.deletesByColumn = function(value, columnName, arrName) {
        let path = getLink(serverIO, 'rest', arrName, `${columnName}?${columnName}=${value}`)
        let title = `Xóa dữ liệu ${value}`;

        $http.delete(path).then(r => {
            if(r.status == 200) {
                let count = 0;
                for (let i = 0; i < $scope[arrName].length; i++) {
                    let x = $scope[arrName][i][columnName];
                    if(x == value) {$scope[arrName].splice(i--,1); count++}
                }
                $scope.mes = {b:'bg-success', t:title, c:`Đã xóa ${count} dữ liệu chứa ${value}`}
            } else $scope.mes = {b:'bg-warn', t:title, c:`Xóa dữ liệu ${value} không thành công!`}
        }).catch(e => {
            console.error(e.data ? e.data.message : e);
            $scope.mes = {b:'bg-danger', t:title, c: `Lỗi xóa thông tin: ${e.data ? e.data.message : e.status}`}
        }); 
        toast.show();
    }

    $scope.delete = function (key, arrName) { // delete data by key("id") and *not return.
        if (!key) return; // key undefined, definitely does not exist.
        let isObject = typeof key == "object";
        let i = getIndex(isObject ? null : 'id', key, $scope[arrName]);
        let path = getLink(serverIO,'rest',arrName,isObject ? `id?${objectPath(key.id)}`: key);
        let title =`Xóa dữ liệu ${arrName}`;
        
        if (-1 < i) $http.delete(path, isObject ? key.id : null).then(result => {
            if(result.status == 200) {
                $scope[arrName].splice(i,1);
                console.log(`${key} has been deleted.`);
                $scope.mes = {b:'bg-primary', t:title, c:`Đã xóa dữ liệu có mã: ${isObject ? JSON.stringify(key.id) : key}`}
            } else {
                console.warn(`status is ${result.status} cannot delete!`);
                $scope.mes = {b:'bg-warning', t:title, c:`Không thể xóa: ${isObject ? JSON.stringify(key.id) : key}`}
            }
        }).catch(err => {
            console.error(err.data 
                ? `error's ${err.data.message} cannot delete!` 
                : 'delete error', err
            );
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi xóa dữ liệu ${err.data ? 
                err.data.status == 500 ? `mã ${key} đang liên kết, hiện không thể xóa` : 
                `Lỗi thực hiện: ${err.data.message}` : err.status}`}
        }); else $scope.mes = {b:'bg-warning', t:title, c:`${isObject ? JSON.stringify(key.id) : key} không tồn tại, không thể xóa thông tin`}
        toast.show();
    };

    // _______________________________________________________________________ FILES
    $scope.isFile = function(str) {
        if(str) return str.lastIndexOf('.') > -1;
    }
    
    $scope.breadcrumb = function (str) {
        if(!str) return;
        let cut = '0/data/';
        let uri = str.substring(str.lastIndexOf(cut)+cut.length, str.length-1);
        var arr = [`${serverIO}/data`, ...uri.split('/')];
        return arr;
    }

    $scope.postDirs = function (path, uri) {
        let title = `Tạo mới thư mục ${uri}`;

        $http.post(pathSP(
            path.endsWith('/') ? path.substring(path.length-1,-1) : path, uri
        )).then(resp => {
            if(resp.status == 200) {
                $scope.mapFile.files.unshift(resp.data);
                $scope.mes = {b:'bg-primary', t:title, c:`Đã tạo thư mục ${uri}`}
            } else $scope.mes = {b:'bg-warning', t:title, c:`Tạo mới thư mục ${uri} không thành công`}
        }).catch(err => {
            console.error('Create folder error', err.data ? err.data.message : err);
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi tạo thư mục: ${err.data ? err.data.message : err.status}`}
        });
        toast.show();
    }

    $scope.postFiles = function (path) {
        var data = new FormData(); // prepare files data
        let title = "Tạo mới tệp";
        for(file of formFile.files) data.append('files', file);

        $http.post( // post data to save
            pathSP(path.endsWith('/') ? path.substring(path.length-1,-1) : path),
            data, {TransformRequest: angular.identity,
                headers: {'content-Type': undefined}
            }
        ).then(resp => {
            if(resp.status == 200) {
                $scope.mapFile.files.unshift(...resp.data);
                for(t of [,'file']) formFile.type = t;
                prepareFile(showImage, formFile);
                $scope.mes = {b:'bg-primary', t:title, c:`đã lưu thành công các tệp\n${resp.data.toString().replaceAll(',','\n')}`}
            } else $scope.mes = {b:'bg-warning', t:title, c:`Lưu ${formFile.files.length} tệp không thành công`}
        }).catch(err => {
            console.error('Files upload error', err.data ? err.data.message : err);
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi tải lên tệp dữ liệu ${err.data ? err.data.message : err.status}`}
        });
        toast.show();
    }

    $scope.deleteFile = function(name) {
        let fileName = name.substring(name.lastIndexOf('/')+1);
        let i = getIndex(null, fileName, $scope.mapFile.files);
        let path = pathSP(name);
        let title = `Xóa tệp ${fileName}`;
        path = path.endsWith('/') ? path.substring(path.length-1,-1): path;
        
        if(i>-1) $http.delete(path).then((resp) => {
            if(resp.status == 200) {
                $scope.mapFile.files.splice(i, 1);
                $scope.mes = {b:'bg-primary', t:title, c:`Đã xóa tệp ${fileName} thành công`}
            } else $scope.mes = {b:'bg-warning', t:title, c:`Xóa tệp ${fileName} không thành công`}
        }).catch(err => {
            console.error('Delete file error', err.data ? err.data.message : err);
            $scope.mes = {b:'bg-danger', t:title, c:`Lỗi xóa tệp ${err.data ? err.data.message : err.status}`}
        }); else $scope.mes = {b:'bg-warning', t:title, c:`${fileName} không tồn tại, không thể xóa tệp`}
        toast.show(); 
    }

    // EX: (http://localhost:8080/data/images/any/a/...,"any") => http://localhost:8080/rest/dirmap/images
    $scope.pathURL = function(str, cut){
        str = str.substring(0, str.lastIndexOf(cut));
        str = str.replace('0/data', '0/rest/dirmap')
        return str;
    }

    function pathSP(str, ...directories) { // replace and concat paths
        return getLink(str.replace('0/data', '0/rest/dir'), directories)
    }
});

function prepareFile(toShow, input) {
    toShow.innerHTML = null;
    for(e of input.files) {
       let div = document.createElement('div');
       div.setAttribute('class', 'p-1 size-5 ar16x9 position-relative');
       div.innerHTML = `<img class="fit-img o-fit-cover" src="${URL.createObjectURL(e)}" alt="${e.name}"><label class="fit-label-img bg-dark bg-opacity-50 text-center text-light">${e.name}</label>`;
       toShow.appendChild(div);
    }
}

function objectPath(key) {
    var path = "";
    for(i of Object.keys(key)) path += `${i}=${key[i]}&`
    return path.substring(0,path.lastIndexOf('&'))
}