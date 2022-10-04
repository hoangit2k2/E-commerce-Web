/**
* @param host is protocol://domain:port
* @param paths add at the end host
* @returns url connect to server
*/
function getLink(host, ...paths) {
    return !paths instanceof String ? host.concat('/', paths) : host.concat('/', paths).replaceAll(',', '/')
}
 
 /**
 * @param column of entity to find in array
 * @param value of column
 * @param array to get index
 */
function getIndex(column, value, array) {
    if(!value || !array) return -1;
    
    if(column) {
        for (let i = 0; i < array.length; i++) 
            if(array[i][column] == value) return i;
    } else {
        for (let i = 0; i < array.length; i++) 
            if(array[i] == value) return i;
    } return -1;
}

/**
* @param input to set type text || pass
* @param control's eye icon
*/
function showPass(input, control) {
    if(input.type == 'password') {
        input.type = 'text';
        control.setAttribute('class', 'fa-solid fa-eye')
    } else {
        input.type = 'password';
        control.setAttribute('class', 'fa-solid fa-eye-slash')
    }                              
}

function prepareFile(setTo, input) {
   if (setTo != null) {
        while (setTo.lastChild) setTo.removeChild(setTo.firstChild);

        total = 0;
        for (file of input.files) {
            total+=file.size;

            let div = document.createElement('div');
            div.setAttribute('class', 'size-5')
            div.innerHTML = `<img src="${URL.createObjectURL(file)}" class="fit-img o-fit-cover" alt="${file.name}">`
            setTo.appendChild(div);
        }
    }
}