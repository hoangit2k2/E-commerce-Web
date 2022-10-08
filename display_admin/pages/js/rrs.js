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

// _________________________________________________________________________________________________
// Dropdown
function drdActive(p) {
    var element = p.getElementsByClassName("dropdown-menu")[0];
    var cls = element.getAttribute("class");
    setClass(element, cls);
}


/** _______________________________________________
 * @param e element
 * @param cls class
 *  */
 function setClass(e, cls) {
    // e is element, cls is class's attribute
    // replace content of the class
    e.setAttribute("class",
        cls.search("show") > -1 ? cls.replace(" show", "") : (cls + " show")
    );
}